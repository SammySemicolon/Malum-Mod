package com.sammy.malum.common.blockentity.obelisk;

import com.sammy.malum.common.blockentity.altar.IAltarAccelerator;
import com.sammy.malum.core.setup.client.ParticleRegistry;
import com.sammy.malum.core.setup.content.block.BlockEntityRegistry;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import com.sammy.ortus.helpers.DataHelper;
import com.sammy.ortus.setup.OrtusParticles;
import com.sammy.ortus.systems.multiblock.MultiBlockStructure;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

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
        Vec3 startPos = DataHelper.fromBlockPos(worldPosition).add(0.5f, 2.15f, 0.5f);
        ParticleBuilders.create(OrtusParticles.WISP_PARTICLE)
                .setAlpha(alpha, 0f)
                .setLifetime(35)
                .setScale(0.2f + level.random.nextFloat() * 0.1f, 0)
                .randomOffset(0.1f)
                .randomMotion(0.01f, 0.01f)
                .setColor(color, endColor)
                .setSpin(0.1f + level.random.nextFloat() * 0.2f)
                .randomMotion(0.0025f, 0.0025f)
                .enableNoClip()
                .repeat(level, startPos.x, startPos.y, startPos.z, 1);

        ParticleBuilders.create(OrtusParticles.SPARKLE_PARTICLE)
                .setAlpha(alpha, 0f)
                .setLifetime(25)
                .setScale(0.5f, 0)
                .randomOffset(0.1, 0.1)
                .randomMotion(0.02f, 0.02f)
                .setColor(color, endColor)
                .randomMotion(0.0025f, 0.0025f)
                .enableNoClip()
                .repeat(level, startPos.x, startPos.y, startPos.z, 2);

    }
}