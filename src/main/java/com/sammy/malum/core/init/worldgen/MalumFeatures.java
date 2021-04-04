package com.sammy.malum.core.init.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.worldgen.RunewoodTreeFeature;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MalumFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MalumMod.MODID);

    public static final Feature<NoFeatureConfig> RUNEWOOD_TREE_AGAIN = new RunewoodTreeFeature();
    public static final RegistryObject<Feature<NoFeatureConfig>> RUNEWOOD_TREE_FEATURE = FEATURES.register("runewood_tree_feature", ()-> RUNEWOOD_TREE_AGAIN);

}