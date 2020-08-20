package com.kittykitcatcat.malum.particles.skull;

import com.kittykitcatcat.malum.init.ModParticles;
import com.kittykitcatcat.malum.particles.ScaleParticleData;
import com.kittykitcatcat.malum.particles.lensmagic.LensMagicParticleData;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
}