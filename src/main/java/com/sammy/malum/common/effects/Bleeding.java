package com.sammy.malum.common.effects;

import com.sammy.malum.core.init.MalumDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;

public class Bleeding extends Effect
{
    public Bleeding()
    {
        super(EffectType.HARMFUL, 0);
    }
    
    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
        super.performEffect(entityLivingBaseIn, amplifier);
        entityLivingBaseIn.attackEntityFrom(MalumDamageSources.BLEEDING, amplifier+1);
    }
    
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return duration % 50 == 0;
    }
}