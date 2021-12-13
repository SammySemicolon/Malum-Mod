package com.sammy.malum.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;

public class AerialAura extends Effect
{
    public AerialAura()
    {
        super(EffectType.BENEFICIAL, 3401115);
        addAttributesModifier(Attributes.MOVEMENT_SPEED, "e3f9c028-d6cc-4cf2-86a6-d5b5efd86be6", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
    
    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }
}