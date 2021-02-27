package com.sammy.malum.common.items.food;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SolarSyrupBottleItem extends HoneyBottleItem
{
    public SolarSyrupBottleItem(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        entityLiving.heal(4);
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
