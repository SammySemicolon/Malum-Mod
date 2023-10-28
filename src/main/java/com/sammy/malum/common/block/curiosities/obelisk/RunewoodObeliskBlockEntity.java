package com.sammy.malum.common.block.curiosities.obelisk;

import com.sammy.malum.common.block.curiosities.spirit_altar.IAltarAccelerator;
import com.sammy.malum.common.block.curiosities.spirit_altar.SpiritAltarBlockEntity;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.visual_effects.SpiritAltarParticleEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;

import java.util.function.Supplier;

public class RunewoodObeliskBlockEntity extends ObeliskCoreBlockEntity implements IAltarAccelerator {
    private static final Vec3 OBELISK_PARTICLE_OFFSET = new Vec3(0.5f, 2f, 0.5f);

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
    public void addParticles(SpiritAltarBlockEntity blockEntity, MalumSpiritType activeSpiritType) {
        SpiritAltarParticleEffects.runewoodObeliskParticles(this, blockEntity, activeSpiritType);
    }

    public Vec3 getParticleOffset() {
        return OBELISK_PARTICLE_OFFSET;
    }
}