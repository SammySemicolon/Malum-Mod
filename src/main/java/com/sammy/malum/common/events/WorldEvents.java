package com.sammy.malum.common.events;

import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WorldEvents
{
    @SubscribeEvent
    public static void explode(ExplosionEvent.Start event)
    {
        if (event.getExplosion().getExploder() instanceof CreeperEntity)
        {
            CreeperEntity entity = (CreeperEntity) event.getExplosion().getExploder();
            if (entity.getActivePotionEffect(Effects.MINING_FATIGUE) != null)
            {
                event.setCanceled(true);
            }
        }
    }
}
