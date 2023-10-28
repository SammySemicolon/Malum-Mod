package com.sammy.malum.registry.common.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.config.CommonConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import team.lodestar.lodestone.systems.worldgen.ChancePlacementFilter;

import java.util.List;

public class PlacedFeatureRegistry {

    public static final ResourceKey<PlacedFeature> ORE_SOULSTONE = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("ore_soulstone"));

    public static final ResourceKey<PlacedFeature> ORE_BRILLIANT = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("ore_brilliant"));
    public static final ResourceKey<PlacedFeature> ORE_NATURAL_QUARTZ = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("ore_natural_quartz"));
    //TODO public static final ResourceKey<PlacedFeature> ORE_BLAZING_QUARTZ = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("ore_blazing_quartz"));

    /*TODO
    public static final ResourceKey<PlacedFeature> GEODE_NATURAL_QUARTZ_UPPER = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("geode_quartz_upper"));
    public static final ResourceKey<PlacedFeature> GEODE_NATURAL_QUARTZ_LOWER = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("geode_quartz_lower"));

    public static final ResourceKey<PlacedFeature> GEODE_CTHONIC_GOLD_FEATURE = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("geode_rare_earth"));

     */
    public static final ResourceKey<PlacedFeature> RUNEWOOD_TREE = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("runewood_tree"));
    public static final ResourceKey<PlacedFeature> RARE_RUNEWOOD_TREE = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("rare_runewood_tree"));

    public static void bootstrap(BootstapContext<PlacedFeature> context) {//TODO fix all parameters
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(RUNEWOOD_TREE, addTreeFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE), 3, 0.02f));
        context.register(ORE_SOULSTONE, addOreFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_SOULSTONE_ORE), -16, 112, 16));
        context.register(ORE_BRILLIANT, addOreFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_BRILLIANT_ORE), -16, 112, 16));
        context.register(ORE_NATURAL_QUARTZ, addOreFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_NATURAL_QUARTZ_ORE), -16, 112, 16));


        //register(context, PlacedFeatureRegistry.RARE_RUNEWOOD_TREE, addTreeFeature(context.lookup(Registries.CONFIGURED_FEATURE).get(ConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE).orElseThrow(), 3, 0.01f));

    }

    private static PlacedFeature addOreFeature(Holder<ConfiguredFeature<?, ?>> configureFeature, int minHeight, int maxHeight, int count) {
        return new PlacedFeature(configureFeature, List.of(
                HeightRangePlacement.triangle(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)),
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                BiomeFilter.biome())
        );
    }

    private static PlacedFeature addTreeFeature(Holder<ConfiguredFeature<?, ?>> configureFeature, int count, float chance) {
        return new PlacedFeature(configureFeature, List.of(
                PlacementUtils.HEIGHTMAP,
                CountPlacement.of(count),
                new ChancePlacementFilter(chance),
                InSquarePlacement.spread(),
                BiomeFilter.biome())
        );
    }
}
