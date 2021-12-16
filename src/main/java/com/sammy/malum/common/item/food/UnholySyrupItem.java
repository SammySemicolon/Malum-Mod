package com.sammy.malum.common.item.food;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.Level.Level;

import net.minecraft.item.Item.Properties;

public class UnholySyrupItem extends HoneyBottleItem {
    public UnholySyrupItem(Properties builder) {
        super(builder);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level LevelIn, LivingEntity entityLiving) {
        if (!LevelIn.isDay()) {
            entityLiving.heal(4);
            entityLiving.addEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
        }
        return super.finishUsingItem(stack, LevelIn, entityLiving);
    }
}