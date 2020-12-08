package com.sammy.malum.common.blocks.taint;

import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class TaintedLogBlock extends RotatedPillarBlock implements ITaintSpreader
{
    public TaintedLogBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
    {
        super.tick(state, worldIn, pos, rand);
        spread(worldIn,pos);
    }
}
