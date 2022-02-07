package com.sammy.malum.common.enchantment;

import com.sammy.malum.core.setup.enchantment.MalumEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class ReboundEnchantment extends Enchantment
{
    public ReboundEnchantment()
    {
        super(Rarity.UNCOMMON, MalumEnchantments.REBOUND_SCYTHE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }
    
    @Override
    public int getMaxLevel()
    {
        return 3;
    }
}
