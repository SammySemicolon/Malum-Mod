package com.sammy.malum.common.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;

public class AuraOfAir extends Effect
{
    public AuraOfAir()
    {
        super(EffectType.BENEFICIAL, 13458603);
        this.addAttributesModifier(Attributes.MOVEMENT_SPEED, "1608235b-c1aa-45dd-b1d2-1626988cb99b", 0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    
    }
    
    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }
}