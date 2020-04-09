package com.kittykitcatcat.malum.particles.soulharvestparticle;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class SoulHarvestParticleData implements IParticleData
{
    public static final IDeserializer<SoulHarvestParticleData> DESERIALIZER = new IDeserializer<SoulHarvestParticleData>()
    {
        @Override
        public SoulHarvestParticleData deserialize(ParticleType<SoulHarvestParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new SoulHarvestParticleData();
        }

        public SoulHarvestParticleData read(ParticleType<SoulHarvestParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new SoulHarvestParticleData();
        }
    };
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.soulHarvest;
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
