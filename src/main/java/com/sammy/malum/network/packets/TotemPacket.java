package com.sammy.malum.network.packets;

import com.sammy.malum.core.init.items.MalumItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TotemPacket
{
    public TotemPacket()
    {
    }
    
    public static TotemPacket decode(PacketBuffer buf)
    {
        return new TotemPacket();
    }
    
    public void encode(PacketBuffer buf)
    {
    }
    
    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> {
            Minecraft.getInstance().gameRenderer.displayItemActivation(MalumItems.POPPET_OF_UNDYING.get().getDefaultInstance());
        });
        context.get().setPacketHandled(true);
    }
}