package com.sammy.malum.core.registry.misc;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.effect.*;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectRegistry
{
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, MalumMod.MODID);

    public static final RegistryObject<Effect> SACRED_AURA = EFFECTS.register("sacred_aura", SacredAura::new);
    public static final RegistryObject<Effect> EARTHEN_AURA = EFFECTS.register("earthen_aura", EarthenAura::new);
    public static final RegistryObject<Effect> CORRUPTED_EARTHEN_AURA = EFFECTS.register("corrupted_earthen_aura", CorruptedEarthenAura::new);
    public static final RegistryObject<Effect> INFERNAL_AURA = EFFECTS.register("infernal_aura", InfernalAura::new);
    public static final RegistryObject<Effect> AERIAL_AURA = EFFECTS.register("aerial_aura", AerialAura::new);
    public static final RegistryObject<Effect> CORRUPTED_AERIAL_AURA = EFFECTS.register("corrupted_aerial_aura", CorruptedAerialAura::new);
    public static final RegistryObject<Effect> AQUEOUS_AURA = EFFECTS.register("aqueous_aura", AqueousAura::new);

}
