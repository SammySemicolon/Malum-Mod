package com.sammy.malum.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.MobEffect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;

public class CorruptedAerialAura extends MobEffect
{
    public CorruptedAerialAura()
    {
        super(EffectType.BENEFICIAL, 3401115);
        addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "e2306a3e-4ffc-45dc-b9c6-30acb18efab3", -0.40f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return super.getAttributeModifierValue(Math.min(1, amplifier), modifier);
    }
}