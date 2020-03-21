package com.kittykitcatcat.malum.network.packets;

import com.kittykitcatcat.malum.capabilities.CapabilityValueGetter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TotalFlightTimePacket
{
    double value;
    public TotalFlightTimePacket(double type)
    {
        this.value = type;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeDouble(value);
    }
    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayerEntity entityLivingBase = context.get().getSender();
            CapabilityValueGetter.setTotalFlightTime(entityLivingBase, value);
        });
        context.get().setPacketHandled(true);
    }

    public static TotalFlightTimePacket decode(PacketBuffer buf)
    {
        return new TotalFlightTimePacket(buf.readDouble());
    }
}