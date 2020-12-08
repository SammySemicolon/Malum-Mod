package com.sammy.malum.common.blocks.taint;

import com.sammy.malum.common.blocks.MalumLeavesBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.awt.*;
import java.util.Random;

public class TaintedLeavesBlock extends MalumLeavesBlock implements ITaintSpreader
{
    public TaintedLeavesBlock(Properties properties, Color maxColor, Color minColor)
    {
        super(properties, maxColor, minColor);
    }
    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
    {
        super.tick(state, worldIn, pos, rand);
        spread(worldIn,pos);
    }
}
