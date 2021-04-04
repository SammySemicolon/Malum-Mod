package com.sammy.malum.core.init.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class MalumStaticFeatures
{
    public static final ConfiguredFeature<?,?> BLAZE_QUARTZ_ORE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "blaze_quartz_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, MalumBlocks.BLAZING_QUARTZ_ORE.get().getDefaultState(), 12)).range(128).square().func_242731_b(20));
    public static final ConfiguredFeature<?,?> RUNESTONE_ORE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "runestone_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, MalumBlocks.GRIMSLATE_ORE.get().getDefaultState(), 6)).range(128).square().func_242731_b(20));
}
