package com.sammy.malum.common.item.tools;

import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;

import net.minecraft.item.Item.Properties;

public class ModHoeItem extends HoeItem
{
    public ModHoeItem(IItemTier material, int damage, float speed, Properties properties)
    {
        super(material, damage, speed - 3, properties.durability(material.getUses()));
    }
}

