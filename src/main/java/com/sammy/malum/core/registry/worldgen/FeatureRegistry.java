package com.sammy.malum.core.registry.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.worldgen.RunewoodTreeFeature;
import com.sammy.malum.common.worldgen.SoulwoodTreeFeature;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.registry.block.BlockRegistry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import static com.sammy.malum.MalumMod.MODID;
import static net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration.INSTANCE;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class FeatureRegistry {

    public static final DeferredRegister<Feature<?>> FEATURE_TYPES = DeferredRegister.create(ForgeRegistries.FEATURES, MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUNEWOOD_TREE = FEATURE_TYPES.register("runewood_tree", RunewoodTreeFeature::new);
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SOULWOOD_TREE = FEATURE_TYPES.register("soulwood_tree", SoulwoodTreeFeature::new);
    public static PlacedFeature BLAZING_QUARTZ_FEATURE, BRILLIANT_STONE_FEATURE, SOULSTONE_FEATURE, SURFACE_SOULSTONE_FEATURE, RUNEWOOD_TREE_FEATURE, RARE_RUNEWOOD_TREE_FEATURE;

    public static final RuleTest IN_STONE = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    public static final RuleTest IN_DEEPSLATE = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    public static final RuleTest IN_NETHERRACK = new TagMatchTest(Tags.Blocks.NETHERRACK);


    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            RUNEWOOD_TREE_FEATURE = registerPlacedFeature("common_runewood", RUNEWOOD_TREE.get().configured(INSTANCE),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, PlacementUtils.countExtra(0, CommonConfig.COMMON_RUNEWOOD_CHANCE.get(), 3));
            RARE_RUNEWOOD_TREE_FEATURE = registerPlacedFeature("rare_runewood", RUNEWOOD_TREE.get().configured(INSTANCE),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, PlacementUtils.countExtra(0, CommonConfig.RARE_RUNEWOOD_CHANCE.get(), 2));

            BLAZING_QUARTZ_FEATURE = registerPlacedFeature("blazing_quartz", Feature.ORE.configured(
                            new OreConfiguration(IN_NETHERRACK, BlockRegistry.BLAZING_QUARTZ_ORE.get().defaultBlockState(),
                                    CommonConfig.BLAZE_QUARTZ_SIZE.get())),
                    CountPlacement.of(CommonConfig.BLAZE_QUARTZ_AMOUNT.get()), PlacementUtils.RANGE_8_8);

            List<OreConfiguration.TargetBlockState> BRILLIANT_TARGET = List.of(OreConfiguration.target(IN_STONE, BlockRegistry.BRILLIANT_STONE.get().defaultBlockState()), OreConfiguration.target(IN_DEEPSLATE, BlockRegistry.BRILLIANT_DEEPSLATE.get().defaultBlockState()));
            BRILLIANT_STONE_FEATURE = registerPlacedFeature("brilliant_stone", Feature.ORE.configured(
                            new OreConfiguration(BRILLIANT_TARGET, CommonConfig.BRILLIANT_STONE_SIZE.get())),
                    CountPlacement.of(CommonConfig.BRILLIANT_STONE_AMOUNT.get()), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.BRILLIANT_STONE_MIN_Y.get()), VerticalAnchor.absolute(CommonConfig.BRILLIANT_STONE_MAX_Y.get())));

            List<OreConfiguration.TargetBlockState> SOULSTONE_TARGET = List.of(OreConfiguration.target(IN_STONE, BlockRegistry.SOULSTONE_ORE.get().defaultBlockState()), OreConfiguration.target(IN_DEEPSLATE, BlockRegistry.DEEPSLATE_SOULSTONE_ORE.get().defaultBlockState()));
            SOULSTONE_FEATURE = registerPlacedFeature("soulstone_feature", Feature.ORE.configured(
                            new OreConfiguration(SOULSTONE_TARGET, CommonConfig.SOULSTONE_SIZE.get())),
                    CountPlacement.of(CommonConfig.SOULSTONE_AMOUNT.get()), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.SOULSTONE_MIN_Y.get()), VerticalAnchor.absolute(CommonConfig.SOULSTONE_MAX_Y.get())));

            SURFACE_SOULSTONE_FEATURE = registerPlacedFeature("surface_soulstone_feature", Feature.ORE.configured(
                            new OreConfiguration(SOULSTONE_TARGET, CommonConfig.SURFACE_SOULSTONE_SIZE.get())),
                    CountPlacement.of(CommonConfig.SURFACE_SOULSTONE_AMOUNT.get()), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.SURFACE_SOULSTONE_MIN_Y.get()), VerticalAnchor.absolute(CommonConfig.SURFACE_SOULSTONE_MAX_Y.get())));
        });
    }

    static <C extends FeatureConfiguration, F extends Feature<C>> PlacedFeature registerPlacedFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        return PlacementUtils.register(registryName, registerConfiguredFeature(registryName, feature).placed(placementModifiers));
    }

    static <C extends FeatureConfiguration, F extends Feature<C>> ConfiguredFeature<C, F> registerConfiguredFeature(String registryName, ConfiguredFeature<C, F> feature) {
        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, DataHelper.prefix(registryName), feature);
    }
}