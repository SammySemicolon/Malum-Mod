package com.sammy.malum.common.effects;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumDamageSources;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public class Bleeding extends Effect
{
    public Bleeding()
    {
        super(EffectType.HARMFUL, 0);
    }
    
    @Override
    public void performEffect(LivingEntity entity, int amplifier)
    {
        entity.attackEntityFrom(MalumDamageSources.BLEEDING, amplifier+1);
    }
    
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return duration % 20 == 0;
    }
}