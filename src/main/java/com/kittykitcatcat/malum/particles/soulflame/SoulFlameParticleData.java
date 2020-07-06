package com.kittykitcatcat.malum.particles.soulflame;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class SoulFlameParticleData implements IParticleData
{
    public static final IDeserializer<SoulFlameParticleData> DESERIALIZER = new IDeserializer<SoulFlameParticleData>()
    {
        @Override
        public SoulFlameParticleData deserialize(ParticleType<SoulFlameParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new SoulFlameParticleData();
        }

        public SoulFlameParticleData read(ParticleType<SoulFlameParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new SoulFlameParticleData();
        }
    };
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.soulFlame;
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
