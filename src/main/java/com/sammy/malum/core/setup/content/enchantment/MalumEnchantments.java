package com.sammy.malum.core.setup.content.enchantment;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.enchantment.HauntedEnchantment;
import com.sammy.malum.common.enchantment.ReboundEnchantment;
import com.sammy.malum.common.enchantment.SpiritPlunderEnchantment;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.setup.content.item.ItemTagRegistry;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MalumEnchantments
{
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MalumMod.MODID);


    public static final EnchantmentCategory SOUL_HUNTER_WEAPON = EnchantmentCategory.create(MalumMod.MODID + ":soul_hunter_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SOUL_HUNTER_WEAPON));
    public static final EnchantmentCategory SCYTHE = EnchantmentCategory.create(MalumMod.MODID + ":scythe_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SCYTHE));
    public static final EnchantmentCategory REBOUND_SCYTHE = EnchantmentCategory.create(MalumMod.MODID + ":rebound_scythe_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SCYTHE) || (CommonConfig.ULTIMATE_REBOUND.get() && i instanceof TieredItem));

    public static final RegistryObject<Enchantment> REBOUND = ENCHANTMENTS.register("rebound", ReboundEnchantment::new);
    public static final RegistryObject<Enchantment> HAUNTED = ENCHANTMENTS.register("haunted", HauntedEnchantment::new);
    public static final RegistryObject<Enchantment> SPIRIT_PLUNDER = ENCHANTMENTS.register("spirit_plunder", SpiritPlunderEnchantment::new);
    
}
