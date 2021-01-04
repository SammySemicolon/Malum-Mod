package com.sammy.malum.common.events;

import com.sammy.malum.core.init.MalumEffects;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents
{
    
    @SubscribeEvent
    public static void bleedingParticles(LivingEvent.LivingUpdateEvent event)
    {
        LivingEntity entity = event.getEntityLiving();
        if (entity.getActivePotionEffect(MalumEffects.BLEEDING.get()) != null)
        {
            entity.world.addParticle(new BlockParticleData(ParticleTypes.BLOCK,Blocks.REDSTONE_BLOCK.getDefaultState()),entity.getPosXRandom(entity.getWidth()),entity.getPosYRandom(),entity.getPosZRandom(entity.getWidth()),0,0,0);
        }
    }
}
