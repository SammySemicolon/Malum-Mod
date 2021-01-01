package com.sammy.malum.core.systems.particles.data;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class ColoredParticleData implements IParticleData
{
    float r1 = 1, g1 = 1, b1 = 1, a1 = 1, r2 = 1, g2 = 1, b2 = 1, a2 = 0;
    public float scale = 1f;
    public boolean gravity = false;
    
    ParticleType<?> type;
    
    public ColoredParticleData(ParticleType<?> type)
    {
        this.type = type;
    }
    
    @Override
    public ParticleType<?> getType()
    {
        return type;
    }
    
    @Override
    public void write(PacketBuffer buffer)
    {
        buffer.writeFloat(r1).writeFloat(g1).writeFloat(b1).writeFloat(a1);
        buffer.writeFloat(r2).writeFloat(g2).writeFloat(b2).writeFloat(a2);
        buffer.writeFloat(scale);
        buffer.writeBoolean(gravity);
    }
    
    @Override
    public String getParameters()
    {
        return getClass().getSimpleName() + ":internal";
    }
    
    public static final IDeserializer<ColoredParticleData> DESERIALIZER = new IDeserializer<ColoredParticleData>()
    {
        @Override
        public ColoredParticleData deserialize(ParticleType<ColoredParticleData> type, StringReader reader) throws CommandSyntaxException
        {
            float r1 = reader.readFloat();
            reader.expect(' ');
            float g1 = reader.readFloat();
            reader.expect(' ');
            float b1 = reader.readFloat();
            reader.expect(' ');
            float a1 = reader.readFloat();
            reader.expect(' ');
            float r2 = reader.readFloat();
            reader.expect(' ');
            float g2 = reader.readFloat();
            reader.expect(' ');
            float b2 = reader.readFloat();
            reader.expect(' ');
            float a2 = reader.readFloat();
            reader.expect(' ');
            float scale = reader.readFloat();
            reader.expect(' ');
            boolean gravity = reader.readBoolean();
            ColoredParticleData data = new ColoredParticleData(type);
            data.r1 = r1;
            data.g1 = g1;
            data.b1 = b1;
            data.a1 = a1;
            data.r2 = r2;
            data.g2 = g2;
            data.b2 = b2;
            data.a2 = a2;
            data.scale = scale;
            data.gravity = gravity;
            return data;
        }
        
        @Override
        public ColoredParticleData read(ParticleType<ColoredParticleData> type, PacketBuffer buf)
        {
            float r1 = buf.readFloat();
            float g1 = buf.readFloat();
            float b1 = buf.readFloat();
            float a1 = buf.readFloat();
            float r2 = buf.readFloat();
            float g2 = buf.readFloat();
            float b2 = buf.readFloat();
            float a2 = buf.readFloat();
            float scale = buf.readFloat();
            boolean gravity = buf.readBoolean();
            ColoredParticleData data = new ColoredParticleData(type);
            data.r1 = r1;
            data.g1 = g1;
            data.b1 = b1;
            data.a1 = a1;
            data.r2 = r2;
            data.g2 = g2;
            data.b2 = b2;
            data.a2 = a2;
            data.scale = scale;
            data.gravity = gravity;
            return data;
        }
    };
    public static Codec<ColoredParticleData> codecFor(ParticleType<?> type) {
        return RecordCodecBuilder.create(instance -> instance.group(
                Codec.FLOAT.fieldOf("r1").forGetter(d -> d.r1),
                Codec.FLOAT.fieldOf("g1").forGetter(d -> d.g1),
                Codec.FLOAT.fieldOf("b1").forGetter(d -> d.b1),
                Codec.FLOAT.fieldOf("a1").forGetter(d -> d.a1),
                Codec.FLOAT.fieldOf("r2").forGetter(d -> d.r2),
                Codec.FLOAT.fieldOf("g2").forGetter(d -> d.g2),
                Codec.FLOAT.fieldOf("b2").forGetter(d -> d.b2),
                Codec.FLOAT.fieldOf("a2").forGetter(d -> d.a2),
                Codec.FLOAT.fieldOf("scale").forGetter(d -> d.scale),
                Codec.BOOL.fieldOf("gravity").forGetter(d -> d.gravity)
        ).apply(instance, (r1, g1, b1, a1, r2, g2, b2, a2, scale, gravity) -> {
            ColoredParticleData data = new ColoredParticleData(type);
            data.r1 = r1; data.g1 = g1; data.b1 = b1; data.a1 = a1;
            data.r2 = r2; data.g2 = g2; data.b2 = b2; data.a2 = a2;
            data.scale = scale;
            data.gravity = gravity;
            return data;
        }));
    }
    public static class Type extends ParticleType<ColoredParticleData>
    {
        public Type(boolean alwaysShow)
        {
            super(alwaysShow, ColoredParticleData.DESERIALIZER);
        }
        
        @Override
        public Codec<ColoredParticleData> func_230522_e_()
        {
            return codecFor(this);
        }
    }
}
