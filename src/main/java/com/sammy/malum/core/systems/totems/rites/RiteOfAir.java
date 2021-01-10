package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.modcontent.MalumRunes.MalumRune;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RiteOfAir extends AffectEntitiesRite
{
    public RiteOfAir(String identifier, boolean isInstant, MalumRune... runes)
    {
        super(identifier, isInstant, runes);
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        entity.addVelocity(0,5f,0);
        entity.addPotionEffect(new EffectInstance(Effects.LEVITATION,40,1));
    }
}
