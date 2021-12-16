package com.sammy.malum.common.item.tools;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class ModPickaxeItem extends PickaxeItem
{
    public ModPickaxeItem(Tier material, int damage, float speed, Properties properties)
    {
        super(material, damage + 1, speed - 2.8f, properties.durability(material.getUses()));
    }
}

