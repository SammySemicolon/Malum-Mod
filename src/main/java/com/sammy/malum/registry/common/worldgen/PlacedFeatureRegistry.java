package com.sammy.malum.registry.common.worldgen;

import com.sammy.malum.MalumMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

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

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        register(context, PlacedFeatureRegistry.ORE_SOULSTONE, addOreFeature(context.lookup(Registries.CONFIGURED_FEATURE)
                .get(ConfiguredFeatureRegistry.CONFIGURED_SOULSTONE_ORE).orElseThrow(), -16, 112, 16));

        register(context, PlacedFeatureRegistry.ORE_BRILLIANT, addOreFeature(context.lookup(Registries.CONFIGURED_FEATURE)
                .get(ConfiguredFeatureRegistry.CONFIGURED_BRILLIANT_ORE).orElseThrow(), -16, 112, 16));

        register(context, PlacedFeatureRegistry.ORE_NATURAL_QUARTZ, addOreFeature(context.lookup(Registries.CONFIGURED_FEATURE)
                .get(ConfiguredFeatureRegistry.CONFIGURED_NATURAL_QUARTZ_ORE).orElseThrow(), -16, 112, 16));

    }

    private static PlacedFeature addOreFeature(Holder<ConfiguredFeature<?, ?>> configureFeature, int minHeight, int maxHeight, int count) {
        return addFeaturePlacement(configureFeature, HeightRangePlacement.triangle(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)), CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome());
    }

    private static PlacedFeature addFeaturePlacement(Holder<ConfiguredFeature<?, ?>> configureFeature, PlacementModifier... placementModifiers) {
        return new PlacedFeature(configureFeature, List.of(placementModifiers));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> featureKey, PlacedFeature feature) {
        context.register(featureKey, feature);
    }
}
