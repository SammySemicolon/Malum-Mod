package com.sammy.malum.registry.common.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.systems.worldgen.ChancePlacementFilter;

import java.util.List;

import static com.sammy.malum.MalumMod.MALUM;

public final class PlacedFeatureRegistry {
	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, MALUM);

	public static final RegistryObject<PlacedFeature> RUNEWOOD_TREE = PLACED_FEATURES.register("common_runewood",
			() -> new PlacedFeature(Holder.direct(ConfiguredFeatureRegistry.RUNEWOOD_TREE_FEATURE.get()), List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, new ChancePlacementFilter(0.02f), CountPlacement.of(3))));

	public static final RegistryObject<PlacedFeature> RARE_RUNEWOOD_TREE = PLACED_FEATURES.register("rare_runewood",
			() -> new PlacedFeature(Holder.direct(ConfiguredFeatureRegistry.RUNEWOOD_TREE_FEATURE.get()), List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, new ChancePlacementFilter(0.01f), CountPlacement.of(3))));

	public static final RegistryObject<PlacedFeature> WEEPING_WELL_FEATURE = PLACED_FEATURES.register("weeping_well",
			() -> new PlacedFeature(Holder.direct(ConfiguredFeatureRegistry.WEEPING_WELL_FEATURE.get()), List.of(RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), HeightRangePlacement.of(ConstantHeight.of(VerticalAnchor.absolute(0))), BiomeFilter.biome())));

	public static final RegistryObject<PlacedFeature> BLAZING_QUARTZ_FEATURE = PLACED_FEATURES.register("blazing_quartz_ore",
			() -> new PlacedFeature(Holder.direct(ConfiguredFeatureRegistry.BLAZING_QUARTZ_FEATURE.get()), List.of(CountPlacement.of(16), InSquarePlacement.spread(), PlacementUtils.RANGE_8_8)));

	public static final RegistryObject<PlacedFeature> BRILLIANCE_FEATURE = PLACED_FEATURES.register("brilliant_stone",
			() -> new PlacedFeature(Holder.direct(ConfiguredFeatureRegistry.BRILLIANCE_FEATURE.get()), List.of(CountPlacement.of(3), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(40)))));

	public static final RegistryObject<PlacedFeature> SOULSTONE_FEATURE = PLACED_FEATURES.register("soulstone",
			() -> new PlacedFeature(Holder.direct(ConfiguredFeatureRegistry.SOULSTONE_FEATURE.get()), List.of(CountPlacement.of(3), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(30)))));

	public static final RegistryObject<PlacedFeature> SURFACE_SOULSTONE_FEATURE = PLACED_FEATURES.register("surface_soulstone",
			() -> new PlacedFeature(Holder.direct(ConfiguredFeatureRegistry.SURFACE_SOULSTONE_FEATURE.get()), List.of(CountPlacement.of(4), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(60), VerticalAnchor.absolute(100)))));

	public static final RegistryObject<PlacedFeature> NATURAL_QUARTZ_FEATURE = PLACED_FEATURES.register("natural_quartz",
			() -> new PlacedFeature(Holder.direct(ConfiguredFeatureRegistry.NATURAL_QUARTZ_FEATURE.get()),
					List.of(CountPlacement.of(2), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(10)))));

	public static final RegistryObject<PlacedFeature> QUARTZ_GEODE_FEATURE = PLACED_FEATURES.register("quartz_geode",
			() -> new PlacedFeature(Holder.direct(ConfiguredFeatureRegistry.QUARTZ_GEODE_FEATURE.get()),
					List.of(RarityFilter.onAverageOnceEvery(48), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48)), BiomeFilter.biome())));

	public static final RegistryObject<PlacedFeature> DEEPSLATE_QUARTZ_GEODE_FEATURE = PLACED_FEATURES.register("deepslate_quartz_geode",
			() -> new PlacedFeature(Holder.direct(ConfiguredFeatureRegistry.DEEPSLATE_QUARTZ_GEODE_FEATURE.get()),
					List.of(RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(-10)), BiomeFilter.biome())));

	public static final RegistryObject<PlacedFeature> CTHONIC_GOLD_GEODE_FEATURE = PLACED_FEATURES.register("cthonic_gold_geode",
			() -> new PlacedFeature(Holder.direct(ConfiguredFeatureRegistry.CTHONIC_GOLD_GEODE_FEATURE.get()),
					List.of(RarityFilter.onAverageOnceEvery(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.aboveBottom(40)), BiomeFilter.biome())));
}