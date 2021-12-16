package com.sammy.malum.common.effect;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;

public class SacredAura extends MobEffect
{
    public SacredAura()
    {
        super(EffectType.BENEFICIAL, 6468648);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier)
    {
        if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth())
        {
            entityLivingBaseIn.heal(1.0F);
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier)
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