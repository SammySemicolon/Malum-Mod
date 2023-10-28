package com.sammy.malum.registry.common.worldgen;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public interface ConfiguredFeatureRegistry {

    static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> configuredFeatureBootstapContext) {
    }
}
