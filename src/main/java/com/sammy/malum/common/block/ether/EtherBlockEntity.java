package com.sammy.malum.common.block.ether;

import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.common.item.ether.EtherItem;
import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;

import java.awt.*;

public class EtherBlockEntity extends LodestoneBlockEntity {
    public Color firstColor;
    public Color secondColor;

    public EtherBlockEntity(BlockEntityType<? extends EtherBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public EtherBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.ETHER.get(), pos, state);
    }

    public void setFirstColor(int rgb) {
        firstColor = new Color(rgb);
    }

    public void setSecondColor(int rgb) {
        secondColor = new Color(rgb);
    }

    @Override
    public void load(CompoundTag compound) {
        setFirstColor(compound.contains("firstColor") ? compound.getInt("firstColor") : EtherItem.DEFAULT_FIRST_COLOR);
        if (getBlockState().getBlock().asItem() instanceof AbstractEtherItem etherItem && etherItem.iridescent) {
            setSecondColor(compound.contains("secondColor") ? compound.getInt("secondColor") : EtherItem.DEFAULT_SECOND_COLOR);
        }
        super.load(compound);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (firstColor != null) {
            compound.putInt("firstColor", firstColor.getRGB());
        }
        if (getBlockState().getBlock().asItem() instanceof AbstractEtherItem etherItem && etherItem.iridescent) {
            if (secondColor != null && secondColor.getRGB() != EtherItem.DEFAULT_SECOND_COLOR) {
                compound.putInt("secondColor", secondColor.getRGB());
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
        return super.onClone(state, target, level, pos, player);
    }

    @Override
    public void init() {
        if (!level.isClientSide) {
            BlockHelper.updateState(level, worldPosition);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide) {
            if (firstColor == null) {
                return;
            }
            Block block = getBlockState().getBlock();
            Color firstColor = ColorHelper.darker(this.firstColor, 1);
            Color secondColor = this.secondColor == null ? firstColor : ColorHelper.brighter(this.secondColor, 1);
            double x = worldPosition.getX() + 0.5;
            double y = worldPosition.getY() + 0.6;
            double z = worldPosition.getZ() + 0.5;
            int lifeTime = 14 + level.random.nextInt(4);
            float scale = 0.17f + level.random.nextFloat() * 0.03f;
            float velocity = 0.04f + level.random.nextFloat() * 0.02f;

            if (block instanceof EtherWallTorchBlock) {
                Direction direction = getBlockState().getValue(WallTorchBlock.FACING);
                x += direction.getNormal().getX() * -0.15f;
                y += 0.3f;
                z += direction.getNormal().getZ() * -0.15f;
                lifeTime -= 6;
            }
            if (block instanceof EtherTorchBlock) {
                y += 0.2f;
                lifeTime -= 4;
            }
            if (block instanceof EtherBrazierBlock) {
                y -= 0.2f;
                lifeTime -= 2;
                scale *= 1.25f;
            }
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(scale, 0).build())
                    .setTransparencyData(GenericParticleData.create(0.75f, 0.25f).build())
                    .setColorData(ColorParticleData.create(firstColor, secondColor).setCoefficient(1.4f).setEasing(Easing.BOUNCE_IN_OUT).build())
                    .setSpinData(SpinParticleData.create(0.2f, 0.4f).setSpinOffset((level.getGameTime() * 0.2f) % 6.28f).setEasing(Easing.QUARTIC_IN).build())
                    .setLifetime(lifeTime)
                    .addMotion(0, velocity, 0)
                    .enableNoClip()
                    .spawn(level, x, y, z);

            WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setScaleData(GenericParticleData.create(scale * 2, scale * 0.1f).build())
                    .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                    .setColorData(ColorParticleData.create(firstColor, secondColor).setEasing(Easing.SINE_IN).setCoefficient(2.25f).build())
                    .setSpinData(SpinParticleData.create(0, 2).setEasing(Easing.QUARTIC_IN).build())
                    .setLifetime(lifeTime)
                    .enableNoClip()
                    .spawn(level, x, y, z);
            if (level.getGameTime() % 2L == 0) {
                y += 0.15f;
                if (level.random.nextFloat() < 0.5f) {
                    WorldParticleBuilder.create(ParticleRegistry.SPIRIT_FLAME_PARTICLE)
                            .setScaleData(GenericParticleData.create(0.5f, 0.75f, 0).build())
                            .setColorData(ColorParticleData.create(firstColor, secondColor).setEasing(Easing.CIRC_IN_OUT).setCoefficient(2.5f).build())
                            .setTransparencyData(GenericParticleData.create(0.2f, 1f, 0).setEasing(Easing.SINE_IN, Easing.QUAD_IN).setCoefficient(3.5f).build())
                            .setRandomOffset(0.15f, 0.2f)
                            .addMotion(0, 0.0035f, 0)
                            .setRandomMotion(0.001f, 0.005f)
                            .addActor(p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.985f-level.random.nextFloat() * 0.04f)))
                            .enableNoClip()
                            .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                            .spawn(level, x, y, z);
                }
                if (level.random.nextFloat() < 0.25f) {
                    WorldParticleBuilder.create(ParticleRegistry.SPIRIT_FLAME_PARTICLE)
                            .setScaleData(GenericParticleData.create(0.3f, 0.5f, 0).build())
                            .setColorData(ColorParticleData.create(firstColor, secondColor).setEasing(Easing.CIRC_IN_OUT).setCoefficient(3.5f).build())
                            .setTransparencyData(GenericParticleData.create(0.2f, 1f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN_OUT).setCoefficient(3.5f).build())
                            .setRandomOffset(0.1f, 0.225f)
                            .addMotion(0, velocity / 2f, 0)
                            .setRandomMotion(0, 0.015f)
                            .addActor(p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.97f-level.random.nextFloat() * 0.025f)))
                            .enableNoClip()
                            .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                            .spawn(level, x, y, z);
                }
            }
        }
    }
}