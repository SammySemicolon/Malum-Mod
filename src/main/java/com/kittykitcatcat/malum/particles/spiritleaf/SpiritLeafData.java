package com.kittykitcatcat.malum.particles.spiritleaf;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class SpiritLeafData implements IParticleData
{
    public static final IDeserializer<SpiritLeafData> DESERIALIZER = new IDeserializer<SpiritLeafData>()
    {
        @Override
        public SpiritLeafData deserialize(ParticleType<SpiritLeafData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new SpiritLeafData();
        }

        public SpiritLeafData read(ParticleType<SpiritLeafData> particleTypeIn, PacketBuffer buffer)
        {
            return new SpiritLeafData();
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
