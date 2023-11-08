package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.*;
import com.sammy.malum.core.systems.spirit.*;

public interface ICrucibleAccelerator {
    CrucibleAcceleratorType getAcceleratorType();

    default boolean canStartAccelerating() {
        return true;
    }

    default boolean canContinueAccelerating() {
        return true;
    }

    ICatalyzerAccelerationTarget getTarget();

    void setTarget(ICatalyzerAccelerationTarget target);

    default void addParticles(ICatalyzerAccelerationTarget target, MalumSpiritType spiritType) {
    }
}