package com.sammy.malum.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;

public class AqueousAura extends Effect
{
    public AqueousAura()
    {
        super(EffectType.BENEFICIAL, 6468648);
        addAttributesModifier(ForgeMod.REACH_DISTANCE.get(), "738bd9e4-23d8-46b0-b8ba-45a2016eec74", 1f, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}