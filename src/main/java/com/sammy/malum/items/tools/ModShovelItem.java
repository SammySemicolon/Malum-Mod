package com.sammy.malum.items.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ShovelItem;
import net.minecraftforge.common.ToolType;

public class ModShovelItem extends ShovelItem
{
    public ModShovelItem(IItemTier material, int damage, float speed, Properties properties)
    {
        super(material, damage + 1.5f, speed - 3f, properties.maxDamage(material.getMaxUses()).addToolType(ToolType.SHOVEL, material.getHarvestLevel()));
    }

}