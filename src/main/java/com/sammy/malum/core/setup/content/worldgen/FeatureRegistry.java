package com.sammy.malum.core.setup.content.worldgen;

import com.google.common.base.Suppliers;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.worldgen.RareEarthsGeode;
import com.sammy.malum.common.worldgen.RunewoodTreeFeature;
import com.sammy.malum.common.worldgen.SoulwoodTreeFeature;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.systems.worldgen.ChancePlacementFilter;
import team.lodestar.lodestone.systems.worldgen.DimensionPlacementFilter;

import java.util.List;
import java.util.function.Supplier;

import static com.sammy.malum.MalumMod.MALUM;
import static net.minecraft.data.worldgen.features.OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;
import static net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration.INSTANCE;

@Mod.EventBusSubscriber(modid = MalumMod.MALUM, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FeatureRegistry {

	public static final DeferredRegister<Feature<?>> FEATURE_TYPES = DeferredRegister.create(ForgeRegistries.FEATURES, MALUM);

	public static final RegistryObject<RunewoodTreeFeature> RUNEWOOD_TREE = FEATURE_TYPES.register("runewood_tree", RunewoodTreeFeature::new);
	public static final RegistryObject<SoulwoodTreeFeature> SOULWOOD_TREE = FEATURE_TYPES.register("soulwood_tree", SoulwoodTreeFeature::new);
	public static final RegistryObject<RareEarthsGeode> RARE_EARTHS_GEODE = FEATURE_TYPES.register("rare_earths_geode", () -> new RareEarthsGeode(GeodeConfiguration.CODEC));


	public static final class ConfiguredFeatures {

		public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, MALUM);
		public static final RegistryObject<ConfiguredFeature<?, ?>> RUNEWOOD_TREE_FEATURE = CONFIGURED_FEATURES.register("runewood_tree",
				() -> new ConfiguredFeature<>(FeatureRegistry.RUNEWOOD_TREE.get(), INSTANCE));

		public static final RegistryObject<ConfiguredFeature<?, ?>> BLAZING_QUARTZ_FEATURE = CONFIGURED_FEATURES.register("blazing_quartz",
				() -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OreFeatures.NETHERRACK, BlockRegistry.BLAZING_QUARTZ_ORE.get().defaultBlockState(), CommonConfig.BLAZE_QUARTZ_SIZE.getConfigValue())));

		public static final Supplier<List<OreConfiguration.TargetBlockState>> BRILLIANCE_TARGET_LIST = Suppliers.memoize(() -> List.of(
				OreConfiguration.target(STONE_ORE_REPLACEABLES, BlockRegistry.BRILLIANT_STONE.get().defaultBlockState()),
				OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, BlockRegistry.BRILLIANT_DEEPSLATE.get().defaultBlockState())));
		public static final RegistryObject<ConfiguredFeature<?, ?>> BRILLIANCE_FEATURE = CONFIGURED_FEATURES.register("brilliance",
				() -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(BRILLIANCE_TARGET_LIST.get(), CommonConfig.BRILLIANT_STONE_SIZE.getConfigValue())));

		public static final Supplier<List<OreConfiguration.TargetBlockState>> SOULSTONE_TARGET_LIST = Suppliers.memoize(() -> List.of(
				OreConfiguration.target(STONE_ORE_REPLACEABLES, BlockRegistry.SOULSTONE_ORE.get().defaultBlockState()),
				OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, BlockRegistry.DEEPSLATE_SOULSTONE_ORE.get().defaultBlockState())));
		public static final RegistryObject<ConfiguredFeature<?, ?>> SOULSTONE_FEATURE = CONFIGURED_FEATURES.register("soulstone_ore", () -> new ConfiguredFeature<>(Feature.ORE,
				new OreConfiguration(SOULSTONE_TARGET_LIST.get(), CommonConfig.SOULSTONE_SIZE.getConfigValue())));

		public static final RegistryObject<ConfiguredFeature<?, ?>> SURFACE_SOULSTONE_FEATURE = CONFIGURED_FEATURES.register("surface_soulstone_ore", () -> new ConfiguredFeature<>(Feature.ORE,
				new OreConfiguration(SOULSTONE_TARGET_LIST.get(), CommonConfig.SURFACE_SOULSTONE_SIZE.getConfigValue())));


		public static final Supplier<List<OreConfiguration.TargetBlockState>> NATURAL_QUARTZ_TARGET_LIST = Suppliers.memoize(() -> List.of(
				OreConfiguration.target(STONE_ORE_REPLACEABLES, BlockRegistry.NATURAL_QUARTZ_ORE.get().defaultBlockState()),
				OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, BlockRegistry.DEEPSLATE_QUARTZ_ORE.get().defaultBlockState())));
		public static final RegistryObject<ConfiguredFeature<?, ?>> NATURAL_QUARTZ_FEATURE = CONFIGURED_FEATURES.register("natural_quartz", () -> new ConfiguredFeature<>(Feature.ORE,
				new OreConfiguration(NATURAL_QUARTZ_TARGET_LIST.get(), CommonConfig.NATURAL_QUARTZ_SIZE.getConfigValue())));

		public static final RegistryObject<ConfiguredFeature<?, ?>> QUARTZ_GEODE_FEATURE = CONFIGURED_FEATURES.register("quartz_geode",
				() -> new ConfiguredFeature<>(Feature.GEODE, new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR), BlockStateProvider.simple(BlockRegistry.NATURAL_QUARTZ_ORE.get()), BlockStateProvider.simple(BlockRegistry.NATURAL_QUARTZ_ORE.get()), BlockStateProvider.simple(Blocks.TUFF), BlockStateProvider.simple(Blocks.SMOOTH_BASALT), List.of(BlockRegistry.NATURAL_QUARTZ_CLUSTER.get().defaultBlockState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS), new GeodeLayerSettings(1D, 1.2D, 2.2D, 2.8D), new GeodeCrackSettings(1f, 4.0D, 3), 0.85D, 0.2D, true, UniformInt.of(3, 5), UniformInt.of(2, 3), UniformInt.of(0, 1), -16, 16, 0.1D, 1)));
		public static final RegistryObject<ConfiguredFeature<?, ?>> DEEPSLATE_QUARTZ_GEODE_FEATURE = CONFIGURED_FEATURES.register("deepslate_quartz_geode",
				() -> new ConfiguredFeature<>(Feature.GEODE, new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR), BlockStateProvider.simple(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get()), BlockStateProvider.simple(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get()), BlockStateProvider.simple(Blocks.CALCITE), BlockStateProvider.simple(Blocks.SMOOTH_BASALT), List.of(BlockRegistry.NATURAL_QUARTZ_CLUSTER.get().defaultBlockState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS), new GeodeLayerSettings(1D, 1.4D, 2.6D, 4.2D), new GeodeCrackSettings(1f, 4.0D, 3), 0.85D, 0.2D, true, UniformInt.of(3, 5), UniformInt.of(2, 3), UniformInt.of(0, 1), -16, 16, 0.1D, 1)));

		public static final RegistryObject<ConfiguredFeature<?, ?>> RARE_EARTH_GEODE_FEATURE = CONFIGURED_FEATURES.register("rare_earth_geode",
				() -> new ConfiguredFeature<>(RARE_EARTHS_GEODE.get(), new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.RAW_GOLD_BLOCK), BlockStateProvider.simple(Blocks.DEEPSLATE_GOLD_ORE), BlockStateProvider.simple(Blocks.RAW_GOLD_BLOCK), BlockStateProvider.simple(Blocks.CALCITE), BlockStateProvider.simple(Blocks.SMOOTH_BASALT), List.of(Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS), new GeodeLayerSettings(0.02D, 0.2D, 0.6D, 1.4D), new GeodeCrackSettings(0.6f, 2.0D, 3), 0.85D, 0.2D, true, UniformInt.of(3, 5), UniformInt.of(2, 3), UniformInt.of(0, 1), -16, 16, 0.1D, 1)));
	}

	public static final class PlacedFeatures {

		public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, MALUM);
		public static final RegistryObject<PlacedFeature> RUNEWOOD_TREE = PLACED_FEATURES.register("common_runewood",
				() -> new PlacedFeature(ConfiguredFeatures.RUNEWOOD_TREE_FEATURE.getHolder().get(),
						List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, new ChancePlacementFilter(CommonConfig.COMMON_RUNEWOOD_CHANCE.getConfigValue().floatValue()), CountPlacement.of(3))));
		public static final RegistryObject<PlacedFeature> RARE_RUNEWOOD_TREE = PLACED_FEATURES.register("rare_runewood",
				() -> new PlacedFeature(ConfiguredFeatures.RUNEWOOD_TREE_FEATURE.getHolder().get(),
						List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, new ChancePlacementFilter(CommonConfig.RARE_RUNEWOOD_CHANCE.getConfigValue().floatValue()), CountPlacement.of(3))));

		public static final RegistryObject<PlacedFeature> BLAZING_QUARTZ_FEATURE = PLACED_FEATURES.register("blazing_quartz_ore",
				() -> new PlacedFeature(ConfiguredFeatures.BLAZING_QUARTZ_FEATURE.getHolder().get(),
						List.of(CountPlacement.of(CommonConfig.BLAZE_QUARTZ_AMOUNT.getConfigValue()), InSquarePlacement.spread(), PlacementUtils.RANGE_8_8)));

		public static final RegistryObject<PlacedFeature> BRILLIANCE_FEATURE = PLACED_FEATURES.register("brilliant_stone",
				() -> new PlacedFeature(ConfiguredFeatures.BRILLIANCE_FEATURE.getHolder().get(),
						List.of(CountPlacement.of(CommonConfig.BRILLIANT_STONE_AMOUNT.getConfigValue()), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.BRILLIANT_STONE_MIN_Y.getConfigValue()), VerticalAnchor.absolute(CommonConfig.BRILLIANT_STONE_MAX_Y.getConfigValue())))));

		public static final RegistryObject<PlacedFeature> SOULSTONE_FEATURE = PLACED_FEATURES.register("soulstone",
				() -> new PlacedFeature(ConfiguredFeatures.SOULSTONE_FEATURE.getHolder().get(),
						List.of(CountPlacement.of(CommonConfig.SOULSTONE_AMOUNT.getConfigValue()), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.SOULSTONE_MIN_Y.getConfigValue()), VerticalAnchor.absolute(CommonConfig.SOULSTONE_MAX_Y.getConfigValue())))));

		public static final RegistryObject<PlacedFeature> SURFACE_SOULSTONE_FEATURE = PLACED_FEATURES.register("surface_soulstone",
				() -> new PlacedFeature(ConfiguredFeatures.SURFACE_SOULSTONE_FEATURE.getHolder().get(),
						List.of(CountPlacement.of(CommonConfig.SURFACE_SOULSTONE_AMOUNT.getConfigValue()), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.SURFACE_SOULSTONE_MIN_Y.getConfigValue()), VerticalAnchor.absolute(CommonConfig.SURFACE_SOULSTONE_MAX_Y.getConfigValue())))));

		public static final RegistryObject<PlacedFeature> NATURAL_QUARTZ_FEATURE = PLACED_FEATURES.register("natural_quartz",
				() -> new PlacedFeature(ConfiguredFeatures.NATURAL_QUARTZ_FEATURE.getHolder().get(),
						List.of(CountPlacement.of(CommonConfig.NATURAL_QUARTZ_AMOUNT.getConfigValue()), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.NATURAL_QUARTZ_MIN_Y.getConfigValue()), VerticalAnchor.absolute(CommonConfig.NATURAL_QUARTZ_MAX_Y.getConfigValue())))));

		public static final RegistryObject<PlacedFeature> QUARTZ_GEODE_FEATURE = PLACED_FEATURES.register("quartz_geode",
				() -> new PlacedFeature(ConfiguredFeatures.QUARTZ_GEODE_FEATURE.getHolder().get(),
						List.of(RarityFilter.onAverageOnceEvery(48), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48)), BiomeFilter.biome(), DimensionPlacementFilter.of(DimensionPlacementFilter.fromStrings(CommonConfig.QUARTZ_GEODE_ALLOWED_DIMENSIONS.getConfigValue())))));

		public static final RegistryObject<PlacedFeature> DEEPSLATE_QUARTZ_GEODE_FEATURE = PLACED_FEATURES.register("deepslate_quartz_geode",
				() -> new PlacedFeature(ConfiguredFeatures.DEEPSLATE_QUARTZ_GEODE_FEATURE.getHolder().get(),
						List.of(RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(-10)), BiomeFilter.biome(), DimensionPlacementFilter.of(DimensionPlacementFilter.fromStrings(CommonConfig.QUARTZ_GEODE_ALLOWED_DIMENSIONS.getConfigValue())))));

		public static final RegistryObject<PlacedFeature> RARE_EARTH_GEODE_FEATURE = PLACED_FEATURES.register("rare_earth_geode",
				() -> new PlacedFeature(ConfiguredFeatures.RARE_EARTH_GEODE_FEATURE.getHolder().get(),
						List.of(RarityFilter.onAverageOnceEvery(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.aboveBottom(40)), BiomeFilter.biome(), DimensionPlacementFilter.of(DimensionPlacementFilter.fromStrings(CommonConfig.RARE_EARTHS_ALLOWED_DIMENSIONS.getConfigValue())))));
	}
}