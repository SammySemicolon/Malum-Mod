package com.sammy.malum.common.item.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.PickaxeItem;
import net.minecraftforge.common.ToolType;

import net.minecraft.item.Item.Properties;

public class ModPickaxeItem extends PickaxeItem
{
    public ModPickaxeItem(IItemTier material, int damage, float speed, Properties properties)
    {
        super(material, damage + 1, speed - 2.8f, properties.durability(material.getUses()).addToolType(ToolType.PICKAXE, material.getLevel()));
    }
}

