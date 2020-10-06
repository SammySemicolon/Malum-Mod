package com.kittykitcatcat.malum.particles.skull;

import com.kittykitcatcat.malum.init.ModParticles;
import com.kittykitcatcat.malum.particles.ScaleParticleData;
import com.kittykitcatcat.malum.particles.lensmagic.LensMagicParticleData;
import com.kittykitcatcat.malum.particles.spiritflame.SpiritFlameParticleData;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class SkullParticleData extends ScaleParticleData
{
    public SkullParticleData(float scale)
    {
        super(scale);
    }
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
    public static Codec<SkullParticleData> CODEC = RecordCodecBuilder
            .create((instance) -> instance.group(
                    Codec.FLOAT.fieldOf("scale").forGetter(i -> i.scale)
            ).apply(instance, SkullParticleData::new));
}