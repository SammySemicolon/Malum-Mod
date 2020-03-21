package com.kittykitcatcat.malum.network.packets;

import com.kittykitcatcat.malum.capabilities.CapabilityValueGetter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CanFlyPacket
{
    boolean value;

    public CanFlyPacket(boolean type)
    {
        this.value = type;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeBoolean(value);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            ServerPlayerEntity entityLivingBase = context.get().getSender();
            CapabilityValueGetter.setCanFly(entityLivingBase, value);
        });
        context.get().setPacketHandled(true);
    }

    public static CanFlyPacket decode(PacketBuffer buf)
    {
        return new CanFlyPacket(buf.readBoolean());
    }
}