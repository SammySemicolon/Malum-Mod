package com.sammy.malum.core.init.enchantments;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.enchantments.OutflowEnchantment;
import com.sammy.malum.common.enchantments.ReboundEnchantment;
import com.sammy.malum.common.enchantments.SpiritPlunderEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MalumEnchantments
{
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MalumMod.MODID);
    
    public static final RegistryObject<Enchantment> REBOUND = ENCHANTMENTS.register("rebound", ReboundEnchantment::new);
    public static final RegistryObject<Enchantment> OUTFLOW = ENCHANTMENTS.register("outflow", OutflowEnchantment::new);
    public static final RegistryObject<Enchantment> SPIRIT_PLUNDER = ENCHANTMENTS.register("spirit_plunder", SpiritPlunderEnchantment::new);
    
}
