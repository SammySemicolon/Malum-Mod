package com.sammy.malum.core.registry.misc;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectRegistry
{
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MalumMod.MODID);

    public static final RegistryObject<MobEffect> SACRED_AURA = EFFECTS.register("sacred_aura", SacredAura::new);
    public static final RegistryObject<MobEffect> EARTHEN_AURA = EFFECTS.register("earthen_aura", EarthenAura::new);
    public static final RegistryObject<MobEffect> CORRUPTED_EARTHEN_AURA = EFFECTS.register("corrupted_earthen_aura", CorruptedEarthenAura::new);
    public static final RegistryObject<MobEffect> INFERNAL_AURA = EFFECTS.register("infernal_aura", InfernalAura::new);
    public static final RegistryObject<MobEffect> AERIAL_AURA = EFFECTS.register("aerial_aura", AerialAura::new);
    public static final RegistryObject<MobEffect> CORRUPTED_AERIAL_AURA = EFFECTS.register("corrupted_aerial_aura", CorruptedAerialAura::new);
    public static final RegistryObject<MobEffect> AQUEOUS_AURA = EFFECTS.register("aqueous_aura", AqueousAura::new);

}
