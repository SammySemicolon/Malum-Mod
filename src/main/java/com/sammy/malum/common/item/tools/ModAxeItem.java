package com.sammy.malum.common.item.tools;

import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraftforge.common.ToolType;

import net.minecraft.item.Item.Properties;

public class ModAxeItem extends AxeItem
{
    public ModAxeItem(IItemTier material, float damage, float speed, Properties properties)
    {
        super(material, damage + 6, speed - 3.2f, properties.durability(material.getUses()).addToolType(ToolType.AXE, material.getLevel()));
    }
}


