package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RiteOfAir extends AffectEntitiesRite
{
    public RiteOfAir(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        entity.setMotion(0,20f,0);
        entity.velocityChanged = true;
        //entity.addPotionEffect(new EffectInstance(Effects.LEVITATION,40,1));
    }
}
