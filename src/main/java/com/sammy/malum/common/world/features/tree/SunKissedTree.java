package com.sammy.malum.common.world.features.tree;

import com.sammy.malum.core.init.MalumFeatures;
import net.minecraft.block.trees.BigTree;
import net.minecraft.block.trees.SpruceTree;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class SunKissedTree extends BigTree
{
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive)
    {
        return MalumFeatures.SUN_KISSED_TREE;
    }
    
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getHugeTreeFeature(Random rand)
    {
        return MalumFeatures.HUGE_SUN_KISSED_TREE;
    }
}