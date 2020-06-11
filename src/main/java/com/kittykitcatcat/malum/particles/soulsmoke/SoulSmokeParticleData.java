package com.kittykitcatcat.malum.particles.soulsmoke;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class SoulSmokeParticleData implements IParticleData
{
    public static final IDeserializer<SoulSmokeParticleData> DESERIALIZER = new IDeserializer<SoulSmokeParticleData>()
    {
        @Override
        public SoulSmokeParticleData deserialize(ParticleType<SoulSmokeParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new SoulSmokeParticleData();
        }

        public SoulSmokeParticleData read(ParticleType<SoulSmokeParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new SoulSmokeParticleData();
        }
    };
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.soulSmoke;
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
