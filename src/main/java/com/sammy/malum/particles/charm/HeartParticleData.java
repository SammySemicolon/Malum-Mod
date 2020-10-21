package com.sammy.malum.particles.charm;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.init.ModParticles;
import com.sammy.malum.particles.ScaleParticleData;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleType;

public class HeartParticleData extends ScaleParticleData
{
    public HeartParticleData(float scale)
    {
        super(scale);
    }
    public static final IDeserializer<HeartParticleData> DESERIALIZER = new IDeserializer<HeartParticleData>()
    {
        @Override
        public HeartParticleData deserialize(ParticleType<HeartParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new HeartParticleData(reader.readFloat());
        }
        
        public HeartParticleData read(ParticleType<HeartParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new HeartParticleData(buffer.readFloat());
        }
    };
    
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.heart;
    }
    
    public static class Type extends ParticleType<HeartParticleData>
    {
        public Type(boolean alwaysShow)
        {
            super(alwaysShow, HeartParticleData.DESERIALIZER);
        }
        
        @Override
        public Codec<HeartParticleData> func_230522_e_()
        {
            return CODEC;
        }
    }
    public static Codec<HeartParticleData> CODEC = RecordCodecBuilder
            .create((instance) -> instance.group(
                    Codec.FLOAT.fieldOf("scale").forGetter(i -> i.scale)
            ).apply(instance, HeartParticleData::new));
}