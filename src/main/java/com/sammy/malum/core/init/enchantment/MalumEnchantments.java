package com.sammy.malum.core.init.enchantment;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.enchantment.HauntedEnchantment;
import com.sammy.malum.common.enchantment.ReboundEnchantment;
import com.sammy.malum.common.enchantment.SpiritPlunderEnchantment;
import com.sammy.malum.common.item.tools.spirittools.PithingNeedleItem;
import com.sammy.malum.common.item.tools.spirittools.ScytheItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MalumEnchantments
{
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MalumMod.MODID);

    public static final EnchantmentType SCYTHE_ONLY = EnchantmentType.create(MalumMod.MODID + ":scythe_only", i -> i instanceof ScytheItem);
    public static final EnchantmentType SCYTHE_OR_DAGGER = EnchantmentType.create(MalumMod.MODID + ":scythe_dagger_only", i -> i instanceof ScytheItem || i instanceof PithingNeedleItem);

    public static final RegistryObject<Enchantment> REBOUND = ENCHANTMENTS.register("rebound", ReboundEnchantment::new);
    public static final RegistryObject<Enchantment> HAUNTED = ENCHANTMENTS.register("haunted", HauntedEnchantment::new);
    public static final RegistryObject<Enchantment> SPIRIT_PLUNDER = ENCHANTMENTS.register("spirit_plunder", SpiritPlunderEnchantment::new);
    
}
