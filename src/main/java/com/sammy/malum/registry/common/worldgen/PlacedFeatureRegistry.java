package com.sammy.malum.registry.common.worldgen;

import com.google.common.collect.ImmutableList;
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
import team.lodestar.lodestone.systems.worldgen.DimensionPlacementFilter;

import java.util.ArrayList;
import java.util.List;

public class PlacedFeatureRegistry {

    public static final ResourceKey<PlacedFeature> ORE_SOULSTONE = registerKey("ore_soulstone");
    public static final ResourceKey<PlacedFeature> ORE_BRILLIANT = registerKey("ore_brilliant");
    public static final ResourceKey<PlacedFeature> ORE_NATURAL_QUARTZ = registerKey("ore_natural_quartz");
    public static final ResourceKey<PlacedFeature> ORE_BLAZING_QUARTZ = registerKey("blazing_quartz_ore");

    public static final ResourceKey<PlacedFeature> RUNEWOOD_TREE = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("runewood_tree"));
    public static final ResourceKey<PlacedFeature> RARE_RUNEWOOD_TREE = ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath("rare_runewood_tree"));

    public static final ResourceKey<PlacedFeature> QUARTZ_GEODE_FEATURE = registerKey("quartz_geode");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_QUARTZ_GEODE_FEATURE = registerKey("deepslate_quartz_geode");
    public static final ResourceKey<PlacedFeature> CTHONIC_GOLD_GEODE_FEATURE = registerKey("rare_earth_geode");

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, MalumMod.malumPath(name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(ORE_SOULSTONE, addOreFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_SOULSTONE_ORE), -64, 100, 3));
        context.register(ORE_BRILLIANT, addOreFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_BRILLIANT_ORE), -64, 40, 3));
        context.register(ORE_NATURAL_QUARTZ, addOreFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_NATURAL_QUARTZ_ORE), -64, 10, 2));
        context.register(ORE_BLAZING_QUARTZ, addOreFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_BLAZING_QUARTZ_ORE), -16, 112, 16));


        context.register(RUNEWOOD_TREE,
                new PlacedFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE),
                        ImmutableList.<PlacementModifier>builder().add(
                                PlacementUtils.HEIGHTMAP,
                                RarityFilter.onAverageOnceEvery(10),
                                InSquarePlacement.spread(),
                                CountPlacement.of(3)
                        ).build()
                )
        );

        context.register(RARE_RUNEWOOD_TREE,
                new PlacedFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE),
                        ImmutableList.<PlacementModifier>builder().add(
                                PlacementUtils.HEIGHTMAP,
                                RarityFilter.onAverageOnceEvery(5),
                                InSquarePlacement.spread(),
                                CountPlacement.of(3)
                        ).build()
                )
        );


        context.register(QUARTZ_GEODE_FEATURE,
                new PlacedFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_QUARTZ_GEODE_FEATURE),
                        ImmutableList.<PlacementModifier>builder().add(
                                        RarityFilter.onAverageOnceEvery(24),
                                        InSquarePlacement.spread(),
                                        HeightRangePlacement.uniform(
                                                VerticalAnchor.aboveBottom(6),
                                                VerticalAnchor.absolute(-10)),
                                        BiomeFilter.biome())
                                .build()
                ));


        context.register(DEEPSLATE_QUARTZ_GEODE_FEATURE,
                new PlacedFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_DEEPSLATE_QUARTZ_GEODE_FEATURE),
                        ImmutableList.<PlacementModifier>builder().add(
                                RarityFilter.onAverageOnceEvery(24),
                                        InSquarePlacement.spread(),
                                        HeightRangePlacement.uniform(
                                                VerticalAnchor.aboveBottom(6),
                                                VerticalAnchor.absolute(-10)),
                                        BiomeFilter.biome())
                                .build()
                ));

        context.register(CTHONIC_GOLD_GEODE_FEATURE,
                new PlacedFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_CTHONIC_GOLD_GEODE_FEATURE),
                        ImmutableList.<PlacementModifier>builder().add(
                                RarityFilter.onAverageOnceEvery(30),
                                        InSquarePlacement.spread(),
                                        HeightRangePlacement.uniform(
                                                VerticalAnchor.aboveBottom(6),
                                                VerticalAnchor.aboveBottom(40)),
                                        BiomeFilter.biome())
                                .build()
                ));


    }


    private static PlacedFeature addOreFeature(Holder<ConfiguredFeature<?, ?>> configureFeature, int minHeight, int maxHeight, int count) {
        return new PlacedFeature(configureFeature, List.of(
                HeightRangePlacement.triangle(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)),
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                BiomeFilter.biome())
        );
    }
}
