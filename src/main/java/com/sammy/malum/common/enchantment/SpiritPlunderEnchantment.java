package com.sammy.malum.common.enchantment;

import com.sammy.malum.core.init.enchantment.MalumEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

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
