package com.sammy.malum.common.item.food;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
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
        entityLiving.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
