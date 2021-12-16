package com.sammy.malum.common.tile;

import com.sammy.malum.common.item.misc.MalumSpiritItem;
import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;

import java.awt.*;

public class SpiritJarTileEntity extends SimpleBlockEntity {
    public SpiritJarTileEntity(BlockPos pos, BlockState state) {
        super(TileEntityRegistry.SPIRIT_JAR_TILE_ENTITY.get(), pos, state);
    }

    public MalumSpiritType type;
    public int count;

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.getItem() instanceof MalumSpiritItem) {
            MalumSpiritItem spiritSplinterItem = (MalumSpiritItem) heldItem.getItem();
            if (type == null) {
                type = spiritSplinterItem.type;
                count = heldItem.getCount();
                player.setItemInHand(hand, ItemStack.EMPTY);
                useParticles(level, worldPosition, type);
                return InteractionResult.SUCCESS;
            } else if (type.equals(spiritSplinterItem.type)) {
                count += heldItem.getCount();
                player.setItemInHand(hand, ItemStack.EMPTY);
                useParticles(level, worldPosition, type);
                return InteractionResult.SUCCESS;
            }
        } else if (type != null) {
            int max = player.isShiftKeyDown() ? 64 : 1;
            int count = Math.min(this.count, max);
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(type.splinterItem(), count));
            this.count -= count;
            useParticles(level, worldPosition, type);
            if (this.count == 0) {
                type = null;
            }
            return InteractionResult.SUCCESS;
        }
        return super.onUse(player, hand);
    }

    @Override
    public void onBreak() {
        while (count > 0)
        {
            int stackCount = Math.min(count, 64);
            level.addFreshEntity(new ItemEntity(level,worldPosition.getX()+0.5f,worldPosition.getY()+0.5f,worldPosition.getZ()+0.5f,new ItemStack(type.splinterItem(), stackCount)));
            count -= stackCount;
        }
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        if (type != null) {
            compound.putString("spirit", type.identifier);
        }
        if (count != 0) {
            compound.putInt("count", count);
        }
        return super.save(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        if (compound.contains("spirit")) {
            type = SpiritHelper.getSpiritType(compound.getString("spirit"));
        }
        count = compound.getInt("count");
        super.load(compound);
    }

    public void tick() {
        if (level.isClientSide) {
            if (type != null) {
                double x = getBlockPos().getX() + 0.5f;
                double y = getBlockPos().getY() + 0.5f + Math.sin(level.getGameTime() / 20f) * 0.2f;
                double z = getBlockPos().getZ() + 0.5f;
                Color color = type.color;
                SpiritHelper.spawnSpiritParticles(level, x, y, z, color);
            }
        }
    }

    public void useParticles(Level level, BlockPos pos, MalumSpiritType type) {
        Color color = type.color;
        RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.4f, 0f)
                .setLifetime(20)
                .setScale(0.3f, 0)
                .setSpin(0.2f)
                .randomOffset(0.1f, 0.1f)
                .setColor(color, color.darker())
                .enableNoClip()
                .repeat(level, pos.getX(), pos.getY(), pos.getZ(), 10);
    }
}