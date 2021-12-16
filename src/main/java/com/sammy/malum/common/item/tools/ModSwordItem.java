package com.sammy.malum.common.item.tools;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class ModSwordItem extends SwordItem
{
    public ModSwordItem(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage + 3, attackSpeed - 2.4f, properties.durability(material.getUses()));
    }
}

