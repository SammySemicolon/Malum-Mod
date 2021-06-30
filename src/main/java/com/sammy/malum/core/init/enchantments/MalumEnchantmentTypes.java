package com.sammy.malum.core.init.enchantments;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.items.tools.spirittools.SacrificialDaggerItem;
import com.sammy.malum.common.items.tools.spirittools.ScytheItem;
import net.minecraft.enchantment.EnchantmentType;

public class MalumEnchantmentTypes
{
    public static final EnchantmentType SCYTHE_ONLY = EnchantmentType.create(MalumMod.MODID + ":scythe_only", i -> i instanceof ScytheItem);
    public static final EnchantmentType SCYTHE_DAGGER_ONLY = EnchantmentType.create(MalumMod.MODID + ":scythe_dagger_only", i -> i instanceof ScytheItem || i instanceof SacrificialDaggerItem);

}
