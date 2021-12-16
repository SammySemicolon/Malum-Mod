package com.sammy.malum.common.block;

import com.sammy.malum.core.registry.Levelgen.FeatureRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.Level.server.ServerLevel;

import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class RunewoodSaplingBlock extends SaplingBlock
{
    public RunewoodSaplingBlock(Properties properties)
    {
        super(null, properties);
    }

    @Override
    public void advanceTree(ServerLevel Level, BlockPos pos, BlockState state, Random rand)
    {
        if (state.getValue(STAGE) == 0)
        {
            Level.setBlock(pos, state.cycle(STAGE), 4);
        }
        else
        {
            if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(Level, rand, pos))
            {
                return;
            }
            FeatureRegistry.RUNEWOOD_TREE.get().place(Level, Level.getChunkSource().generator, rand, pos, null);
        }
    }
}