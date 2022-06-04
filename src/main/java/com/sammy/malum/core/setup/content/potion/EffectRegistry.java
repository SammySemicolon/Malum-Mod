package com.sammy.malum.core.setup.content.potion;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectRegistry
{
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MalumMod.MALUM);


    public static final RegistryObject<MobEffect> EARTHEN_AURA = EFFECTS.register("earthen_fortitude", EarthenAura::new);
    public static final RegistryObject<MobEffect> CORRUPTED_EARTHEN_AURA = EFFECTS.register("earthen_might", CorruptedEarthenAura::new);

    public static final RegistryObject<MobEffect> INFERNAL_AURA = EFFECTS.register("infernal_alacrity", InfernalAura::new);
    public static final RegistryObject<MobEffect> CORRUPTED_INFERNAL_AURA = EFFECTS.register("infernal_barrier", CorruptedInfernalAura::new);

    public static final RegistryObject<MobEffect> AERIAL_AURA = EFFECTS.register("zephyrs_blessing", AerialAura::new);
    public static final RegistryObject<MobEffect> CORRUPTED_AERIAL_AURA = EFFECTS.register("aethers_grip", CorruptedAerialAura::new);

    public static final RegistryObject<MobEffect> AQUEOUS_AURA = EFFECTS.register("aqueous_aura", AqueousAura::new);
    public static final RegistryObject<MobEffect> CORRUPTED_AQUEOUS_AURA = EFFECTS.register("corrupted_aqueous_aura", CorruptedAqueousAura::new);

}
