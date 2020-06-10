package com.kittykitcatcat.malum.particles.bfcshockwave;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class BigFuckingShockwaveData implements IParticleData
{
    public static final IDeserializer<BigFuckingShockwaveData> DESERIALIZER = new IDeserializer<BigFuckingShockwaveData>()
    {
        @Override
        public BigFuckingShockwaveData deserialize(ParticleType<BigFuckingShockwaveData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new BigFuckingShockwaveData();
        }

        public BigFuckingShockwaveData read(ParticleType<BigFuckingShockwaveData> particleTypeIn, PacketBuffer buffer)
        {
            return new BigFuckingShockwaveData();
        }
    };
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.bfcShockwave;
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
