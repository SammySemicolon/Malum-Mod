package com.sammy.malum.items.tools;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;


public class ModBusterSwordItem extends ModSwordItem
{
    public ModBusterSwordItem(IItemTier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties.maxDamage(material.getMaxUses()));
    }
    
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (target.isAlive())
        {
        
        }
        return super.hitEntity(stack, target, attacker);
    }
}

