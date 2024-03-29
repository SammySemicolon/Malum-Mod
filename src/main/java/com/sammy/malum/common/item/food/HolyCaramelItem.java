package com.sammy.malum.common.item.food;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HolyCaramelItem extends Item {

    public HolyCaramelItem(Properties builder) {
        super(builder);
    }

    public void consume(LivingEntity entity) {
        entity.heal(3);
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        pEntityLiving.heal(3);
        return super.finishUsingItem(pStack, pLevel, pEntityLiving);
    }
}