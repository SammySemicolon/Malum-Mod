package com.sammy.malum.core.systems.events;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

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
