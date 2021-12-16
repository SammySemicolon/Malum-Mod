package com.sammy.malum.common.item.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HolySyrupItem extends HoneyBottleItem {
    public HolySyrupItem(Properties builder) {
        super(builder);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        if (level.isDay()) {
            entityLiving.heal(4);
            entityLiving.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
        }
        return super.finishUsingItem(stack, level, entityLiving);
    }
}