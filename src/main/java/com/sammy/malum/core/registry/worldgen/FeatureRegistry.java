package com.sammy.malum.core.registry.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.Levelgen.RunewoodTreeFeature;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.registry.block.BlockRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;

@Mod.EventBusSubscriber(modid= MalumMod.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class FeatureRegistry
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUNEWOOD_TREE = FEATURES.register("runewood_tree", RunewoodTreeFeature::new);

    public static ConfiguredFeature<?, ?> COMMON_RUNEWOOD_TREE;
    public static ConfiguredFeature<?, ?> RARE_CONFIGURED_RUNEWOOD_TREE;

    public static ConfiguredFeature<?, ?> BLAZE_QUARTZ_ORE;
    public static ConfiguredFeature<?, ?> BRILLIANT_STONE;
    public static ConfiguredFeature<?, ?> SOULSTONE_ORE;
    public static ConfiguredFeature<?, ?> SOULSTONE_ORE_SURFACE;

    public static void register()
    {
        COMMON_RUNEWOOD_TREE = Registry.register(LevelGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "runewood_tree", RUNEWOOD_TREE.get().configured(INSTANCE).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, CommonConfig.COMMON_RUNEWOOD_CHANCE.get(), 1))));
        RARE_CONFIGURED_RUNEWOOD_TREE = Registry.register(LevelGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "rare_runewood_tree", RUNEWOOD_TREE.get().configured(INSTANCE).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(0, CommonConfig.RARE_RUNEWOOD_CHANCE.get(), 2))));

        BLAZE_QUARTZ_ORE = Registry.register(LevelGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "blaze_quartz_ore", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, BlockRegistry.BLAZING_QUARTZ_ORE.get().defaultBlockState(), CommonConfig.BLAZE_QUARTZ_SIZE.get())).range(128).squared().count(20));
        BRILLIANT_STONE = Registry.register(LevelGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "brilliant_stone", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.BRILLIANT_STONE.get().defaultBlockState(), CommonConfig.BRILLIANT_STONE_SIZE.get())).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(16, 8))).squared());

        SOULSTONE_ORE = Registry.register(LevelGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "soulstone_ore", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.SOULSTONE_ORE.get().defaultBlockState(), CommonConfig.SURFACE_SOULSTONE_SIZE.get())).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(16, 16))).squared());
        SOULSTONE_ORE_SURFACE = Registry.register(LevelGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "soulstone_ore_surface", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.SOULSTONE_ORE.get().defaultBlockState(), CommonConfig.SOULSTONE_SIZE.get())).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(64, 16))).squared());
    }

    @SubscribeEvent
    public static void registerFeatures(FMLCommonSetupEvent event)
    {
        event.enqueueWork(FeatureRegistry::register);
    }
}