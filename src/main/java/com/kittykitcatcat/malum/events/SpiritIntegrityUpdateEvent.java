package com.kittykitcatcat.malum.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class SpiritIntegrityUpdateEvent extends LivingEvent
{
    public final ItemStack stack;
    public final PlayerEntity playerEntity;
    public int integrityChange;
    public SpiritIntegrityUpdateEvent(ItemStack stack, PlayerEntity playerEntity, int integrityChange)
    {
        super(playerEntity);
        this.stack = stack;
        this.playerEntity = playerEntity;
        this.integrityChange = integrityChange;
    }
    @Cancelable
    public static class Decrease extends SpiritIntegrityUpdateEvent
    {
        public Decrease(ItemStack stack, PlayerEntity playerEntity, int integrityChange)
        {
            super(stack, playerEntity,integrityChange);
        }
    }
    public static class Fill extends SpiritIntegrityUpdateEvent
    {
        public Fill(ItemStack stack, PlayerEntity playerEntity, int integrityChange)
        {
            super(stack, playerEntity,integrityChange);
        }
    }
}