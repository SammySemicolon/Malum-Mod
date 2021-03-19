package com.sammy.malum.core.init.worldgen;

import com.google.common.collect.ImmutableList;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.world.features.tree.GradientFoliagePlacer;
import com.sammy.malum.common.world.features.tree.HugeGradientBlockStateProvider;
import com.sammy.malum.common.world.features.tree.SapBlockStateProvider;
import com.sammy.malum.common.world.features.tree.SunKissedGroundDecorator;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.MegaPineFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class MalumFeatures
{
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> RUNEWOOD_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "sun_kissed_tree", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SapBlockStateProvider(), new SimpleBlockStateProvider(MalumBlocks.SUN_KISSED_LEAVES.get().getDefaultState()), new GradientFoliagePlacer(FeatureSpread.func_242253_a(3, 2), FeatureSpread.func_242253_a(1, 3), FeatureSpread.func_242253_a(2, 1)), new StraightTrunkPlacer(7, 3, 1), new TwoLayerFeature(3, 1, 3)).setDecorators(ImmutableList.of(new SunKissedGroundDecorator(new SimpleBlockStateProvider(MalumBlocks.SUN_KISSED_GRASS_BLOCK.get().getDefaultState())))).build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> HUGE_RUNEWOOD_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "huge_sun_kissed_tree", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SapBlockStateProvider(), new HugeGradientBlockStateProvider(MalumBlocks.SUN_KISSED_LEAVES.get().getDefaultState()), new MegaPineFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), FeatureSpread.func_242253_a(13, 4)), new GiantTrunkPlacer(13, 2, 14), new TwoLayerFeature(1, 1, 2))).setDecorators(ImmutableList.of(new SunKissedGroundDecorator(new SimpleBlockStateProvider(MalumBlocks.SUN_KISSED_GRASS_BLOCK.get().getDefaultState())))).build()));
    
    
    public static final ConfiguredFeature<?, ?> SUN_SPOT = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "sun_spot",  RUNEWOOD_TREE.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.025F, 5))));
    
    public static final ConfiguredFeature<?,?> BLAZE_QUARTZ_ORE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "blaze_quartz_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, MalumBlocks.BLAZE_QUARTZ_ORE.get().getDefaultState(), 6)).range(128).square().func_242731_b(20));
    
}