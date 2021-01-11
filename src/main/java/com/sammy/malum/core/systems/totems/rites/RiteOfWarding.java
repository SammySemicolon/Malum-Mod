package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumRunes;
import com.sammy.malum.core.systems.particles.ParticleManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber
public class RiteOfWarding extends AffectEntitiesRite
{
    public RiteOfWarding(String identifier, boolean isInstant, MalumRunes.MalumRune... runes)
    {
        super(identifier, isInstant, runes);
    }
    
    @Override
    public int range()
    {
        return 20;
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        entity.addPotionEffect(new EffectInstance(MalumEffects.WARDING.get(),100,0));
    }
    @SubscribeEvent
    public static void knockBack(LivingKnockBackEvent event)
    {
        if (event.getEntityLiving().getActivePotionEffect(MalumEffects.WARDING.get()) != null)
        {
            event.setStrength(0);
        }
    }
    @SubscribeEvent
    public static void explode(ExplosionEvent.Start event)
    {
        if (event.getExplosion().getExploder() instanceof CreeperEntity)
        {
            CreeperEntity entity = (CreeperEntity) event.getExplosion().getExploder();
            if (entity.getActivePotionEffect(MalumEffects.WARDING.get()) != null)
            {
                event.setCanceled(true);
            }
        }
    }
}
