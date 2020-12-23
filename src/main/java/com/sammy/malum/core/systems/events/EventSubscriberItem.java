package com.sammy.malum.core.systems.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface EventSubscriberItem
{
    default boolean hasEntityKill()
    {
        return false;
    }
    default void onEntityKill(ItemStack stack, PlayerEntity player, LivingEntity entity)
    {
    }
}
