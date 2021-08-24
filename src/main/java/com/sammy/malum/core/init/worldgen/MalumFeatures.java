package com.sammy.malum.core.init.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.worldgen.RunewoodTreeFeature;
import com.sammy.malum.core.init.block.MalumBlocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;

import static net.minecraft.world.gen.feature.NoFeatureConfig.field_236559_b_;

public class MalumFeatures
{
    public static Feature<NoFeatureConfig> RUNEWOOD_TREE;
    public static ConfiguredFeature<?, ?> CONFIGURED_RUNEWOOD_TREE;
    public static ConfiguredFeature<?, ?> RARE_CONFIGURED_RUNEWOOD_TREE;

    public static ConfiguredFeature<?, ?> BLAZE_QUARTZ_ORE;
    public static ConfiguredFeature<?, ?> SOULSTONE_ORE;
    public static ConfiguredFeature<?, ?> SOULSTONE_ORE_SURFACE;

    public static void register()
    {
        RUNEWOOD_TREE = new RunewoodTreeFeature();
        CONFIGURED_RUNEWOOD_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "runewood_tree", RUNEWOOD_TREE.withConfiguration(field_236559_b_).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.04F, 1))));
        RARE_CONFIGURED_RUNEWOOD_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "rare_runewood_tree", RUNEWOOD_TREE.withConfiguration(field_236559_b_).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.02F, 2))));
        BLAZE_QUARTZ_ORE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "blaze_quartz_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, MalumBlocks.BLAZING_QUARTZ_ORE.get().getDefaultState(), 7)).range(128).square().func_242731_b(20));
        SOULSTONE_ORE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "soulstone_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, MalumBlocks.SOULSTONE_ORE.get().getDefaultState(), 12)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(16, 16))).square());
        SOULSTONE_ORE_SURFACE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "soulstone_ore_surface", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, MalumBlocks.SOULSTONE_ORE.get().getDefaultState(), 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(64, 16))).square());

    }
}