package com.sammy.malum.common.block;

import com.sammy.malum.core.registry.worldgen.FeatureRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class RunewoodSaplingBlock extends SaplingBlock
{
    public RunewoodSaplingBlock(Properties properties)
    {
        super(null, properties);
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, Random rand)
    {
        if (state.getValue(STAGE) == 0)
        {
            level.setBlock(pos, state.cycle(STAGE), 4);
        }
        else
        {
            if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(level, rand, pos))
            {
                return;
            }
            FeatureRegistry.RUNEWOOD_TREE.get().place(new FeaturePlaceContext<NoneFeatureConfiguration>(level,level.getChunkSource().getGenerator(), level.random,pos, NoneFeatureConfiguration.INSTANCE));
        }
    }
}