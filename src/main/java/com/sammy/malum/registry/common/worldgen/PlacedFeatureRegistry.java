package com.sammy.malum.registry.common.worldgen;

import com.sammy.malum.MalumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.*;

public class PlacedFeatureRegistry {

    public static final ResourceKey<PlacedFeature> ORE_SOULSTONE = registerKey("ore_soulstone");
    public static final ResourceKey<PlacedFeature> ORE_BRILLIANT = registerKey("ore_brilliant");
    public static final ResourceKey<PlacedFeature> ORE_NATURAL_QUARTZ = registerKey("ore_natural_quartz");
    public static final ResourceKey<PlacedFeature> ORE_CTHONIC_GOLD = registerKey("cthonic_gold_ore");
    public static final ResourceKey<PlacedFeature> ORE_BLAZING_QUARTZ = registerKey("blazing_quartz_ore");

    public static final ResourceKey<PlacedFeature> RUNEWOOD_TREE = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("runewood_tree"));
    public static final ResourceKey<PlacedFeature> RARE_RUNEWOOD_TREE = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("rare_runewood_tree"));
    public static final ResourceKey<PlacedFeature> AZURE_RUNEWOOD_TREE = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("azure_runewood_tree"));
    public static final ResourceKey<PlacedFeature> RARE_AZURE_RUNEWOOD_TREE = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("rare_azure_runewood_tree"));

    public static final ResourceKey<PlacedFeature> QUARTZ_GEODE_FEATURE = registerKey("quartz_geode");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_QUARTZ_GEODE_FEATURE = registerKey("deepslate_quartz_geode");

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath(name));
    }
}