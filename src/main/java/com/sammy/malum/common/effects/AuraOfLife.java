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
        this.addAttributesModifier(Attributes.MAX_HEALTH, "da900b3a-5b5b-4fcd-8401-33f0ae38edc3", 2F, AttributeModifier.Operation.ADDITION);
    
    }
    
    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }
}