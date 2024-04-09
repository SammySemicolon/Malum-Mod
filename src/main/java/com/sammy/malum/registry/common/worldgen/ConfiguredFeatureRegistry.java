package com.sammy.malum.registry.common.worldgen;

import com.sammy.malum.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.levelgen.feature.*;


public class ConfiguredFeatureRegistry {

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SOULSTONE_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("soulstone_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_BRILLIANT_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("brilliant_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_NATURAL_QUARTZ_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("natural_quartz_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_BLAZING_QUARTZ_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("blazing_quartz_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CTHONIC_GOLD_ORE_FEATURE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("cthonic_gold_ore"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_RUNEWOOD_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("runewood_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_AZURE_RUNEWOOD_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("azure_runewood_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SOULWOOD_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("soulwood_tree"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_QUARTZ_GEODE_FEATURE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("quartz_geode"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_DEEPSLATE_QUARTZ_GEODE_FEATURE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("deepslate_quartz_geode"));
}
