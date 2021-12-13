package com.sammy.malum.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;

public class CorruptedEarthenAura extends Effect
{
    public CorruptedEarthenAura()
    {
        super(EffectType.BENEFICIAL, 3401115);
        addAttributesModifier(Attributes.ATTACK_DAMAGE, "e2a25284-a8b1-41a5-9472-90cc83793d44", 2, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }
}