package com.sammy.malum.common.blockentity.altar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public interface IAltarAccelerator {
    public AltarAcceleratorType getAcceleratorType();
    public default boolean canAccelerate()
    {
        return true;
    }
    public float getAcceleration();

    default void addParticles(BlockPos altarPos, Vec3 altarentity) {

    }
    public default void addParticles(Color color, Color endColor, float alpha, BlockPos altarPos, Vec3 altarItemPos)
    {

    }

    public record AltarAcceleratorType(int maximumEntries, String type) {
    }
}
