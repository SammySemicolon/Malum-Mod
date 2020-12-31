package com.sammy.malum.client.particles.spiritflame;

import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.data.MalumParticleData;
import net.minecraft.particles.ParticleType;

public class SpiritFlameParticleData extends MalumParticleData
{
    public SpiritFlameParticleData(float scale, boolean gravity)
    {
        super(MalumParticles.spirit_flame);
        this.scale = scale;
        this.gravity = gravity;
    }
    
    @Override
    public ParticleType<?> getType()
    {
        return MalumParticles.spirit_flame;
    }
}