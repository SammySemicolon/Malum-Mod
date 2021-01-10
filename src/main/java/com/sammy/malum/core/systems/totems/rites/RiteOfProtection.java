package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.modcontent.MalumRunes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RiteOfProtection extends AffectEntitiesRite
{
    public RiteOfProtection(String identifier, boolean isInstant, MalumRunes.MalumRune... runes)
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
        entity.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE,100,2));
    }
}
