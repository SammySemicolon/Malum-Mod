package com.kittykitcatcat.malum.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.kittykitcatcat.malum.ClientHandler.setHusk;

public class HuskChangePacket
{
    int id;
    boolean value;

    public HuskChangePacket(int id, boolean value)
    {
        this.id = id;
        this.value = value;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeInt(id);
        buf.writeBoolean(value);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
                DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
                {
                    setHusk(id, value);
                }));
        context.get().setPacketHandled(true);
    }

    public static HuskChangePacket decode(PacketBuffer buf)
    {
        int uuid = buf.readInt();
        boolean value = buf.readBoolean();
        return new HuskChangePacket(uuid, value);
    }
}