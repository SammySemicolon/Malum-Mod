package com.kittykitcatcat.malum.items.tools;

import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;

public class ModHoeItem extends HoeItem
{
    public ModHoeItem(IItemTier material, float speed, Properties properties)
    {
        super(material, speed - 3, properties.maxDamage(material.getMaxUses()));
    }

}

