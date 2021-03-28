package com.sammy.malum.common.blocks;

import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class MalumOreBlock extends OreBlock
{
    public final int min;
    public final int max;
    public MalumOreBlock(Properties properties, int min, int max)
    {
        super(properties);
        this.min = min;
        this.max = max;
    }

    @Override
    protected int getExperience(Random rand)
    {
        return MathHelper.nextInt(rand, min, max);
    }

    @Override
    public void dropXpOnBlockBreak(ServerWorld worldIn, BlockPos pos, int amount)
    {
        if (this.equals(MalumBlocks.BLAZING_QUARTZ_ORE.get()))
        {

        }
        super.dropXpOnBlockBreak(worldIn, pos, amount);
    }
}
