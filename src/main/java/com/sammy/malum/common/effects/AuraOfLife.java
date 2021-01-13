package com.sammy.malum.common.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class AuraOfLife extends Effect
{
    public AuraOfLife()
    {
        super(EffectType.BENEFICIAL, 13458603);
    }
    
    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
        if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth()) {
            entityLivingBaseIn.heal(amplifier+1);
        }
        super.performEffect(entityLivingBaseIn, amplifier);
    }
    
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return duration % 50 == 0;
    }
}
