package com.kittykitcatcat.malum.items.tools;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;


public class ModBusterSword extends ModSwordItem
{
    public ModBusterSword(IItemTier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties.maxDamage(material.getMaxUses()));
    }
    
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        return super.hitEntity(stack, target, attacker);
    }
}

