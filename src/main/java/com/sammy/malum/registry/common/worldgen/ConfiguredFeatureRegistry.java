package com.sammy.malum.registry.common.worldgen;

import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.*;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

import static com.sammy.malum.MalumMod.MALUM;
import static net.minecraft.tags.BlockTags.*;
import static net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration.INSTANCE;

public final class ConfiguredFeatureRegistry {
	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registries.CONFIGURED_FEATURE, MALUM);

	public static final RegistryObject<ConfiguredFeature<?, ?>> RUNEWOOD_TREE_FEATURE = CONFIGURED_FEATURES.register("runewood_tree", () -> new ConfiguredFeature<>(FeatureRegistry.RUNEWOOD_TREE.get(), INSTANCE));

	public static final RegistryObject<ConfiguredFeature<?, ?>> WEEPING_WELL_FEATURE = CONFIGURED_FEATURES.register("weeping_well", () -> new ConfiguredFeature<>(FeatureRegistry.WEEPING_WELL.get(), INSTANCE));

	public static final RegistryObject<ConfiguredFeature<?, ?>> BLAZING_QUARTZ_FEATURE = CONFIGURED_FEATURES.register("blazing_quartz", () -> new ConfiguredFeature<>(Feature.ORE,
			new OreConfiguration(new BlockMatchTest(Blocks.NETHERRACK), BlockRegistry.BLAZING_QUARTZ_ORE.get().defaultBlockState(), 14)));

	public static final Supplier<List<OreConfiguration.TargetBlockState>> BRILLIANCE_TARGET_LIST = () -> List.of(OreConfiguration.target(new TagMatchTest(STONE_ORE_REPLACEABLES), BlockRegistry.BRILLIANT_STONE.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(DEEPSLATE_ORE_REPLACEABLES), BlockRegistry.BRILLIANT_DEEPSLATE.get().defaultBlockState()));
	public static final RegistryObject<ConfiguredFeature<?, ?>> BRILLIANCE_FEATURE = CONFIGURED_FEATURES.register("brilliance", () -> new ConfiguredFeature<>(Feature.ORE,
			new OreConfiguration(BRILLIANCE_TARGET_LIST.get(), 4)));

	public static final Supplier<List<OreConfiguration.TargetBlockState>> SOULSTONE_TARGET_LIST =  () -> List.of(OreConfiguration.target(new TagMatchTest(STONE_ORE_REPLACEABLES), BlockRegistry.SOULSTONE_ORE.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(DEEPSLATE_ORE_REPLACEABLES), BlockRegistry.DEEPSLATE_SOULSTONE_ORE.get().defaultBlockState()));
	public static final RegistryObject<ConfiguredFeature<?, ?>> SOULSTONE_FEATURE = CONFIGURED_FEATURES.register("soulstone_ore", () -> new ConfiguredFeature<>(Feature.ORE,
			new OreConfiguration(SOULSTONE_TARGET_LIST.get(), 8)));

	public static final  RegistryObject<ConfiguredFeature<?, ?>> SURFACE_SOULSTONE_FEATURE = CONFIGURED_FEATURES.register("surface_soulstone_ore", () -> new ConfiguredFeature<>(Feature.ORE,
			new OreConfiguration(SOULSTONE_TARGET_LIST.get(), 4)));


	public static final Supplier<List<OreConfiguration.TargetBlockState>> NATURAL_QUARTZ_TARGET_LIST = () -> List.of(OreConfiguration.target(new TagMatchTest(STONE_ORE_REPLACEABLES), BlockRegistry.NATURAL_QUARTZ_ORE.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(DEEPSLATE_ORE_REPLACEABLES), BlockRegistry.DEEPSLATE_QUARTZ_ORE.get().defaultBlockState()));
	public static final  RegistryObject<ConfiguredFeature<?, ?>> NATURAL_QUARTZ_FEATURE = CONFIGURED_FEATURES.register("natural_quartz", () -> new ConfiguredFeature<>(Feature.ORE,
			new OreConfiguration(NATURAL_QUARTZ_TARGET_LIST.get(), 5)));

	public static final  RegistryObject<ConfiguredFeature<?, ?>> QUARTZ_GEODE_FEATURE = CONFIGURED_FEATURES.register("quartz_geode", () ->
			new ConfiguredFeature<>(Feature.GEODE, new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR), BlockStateProvider.simple(BlockRegistry.NATURAL_QUARTZ_ORE.get()), BlockStateProvider.simple(BlockRegistry.NATURAL_QUARTZ_ORE.get()), BlockStateProvider.simple(Blocks.TUFF), BlockStateProvider.simple(Blocks.SMOOTH_BASALT), List.of(BlockRegistry.NATURAL_QUARTZ_CLUSTER.get().defaultBlockState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS), new GeodeLayerSettings(1D, 1.2D, 2.2D, 2.8D), new GeodeCrackSettings(1f, 4.0D, 3), 0.85D, 0.2D, true, UniformInt.of(3, 5), UniformInt.of(2, 3), UniformInt.of(0, 1), -16, 16, 0.1D, 1)));

	public static final  RegistryObject<ConfiguredFeature<?, ?>> DEEPSLATE_QUARTZ_GEODE_FEATURE = CONFIGURED_FEATURES.register("deepslate_quartz_geode", () ->
			new ConfiguredFeature<>(Feature.GEODE, new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR), BlockStateProvider.simple(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get()), BlockStateProvider.simple(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get()), BlockStateProvider.simple(Blocks.CALCITE), BlockStateProvider.simple(Blocks.SMOOTH_BASALT), List.of(BlockRegistry.NATURAL_QUARTZ_CLUSTER.get().defaultBlockState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS), new GeodeLayerSettings(1D, 1.4D, 2.6D, 4.2D), new GeodeCrackSettings(1f, 4.0D, 3), 0.85D, 0.2D, true, UniformInt.of(3, 5), UniformInt.of(2, 3), UniformInt.of(0, 1), -16, 16, 0.1D, 1)));

	public static final  RegistryObject<ConfiguredFeature<?, ?>> CTHONIC_GOLD_GEODE_FEATURE = CONFIGURED_FEATURES.register("cthonic_gold_geode", () ->
			new ConfiguredFeature<>(FeatureRegistry.CTHONIC_GOLD_GEODE.get(), new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.RAW_GOLD_BLOCK), BlockStateProvider.simple(Blocks.DEEPSLATE_GOLD_ORE), BlockStateProvider.simple(Blocks.RAW_GOLD_BLOCK), BlockStateProvider.simple(Blocks.CALCITE), BlockStateProvider.simple(Blocks.SMOOTH_BASALT), List.of(Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS), new GeodeLayerSettings(0.02D, 0.2D, 0.6D, 1.4D), new GeodeCrackSettings(0.6f, 2.0D, 3), 0.85D, 0.2D, true, UniformInt.of(3, 5), UniformInt.of(2, 3), UniformInt.of(0, 1), -16, 16, 0.1D, 1)));
}
