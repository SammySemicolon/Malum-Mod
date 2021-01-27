package com.sammy.malum.common.effects;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;

public class BladeOfWind extends Effect
{
    public BladeOfWind()
    {
        super(EffectType.BENEFICIAL, 8171462);
        this.addAttributesModifier(Attributes.ATTACK_SPEED, "e2512c23-62a1-4ec7-a718-166f79416800", 0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributesModifier(Attributes.KNOCKBACK_RESISTANCE, "20e94b1c-9fbd-47a1-8204-c71437e2b620", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributesModifier(Attributes.ATTACK_KNOCKBACK, "e9b85f77-6676-439a-b1a8-3abd3ab4c55f", 0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributesModifier(ForgeMod.REACH_DISTANCE.get(), "6aa00bef-6409-46f5-ab8e-6ee3043a70c2", 0.25F, AttributeModifier.Operation.ADDITION);
    }
}
