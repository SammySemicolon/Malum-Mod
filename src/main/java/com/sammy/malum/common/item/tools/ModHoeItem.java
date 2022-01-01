package com.sammy.malum.common.item.tools;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;

public class ModHoeItem extends HoeItem
{
    public ModHoeItem(Tier material, int damage, float speed, Properties properties)
    {
        super(material, damage-2, speed, properties.durability(material.getUses()));
    }
}

