package com.sammy.malum.particles.particletypes.skull;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.init.ModParticles;
import com.sammy.malum.particles.ScaleParticleData;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleType;

public class SkullParticleData extends ScaleParticleData
{
    public static final IDeserializer<SkullParticleData> DESERIALIZER = new IDeserializer<SkullParticleData>()
    {
        @Override
        public SkullParticleData deserialize(ParticleType<SkullParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new SkullParticleData(reader.readFloat());
        }
        
        public SkullParticleData read(ParticleType<SkullParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new SkullParticleData(buffer.readFloat());
        }
    };
    public static Codec<SkullParticleData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(Codec.FLOAT.fieldOf("scale").forGetter(i -> i.scale)).apply(instance, SkullParticleData::new));
    
    public SkullParticleData(float scale)
    {
        super(scale);
    }
    
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.skull;
    }
    
    public static class Type extends ParticleType<SkullParticleData>
    {
        public Type(boolean alwaysShow)
        {
            super(alwaysShow, SkullParticleData.DESERIALIZER);
        }
        
        @Override
        public Codec<SkullParticleData> func_230522_e_()
        {
            return CODEC;
        }
    }
}