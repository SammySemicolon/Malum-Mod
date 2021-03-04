package com.sammy.malum.common.items.equipment.armor;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;

import static com.sammy.malum.common.items.MalumArmorTiers.ArmorTierEnum.SOUL_STAINED_STEEL;

public class SoulStainedSteelArmorItem extends ArmorItem
{
    public SoulStainedSteelArmorItem(EquipmentSlotType slot, Properties builder)
    {
        super(SOUL_STAINED_STEEL, slot, builder);
    }
}