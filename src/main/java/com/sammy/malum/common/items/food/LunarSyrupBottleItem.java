package com.sammy.malum.common.items.food;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class LunarSyrupBottleItem extends HoneyBottleItem
{
    public LunarSyrupBottleItem(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        entityLiving.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION,1200,0));
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
