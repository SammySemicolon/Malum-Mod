package com.sammy.malum.items.equipment.armor;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;

public class ModArmor extends ArmorItem
{
    public ModArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder)
    {
        super(materialIn, slot, builder);
        builder.maxDamage(materialIn.getDurability(slot));
    }
}