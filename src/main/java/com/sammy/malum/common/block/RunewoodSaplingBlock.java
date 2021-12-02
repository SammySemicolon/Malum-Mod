package com.sammy.malum.common.block;

import com.sammy.malum.core.registry.worldgen.FeatureRegistry;
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
            FeatureRegistry.RUNEWOOD_TREE.get().generate(world, world.getChunkProvider().generator, rand, pos, null);
        }
    }
}