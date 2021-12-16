package com.sammy.malum.common.item.food;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
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
            entityLiving.addEffect(new MobEffectInstance(Effects.REGENERATION, 200, 0));
        }
        return super.finishUsingItem(stack, LevelIn, entityLiving);
    }
}