package com.sammy.malum.common.block.spirit_altar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public interface IAltarAccelerator {
    public AcceleratorType getAcceleratorType();
    public float getAcceleration();
    public default void addParticles(Color color, float alpha, BlockPos altarPos, Vec3 altarItemPos)
    {

    }
    public static class AcceleratorType
    {
        public final int maximumEntries;
        public final String type;

        public AcceleratorType(int maximumEntries, String type) {
            this.maximumEntries = maximumEntries;
            this.type = type;
        }
    }
}
