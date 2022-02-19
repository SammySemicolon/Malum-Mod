package com.sammy.malum.common.blockentity.spirit_crucible;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public interface ICrucibleAccelerator {
    CrucibleAcceleratorType getAcceleratorType();

    default boolean canAccelerate() {
        return true;
    }

    public default void setCrucible(SpiritCrucibleCoreBlockEntity crucible) {

    }
    default float getDamageChance(int entries) {
        return getAcceleratorType().getAcceleration(entries);
    }

    default int getMaximumDamage(int entries) {
        return getAcceleratorType().getMaximumDamage(entries);
    }

    default float getAcceleration(int entries) {
        return getAcceleratorType().getAcceleration(entries);
    }

    default void addParticles(BlockPos altarPos, Vec3 crucibleItemPos) {
    }

    default void addParticles(Color color, Color endColor, float alpha, BlockPos altarPos, Vec3 crucibleItemPos) {

    }

    abstract class CrucibleAcceleratorType {
        public final int maximumEntries;
        public final String type;

        public CrucibleAcceleratorType(int maximumEntries, String type) {
            this.maximumEntries = maximumEntries;
            this.type = type;
        }

        public float getDamageChance(int entries) {
            return 0;
        }

        public int getMaximumDamage(int entries) {
            return 0;
        }

        public float getAcceleration(int entries) {
            return 0;
        }
    }

    class ArrayCrucibleAcceleratorType extends CrucibleAcceleratorType {
        public final float[] damageChance;
        public final int[] maximumDamage;
        public final float[] acceleration;

        public ArrayCrucibleAcceleratorType(String type, float[] damageChance, int[] maximumDamage, float[] acceleration) {
            super(damageChance.length, type);
            this.damageChance = damageChance;
            this.maximumDamage = maximumDamage;
            this.acceleration = acceleration;
        }

        @Override
        public float getDamageChance(int entries) {
            return damageChance[entries - 1];
        }

        @Override
        public int getMaximumDamage(int entries) {
            return maximumDamage[entries - 1];
        }

        @Override
        public float getAcceleration(int entries) {
            return acceleration[entries - 1];
        }
    }
}