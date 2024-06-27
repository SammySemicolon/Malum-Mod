package com.sammy.malum.common.block;

import net.minecraft.core.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class SpiritedGlassBlock extends GlassBlock {
    public SpiritedGlassBlock(Properties p_53640_) {
        super(p_53640_);
    }

    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.getBlock() instanceof SpiritedGlassBlock || super.skipRendering(pState, pAdjacentBlockState, pSide);
    }
}
