package com.sammy.malum.core.init.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;

public class MalumStaticFeatures
{
    public static final ConfiguredFeature<?,?> BLAZE_QUARTZ_ORE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "blaze_quartz_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, MalumBlocks.BLAZING_QUARTZ_ORE.get().getDefaultState(), 12)).range(128).square().func_242731_b(20));
    public static final ConfiguredFeature<?,?> SOULSTONE_ORE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "soulstone_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, MalumBlocks.SOULSTONE_ORE.get().getDefaultState(), 12)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(16, 16))).square());
    public static final ConfiguredFeature<?,?> SOULSTONE_ORE_SURFACE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "soulstone_ore_surface", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, MalumBlocks.SOULSTONE_ORE.get().getDefaultState(), 8)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(64, 16))).square());
}
