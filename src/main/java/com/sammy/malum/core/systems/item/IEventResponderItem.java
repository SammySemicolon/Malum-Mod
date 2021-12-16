package com.sammy.malum.core.systems.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface IEventResponderItem {
    public default void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack)
    {
        hurtEvent(attacker, target, stack);
    }
    public default void hurtEvent(LivingEntity attacker, LivingEntity target, ItemStack stack)
    {

    }
    public default void deathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack)
    {
        deathEvent(attacker, target, stack);
    }
    public default void deathEvent(LivingEntity attacker, LivingEntity target, ItemStack stack)
    {

    }
    public default void pickupSpirit(LivingEntity attacker, ItemStack stack)
    {

    }
}
