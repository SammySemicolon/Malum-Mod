package com.sammy.malum.registry.common.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.enchantment.*;
import com.sammy.malum.config.CommonConfig;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EnchantmentRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MalumMod.MALUM);

    public static final EnchantmentCategory SOUL_HUNTER_WEAPON = EnchantmentCategory.create(MalumMod.MALUM + ":soul_hunter_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SOUL_HUNTER_WEAPON));
    public static final EnchantmentCategory SCYTHE = EnchantmentCategory.create(MalumMod.MALUM + ":scythe_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SCYTHE));
    public static final EnchantmentCategory REBOUND_SCYTHE = EnchantmentCategory.create(MalumMod.MALUM + ":rebound_scythe_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SCYTHE) || (CommonConfig.ULTIMATE_REBOUND.getConfigValue() && i instanceof TieredItem));
    public static final EnchantmentCategory STAFF = EnchantmentCategory.create(MalumMod.MALUM + ":staff_only", i -> i.getDefaultInstance().is(ItemTagRegistry.STAFF));
    public static final EnchantmentCategory SCYTHE_OR_STAFF = EnchantmentCategory.create(MalumMod.MALUM + ":scythe_or_staff", i -> i.getDefaultInstance().is(ItemTagRegistry.SCYTHE) || i.getDefaultInstance().is(ItemTagRegistry.STAFF));

    public static final DeferredHolder<Enchantment> REBOUND = ENCHANTMENTS.register("rebound", ReboundEnchantment::new);
    public static final DeferredHolder<Enchantment> HAUNTED = ENCHANTMENTS.register("haunted", HauntedEnchantment::new);
    public static final DeferredHolder<Enchantment> SPIRIT_PLUNDER = ENCHANTMENTS.register("spirit_plunder", SpiritPlunderEnchantment::new);
    public static final DeferredHolder<Enchantment> REPLENISHING = ENCHANTMENTS.register("replenishing", ReplenishingEnchantment::new);
}
