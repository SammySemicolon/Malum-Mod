package com.sammy.malum.items;

import com.sammy.malum.items.tools.ModSwordItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class VacantRapier extends ModSwordItem
{
    public VacantRapier(IItemTier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        target.hurtResistantTime = 12;
        return super.hitEntity(stack, target, attacker);
    }

}