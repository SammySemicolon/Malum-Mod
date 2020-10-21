package com.sammy.malum.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.sammy.malum.ClientHandler.setCharm;
import static com.sammy.malum.ClientHandler.setDread;

public class HuskChangePacket
{
    int id;
    boolean dread;
    boolean charm;

    public HuskChangePacket(int id, boolean dread, boolean charm)
    {
        this.id = id;
        this.dread = dread;
        this.charm = charm;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeInt(id);
        buf.writeBoolean(dread);
        buf.writeBoolean(charm);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> {
            setDread(id, dread);
            setCharm(id, charm);
        });
        context.get().setPacketHandled(true);
    }

    public static HuskChangePacket decode(PacketBuffer buf)
    {
        int uuid = buf.readInt();
        boolean dread = buf.readBoolean();
        boolean charm = buf.readBoolean();
        return new HuskChangePacket(uuid, dread,charm);
    }
}