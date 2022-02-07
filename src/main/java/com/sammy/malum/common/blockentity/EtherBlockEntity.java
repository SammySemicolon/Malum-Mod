package com.sammy.malum.common.blockentity;

import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.common.block.ether.EtherTorchBlock;
import com.sammy.malum.common.block.ether.WallEtherTorchBlock;
import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.core.helper.ColorHelper;
import com.sammy.malum.core.setup.ParticleRegistry;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

import java.awt.*;

public class EtherBlockEntity extends SimpleBlockEntity {
    public Color firstColor;
    public Color secondColor;
    public boolean updateData = true;
    public float xOffset;
    public float yOffset;
    public float zOffset;
    public int lifetimeOffset;
    public float scaleMultiplier;
    public EtherBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ETHER.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound) {
        if (compound.contains("firstColor")) {
            firstColor = ColorHelper.getColor(compound.getInt("firstColor"));
        }
        if (compound.contains("secondColor")) {
            secondColor = ColorHelper.getColor(compound.getInt("secondColor"));
        }
        super.load(compound);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (firstColor != null) {
            compound.putInt("firstColor", firstColor.getRGB());
        }
        if (secondColor != null) {
            compound.putInt("secondColor", secondColor.getRGB());
        }
        super.saveAdditional(compound);
    }

    @Override
    public void onPlace(LivingEntity placer, ItemStack stack) {
        AbstractEtherItem item = (AbstractEtherItem) stack.getItem();
        firstColor = ColorHelper.getColor(item.getFirstColor(stack));
        secondColor = ColorHelper.getColor(item.getSecondColor(stack));
        setChanged();
    }

    @Override
    public ItemStack onClone(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = state.getBlock().asItem().getDefaultInstance();
        AbstractEtherItem etherItem = (AbstractEtherItem) stack.getItem();
        if (firstColor != null) {
            etherItem.setFirstColor(stack, firstColor.getRGB());
        }
        if (secondColor != null) {
            etherItem.setSecondColor(stack, secondColor.getRGB());
        }
        setChanged();
        return super.onClone(state, target, level, pos, player);
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (firstColor == null || secondColor == null) {
                return;
            }
            if (updateData)
            {
                updateData = false;
                if (getBlockState().getBlock() instanceof WallEtherTorchBlock) {
                    Direction direction = getBlockState().getValue(WallTorchBlock.FACING);
                    xOffset = direction.getNormal().getX() * -0.28f;
                    yOffset = 0.2f;
                    zOffset = direction.getNormal().getZ() * -0.28f;
                    lifetimeOffset = -6;
                }

                if (getBlockState().getBlock() instanceof EtherTorchBlock) {
                    lifetimeOffset = -4;
                }
                if (getBlockState().getBlock() instanceof EtherBrazierBlock) {
                    yOffset = -0.2f;
                    lifetimeOffset = -2;
                    scaleMultiplier = 1.25f;
                }
            }
            Color firstColor = ColorHelper.darker(this.firstColor, 1);
            Color secondColor = ColorHelper.brighter(this.secondColor, 1);

            double x = worldPosition.getX() + 0.5 + xOffset;
            double y = worldPosition.getY() + 0.6 + yOffset;
            double z = worldPosition.getZ() + 0.5 + zOffset;
            int lifeTime = 14 + level.random.nextInt(4) - lifetimeOffset;
            float scale = 0.17f + level.random.nextFloat() * 0.03f * scaleMultiplier;
            float velocity = 0.04f + level.random.nextFloat() * 0.02f;
            RenderUtilities.create(ParticleRegistry.SPARKLE_PARTICLE)
                    .setScale(scale * 2, 0)
                    .setLifetime(lifeTime)
                    .setAlpha(0.2f)
                    .setColor(firstColor, secondColor)
                    .setColorCurveMultiplier(1.5f)
                    .spawn(level, x, y, z);

            RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                    .setScale(scale, 0)
                    .setLifetime(lifeTime)
                    .setAlpha(0.9f, 0.5f)
                    .setColor(firstColor, secondColor)
                    .setColorCurveMultiplier(2f)
                    .addVelocity(0, velocity, 0)
                    .setSpin(level.random.nextFloat() * 0.5f)
                    .spawn(level, x, y, z);

            if (level.getGameTime() % 2L == 0 && level.random.nextFloat() < 0.25f) {
                y += 0.15f;
                RenderUtilities.create(ParticleRegistry.SPIRIT_FLAME_PARTICLE)
                        .setScale(0.75f, 0)
                        .setColor(firstColor, secondColor)
                        .setColorCurveMultiplier(3f)
                        .randomOffset(0.1f, 0.15f)
                        .addVelocity(0, 0.03f, 0)
                        .spawn(level, x, y, z);

                RenderUtilities.create(ParticleRegistry.SPIRIT_FLAME_PARTICLE)
                        .setScale(0.5f, 0)
                        .setColor(firstColor, secondColor)
                        .setColorCurveMultiplier(3f)
                        .randomOffset(0.1f, 0.15f)
                        .addVelocity(0, velocity, 0)
                        .spawn(level, x, y, z);
            }
        }
    }
}