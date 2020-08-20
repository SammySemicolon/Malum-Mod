package com.kittykitcatcat.malum.particles;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

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