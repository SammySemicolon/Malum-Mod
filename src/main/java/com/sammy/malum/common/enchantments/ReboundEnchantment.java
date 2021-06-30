package com.sammy.malum.common.enchantments;

import com.sammy.malum.core.init.enchantments.MalumEnchantmentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ReboundEnchantment extends Enchantment
{
    public ReboundEnchantment()
    {
        super(Rarity.UNCOMMON, MalumEnchantmentTypes.SCYTHE_ONLY, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }
    
    @Override
    public int getMaxLevel()
    {
        return 3;
    }
}
