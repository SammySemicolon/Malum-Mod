package com.sammy.malum.common.worldgen.tree.grower;

import com.sammy.malum.registry.common.worldgen.ConfiguredFeatureRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class RunewoodTreeGrower extends AbstractTreeGrower {

    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return ConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE;
    }
}
