package com.sammy.malum.common.enchantments;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.items.tools.spirittools.ScytheItem;
import net.minecraft.enchantment.EnchantmentType;

public class MalumEnchantmentTypes
{
    public static EnchantmentType scytheOnly = EnchantmentType.create(MalumMod.MODID + ":scythe_only", i -> i instanceof ScytheItem);

}
