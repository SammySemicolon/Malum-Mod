package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.modcontent.MalumRunes.MalumRune;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RiteOfLevitation extends AffectEntitiesRite
{
    public RiteOfLevitation(String identifier, boolean isInstant, MalumRune... runes)
    {
        super(identifier, isInstant, runes);
    }
    
    @Override
    public int cooldown()
    {
        return 10;
    }
    
    @Override
    public int range()
    {
        return 1;
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        entity.addPotionEffect(new EffectInstance(Effects.LEVITATION,20,8));
    }
}
