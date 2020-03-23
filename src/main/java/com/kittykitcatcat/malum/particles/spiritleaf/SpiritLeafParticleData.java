package com.kittykitcatcat.malum.particles.spiritleaf;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class SpiritLeafParticleData implements IParticleData
{
    public static final IDeserializer<SpiritLeafParticleData> DESERIALIZER = new IDeserializer<SpiritLeafParticleData>()
    {
        @Override
        public SpiritLeafParticleData deserialize(ParticleType<SpiritLeafParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new SpiritLeafParticleData();
        }

        public SpiritLeafParticleData read(ParticleType<SpiritLeafParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new SpiritLeafParticleData();
        }
    };
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.spiritLeaf;
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
