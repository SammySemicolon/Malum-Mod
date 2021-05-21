package com.sammy.malum.core.init.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.worldgen.RunewoodTreeFeature;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraft.world.gen.feature.NoFeatureConfig.field_236559_b_;

public class MalumFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MalumMod.MODID);

    public static final Feature<NoFeatureConfig> RUNEWOOD_TREE = new RunewoodTreeFeature();
    public static final ConfiguredFeature<?, ?> CONFIGURED_RUNEWOOD_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "runewood_tree", RUNEWOOD_TREE.withConfiguration(field_236559_b_).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.01F, 1))));
    public static final ConfiguredFeature<?, ?> RARE_CONFIGURED_RUNEWOOD_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MalumMod.MODID + ":" + "rare_runewood_tree", RUNEWOOD_TREE.withConfiguration(field_236559_b_).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.005F, 1))));

}