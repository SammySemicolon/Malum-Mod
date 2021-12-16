package com.sammy.malum.core.registry.enchantment;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.enchantment.HauntedEnchantment;
import com.sammy.malum.common.enchantment.ReboundEnchantment;
import com.sammy.malum.common.enchantment.SpiritPlunderEnchantment;
import com.sammy.malum.common.item.tools.ScytheItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MalumEnchantments
{
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MalumMod.MODID);

    public static final EnchantmentCategory SCYTHE_ONLY = EnchantmentCategory.create(MalumMod.MODID + ":scythe_only", i -> i instanceof ScytheItem);

    public static final RegistryObject<Enchantment> REBOUND = ENCHANTMENTS.register("rebound", ReboundEnchantment::new);
    public static final RegistryObject<Enchantment> HAUNTED = ENCHANTMENTS.register("haunted", HauntedEnchantment::new);
    public static final RegistryObject<Enchantment> SPIRIT_PLUNDER = ENCHANTMENTS.register("spirit_plunder", SpiritPlunderEnchantment::new);
    
}
