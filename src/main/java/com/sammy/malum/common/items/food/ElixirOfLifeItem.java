package com.sammy.malum.common.items.food;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ElixirOfLifeItem extends HoneyBottleItem
{
    public ElixirOfLifeItem(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        entityLiving.heal(6);
        entityLiving.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, 1));
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
