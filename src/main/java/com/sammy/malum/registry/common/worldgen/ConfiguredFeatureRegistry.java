package com.sammy.malum.registry.common.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.worldgen.RunewoodTreeFeature;
import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;
import java.util.function.Supplier;


public class ConfiguredFeatureRegistry {

    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SOULSTONE_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("soulstone_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_BRILLIANT_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("brilliant_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_NATURAL_QUARTZ_ORE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("natural_quartz_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_RUNEWOOD_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("runewood_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_SOULWOOD_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, MalumMod.malumPath("soulwood_tree"));

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

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);


        context.register(ConfiguredFeatureRegistry.CONFIGURED_SOULSTONE_ORE, addOreConfig(SOULSTONE_TARGET_LIST, 30));
        context.register(ConfiguredFeatureRegistry.CONFIGURED_BRILLIANT_ORE, addOreConfig(BRILLIANT_TARGET_LIST, 30));
        context.register(ConfiguredFeatureRegistry.CONFIGURED_NATURAL_QUARTZ_ORE, addOreConfig(NATURAL_QUARTZ_TARGET_LIST, 30));

        context.register(ConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE, addTreeConfig(FeatureRegistry.RUNEWOOD_TREE.get()));
        //register(context, ConfiguredFeatureRegistry.CONFIGURED_SOULWOOD_TREE, () -> addTreeConfig(FeatureRegistry.SOULWOOD_TREE.get()));
    }

    private static ConfiguredFeature<?,?> addTreeConfig(Feature<NoneFeatureConfiguration> feature) {
        return new ConfiguredFeature<>(feature, FeatureConfiguration.NONE);
    }

    private static ConfiguredFeature<?, ?> addOreConfig(List<OreConfiguration.TargetBlockState> targetList, int veinSize) {
        return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targetList, veinSize));
    }
}
