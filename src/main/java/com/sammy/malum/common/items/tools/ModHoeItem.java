package com.sammy.malum.common.items.tools;

import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;

public class ModHoeItem extends HoeItem
{
    public ModHoeItem(IItemTier material, int damage, float speed, Properties properties)
    {
        super(material, damage, speed - 3, properties.maxDamage(material.getMaxUses()));
    }
}

