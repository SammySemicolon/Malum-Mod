package com.sammy.malum.common.block.curiosities.spirit_altar;

import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.world.phys.*;

public interface IAltarAccelerator {

    AltarAcceleratorType getAcceleratorType();

    default boolean canAccelerate() {
        return true;
    }

    float getAcceleration();

    default void addParticles(SpiritAltarBlockEntity blockEntity, MalumSpiritType activeSpiritType, Vec3 altarItemPos) {

    }

    record AltarAcceleratorType(int maximumEntries, String type) {
    }
}