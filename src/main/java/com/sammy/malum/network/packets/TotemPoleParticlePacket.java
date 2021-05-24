package com.sammy.malum.network.packets;

import com.sammy.malum.network.PacketEffects;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TotemPoleParticlePacket
{
    String spirit;
    int posX;
    int posY;
    int posZ;
    boolean success;
    public TotemPoleParticlePacket(String spirit, int posX, int posY, int posZ, boolean success)
    {
        this.spirit = spirit;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.success = success;
    }

    public static TotemPoleParticlePacket decode(PacketBuffer buf)
    {
        String spirit = buf.readString();
        int posX = buf.readInt();
        int posY = buf.readInt();
        int posZ = buf.readInt();
        boolean sparkles = buf.readBoolean();
        return new TotemPoleParticlePacket(spirit, posX, posY, posZ, sparkles);
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeString(spirit);
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
        buf.writeBoolean(success);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> PacketEffects.totemBlockParticles(spirit, new BlockPos(posX,posY,posZ), success));
        context.get().setPacketHandled(true);
    }
}