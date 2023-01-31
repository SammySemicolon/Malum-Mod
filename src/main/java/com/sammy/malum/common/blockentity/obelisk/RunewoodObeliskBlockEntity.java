package com.sammy.malum.common.blockentity.obelisk;

import com.sammy.malum.common.blockentity.spirit_altar.IAltarAccelerator;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;

import java.awt.*;
import java.util.function.Supplier;

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
        return 1f;
    }

    @Override
    public void addParticles(Color color, Color endColor, float alpha, BlockPos altarPos, Vec3 altarItemPos) {
        Vec3 startPos = BlockHelper.fromBlockPos(worldPosition).add(0.5f, 2.15f, 0.5f);
        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(alpha, 0f).build())
                .setScaleData(GenericParticleData.create(0.2f + level.random.nextFloat() * 0.1f, 0).build())
                .setLifetime(35)
                .setRandomOffset(0.1f)
                .setRandomMotion(0.01f, 0.01f)
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setSpinData(SpinParticleData.create(0.1f + level.random.nextFloat() * 0.2f).build())
                .setRandomMotion(0.0025f, 0.0025f)
                .enableNoClip()
                .repeat(level, startPos.x, startPos.y, startPos.z, 1);

        WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(alpha, 0f).build())
                .setScaleData(GenericParticleData.create(0.5f, 0).build())
                .setLifetime(25)
                .setRandomOffset(0.1, 0.1)
                .setRandomMotion(0.02f, 0.02f)
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setRandomMotion(0.0025f, 0.0025f)
                .enableNoClip()
                .repeat(level, startPos.x, startPos.y, startPos.z, 2);

    }
}