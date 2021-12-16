package com.sammy.malum.common.item.tools;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

public class ModAxeItem extends AxeItem
{
    public ModAxeItem(Tier material, float damage, float speed, Properties properties)
    {
        super(material, damage + 6, speed - 3.2f, properties.durability(material.getUses()));
    }
}


