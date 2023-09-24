package com.sammy.malum.common.block.curiosities.spirit_altar;

import com.sammy.malum.core.systems.spirit.*;

public interface IAltarAccelerator {

    AltarAcceleratorType getAcceleratorType();

    default boolean canAccelerate() {
        return true;
    }

    float getAcceleration();

    default void addParticles(SpiritAltarBlockEntity blockEntity, MalumSpiritType activeSpiritType) {

    }

    record AltarAcceleratorType(int maximumEntries, String type) {
    }
}