package com.kittykitcatcat.malum.particles.boom;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class BoomParticleData implements IParticleData
{
    public static final IDeserializer<BoomParticleData> DESERIALIZER = new IDeserializer<BoomParticleData>()
    {
        @Override
        public BoomParticleData deserialize(ParticleType<BoomParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new BoomParticleData();
        }

        public BoomParticleData read(ParticleType<BoomParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new BoomParticleData();
        }
    };
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.boom;
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
