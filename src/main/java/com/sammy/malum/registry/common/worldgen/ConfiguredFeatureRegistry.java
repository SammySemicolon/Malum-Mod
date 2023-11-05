package com.sammy.malum.registry.common.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;


public class ConfiguredFeatureRegistry {

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SOULSTONE_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("soulstone_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_BRILLIANT_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("brilliant_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_NATURAL_QUARTZ_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("natural_quartz_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_BLAZING_QUARTZ_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("blazing_quartz_ore"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_RUNEWOOD_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("runewood_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SOULWOOD_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("soulwood_tree"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_CTHONIC_GOLD_GEODE_FEATURE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("cthonic_gold_geode"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_QUARTZ_GEODE_FEATURE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("quartz_geode"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_DEEPSLATE_QUARTZ_GEODE_FEATURE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("deepslate_quartz_geode"));


    private static final RuleTest STONE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    private static final RuleTest DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

    public static final List<OreConfiguration.TargetBlockState> SOULSTONE_TARGET_LIST = List.of(
            OreConfiguration.target(STONE_ORE_REPLACEABLES, BlockRegistry.SOULSTONE_ORE.get().defaultBlockState()),
            OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, BlockRegistry.DEEPSLATE_SOULSTONE_ORE.get().defaultBlockState()));

    public static final List<OreConfiguration.TargetBlockState> BRILLIANT_TARGET_LIST = List.of(
            OreConfiguration.target(STONE_ORE_REPLACEABLES, BlockRegistry.BRILLIANT_STONE.get().defaultBlockState()),
            OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, BlockRegistry.BRILLIANT_DEEPSLATE.get().defaultBlockState()));

    public static final List<OreConfiguration.TargetBlockState> NATURAL_QUARTZ_TARGET_LIST = List.of(
            OreConfiguration.target(STONE_ORE_REPLACEABLES, BlockRegistry.NATURAL_QUARTZ_ORE.get().defaultBlockState()),
            OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, BlockRegistry.DEEPSLATE_QUARTZ_ORE.get().defaultBlockState()));

    public static final List<OreConfiguration.TargetBlockState> BLAZING_QUARTZ_TARGET_LIST = List.of(
            OreConfiguration.target(new TagMatchTest(BlockTags.BASE_STONE_NETHER), BlockRegistry.BLAZING_QUARTZ_ORE.get().defaultBlockState()));


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(ConfiguredFeatureRegistry.CONFIGURED_SOULSTONE_ORE, addOreConfig(SOULSTONE_TARGET_LIST, 8));
        context.register(ConfiguredFeatureRegistry.CONFIGURED_BRILLIANT_ORE, addOreConfig(BRILLIANT_TARGET_LIST, 4));
        context.register(ConfiguredFeatureRegistry.CONFIGURED_NATURAL_QUARTZ_ORE, addOreConfig(NATURAL_QUARTZ_TARGET_LIST, 5));
        context.register(ConfiguredFeatureRegistry.CONFIGURED_BLAZING_QUARTZ_ORE, addOreConfig(BLAZING_QUARTZ_TARGET_LIST, 14));

        context.register(ConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE, addTreeConfig(FeatureRegistry.RUNEWOOD_TREE.get()));
        context.register(ConfiguredFeatureRegistry.CONFIGURED_SOULWOOD_TREE, addTreeConfig(FeatureRegistry.SOULWOOD_TREE.get()));


        context.register(ConfiguredFeatureRegistry.CONFIGURED_QUARTZ_GEODE_FEATURE, new ConfiguredFeature<>(Feature.GEODE, new GeodeConfiguration(
                new GeodeBlockSettings(
                        BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(BlockRegistry.NATURAL_QUARTZ_ORE.get()),
                        BlockStateProvider.simple(BlockRegistry.NATURAL_QUARTZ_ORE.get()),
                        BlockStateProvider.simple(Blocks.TUFF), BlockStateProvider.simple(Blocks.SMOOTH_BASALT),
                        List.of(BlockRegistry.NATURAL_QUARTZ_CLUSTER.get().defaultBlockState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                new GeodeLayerSettings(1D, 1.2D, 2.2D, 2.8D),
                new GeodeCrackSettings(1f, 4.0D, 3),
                0.85D,
                0.2D,
                true,
                UniformInt.of(3, 5),
                UniformInt.of(2, 3),
                UniformInt.of(0, 1),
                -16,
                16,
                0.1D,
                1)
        ));


        context.register(ConfiguredFeatureRegistry.CONFIGURED_DEEPSLATE_QUARTZ_GEODE_FEATURE, new ConfiguredFeature<>(Feature.GEODE, new GeodeConfiguration(
                new GeodeBlockSettings(
                        BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get()),
                        BlockStateProvider.simple(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get()),
                        BlockStateProvider.simple(Blocks.CALCITE),
                        BlockStateProvider.simple(Blocks.SMOOTH_BASALT),
                        List.of(BlockRegistry.NATURAL_QUARTZ_CLUSTER.get().defaultBlockState()),
                        BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                new GeodeLayerSettings(1D, 1.4D, 2.6D, 4.2D),
                new GeodeCrackSettings(1f, 4.0D, 3),
                0.85D,
                0.2D,
                true,
                UniformInt.of(3, 5),
                UniformInt.of(2, 3),
                UniformInt.of(0, 1),
                -16,
                16,
                0.1D,
                1)
        ));

        context.register(ConfiguredFeatureRegistry.CONFIGURED_CTHONIC_GOLD_GEODE_FEATURE, new ConfiguredFeature<>(Feature.GEODE, new GeodeConfiguration(
                new GeodeBlockSettings(
                        BlockStateProvider.simple(Blocks.RAW_GOLD_BLOCK),
                        BlockStateProvider.simple(Blocks.DEEPSLATE_GOLD_ORE),
                        BlockStateProvider.simple(Blocks.RAW_GOLD_BLOCK),
                        BlockStateProvider.simple(Blocks.CALCITE),
                        BlockStateProvider.simple(Blocks.SMOOTH_BASALT),
                        List.of(Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState()),
                        BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                new GeodeLayerSettings(0.02D, 0.2D, 0.6D, 1.4D),
                new GeodeCrackSettings(0.6f, 2.0D, 3),
                0.85D,
                0.2D,
                true,
                UniformInt.of(3, 5),
                UniformInt.of(2, 3),
                UniformInt.of(0, 1),
                -16,
                16,
                0.1D,
                1)
        ));


    }

    private static ConfiguredFeature<?, ?> addTreeConfig(Feature<NoneFeatureConfiguration> feature) {
        return new ConfiguredFeature<>(feature, FeatureConfiguration.NONE);
    }

    private static ConfiguredFeature<?, ?> addOreConfig(List<OreConfiguration.TargetBlockState> targetList, int veinSize) {
        return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targetList, veinSize));
    }
}
