package com.sammy.malum.common.block.spirit_altar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IAltarAccelerator {
    public AcceleratorType getType();
    public float getAcceleration(Level level, BlockPos pos, BlockState state);
    public static class AcceleratorType
    {
        public final int maximumEntries;
        public final String type;

        protected AcceleratorType(int maximumEntries, String type) {
            this.maximumEntries = maximumEntries;
            this.type = type;
        }
    }
}
