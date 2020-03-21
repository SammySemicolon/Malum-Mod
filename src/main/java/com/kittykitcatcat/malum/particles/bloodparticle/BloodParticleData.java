package com.kittykitcatcat.malum.particles.bloodparticle;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class BloodParticleData implements IParticleData
{
    public static final IDeserializer<BloodParticleData> DESERIALIZER = new IDeserializer<BloodParticleData>()
    {
        @Override
        public BloodParticleData deserialize(ParticleType<BloodParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new BloodParticleData();
        }

        public BloodParticleData read(ParticleType<BloodParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new BloodParticleData();
        }
    };
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.bloodParticle;
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
