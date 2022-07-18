package com.sammy.malum.common.item.food;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
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