package com.sammy.malum.common.world.features.tree;

import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;

import java.util.Random;

public class SapBlockStateProvider extends SimpleBlockStateProvider
{
    public SapBlockStateProvider()
    {
        super(MalumBlocks.RUNEWOOD_LOG.get().getDefaultState());
    }
    
    @Override
    public BlockState getBlockState(Random randomIn, BlockPos blockPosIn)
    {
        if (randomIn.nextFloat() < 0.1f)
        {
            return MalumBlocks.SAP_FILLED_RUNEWOOD_LOG.get().getDefaultState();
        }
        return super.getBlockState(randomIn, blockPosIn);
    }
}
