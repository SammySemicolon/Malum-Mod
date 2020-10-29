package com.sammy.malum.items.tools;

import net.minecraft.item.IItemTier;


public class ModBusterSwordItem extends ModSwordItem
{
    public ModBusterSwordItem(IItemTier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties.maxDamage(material.getMaxUses()));
    }
}

