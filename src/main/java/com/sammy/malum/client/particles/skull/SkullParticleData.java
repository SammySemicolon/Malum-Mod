package com.sammy.malum.client.particles.skull;

import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.data.MalumParticleData;
import net.minecraft.particles.ParticleType;

public class SkullParticleData extends MalumParticleData
{
    public SkullParticleData(float scale, boolean gravity)
    {
        super(MalumParticles.skull);
        this.scale = scale;
        this.gravity = gravity;
    }
    
    @Override
    public ParticleType<?> getType()
    {
        return MalumParticles.skull;
    }
}