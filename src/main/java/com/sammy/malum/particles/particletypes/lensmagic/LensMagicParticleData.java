package com.sammy.malum.particles.particletypes.lensmagic;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.init.ModParticles;
import com.sammy.malum.particles.ScaleParticleData;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleType;

public class LensMagicParticleData extends ScaleParticleData
{
    public static final IDeserializer<LensMagicParticleData> DESERIALIZER = new IDeserializer<LensMagicParticleData>()
    {
        @Override
        public LensMagicParticleData deserialize(ParticleType<LensMagicParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            return new LensMagicParticleData(reader.readFloat());
        }
        
        public LensMagicParticleData read(ParticleType<LensMagicParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new LensMagicParticleData(buffer.readFloat());
        }
    };
    public static Codec<LensMagicParticleData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(Codec.FLOAT.fieldOf("scale").forGetter(i -> i.scale)).apply(instance, LensMagicParticleData::new));
    
    public LensMagicParticleData(float scale)
    {
        super(scale);
    }
    
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.lensMagic;
    }
    
    public static class Type extends ParticleType<LensMagicParticleData>
    {
        public Type(boolean alwaysShow)
        {
            super(alwaysShow, LensMagicParticleData.DESERIALIZER);
        }
        
        @Override
        public Codec<LensMagicParticleData> func_230522_e_()
        {
            return CODEC;
        }
    }
}
