package com.sammy.malum.common.effect;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.common.ForgeMod;

public class CorruptedEarthenAura extends MobEffect
{
    public CorruptedEarthenAura()
    {
        super(MobEffectCategory.BENEFICIAL, 3401115);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, "e2a25284-a8b1-41a5-9472-90cc83793d44", 2, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }
}