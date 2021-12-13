package com.sammy.malum.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class InfernalAura extends Effect
{
    public InfernalAura()
    {
        super(EffectType.BENEFICIAL, 6468648);
        addAttributesModifier(Attributes.ATTACK_SPEED, "0a74b987-a6ec-4b9f-815e-a589bf435b93", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}