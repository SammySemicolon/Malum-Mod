package com.sammy.malum.common.enchantment;

import com.sammy.malum.core.registry.enchantment.MalumEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class ReboundEnchantment extends Enchantment
{
    public ReboundEnchantment()
    {
        super(Rarity.UNCOMMON, MalumEnchantments.SCYTHE_ONLY, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }
    
    @Override
    public int getMaxLevel()
    {
        return 3;
    }
}
