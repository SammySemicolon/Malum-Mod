package com.sammy.malum.core.init;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.world.features.tree.HugeSunKissedBlockStateProvider;
import com.sammy.malum.common.world.features.tree.SunKissedFoliagePlacer;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.MegaPineFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumHelper.prefix;

public class MalumFeatures
{
    
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> SUN_KISSED_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID +":"+ "sun_kissed_tree",
            Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(MalumBlocks.SUN_KISSED_LOG.get().getDefaultState()),
                    new SimpleBlockStateProvider(MalumBlocks.SUN_KISSED_LEAVES.get().getDefaultState()),
                    new SunKissedFoliagePlacer(
                            FeatureSpread.func_242253_a(2, 1),
                            FeatureSpread.func_242253_a(0, 2),
                            FeatureSpread.func_242253_a(1, 1)),
                    new StraightTrunkPlacer(5, 2, 1),
                    new TwoLayerFeature(2, 0, 2)).build()));
    
    public static final ConfiguredFeature<?, ?> RARE_SUN_KISSED_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID +":"+ "rare_sun_kissed_tree",
            SUN_KISSED_TREE.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.1F, 1)))


    );
    
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> HUGE_SUN_KISSED_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,MalumMod.MODID +":"+ "huge_sun_kissed_tree",
            Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(MalumBlocks.SUN_KISSED_LOG.get().getDefaultState()),
                    new HugeSunKissedBlockStateProvider(MalumBlocks.SUN_KISSED_LEAVES.get().getDefaultState()),
                    new MegaPineFoliagePlacer(
                            FeatureSpread.func_242252_a(0),
                            FeatureSpread.func_242252_a(0),
                            FeatureSpread.func_242253_a(13, 4)),
                    new GiantTrunkPlacer(13, 2, 14),
                    new TwoLayerFeature(1, 1, 2))).build()));

}