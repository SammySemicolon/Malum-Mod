package com.sammy.malum.common.effects;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class AuraOfEarth extends Effect
{
    public AuraOfEarth()
    {
        super(EffectType.BENEFICIAL, 10044730);
        this.addAttributesModifier(Attributes.ARMOR, "1608235b-c1aa-45dd-b1d2-1626988cb99b", 2F, AttributeModifier.Operation.ADDITION);
    }
}
