package com.sammy.malum.common.enchantment;

import com.sammy.malum.core.registry.enchantment.MalumEnchantments;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlot;

import net.minecraft.enchantment.Enchantment.Rarity;

public class SpiritPlunderEnchantment extends Enchantment
{
    public SpiritPlunderEnchantment()
    {
        super(Rarity.COMMON, MalumEnchantments.SCYTHE_ONLY, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }
    
    @Override
    public int getMaxLevel()
    {
        return 2;
    }
    
}
