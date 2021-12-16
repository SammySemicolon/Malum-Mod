package com.sammy.malum.common.item.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ShovelItem;
import net.minecraftforge.common.ToolType;

import net.minecraft.item.Item.Properties;

public class ModShovelItem extends ShovelItem
{
    public ModShovelItem(IItemTier material, int damage, float speed, Properties properties)
    {
        super(material, damage + 1.5f, speed - 3f, properties.durability(material.getUses()).addToolType(ToolType.SHOVEL, material.getLevel()));
    }
}