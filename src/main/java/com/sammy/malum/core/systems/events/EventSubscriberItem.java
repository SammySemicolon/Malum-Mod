package com.sammy.malum.core.systems.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public interface EventSubscriberItem
{
    public default boolean onEntityKill(ItemStack stack, PlayerEntity player, LivingEntity entity, boolean run)
    {
        return false;
    }
}
