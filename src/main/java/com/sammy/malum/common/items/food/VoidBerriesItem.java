package com.sammy.malum.common.items.food;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class VoidBerriesItem extends Item
{
    public VoidBerriesItem(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        if (entityLiving instanceof PlayerEntity)
        {
            ((PlayerEntity) entityLiving).addExhaustion(5);
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
