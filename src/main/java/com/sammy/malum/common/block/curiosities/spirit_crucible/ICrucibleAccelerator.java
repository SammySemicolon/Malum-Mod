package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;

import java.util.*;

public interface ICrucibleAccelerator {
    CrucibleAcceleratorType getAcceleratorType();

    default Optional<AbstractAugmentItem> getAugmentType() {
        return AbstractAugmentItem.getAugmentType(getAugment());
    }

    ItemStack getAugment();

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

    abstract class CrucibleAcceleratorType {
        public final int maximumEntries;
        public final ResourceLocation type;

        public CrucibleAcceleratorType(int maximumEntries, ResourceLocation type) {
            this.maximumEntries = maximumEntries;
            this.type = type;
        }

        public abstract float getExtraDamageRollChance(int count);


        public abstract float getAcceleration(int count);
    }
}