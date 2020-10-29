package com.sammy.malum.world;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;

import javax.annotation.Nullable;
import java.util.Random;

public class SpiritwoodTree extends Tree
{
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive)
    {
        return Features.BIRCH;
    }
}