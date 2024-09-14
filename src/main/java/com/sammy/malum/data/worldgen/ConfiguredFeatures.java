package com.sammy.malum.data.worldgen;

import com.sammy.malum.common.worldgen.tree.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.worldgen.*;
import net.minecraft.data.worldgen.*;
import net.minecraft.tags.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.*;

public class ConfiguredFeatures {

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

    public static final List<OreConfiguration.TargetBlockState> CTHONIC_GOLD_TARGET_LIST = List.of(
            OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, BlockRegistry.CTHONIC_GOLD_ORE.get().defaultBlockState()),
            OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState()));

    public static final List<OreConfiguration.TargetBlockState> BLAZING_QUARTZ_TARGET_LIST = List.of(
            OreConfiguration.target(new TagMatchTest(BlockTags.BASE_STONE_NETHER), BlockRegistry.BLAZING_QUARTZ_ORE.get().defaultBlockState()));

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(ConfiguredFeatureRegistry.CONFIGURED_SOULSTONE_ORE, addOreConfig(SOULSTONE_TARGET_LIST, 8));
        context.register(ConfiguredFeatureRegistry.CONFIGURED_BRILLIANT_ORE, addOreConfig(BRILLIANT_TARGET_LIST, 4));
        context.register(ConfiguredFeatureRegistry.CONFIGURED_NATURAL_QUARTZ_ORE, addOreConfig(NATURAL_QUARTZ_TARGET_LIST, 5));
        context.register(ConfiguredFeatureRegistry.CONFIGURED_CTHONIC_GOLD_ORE_FEATURE, addOreConfig(FeatureRegistry.CTHONIC_GOLD_ORE.get(), CTHONIC_GOLD_TARGET_LIST, 8));
        context.register(ConfiguredFeatureRegistry.CONFIGURED_BLAZING_QUARTZ_ORE, addOreConfig(BLAZING_QUARTZ_TARGET_LIST, 14));

        context.register(ConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE, addTreeConfig(FeatureRegistry.RUNEWOOD_TREE.get(), new RunewoodTreeConfiguration(
                BlockRegistry.RUNEWOOD_SAPLING.get(),
                BlockRegistry.RUNEWOOD_LOG.get(),
                BlockRegistry.EXPOSED_RUNEWOOD_LOG.get(),
                BlockRegistry.RUNEWOOD_LEAVES.get(),
                BlockRegistry.HANGING_RUNEWOOD_LEAVES.get()
        )));
        context.register(ConfiguredFeatureRegistry.CONFIGURED_AZURE_RUNEWOOD_TREE, addTreeConfig(FeatureRegistry.RUNEWOOD_TREE.get(), new RunewoodTreeConfiguration(
                BlockRegistry.RUNEWOOD_SAPLING.get(),
                BlockRegistry.RUNEWOOD_LOG.get(),
                BlockRegistry.EXPOSED_RUNEWOOD_LOG.get(),
                BlockRegistry.AZURE_RUNEWOOD_LEAVES.get(),
                BlockRegistry.HANGING_AZURE_RUNEWOOD_LEAVES.get()
        )));

        context.register(ConfiguredFeatureRegistry.CONFIGURED_SOULWOOD_TREE, addTreeConfig(FeatureRegistry.SOULWOOD_TREE.get(), NoneFeatureConfiguration.INSTANCE));

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
    }

    private static <T extends FeatureConfiguration, K extends Feature<T>> ConfiguredFeature<?, ?> addTreeConfig(K feature, T config) {
        return new ConfiguredFeature<>(feature, config);
    }

    private static ConfiguredFeature<?, ?> addOreConfig(List<OreConfiguration.TargetBlockState> targetList, int veinSize) {
        return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targetList, veinSize));
    }

    private static ConfiguredFeature<?, ?> addOreConfig(Feature<OreConfiguration> feature, List<OreConfiguration.TargetBlockState> targetList, int veinSize) {
        return new ConfiguredFeature<>(feature, new OreConfiguration(targetList, veinSize));
    }
}