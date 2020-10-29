package com.sammy.malum.particles.spiritflame;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.init.ModParticles;
import com.sammy.malum.particles.ScaleParticleData;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleType;

public class SpiritFlameParticleData extends ScaleParticleData
{
    public static final IDeserializer<SpiritFlameParticleData> DESERIALIZER = new IDeserializer<SpiritFlameParticleData>()
    {
        @Override
        public SpiritFlameParticleData deserialize(ParticleType<SpiritFlameParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new SpiritFlameParticleData(reader.readFloat());
        }
        
        public SpiritFlameParticleData read(ParticleType<SpiritFlameParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new SpiritFlameParticleData(buffer.readFloat());
        }
    };
    public static Codec<SpiritFlameParticleData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(Codec.FLOAT.fieldOf("scale").forGetter(i -> i.scale)).apply(instance, SpiritFlameParticleData::new));
    
    public SpiritFlameParticleData(float scale)
    {
        super(scale);
    }
    
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.spiritFlame;
    }
    
    public static class Type extends ParticleType<SpiritFlameParticleData>
    {
        public Type(boolean alwaysShow)
        {
            super(alwaysShow, SpiritFlameParticleData.DESERIALIZER);
        }
        
        @Override
        public Codec<SpiritFlameParticleData> func_230522_e_()
        {
            return CODEC;
        }
    }
}