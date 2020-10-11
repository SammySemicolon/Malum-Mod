package com.sammy.malum.particles;

import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;

public abstract class ScaleParticleData implements IParticleData
{
    public float scale;
    public ScaleParticleData(float scale)
    {
        this.scale = scale;
    }
    @Override
    public void write(PacketBuffer buffer)
    {
        buffer.writeFloat(scale);
    }
    
    @Override
    public String getParameters()
    {
        return "";
    }
}