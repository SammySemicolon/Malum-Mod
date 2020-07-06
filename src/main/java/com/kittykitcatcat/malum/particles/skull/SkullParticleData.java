package com.kittykitcatcat.malum.particles.skull;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class SkullParticleData implements IParticleData
{
    public static final IDeserializer<SkullParticleData> DESERIALIZER = new IDeserializer<SkullParticleData>()
    {
        @Override
        public SkullParticleData deserialize(ParticleType<SkullParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new SkullParticleData();
        }

        public SkullParticleData read(ParticleType<SkullParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new SkullParticleData();
        }
    };

    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.skull;
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