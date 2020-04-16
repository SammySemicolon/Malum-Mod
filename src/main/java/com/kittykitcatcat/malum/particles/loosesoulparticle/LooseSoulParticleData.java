package com.kittykitcatcat.malum.particles.loosesoulparticle;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class LooseSoulParticleData implements IParticleData
{
    public static final IDeserializer<LooseSoulParticleData> DESERIALIZER = new IDeserializer<LooseSoulParticleData>()
    {
        @Override
        public LooseSoulParticleData deserialize(ParticleType<LooseSoulParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new LooseSoulParticleData();
        }

        public LooseSoulParticleData read(ParticleType<LooseSoulParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new LooseSoulParticleData();
        }
    };
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.looseSoul;
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
