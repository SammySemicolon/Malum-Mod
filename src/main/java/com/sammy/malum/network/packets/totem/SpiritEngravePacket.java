package com.sammy.malum.network.packets.totem;

import com.sammy.malum.network.PacketEffects;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SpiritEngravePacket
{
    String spirit;
    int posX;
    int posY;
    int posZ;
    public SpiritEngravePacket(String spirit, int posX, int posY, int posZ)
    {
        this.spirit = spirit;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static SpiritEngravePacket decode(PacketBuffer buf)
    {
        String spirit = buf.readString();
        int posX = buf.readInt();
        int posY = buf.readInt();
        int posZ = buf.readInt();
        return new SpiritEngravePacket(spirit, posX, posY, posZ);
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeString(spirit);
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> PacketEffects.spiritEngrave(spirit, new BlockPos(posX,posY,posZ)));
        context.get().setPacketHandled(true);
    }
}