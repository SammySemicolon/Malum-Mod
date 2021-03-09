package com.sammy.malum.common.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;

public class AuraOfMagic extends Effect
{
    public AuraOfMagic()
    {
        super(EffectType.BENEFICIAL, 14270531);
        this.addAttributesModifier(Attributes.KNOCKBACK_RESISTANCE, "84ec4072-d56b-44f4-93ac-5d1be48d1664", 0.5F, AttributeModifier.Operation.ADDITION);
    }
    
    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }
    
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return false;
    }
}