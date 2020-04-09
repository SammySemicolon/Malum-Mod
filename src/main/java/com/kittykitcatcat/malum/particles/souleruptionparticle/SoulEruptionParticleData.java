package com.kittykitcatcat.malum.particles.souleruptionparticle;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class SoulEruptionParticleData implements IParticleData
{
    public static final IDeserializer<SoulEruptionParticleData> DESERIALIZER = new IDeserializer<SoulEruptionParticleData>()
    {
        @Override
        public SoulEruptionParticleData deserialize(ParticleType<SoulEruptionParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new SoulEruptionParticleData();
        }

        public SoulEruptionParticleData read(ParticleType<SoulEruptionParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new SoulEruptionParticleData();
        }
    };
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.soulEruption;
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
