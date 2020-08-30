package com.kittykitcatcat.malum.particles.lensmagic;

import com.kittykitcatcat.malum.init.ModParticles;
import com.kittykitcatcat.malum.particles.ScaleParticleData;
import com.kittykitcatcat.malum.particles.skull.SkullParticleData;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class LensMagicParticleData extends ScaleParticleData
{
    public LensMagicParticleData(float scale)
    {
        super(scale);
    }
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
    
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.lensMagic;
    }
}
