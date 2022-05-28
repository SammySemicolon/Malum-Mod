package com.sammy.malum.common.blockentity;

import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.common.block.ether.EtherTorchBlock;
import com.sammy.malum.common.block.ether.EtherWallTorchBlock;
import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.common.item.ether.EtherItem;
import com.sammy.malum.core.setup.client.ParticleRegistry;
import com.sammy.malum.core.setup.content.block.BlockEntityRegistry;
import com.sammy.ortus.helpers.ColorHelper;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
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

public class EtherBlockEntity extends OrtusBlockEntity {
    public int firstColorRGB;
    public Color firstColor;
    public int secondColorRGB;
    public Color secondColor;

    public EtherBlockEntity(BlockEntityType<? extends EtherBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public EtherBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ETHER.get(), pos, state);
    }

    public void setFirstColor(int rgb) {
        firstColorRGB = rgb;
        firstColor = new Color(rgb);
    }

    public void setSecondColor(int rgb) {
        secondColorRGB = rgb;
        secondColor = new Color(rgb);
    }

    @Override
    public void load(CompoundTag compound) {
        if (compound.contains("firstColor")) {
            setFirstColor(compound.getInt("firstColor"));
        } else {
            setFirstColor(EtherItem.defaultFirstColor);
        }
        if (getBlockState().getBlock().asItem() instanceof AbstractEtherItem etherItem && etherItem.iridescent) {
            if (compound.contains("secondColor")) {
                setSecondColor(compound.getInt("secondColor"));
            } else {
                setSecondColor(EtherItem.defaultSecondColor);
            }
        }
        super.load(compound);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (firstColor != null && firstColorRGB != EtherItem.defaultFirstColor) {
            compound.putInt("firstColor", firstColorRGB);
        }
        if (getBlockState().getBlock().asItem() instanceof AbstractEtherItem etherItem && etherItem.iridescent) {
            if (secondColor != null && secondColorRGB != EtherItem.defaultSecondColor) {
                compound.putInt("secondColor", secondColorRGB);
            }
        }
        super.saveAdditional(compound);
    }

    @Override
    public void onPlace(LivingEntity placer, ItemStack stack) {
        AbstractEtherItem item = (AbstractEtherItem) stack.getItem();
        setFirstColor(item.getFirstColor(stack));
        if (item.iridescent) {
            setSecondColor(item.getSecondColor(stack));
        }
        setChanged();
    }

    @Override
    public ItemStack onClone(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = state.getBlock().asItem().getDefaultInstance();
        AbstractEtherItem etherItem = (AbstractEtherItem) stack.getItem();
        if (firstColor != null) {
            etherItem.setFirstColor(stack, firstColorRGB);
        }
        if (secondColor != null) {
            etherItem.setSecondColor(stack, secondColorRGB);
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
            ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
                    .setScale(scale, 0)
                    .setLifetime(lifeTime)
                    .setAlpha(0.75f, 0.25f)
                    .setColor(firstColor, secondColor)
                    .setColorCoefficient(1.4f)
                    .setColorEasing(Easing.BOUNCE_IN_OUT)
                    .setSpinOffset((level.getGameTime() * 0.2f) % 6.28f)
                    .setSpin(0, 0.4f)
                    .setSpinEasing(Easing.QUARTIC_IN)
                    .addMotion(0, velocity, 0)
                    .enableNoClip()
                    .spawn(level, x, y, z);

            ParticleBuilders.create(OrtusParticleRegistry.SPARKLE_PARTICLE)
                    .setScale(scale * 2, scale * 0.1f)
                    .setLifetime(lifeTime)
                    .setAlpha(0.2f)
                    .setColor(firstColor, secondColor)
                    .setColorEasing(Easing.EXPO_OUT)
                    .setColorCoefficient(1.25f)
                    .setAlphaCoefficient(1.5f)
                    .setSpin(0, 2)
                    .setSpinEasing(Easing.QUARTIC_IN)
                    .enableNoClip()
                    .spawn(level, x, y, z);
            if (level.getGameTime() % 2L == 0) {
                y += 0.15f;
                if (level.random.nextFloat() < 0.5f) {
                    ParticleBuilders.create(ParticleRegistry.SPIRIT_FLAME_PARTICLE)
                            .setScale(0.5f, 0.75f, 0)
                            .setColor(firstColor, secondColor)
                            .setColorCoefficient(1.5f)
                            .setAlpha(0.5f, 1f, 0)
                            .setAlphaEasing(Easing.SINE_IN, Easing.QUAD_IN)
                            .setAlphaCoefficient(3.5f)
                            .randomOffset(0.1f, 0.2f)
                            .addMotion(0, 0.01f, 0)
                            .setMotionCoefficient(0.975f)
                            .enableNoClip()
                            .spawn(level, x, y, z);
                }
                if (level.random.nextFloat() < 0.25f) {
                    ParticleBuilders.create(ParticleRegistry.SPIRIT_FLAME_PARTICLE)
                            .setScale(0.3f, 0.5f, 0)
                            .setColor(firstColor, secondColor)
                            .setColorCoefficient(2f)
                            .setAlpha(0.5f, 1f, 0)
                            .setAlphaEasing(Easing.SINE_IN, Easing.CIRC_IN_OUT)
                            .setAlphaCoefficient(3.5f)
                            .randomOffset(0.125f, 0.2f)
                            .addMotion(0, velocity / 2f, 0)
                            .setMotionCoefficient(0.97f)

                            .enableNoClip()
                            .spawn(level, x, y, z);
                }
            }
        }
    }
}