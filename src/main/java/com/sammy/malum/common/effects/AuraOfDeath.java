package com.sammy.malum.common.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class AuraOfDeath extends Effect
{
    public AuraOfDeath()
    {
        super(EffectType.BENEFICIAL, 13458603);
        this.addAttributesModifier(Attributes.ATTACK_DAMAGE, "51b42e3f-f05e-4e69-98a8-b64973651fb4", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    
    }
    
    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }
}