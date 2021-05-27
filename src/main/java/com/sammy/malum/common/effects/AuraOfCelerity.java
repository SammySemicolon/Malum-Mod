package com.sammy.malum.common.effects;

import com.sammy.malum.MalumHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;

import java.awt.*;

public class AuraOfCelerity extends Effect
{
    public AuraOfCelerity()
    {
        super(EffectType.BENEFICIAL, 3401115);
        addAttributesModifier(ForgeMod.REACH_DISTANCE.get(), "0cf1d054-5cbe-43da-9e02-f818b8c99e48", 0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributesModifier(Attributes.MOVEMENT_SPEED, "5955aa1e-087a-4b8c-8183-e65ef941cda1", 0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributesModifier(Attributes.ATTACK_SPEED, "c35b6324-54f7-443a-849d-1a8d2591ef92", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
    
    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }
}