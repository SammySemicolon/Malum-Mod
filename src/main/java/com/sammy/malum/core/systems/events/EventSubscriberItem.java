package com.sammy.malum.core.systems.events;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public interface EventSubscriberItem
{
    public default boolean onEntityKill(ItemStack stack, PlayerEntity player, LivingEntity entity, boolean run)
    {
        return false;
    }
    public default boolean onBlockRightClick(ItemStack stack, PlayerEntity player, BlockState state, BlockPos pos, boolean run)
    {
        return false;
    }
}
