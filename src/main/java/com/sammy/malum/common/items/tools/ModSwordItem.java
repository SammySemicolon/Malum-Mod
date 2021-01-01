package com.sammy.malum.common.items.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;

public class ModSwordItem extends SwordItem
{
    public ModSwordItem(IItemTier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage + 3, attackSpeed - 2.4f, properties.maxDamage(material.getMaxUses()));
    }
}

