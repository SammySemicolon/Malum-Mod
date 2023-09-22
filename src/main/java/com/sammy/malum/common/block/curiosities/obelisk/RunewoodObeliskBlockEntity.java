package com.sammy.malum.common.block.curiosities.obelisk;

import com.sammy.malum.common.block.curiosities.spirit_altar.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.multiblock.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;

import java.awt.*;
import java.util.function.*;

public class RunewoodObeliskBlockEntity extends ObeliskCoreBlockEntity implements IAltarAccelerator {
    public static final AltarAcceleratorType OBELISK = new AltarAcceleratorType(4, "obelisk");
    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.RUNEWOOD_OBELISK_COMPONENT.get().defaultBlockState())));

    public RunewoodObeliskBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.RUNEWOOD_OBELISK.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    public AltarAcceleratorType getAcceleratorType() {
        return OBELISK;
    }

    @Override
    public float getAcceleration() {
        return 0.25f;
    }

    @Override
    public void addParticles(SpiritAltarBlockEntity blockEntity, MalumSpiritType activeSpiritType, Vec3 altarItemPos) {
        Color firstColor = activeSpiritType.getPrimaryColor();
        Color secondColor = activeSpiritType.getSecondaryColor();
        Vec3 startPos = BlockHelper.fromBlockPos(worldPosition).add(0.5f, 2.15f, 0.5f);
        float alpha = 0.06f;
        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(alpha/4f, alpha, 0f).setEasing(Easing.ELASTIC_IN, Easing.SINE_IN_OUT).build())
                .setScaleData(GenericParticleData.create(0.25f + level.random.nextFloat() * 0.1f, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setLifetime(35)
                .setRandomOffset(0.1f)
                .setRandomMotion(0.01f, 0.01f)
                .setColorData(ColorParticleData.create(firstColor, secondColor).build())
                .setSpinData(SpinParticleData.create(0.1f + level.random.nextFloat() * 0.2f).build())
                .setRandomMotion(0.0025f, 0.0025f)
                .enableNoClip()
                .repeat(level, startPos.x, startPos.y, startPos.z, 1);

        WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(alpha, 0f).setEasing(Easing.EXPO_IN_OUT).build())
                .setScaleData(GenericParticleData.create(0.5f, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setLifetime(25)
                .setRandomOffset(0.1, 0.1)
                .setRandomMotion(0.02f, 0.02f)
                .setColorData(ColorParticleData.create(firstColor, secondColor).build())
                .setRandomMotion(0.0025f, 0.0025f)
                .enableNoClip()
                .repeat(level, startPos.x, startPos.y, startPos.z, 2);

    }
}