package com.sammy.malum.common.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;

public class AuraOfWater extends Effect
{
    public AuraOfWater()
    {
        super(EffectType.BENEFICIAL, 13458603);
        this.addAttributesModifier(ForgeMod.SWIM_SPEED.get(), "59bdd704-bf7b-43df-894e-3ecc718954d9", 0.4F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    
    }
    
    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }
}