package com.sammy.malum.core.init;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.effects.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MalumEffects
{
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, MalumMod.MODID);
    public static final RegistryObject<Effect> ENTANGLED = EFFECTS.register("entangled", Entangled::new);
    public static final RegistryObject<Effect> FOOLS_LUCK = EFFECTS.register("fools_luck", FoolsLuck::new);
    public static final RegistryObject<Effect> BLEEDING = EFFECTS.register("bleeding", Bleeding::new);
    public static final RegistryObject<Effect> WARDING = EFFECTS.register("warding_aura", Warding::new);
    public static final RegistryObject<Effect> AGILITY = EFFECTS.register("aura_of_agility", AuraOfAgility::new);
    public static final RegistryObject<Effect> BLADE_OF_WIND = EFFECTS.register("blade_of_wind", BladeOfWind::new);
    public static final RegistryObject<Effect> LIFE = EFFECTS.register("aura_of_life", AuraOfLife::new);
    public static final RegistryObject<Effect> ENDER_LINK = EFFECTS.register("ender_link", EnderLink::new);
    public static final RegistryObject<Effect> FORTITUDE = EFFECTS.register("aura_of_fortitude", AuraOfFortitude::new);
    
}
