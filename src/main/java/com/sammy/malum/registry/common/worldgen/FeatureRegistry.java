package com.sammy.malum.registry.common.worldgen;

import com.sammy.malum.common.worldgen.ore.CthonicGoldOreFeature;
import com.sammy.malum.common.worldgen.ore.LayeredOreFeature;
import com.sammy.malum.common.worldgen.tree.RunewoodTreeFeature;
import com.sammy.malum.common.worldgen.tree.SoulwoodTreeFeature;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;

import static com.sammy.malum.MalumMod.MALUM;

public class FeatureRegistry {

    public static final LazyRegistrar<Feature<?>> FEATURE_TYPES = LazyRegistrar.create(BuiltInRegistries.FEATURE, MALUM);

    public static final RegistryObject<RunewoodTreeFeature> RUNEWOOD_TREE = FEATURE_TYPES.register("runewood_tree", RunewoodTreeFeature::new);
    public static final RegistryObject<SoulwoodTreeFeature> SOULWOOD_TREE = FEATURE_TYPES.register("soulwood_tree", SoulwoodTreeFeature::new);
    public static final RegistryObject<LayeredOreFeature> CTHONIC_GOLD_ORE = FEATURE_TYPES.register("cthonic_gold_ore", CthonicGoldOreFeature::new);
}