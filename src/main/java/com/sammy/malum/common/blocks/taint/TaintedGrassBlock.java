package com.sammy.malum.common.blocks.taint;

import com.sammy.malum.MalumHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class TaintedGrassBlock extends GrassBlock implements ITaintSpreader
{
    public TaintedGrassBlock(Properties properties)
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
