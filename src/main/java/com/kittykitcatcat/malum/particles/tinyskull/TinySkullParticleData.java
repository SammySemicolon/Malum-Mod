package com.kittykitcatcat.malum.particles.tinyskull;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class TinySkullParticleData implements IParticleData
{
    public static final IDeserializer<TinySkullParticleData> DESERIALIZER = new IDeserializer<TinySkullParticleData>()
    {
        @Override
        public TinySkullParticleData deserialize(ParticleType<TinySkullParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new TinySkullParticleData();
        }

        public TinySkullParticleData read(ParticleType<TinySkullParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new TinySkullParticleData();
        }
    };

    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.tinySkull;
    }

    @Override
    public void write(PacketBuffer buffer)
    {

    }

    @Override
    public String getParameters()
    {
        return "";
    }
}