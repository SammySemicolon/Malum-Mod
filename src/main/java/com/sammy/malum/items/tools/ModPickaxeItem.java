package com.sammy.malum.items.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.PickaxeItem;
import net.minecraftforge.common.ToolType;

public class ModPickaxeItem extends PickaxeItem
{
    public ModPickaxeItem(IItemTier material, int damage, float speed, Properties properties)
    {
        super(material, damage + 1, speed - 2.8f, properties.maxDamage(material.getMaxUses()).addToolType(ToolType.PICKAXE, material.getHarvestLevel()));
    }

}

