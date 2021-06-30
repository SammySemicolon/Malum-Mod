package com.sammy.malum.common.enchantments;

import com.sammy.malum.core.init.enchantments.MalumEnchantmentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class SpiritPlunderEnchantment extends Enchantment
{
    public SpiritPlunderEnchantment()
    {
        super(Rarity.COMMON, MalumEnchantmentTypes.SCYTHE_DAGGER_ONLY, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }
    
    @Override
    public int getMaxLevel()
    {
        return 2;
    }
    
}
