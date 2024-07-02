package com.sammy.malum.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SpiritedGlassBlock extends GlassBlock {
    public SpiritedGlassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
        return adjacentState.getBlock() instanceof SpiritedGlassBlock || super.skipRendering(state, adjacentState, direction);
    }
}
