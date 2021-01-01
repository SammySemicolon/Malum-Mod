package com.sammy.malum.common.items.tools.scythes;

import com.sammy.malum.common.items.tools.ModSwordItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;


public class ScytheItem extends ModSwordItem
{
    public ScytheItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn)
    {
        super(tier, attackDamageIn, attackSpeedIn - 0.8f, builderIn);
    }
}