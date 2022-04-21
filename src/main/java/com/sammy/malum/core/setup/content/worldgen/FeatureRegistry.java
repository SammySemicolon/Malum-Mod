package com.sammy.malum.core.setup.content.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.worldgen.RunewoodTreeFeature;
import com.sammy.malum.common.worldgen.SoulwoodTreeFeature;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import com.sammy.malum.core.systems.worldgen.ChancePlacementFilter;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import static com.sammy.malum.MalumMod.MODID;
import static net.minecraft.data.worldgen.features.OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;
import static net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration.INSTANCE;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class FeatureRegistry {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MODID);

    public static final RegistryObject<RunewoodTreeFeature> RUNEWOOD_TREE = FEATURES.register("runewood_tree", RunewoodTreeFeature::new);
    public static final RegistryObject<SoulwoodTreeFeature> SOULWOOD_TREE = FEATURES.register("soulwood_tree", SoulwoodTreeFeature::new);

    public static final class ConfiguredFeatures {
        public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> RUNEWOOD_TREE_FEATURE = FeatureUtils.register("runewood_tree", FeatureRegistry.RUNEWOOD_TREE.get(), INSTANCE);

        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> BLAZING_QUARTZ_FEATURE = FeatureUtils.register("blazing_quartz", Feature.ORE,
                new OreConfiguration(OreFeatures.NETHERRACK, BlockRegistry.BLAZING_QUARTZ_ORE.get().defaultBlockState(), CommonConfig.BLAZE_QUARTZ_SIZE.get()));

        public static final List<OreConfiguration.TargetBlockState> BRILLIANCE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, BlockRegistry.BRILLIANT_STONE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, BlockRegistry.BRILLIANT_DEEPSLATE.get().defaultBlockState()));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> BRILLIANCE_FEATURE = FeatureUtils.register("brilliance", Feature.ORE,
                new OreConfiguration(BRILLIANCE_TARGET_LIST, CommonConfig.BRILLIANT_STONE_SIZE.get()));

        public static final List<OreConfiguration.TargetBlockState> SOULSTONE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, BlockRegistry.SOULSTONE_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, BlockRegistry.DEEPSLATE_SOULSTONE_ORE.get().defaultBlockState()));
        public static final Holder<ConfiguredFeature<OreConfiguration, ?>> SOULSTONE_FEATURE = FeatureUtils.register("soulstone_ore", Feature.ORE,
                new OreConfiguration(SOULSTONE_TARGET_LIST, CommonConfig.SOULSTONE_SIZE.get()));
    }

    public static final class PlacedFeatures {
        public static final Holder<PlacedFeature> RUNEWOOD_TREE = PlacementUtils.register("common_runewood", ConfiguredFeatures.RUNEWOOD_TREE_FEATURE,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, new ChancePlacementFilter(CommonConfig.COMMON_RUNEWOOD_CHANCE.get().floatValue()), CountPlacement.of(3));
        public static final Holder<PlacedFeature> RARE_RUNEWOOD_TREE = PlacementUtils.register("rare_runewood", ConfiguredFeatures.RUNEWOOD_TREE_FEATURE,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, new ChancePlacementFilter(CommonConfig.RARE_RUNEWOOD_CHANCE.get().floatValue()), CountPlacement.of(3));

        public static final Holder<PlacedFeature> BLAZING_QUARTZ_FEATURE = PlacementUtils.register("blazing_quartz_ore", ConfiguredFeatures.BLAZING_QUARTZ_FEATURE,
                CountPlacement.of(CommonConfig.BLAZE_QUARTZ_AMOUNT.get()), PlacementUtils.RANGE_8_8);
        public static final Holder<PlacedFeature> BRILLIANCE_FEATURE = PlacementUtils.register("brilliant_stone", ConfiguredFeatures.BRILLIANCE_FEATURE,
                CountPlacement.of(CommonConfig.BRILLIANT_STONE_AMOUNT.get()), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.BRILLIANT_STONE_MIN_Y.get()), VerticalAnchor.absolute(CommonConfig.BRILLIANT_STONE_MAX_Y.get())));
        public static final Holder<PlacedFeature> SOULSTONE_FEATURE = PlacementUtils.register("soulstone", ConfiguredFeatures.SOULSTONE_FEATURE,
                CountPlacement.of(CommonConfig.SOULSTONE_AMOUNT.get()), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.SOULSTONE_MIN_Y.get()), VerticalAnchor.absolute(CommonConfig.SOULSTONE_MAX_Y.get())));
        public static final Holder<PlacedFeature> SURFACE_SOULSTONE_FEATURE = PlacementUtils.register("surface_soulstone", ConfiguredFeatures.SOULSTONE_FEATURE,
                CountPlacement.of(CommonConfig.SURFACE_SOULSTONE_AMOUNT.get()), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.SURFACE_SOULSTONE_MIN_Y.get()), VerticalAnchor.absolute(CommonConfig.SURFACE_SOULSTONE_MAX_Y.get())));

    }
}