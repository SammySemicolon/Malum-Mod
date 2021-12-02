package com.sammy.malum.core.registry.misc;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.effect.AuraOfCelerity;
import com.sammy.malum.common.effect.AuraOfWarding;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectRegistry
{
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, MalumMod.MODID);

    public static final RegistryObject<Effect> AURA_OF_WARDING = EFFECTS.register("aura_of_warding", AuraOfWarding::new);
    public static final RegistryObject<Effect> AURA_OF_CELERITY = EFFECTS.register("aura_of_celerity", AuraOfCelerity::new);

}
