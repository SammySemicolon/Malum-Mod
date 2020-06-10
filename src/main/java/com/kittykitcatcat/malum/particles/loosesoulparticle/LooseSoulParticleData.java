package com.kittykitcatcat.malum.particles.loosesoulparticle;

import com.kittykitcatcat.malum.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class LooseSoulParticleData implements IParticleData
{
    public static final IDeserializer<LooseSoulParticleData> DESERIALIZER = new IDeserializer<LooseSoulParticleData>()
    {
        @Override

        public LooseSoulParticleData deserialize(ParticleType<LooseSoulParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
        {
            reader.expect(' ');
            return new LooseSoulParticleData(reader.readDouble(), reader.readDouble(), reader.readDouble());
        }


        public LooseSoulParticleData read(ParticleType<LooseSoulParticleData> particleTypeIn, PacketBuffer buffer)
        {
            return new LooseSoulParticleData(buffer.readDouble(),buffer.readDouble(),buffer.readDouble());
        }
    };
    @Override
    public ParticleType<?> getType()
    {
        return ModParticles.looseSoul;
    }
    public double X;
    public double Y;
    public double Z;
    public LooseSoulParticleData(double X, double Y, double Z)
    {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }
    @Override
    public void write(PacketBuffer buffer)
    {
        buffer.writeDouble(X);
        buffer.writeDouble(Y);
        buffer.writeDouble(Z);
    }

    @Override
    public String getParameters()
    {
        return ""+this.X+" "+this.Y+" "+this.Z;
    }
}