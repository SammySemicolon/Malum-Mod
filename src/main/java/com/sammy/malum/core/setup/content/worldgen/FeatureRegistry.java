package com.sammy.malum.core.setup.content.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.worldgen.RareEarthsGeode;
import com.sammy.malum.common.worldgen.RunewoodTreeFeature;
import com.sammy.malum.common.worldgen.SoulwoodTreeFeature;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import com.sammy.ortus.systems.worldgen.ChancePlacementFilter;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
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
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import static com.sammy.malum.MalumMod.MALUM;
import static net.minecraft.data.worldgen.features.OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;
import static net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration.INSTANCE;

@Mod.EventBusSubscriber(modid= MalumMod.MALUM, bus= Mod.EventBusSubscriber.Bus.MOD)
public class FeatureRegistry {

    public static final DeferredRegister<Feature<?>> FEATURE_TYPES = DeferredRegister.create(ForgeRegistries.FEATURES, MALUM);

    public static final RegistryObject<RunewoodTreeFeature> RUNEWOOD_TREE = FEATURE_TYPES.register("runewood_tree", RunewoodTreeFeature::new);
    public static final RegistryObject<SoulwoodTreeFeature> SOULWOOD_TREE = FEATURE_TYPES.register("soulwood_tree", SoulwoodTreeFeature::new);
    public static final RegistryObject<RareEarthsGeode> RARE_EARTHS_GEODE = FEATURE_TYPES.register("rare_earths_geode", ()->new RareEarthsGeode(GeodeConfiguration.CODEC));


    public static final class ConfiguredFeatures {
        public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> RUNEWOOD_TREE_FEATURE = FeatureUtils.register("runewood_tree", FeatureRegistry.RUNEWOOD_TREE.get(), INSTANCE);

        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> BLAZING_QUARTZ_FEATURE = FeatureUtils.register("blazing_quartz", Feature.ORE,
                new OreConfiguration(OreFeatures.NETHERRACK, BlockRegistry.BLAZING_QUARTZ_ORE.get().defaultBlockState(), CommonConfig.BLAZE_QUARTZ_SIZE.getConfigValue()));

        public static final List<OreConfiguration.TargetBlockState> BRILLIANCE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, BlockRegistry.BRILLIANT_STONE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, BlockRegistry.BRILLIANT_DEEPSLATE.get().defaultBlockState()));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> BRILLIANCE_FEATURE = FeatureUtils.register("brilliance", Feature.ORE,
                new OreConfiguration(BRILLIANCE_TARGET_LIST, CommonConfig.BRILLIANT_STONE_SIZE.getConfigValue()));

        public static final List<OreConfiguration.TargetBlockState> SOULSTONE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, BlockRegistry.SOULSTONE_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, BlockRegistry.DEEPSLATE_SOULSTONE_ORE.get().defaultBlockState()));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> SOULSTONE_FEATURE = FeatureUtils.register("soulstone_ore", Feature.ORE,
                new OreConfiguration(SOULSTONE_TARGET_LIST, CommonConfig.SOULSTONE_SIZE.getConfigValue()));

        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> SURFACE_SOULSTONE_FEATURE = FeatureUtils.register("surface_soulstone_ore", Feature.ORE,
                new OreConfiguration(SOULSTONE_TARGET_LIST, CommonConfig.SURFACE_SOULSTONE_SIZE.getConfigValue()));


        public static final List<OreConfiguration.TargetBlockState> NATURAL_QUARTZ_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, BlockRegistry.NATURAL_QUARTZ_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, BlockRegistry.DEEPSLATE_QUARTZ_ORE.get().defaultBlockState()));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> NATURAL_QUARTZ_FEATURE = FeatureUtils.register("natural_quartz", Feature.ORE,
                new OreConfiguration(NATURAL_QUARTZ_TARGET_LIST, CommonConfig.NATURAL_QUARTZ_SIZE.getConfigValue()));

        public static final Holder<ConfiguredFeature<GeodeConfiguration, ?>> QUARTZ_GEODE_FEATURE = FeatureUtils.register("quartz_geode", Feature.GEODE, new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR), BlockStateProvider.simple(BlockRegistry.NATURAL_QUARTZ_ORE.get()), BlockStateProvider.simple(BlockRegistry.NATURAL_QUARTZ_ORE.get()), BlockStateProvider.simple(Blocks.TUFF), BlockStateProvider.simple(Blocks.SMOOTH_BASALT), List.of(BlockRegistry.NATURAL_QUARTZ_CLUSTER.get().defaultBlockState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS), new GeodeLayerSettings(1D, 1.2D, 2.2D, 2.8D), new GeodeCrackSettings(1f, 4.0D, 3), 0.85D, 0.2D, true, UniformInt.of(3, 5), UniformInt.of(2, 3), UniformInt.of(0, 1), -16, 16, 0.1D, 1));
        public static final Holder<ConfiguredFeature<GeodeConfiguration, ?>> DEEPSLATE_QUARTZ_GEODE_FEATURE = FeatureUtils.register("deepslate_quartz_geode", Feature.GEODE, new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR), BlockStateProvider.simple(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get()), BlockStateProvider.simple(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get()), BlockStateProvider.simple(Blocks.CALCITE), BlockStateProvider.simple(Blocks.SMOOTH_BASALT), List.of(BlockRegistry.NATURAL_QUARTZ_CLUSTER.get().defaultBlockState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS), new GeodeLayerSettings(1D, 1.4D, 2.6D, 4.2D), new GeodeCrackSettings(1f, 4.0D, 3), 0.85D, 0.2D, true, UniformInt.of(3, 5), UniformInt.of(2, 3), UniformInt.of(0, 1), -16, 16, 0.1D, 1));

        public static final Holder<ConfiguredFeature<GeodeConfiguration, ?>> RARE_EARTH_GEODE_FEATURE = FeatureUtils.register("rare_earth_geode", RARE_EARTHS_GEODE.get(), new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.RAW_GOLD_BLOCK), BlockStateProvider.simple(Blocks.DEEPSLATE_GOLD_ORE), BlockStateProvider.simple(Blocks.RAW_GOLD_BLOCK), BlockStateProvider.simple(Blocks.CALCITE), BlockStateProvider.simple(Blocks.SMOOTH_BASALT), List.of(Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS), new GeodeLayerSettings(0.02D, 0.2D, 0.6D, 1.4D), new GeodeCrackSettings(0.6f, 2.0D, 3), 0.85D, 0.2D, true, UniformInt.of(3, 5), UniformInt.of(2, 3), UniformInt.of(0, 1), -16, 16, 0.1D, 1));
    }

    public static final class PlacedFeatures {
        public static final Holder<PlacedFeature> RUNEWOOD_TREE = PlacementUtils.register("common_runewood", ConfiguredFeatures.RUNEWOOD_TREE_FEATURE,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, new ChancePlacementFilter(CommonConfig.COMMON_RUNEWOOD_CHANCE.getConfigValue().floatValue()), CountPlacement.of(3));
        public static final Holder<PlacedFeature> RARE_RUNEWOOD_TREE = PlacementUtils.register("rare_runewood", ConfiguredFeatures.RUNEWOOD_TREE_FEATURE,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, new ChancePlacementFilter(CommonConfig.RARE_RUNEWOOD_CHANCE.getConfigValue().floatValue()), CountPlacement.of(3));

        public static final Holder<PlacedFeature> BLAZING_QUARTZ_FEATURE = PlacementUtils.register("blazing_quartz_ore", ConfiguredFeatures.BLAZING_QUARTZ_FEATURE,
                CountPlacement.of(CommonConfig.BLAZE_QUARTZ_AMOUNT.getConfigValue()), InSquarePlacement.spread(), PlacementUtils.RANGE_8_8);

        public static final Holder<PlacedFeature> BRILLIANCE_FEATURE = PlacementUtils.register("brilliant_stone", ConfiguredFeatures.BRILLIANCE_FEATURE,
                CountPlacement.of(CommonConfig.BRILLIANT_STONE_AMOUNT.getConfigValue()), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.BRILLIANT_STONE_MIN_Y.getConfigValue()), VerticalAnchor.absolute(CommonConfig.BRILLIANT_STONE_MAX_Y.getConfigValue())));

        public static final Holder<PlacedFeature> SOULSTONE_FEATURE = PlacementUtils.register("soulstone", ConfiguredFeatures.SOULSTONE_FEATURE,
                CountPlacement.of(CommonConfig.SOULSTONE_AMOUNT.getConfigValue()), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.SOULSTONE_MIN_Y.getConfigValue()), VerticalAnchor.absolute(CommonConfig.SOULSTONE_MAX_Y.getConfigValue())));

        public static final Holder<PlacedFeature> SURFACE_SOULSTONE_FEATURE = PlacementUtils.register("surface_soulstone", ConfiguredFeatures.SURFACE_SOULSTONE_FEATURE,
                CountPlacement.of(CommonConfig.SURFACE_SOULSTONE_AMOUNT.getConfigValue()), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.SURFACE_SOULSTONE_MIN_Y.getConfigValue()), VerticalAnchor.absolute(CommonConfig.SURFACE_SOULSTONE_MAX_Y.getConfigValue())));

        public static final Holder<PlacedFeature> NATURAL_QUARTZ_FEATURE = PlacementUtils.register("natural_quartz", ConfiguredFeatures.NATURAL_QUARTZ_FEATURE,
                CountPlacement.of(CommonConfig.NATURAL_QUARTZ_AMOUNT.getConfigValue()), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.NATURAL_QUARTZ_MIN_Y.getConfigValue()), VerticalAnchor.absolute(CommonConfig.NATURAL_QUARTZ_MAX_Y.getConfigValue())));

        public static final Holder<PlacedFeature> QUARTZ_GEODE_FEATURE = PlacementUtils.register("quartz_geode", ConfiguredFeatures.QUARTZ_GEODE_FEATURE,
                RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48)), BiomeFilter.biome());

        public static final Holder<PlacedFeature> DEEPSLATE_QUARTZ_GEODE_FEATURE = PlacementUtils.register("deepslate_quartz_geode", ConfiguredFeatures.DEEPSLATE_QUARTZ_GEODE_FEATURE,
                RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(-10)), BiomeFilter.biome());

        public static final Holder<PlacedFeature> RARE_EARTH_GEODE_FEATURE = PlacementUtils.register("rare_earth_geode", ConfiguredFeatures.RARE_EARTH_GEODE_FEATURE,
                RarityFilter.onAverageOnceEvery(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.aboveBottom(40)), BiomeFilter.biome());

    }
}