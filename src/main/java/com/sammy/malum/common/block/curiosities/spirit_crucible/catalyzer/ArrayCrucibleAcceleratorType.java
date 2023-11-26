package com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer;

public class ArrayCrucibleAcceleratorType extends CrucibleAcceleratorType {
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
