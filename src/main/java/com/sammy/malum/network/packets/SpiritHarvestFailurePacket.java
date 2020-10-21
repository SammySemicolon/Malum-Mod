package com.sammy.malum.network.packets;

import com.sammy.malum.ClientHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.sammy.malum.ClientHandler.setCharm;
import static com.sammy.malum.ClientHandler.setDread;

public class SpiritHarvestFailurePacket
{
    public SpiritHarvestFailurePacket()
    {
    }

    public void encode(PacketBuffer buf)
    {
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(ClientHandler::spiritHarvestStop);
        context.get().setPacketHandled(true);
    }

    public static SpiritHarvestFailurePacket decode(PacketBuffer buf)
    {
        return new SpiritHarvestFailurePacket();
    }
}