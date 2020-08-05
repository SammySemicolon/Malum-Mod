package com.kittykitcatcat.malum.particles.spiritflame;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class SpiritFlameParticleData implements IParticleData
{
    public static final IDeserializer<SpiritFlameParticleData> DESERIALIZER = new IDeserializer<SpiritFlameParticleData>()
    {
        @Override
        public SpiritFlameParticleData deserialize(ParticleType<SpiritFlameParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new SpiritFlameParticleData();
        }

        public SpiritFlameParticleData read(ParticleType<SpiritFlameParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new SpiritFlameParticleData();
        }
    };
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.spiritFlame;
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
