package com.sammy.malum.client.particles.itemcircle;

import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.data.MalumParticleData;
import net.minecraft.particles.ParticleType;

public class ItemCircleParticleData extends MalumParticleData
{
    public ItemCircleParticleData(float scale, boolean gravity)
    {
        super(MalumParticles.item_circle);
        this.scale = scale;
        this.gravity = gravity;
    }
    
    @Override
    public ParticleType<?> getType()
    {
        return MalumParticles.item_circle;
    }
}