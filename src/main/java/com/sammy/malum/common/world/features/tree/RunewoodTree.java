package com.sammy.malum.common.world.features.tree;

import com.sammy.malum.core.init.worldgen.MalumFeatures;
import net.minecraft.block.trees.BigTree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class RunewoodTree extends BigTree
{
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive)
    {
        return MalumFeatures.RUNEWOOD_TREE;
    }
    
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getHugeTreeFeature(Random rand)
    {
        return MalumFeatures.HUGE_RUNEWOOD_TREE;
    }
}