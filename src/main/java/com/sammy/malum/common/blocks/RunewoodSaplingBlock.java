package com.sammy.malum.common.blocks;

import com.sammy.malum.core.init.worldgen.MalumFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class RunewoodSaplingBlock extends SaplingBlock
{
    public RunewoodSaplingBlock(Properties properties)
    {
        super(null, properties);
    }

    @Override
    public void placeTree(ServerWorld world, BlockPos pos, BlockState state, Random rand)
    {
        if (state.get(STAGE) == 0)
        {
            world.setBlockState(pos, state.func_235896_a_(STAGE), 4);
        }
        else
        {
            if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(world, rand, pos))
            {
                return;
            }
            MalumFeatures.RUNEWOOD_TREE_FEATURE.get().generate(world, world.getChunkProvider().generator, rand, pos, null);
        }
        super.placeTree(world, pos, state, rand);
    }
}