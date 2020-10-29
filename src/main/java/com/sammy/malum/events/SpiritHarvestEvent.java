package com.sammy.malum.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class SpiritHarvestEvent extends LivingEvent
{
    public final LivingEntity target;
    public final PlayerEntity playerEntity;
    
    public SpiritHarvestEvent(LivingEntity target, PlayerEntity playerEntity)
    {
        super(playerEntity);
        this.target = target;
        this.playerEntity = playerEntity;
    }
    
    public static class Pre extends SpiritHarvestEvent
    {
        public int extraSpirits = 0;
        
        public Pre(LivingEntity target, PlayerEntity playerEntity)
        {
            super(target, playerEntity);
        }
    }
    
    public static class Post extends SpiritHarvestEvent
    {
        public Post(LivingEntity target, PlayerEntity playerEntity, int spiritCount)
        {
            super(target, playerEntity);
        }
    }
}