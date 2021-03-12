package com.sammy.malum.common.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;

public class AuraOfFire extends Effect
{
    public AuraOfFire()
    {
        super(EffectType.BENEFICIAL, 13458603);
        this.addAttributesModifier(Attributes.ATTACK_SPEED, "aaf64233-1952-46ce-9159-a47d22be0ee6", 0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
    
    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }
}