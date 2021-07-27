package com.sammy.malum.common.potion_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class AuraOfWarding extends Effect
{
    public AuraOfWarding()
    {
        super(EffectType.BENEFICIAL, 6468648);
        addAttributesModifier(Attributes.ARMOR, "04448cbf-ee2c-4f36-b71f-e641a312834a", 3f, AttributeModifier.Operation.ADDITION);
        addAttributesModifier(Attributes.ARMOR_TOUGHNESS, "dc5fc5d7-db54-403f-810d-a16de6293ffd", 1.5f, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
        if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth())
        {
            entityLivingBaseIn.heal(1.0F);
        }
    }

    public boolean isReady(int duration, int amplifier)
    {
        int k = 50 >> amplifier;
        if (k > 0)
        {
            return duration % k == 0;
        }
        else
        {
            return true;
        }
    }
}