package com.sammy.malum.common.blockentity.storage;

import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.client.ParticleRegistry;
import com.sammy.malum.core.setup.content.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.malum.core.helper.RenderHelper;
import com.sammy.malum.core.systems.rendering.particle.ParticleBuilders;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SpiritJarBlockEntity extends SimpleBlockEntity {

    public SpiritJarBlockEntity(BlockEntityType<? extends SpiritJarBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SpiritJarBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.SPIRIT_JAR.get(), pos, state);
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
                if (!player.level.isClientSide) {
                    player.setItemInHand(hand, ItemStack.EMPTY);
                    BlockHelper.updateAndNotifyState(level, worldPosition);
                }
                else
                {
                    spawnUseParticles(level, worldPosition, type);
                }
                return InteractionResult.SUCCESS;
            }
        } else if (type != null) {
            int max = player.isShiftKeyDown() ? 64 : 1;
            int count = Math.min(this.count, max);
            if (!player.level.isClientSide) {
                this.count -= count;
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(type.getSplinterItem(), count));
                if (this.count == 0) {
                    type = null;
                }
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
            else
            {
                spawnUseParticles(level, worldPosition, type);
            }
            return InteractionResult.SUCCESS;
        }
        return super.onUse(player, hand);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onPlace(LivingEntity placer, ItemStack stack) {
        if (stack.hasTag())
        {
            load(stack.getTag());
        }
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (type != null) {
            compound.putString("spirit", type.identifier);
        }
        compound.putInt("count", count);
    }

    @Override
    public void load(@NotNull CompoundTag compound) {
        if (compound.contains("spirit")) {
            type = SpiritHelper.getSpiritType(compound.getString("spirit"));
        }
        else
        {
            type = null;
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
        ParticleBuilders.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.15f, 0f)
                .setLifetime(20)
                .setScale(0.3f, 0)
                .setSpin(0.2f)
                .randomMotion(0.02f)
                .randomOffset(0.1f, 0.1f)
                .setColor(color, color.darker())
                .enableNoClip()
                .repeat(level, pos.getX()+0.5f, pos.getY()+0.5f + Math.sin(level.getGameTime() / 20f) * 0.2f, pos.getZ()+0.5f, 10);
    }
}