package com.sammy.malum.core.systems.particles.data;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

//Huge thanks to Elucent for help with this, original code: https://github.com/elucent/eidolon/blob/master/src/main/java/elucent/eidolon/particle/GenericParticleData.java
public class MalumParticleData implements IParticleData
{
    public float scale = 1f;
    public boolean gravity = false;
    
    ParticleType<?> type;
    
    public MalumParticleData(ParticleType<?> type)
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
        buffer.writeFloat(scale);
        buffer.writeBoolean(gravity);
    }
    
    @Override
    public String getParameters()
    {
        return getClass().getSimpleName() + ":internal";
    }
    
    public static final IDeserializer<MalumParticleData> DESERIALIZER = new IDeserializer<MalumParticleData>()
    {
        @Override
        public MalumParticleData deserialize(ParticleType<MalumParticleData> type, StringReader reader) throws CommandSyntaxException
        {
            reader.expect(' ');
            float scale = reader.readFloat();
            reader.expect(' ');
            boolean gravity = reader.readBoolean();
            MalumParticleData data = new MalumParticleData(type);
            data.scale = scale;
            data.gravity = gravity;
            return data;
        }
        
        @Override
        public MalumParticleData read(ParticleType<MalumParticleData> type, PacketBuffer buf)
        {
            float scale = buf.readFloat();
            boolean gravity = buf.readBoolean();
            MalumParticleData data = new MalumParticleData(type);
            data.scale = scale;
            data.gravity = gravity;
            return data;
        }
    };
    public static Codec<MalumParticleData> codecFor(ParticleType<?> type)
    {
        return RecordCodecBuilder.create(instance -> instance.group(Codec.FLOAT.fieldOf("scale").forGetter(d -> d.scale), Codec.BOOL.fieldOf("gravity").forGetter(d -> d.gravity)).apply(instance, (scale, gravity) -> {
            MalumParticleData data = new MalumParticleData(type);
            data.scale = scale;
            data.gravity = gravity;
            return data;
        }));
    }
    public static class Type extends ParticleType<MalumParticleData>
    {
        public Type(boolean alwaysShow)
        {
            super(alwaysShow, MalumParticleData.DESERIALIZER);
        }
        
        @Override
        public Codec<MalumParticleData> func_230522_e_()
        {
            return codecFor(this);
        }
    }
}