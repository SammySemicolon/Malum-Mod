package com.kittykitcatcat.malum.particles.bonk;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class BonkParticleData implements IParticleData
{
    public static final IDeserializer<BonkParticleData> DESERIALIZER = new IDeserializer<BonkParticleData>()
    {
        @Override
        public BonkParticleData deserialize(ParticleType<BonkParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new BonkParticleData();
        }

        public BonkParticleData read(ParticleType<BonkParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new BonkParticleData();
        }
    };
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.bonk;
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
