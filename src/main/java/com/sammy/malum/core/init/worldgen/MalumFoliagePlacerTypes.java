package com.sammy.malum.core.init.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.world.features.tree.SunKissedFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MalumFoliagePlacerTypes
{
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, MalumMod.MODID);
    public static RegistryObject<FoliagePlacerType<SunKissedFoliagePlacer>> SUN_KISSED_TREE = FOLIAGE.register("sun_kissed_foliage_placer", () -> new FoliagePlacerType<>(SunKissedFoliagePlacer.type));
}