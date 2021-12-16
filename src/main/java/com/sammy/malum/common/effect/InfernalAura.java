package com.sammy.malum.common.effect;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.potion.EffectType;

public class InfernalAura extends MobEffect
{
    public InfernalAura()
    {
        super(EffectType.BENEFICIAL, 6468648);
        addAttributeModifier(Attributes.ATTACK_SPEED, "0a74b987-a6ec-4b9f-815e-a589bf435b93", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}