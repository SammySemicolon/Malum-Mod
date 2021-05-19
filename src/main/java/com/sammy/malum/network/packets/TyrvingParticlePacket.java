package com.sammy.malum.network.packets;

import com.sammy.malum.network.PacketEffects;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TyrvingParticlePacket
{
    double posX;
    double posY;
    double posZ;
    public TyrvingParticlePacket(double posX, double posY, double posZ)
    {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }
    
    public static TyrvingParticlePacket decode(PacketBuffer buf)
    {
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return new TyrvingParticlePacket(posX,posY,posZ);
    }
    
    public void encode(PacketBuffer buf)
    {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }
    
    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> PacketEffects.tyrving(posX,posY,posZ));
        context.get().setPacketHandled(true);
    }
}