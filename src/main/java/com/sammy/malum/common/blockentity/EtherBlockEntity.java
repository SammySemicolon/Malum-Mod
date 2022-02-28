package com.sammy.malum.common.blockentity;

import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.common.block.ether.EtherTorchBlock;
import com.sammy.malum.common.block.ether.EtherWallTorchBlock;
import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.core.helper.ColorHelper;
import com.sammy.malum.core.setup.client.ParticleRegistry;
import com.sammy.malum.core.setup.content.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.malum.core.helper.RenderHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

import java.awt.*;

public class EtherBlockEntity extends SimpleBlockEntity {
    public Color firstColor;
    public Color secondColor;

    public EtherBlockEntity(BlockEntityType<? extends EtherBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

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
        if (item.iridescent) {
            secondColor = ColorHelper.getColor(item.getSecondColor(stack));
        }
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
            if (firstColor == null) {
                return;
            }
            Color firstColor = ColorHelper.darker(this.firstColor, 1);
            Color secondColor = this.secondColor == null ? firstColor : ColorHelper.brighter(this.secondColor, 1);

            double x = worldPosition.getX() + 0.5;
            double y = worldPosition.getY() + 0.6;
            double z = worldPosition.getZ() + 0.5;
            int lifeTime = 14 + level.random.nextInt(4);
            float scale = 0.17f + level.random.nextFloat() * 0.03f;
            float velocity = 0.04f + level.random.nextFloat() * 0.02f;
            if (getBlockState().getBlock() instanceof EtherWallTorchBlock) {
                Direction direction = getBlockState().getValue(WallTorchBlock.FACING);
                x += direction.getNormal().getX() * -0.28f;
                y += 0.2f;
                z += direction.getNormal().getZ() * -0.28f;
                lifeTime -= 6;
            }

            if (getBlockState().getBlock() instanceof EtherTorchBlock) {
                lifeTime -= 4;
            }
            if (getBlockState().getBlock() instanceof EtherBrazierBlock) {
                y -= 0.2f;
                lifeTime -= 2;
                scale *= 1.25f;
            }
            RenderHelper.create(ParticleRegistry.SPARKLE_PARTICLE)
                    .setScale(scale * 2, 0)
                    .setLifetime(lifeTime)
                    .setAlpha(0.2f)
                    .setColor(firstColor, secondColor)
                    .setColorCurveMultiplier(1.5f)
                    .setAlphaCurveMultiplier(1.5f)
                    .enableNoClip()
                    .spawn(level, x, y, z);
            RenderHelper.create(ParticleRegistry.WISP_PARTICLE)
                    .setScale(scale, 0)
                    .setLifetime(lifeTime)
                    .setAlpha(0.9f, 0.5f)
                    .setColor(firstColor, secondColor)
                    .setColorCurveMultiplier(2f)
                    .setAlphaCurveMultiplier(2f)
                    .addVelocity(0, velocity, 0)
                    .setSpin(level.random.nextFloat() * 0.5f)
                    .enableNoClip()
                    .spawn(level, x, y, z);
            if (level.getGameTime() % 2L == 0 && level.random.nextFloat() < 0.25f) {
                y += 0.15f;
                RenderHelper.create(ParticleRegistry.SPIRIT_FLAME_PARTICLE)
                        .setScale(0.75f, 0)
                        .setColor(firstColor, secondColor)
                        .setColorCurveMultiplier(2f)
                        .setAlphaCurveMultiplier(3f)
                        .randomOffset(0.15f, 0.2f)
                        .addVelocity(0, 0.03f, 0)
                        .enableNoClip()
                        .spawn(level, x, y, z);
                RenderHelper.create(ParticleRegistry.SPIRIT_FLAME_PARTICLE)
                        .setScale(0.5f, 0)
                        .setColor(firstColor, secondColor)
                        .setColorCurveMultiplier(3f)
                        .setAlphaCurveMultiplier(3f)
                        .randomOffset(0.15f, 0.2f)
                        .addVelocity(0, velocity, 0)
                        .enableNoClip()
                        .spawn(level, x, y, z);
            }
        }
    }
}