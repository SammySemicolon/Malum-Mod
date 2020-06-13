package com.kittykitcatcat.malum.world.biomes;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class SpiritwoodTree extends Tree
{

    public SpiritwoodTree() {
    }

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_)
    {
        return Feature.NORMAL_TREE.withConfiguration(SpiritForest.spiritwood_tree_config);
    }
}
