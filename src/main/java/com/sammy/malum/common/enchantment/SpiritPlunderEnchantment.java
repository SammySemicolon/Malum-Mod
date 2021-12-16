package com.sammy.malum.common.enchantment;

import com.sammy.malum.core.registry.enchantment.MalumEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

import net.minecraft.enchantment.Enchantment.Rarity;

public class SpiritPlunderEnchantment extends Enchantment
{
    public SpiritPlunderEnchantment()
    {
        super(Rarity.COMMON, MalumEnchantments.SCYTHE_ONLY, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }
    
    @Override
    public int getMaxLevel()
    {
        return 2;
    }
    
}
