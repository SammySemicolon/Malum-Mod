package com.sammy.malum.common.item.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;

import net.minecraft.item.Item.Properties;

public class ModSwordItem extends SwordItem
{
    public ModSwordItem(IItemTier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage + 3, attackSpeed - 2.4f, properties.durability(material.getUses()));
    }
}

