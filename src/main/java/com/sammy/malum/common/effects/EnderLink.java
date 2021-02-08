package com.sammy.malum.common.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EnderLink extends Effect
{
    public EnderLink()
    {
        super(EffectType.NEUTRAL, 0);
    }
    
    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }
    
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return false;
    }
}
