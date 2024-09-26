package com.sammy.malum.registry.common.item;

import com.sammy.malum.MalumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EnchantmentRegistry {

    public static final ResourceKey<Enchantment> REBOUND = keyOf("rebound");
    public static final ResourceKey<Enchantment> HAUNTED = keyOf("haunted");
    public static final ResourceKey<Enchantment> SPIRIT_PLUNDER = keyOf("spirit_plunder");
    public static final ResourceKey<Enchantment> REPLENISHING = keyOf("replenishing");

    static ResourceKey<Enchantment> keyOf(String id) {
        return ResourceKey.create(Registries.ENCHANTMENT, MalumMod.malumPath(id));
    }
}
