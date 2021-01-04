package com.sammy.malum.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class SpiritPlunderEnchantment extends Enchantment
{
    public SpiritPlunderEnchantment()
    {
        super(Rarity.COMMON, MalumEnchantmentTypes.scytheOnly, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }
    
    @Override
    public int getMaxLevel()
    {
        return 2;
    }
    
}
