package com.kittykitcatcat.malum.particles.bonk;

import com.kittykitcatcat.malum.init.ModParticles;
import com.kittykitcatcat.malum.particles.ScaleParticleData;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class BonkParticleData extends ScaleParticleData
{
    public static final IDeserializer<BonkParticleData> DESERIALIZER = new IDeserializer<BonkParticleData>()
    {
        @Override
        public BonkParticleData deserialize(ParticleType<BonkParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new BonkParticleData(reader.readFloat());
        }

        public BonkParticleData read(ParticleType<BonkParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new BonkParticleData(buffer.readFloat());
        }
    };
    
    public BonkParticleData(float scale)
    {
        super(scale);
    }
    
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.bonk;
    }
    public static class Type extends ParticleType<BonkParticleData>
    {
        public Type(boolean alwaysShow)
        {
            super(alwaysShow, BonkParticleData.DESERIALIZER);
        }
        
        @Override
        public Codec<BonkParticleData> func_230522_e_()
        {
            return CODEC;
        }
    }
    public static Codec<BonkParticleData> CODEC = RecordCodecBuilder
            .create((instance) -> instance.group(
                    Codec.FLOAT.fieldOf("scale").forGetter(i -> i.scale)
            ).apply(instance, BonkParticleData::new));
}
