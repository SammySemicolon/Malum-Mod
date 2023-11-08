package com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer;

public abstract class CrucibleAcceleratorType {
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
