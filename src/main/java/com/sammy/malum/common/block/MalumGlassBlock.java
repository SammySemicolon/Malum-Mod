package com.sammy.malum.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MalumGlassBlock extends GlassBlock {
    public MalumGlassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
        return adjacentState.getBlock() instanceof MalumGlassBlock || super.skipRendering(state, adjacentState, direction);
    }
}
