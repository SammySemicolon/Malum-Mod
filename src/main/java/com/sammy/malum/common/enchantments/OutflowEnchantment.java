package com.sammy.malum.common.enchantments;

import com.sammy.malum.common.enchantments.MalumEnchantmentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class OutflowEnchantment extends Enchantment
{
    public OutflowEnchantment()
    {
        super(Rarity.UNCOMMON, MalumEnchantmentTypes.scytheOnly, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }
    
    @Override
    public int getMaxLevel()
    {
        return 5;
    }
    
}
