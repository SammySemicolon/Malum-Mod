package com.sammy.malum.registry.common.worldgen;

import com.sammy.malum.common.worldgen.RareEarthsGeode;
import com.sammy.malum.common.worldgen.RunewoodTreeFeature;
import com.sammy.malum.common.worldgen.SoulwoodTreeFeature;
import com.sammy.malum.common.worldgen.WeepingWellFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.sammy.malum.MalumMod.MALUM;

public class FeatureRegistry {

	public static final DeferredRegister<Feature<?>> FEATURE_TYPES = DeferredRegister.create(ForgeRegistries.FEATURES, MALUM);

	public static final RegistryObject<WeepingWellFeature> WEEPING_WELL = FEATURE_TYPES.register("weeping_well", WeepingWellFeature::new);
	public static final RegistryObject<RunewoodTreeFeature> RUNEWOOD_TREE = FEATURE_TYPES.register("runewood_tree", RunewoodTreeFeature::new);
	public static final RegistryObject<SoulwoodTreeFeature> SOULWOOD_TREE = FEATURE_TYPES.register("soulwood_tree", SoulwoodTreeFeature::new);
	public static final RegistryObject<RareEarthsGeode> CTHONIC_GOLD_GEODE = FEATURE_TYPES.register("cthonic_gold_geode", () -> new RareEarthsGeode(GeodeConfiguration.CODEC));
}