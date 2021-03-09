package com.sammy.malum.core.init;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.effects.*;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MalumEffects
{
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, MalumMod.MODID);
    public static final RegistryObject<Effect> BLEEDING = EFFECTS.register("bleeding", Bleeding::new);
    
    public static final RegistryObject<Effect> LIFE = EFFECTS.register("aura_of_life", AuraOfLife::new);
    public static final RegistryObject<Effect> DEATH = EFFECTS.register("aura_of_death", AuraOfDeath::new);
    public static final RegistryObject<Effect> MAGIC = EFFECTS.register("aura_of_magic", AuraOfMagic::new);
    public static final RegistryObject<Effect> EARTH = EFFECTS.register("aura_of_earth", AuraOfEarth::new);
    public static final RegistryObject<Effect> AIR = EFFECTS.register("aura_of_air", AuraOfAir::new);
    public static final RegistryObject<Effect> WATER = EFFECTS.register("aura_of_water", AuraOfWater::new);
    
    public static final RegistryObject<Effect> BLADE_OF_WIND = EFFECTS.register("blade_of_wind", BladeOfWind::new);
    public static final RegistryObject<Effect> ENDER_LINK = EFFECTS.register("ender_link", EnderLink::new);
    
}
