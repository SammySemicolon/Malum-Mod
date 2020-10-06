package com.kittykitcatcat.malum.particles;

import com.kittykitcatcat.malum.particles.bonk.BonkParticleData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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