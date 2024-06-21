package com.sammy.malum.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.sammy.malum.registry.common.worldgen.ConfiguredFeatureRegistry;
import com.sammy.malum.registry.common.worldgen.PlacedFeatureRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class PlacedFeatures {
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(PlacedFeatureRegistry.ORE_SOULSTONE, addOreFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_SOULSTONE_ORE), -64, 100, 3));
        context.register(PlacedFeatureRegistry.ORE_BRILLIANT, addOreFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_BRILLIANT_ORE), -64, 40, 3));
        context.register(PlacedFeatureRegistry.ORE_NATURAL_QUARTZ, addOreFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_NATURAL_QUARTZ_ORE), -64, 10, 2));
        context.register(PlacedFeatureRegistry.ORE_BLAZING_QUARTZ, addOreFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_BLAZING_QUARTZ_ORE), -16, 112, 16));
        context.register(PlacedFeatureRegistry.ORE_CTHONIC_GOLD, addOreFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_CTHONIC_GOLD_ORE_FEATURE), -48, -0, 1, RarityFilter.onAverageOnceEvery(3)));

        context.register(PlacedFeatureRegistry.RUNEWOOD_TREE,
                new PlacedFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE),
                        ImmutableList.<PlacementModifier>builder().add(
                                PlacementUtils.HEIGHTMAP,
                                RarityFilter.onAverageOnceEvery(8),
                                InSquarePlacement.spread(),
                                CountPlacement.of(3)
                        ).build()
                )
        );
        context.register(PlacedFeatureRegistry.RARE_RUNEWOOD_TREE,
                new PlacedFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE),
                        ImmutableList.<PlacementModifier>builder().add(
                                PlacementUtils.HEIGHTMAP,
                                RarityFilter.onAverageOnceEvery(16),
                                InSquarePlacement.spread(),
                                CountPlacement.of(3)
                        ).build()
                )
        );

        context.register(PlacedFeatureRegistry.AZURE_RUNEWOOD_TREE,
                new PlacedFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_AZURE_RUNEWOOD_TREE),
                        ImmutableList.<PlacementModifier>builder().add(
                                PlacementUtils.HEIGHTMAP,
                                RarityFilter.onAverageOnceEvery(12),
                                InSquarePlacement.spread(),
                                CountPlacement.of(3)
                        ).build()
                )
        );
        context.register(PlacedFeatureRegistry.RARE_AZURE_RUNEWOOD_TREE,
                new PlacedFeature(features.getOrThrow(ConfiguredFeatureRegistry.CONFIGURED_AZURE_RUNEWOOD_TREE),
                        ImmutableList.<PlacementModifier>builder().add(
                                PlacementUtils.HEIGHTMAP,
                                RarityFilter.onAverageOnceEvery(20),
                                InSquarePlacement.spread(),
                                CountPlacement.of(3)
                        ).build()
                )
        );

        context.register(PlacedFeatureRegistry.QUARTZ_GEODE_FEATURE,
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


        context.register(PlacedFeatureRegistry.DEEPSLATE_QUARTZ_GEODE_FEATURE,
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
    }

    private static PlacedFeature addOreFeature(Holder<ConfiguredFeature<?, ?>> configureFeature, int minHeight, int maxHeight, int count, PlacementModifier... extraModifiers) {
        final List<PlacementModifier> modifiers = ImmutableList.<PlacementModifier>builder().add(
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)),
                        CountPlacement.of(count),
                        InSquarePlacement.spread(),
                        BiomeFilter.biome())
                .add(extraModifiers)
                .build();
        return new PlacedFeature(configureFeature, modifiers);
    }

    private static PlacedFeature addOreFeature(Holder<ConfiguredFeature<?, ?>> configureFeature, int minHeight, int maxHeight, int count) {
        return new PlacedFeature(configureFeature, List.of(
                HeightRangePlacement.triangle(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)),
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                BiomeFilter.biome()));
    }
}