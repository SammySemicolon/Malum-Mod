package com.sammy.malum.common.items.tools;

import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraftforge.common.ToolType;

public class ModAxeItem extends AxeItem
{
    public ModAxeItem(IItemTier material, float damage, float speed, Properties properties)
    {
        super(material, damage + 6, speed - 3.2f, properties.maxDamage(material.getMaxUses()).addToolType(ToolType.AXE, material.getHarvestLevel()));
    }
}


