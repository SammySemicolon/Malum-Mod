package com.sammy.malum.common.blockentity.item_storage;

import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.registry.ParticleRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.helper.SpiritHelper;
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
        super(BlockEntityRegistry.SPIRIT_JAR.get(), pos, state);
    }

    public MalumSpiritType type;
    public int count;

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.getItem() instanceof MalumSpiritItem spiritSplinterItem) {
            if (type == null || type.equals(spiritSplinterItem.type))
            {
                type = spiritSplinterItem.type;
                count += heldItem.getCount();
                player.setItemInHand(hand, ItemStack.EMPTY);
                spawnUseParticles(level, worldPosition, type);
                BlockHelper.updateAndNotifyState(level, worldPosition);
                return InteractionResult.SUCCESS;
            }
        } else if (type != null) {
            int max = player.isShiftKeyDown() ? 64 : 1;
            int count = Math.min(this.count, max);
            this.count -= count;
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(type.getSplinterItem(), count));
            spawnUseParticles(level, worldPosition, type);
            if (this.count == 0) {
                type = null;
            }
            BlockHelper.updateAndNotifyState(level, worldPosition);
            return InteractionResult.SUCCESS;
        }
        return super.onUse(player, hand);
    }

    @Override
    public void onBreak() {
        while (count > 0)
        {
            int stackCount = Math.min(count, 64);
            level.addFreshEntity(new ItemEntity(level,worldPosition.getX()+0.5f,worldPosition.getY()+0.5f,worldPosition.getZ()+0.5f,new ItemStack(type.getSplinterItem(), stackCount)));
            count -= stackCount;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (type != null) {
            compound.putString("spirit", type.identifier);
        }
        if (count != 0) {
            compound.putInt("count", count);
        }
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        if (compound.contains("spirit")) {
            type = SpiritHelper.getSpiritType(compound.getString("spirit"));
        }
        count = compound.getInt("count");
        super.load(compound);
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (type != null) {
                double x = getBlockPos().getX() + 0.5f;
                double y = getBlockPos().getY() + 0.5f + Math.sin(level.getGameTime() / 20f) * 0.2f;
                double z = getBlockPos().getZ() + 0.5f;
                SpiritHelper.spawnSpiritParticles(level, x, y, z, type.color, type.endColor);
            }
        }
    }

    public void spawnUseParticles(Level level, BlockPos pos, MalumSpiritType type) {
        Color color = type.color;
        RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.4f, 0f)
                .setLifetime(20)
                .setScale(0.3f, 0)
                .setSpin(0.2f)
                .randomOffset(0.1f, 0.1f)
                .setColor(color, color.darker())
                .enableNoClip()
                .repeat(level, pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f, 10);
    }
}