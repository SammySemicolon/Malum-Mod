package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.modcontent.MalumRunes.MalumRune;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RiteOfWater extends AffectEntitiesRite
{
    public RiteOfWater(String identifier, boolean isInstant, MalumRune... runes)
    {
        super(identifier, isInstant, runes);
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        entity.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING,400,0));
    }
}
