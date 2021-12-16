package com.sammy.malum.common.packets.particle.altar;

import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.network.PacketEffects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class SpiritAltarCraftParticlePacket
{
    ArrayList<String> spirits;
    double posX;
    double posY;
    double posZ;

    public static SpiritAltarCraftParticlePacket fromSpirits(ArrayList<MalumSpiritType> spiritTypes, double posX, double posY, double posZ)
    {
        ArrayList<String> spirits = new ArrayList<>();
        for (MalumSpiritType type : spiritTypes)
        {
            spirits.add(type.identifier);
        }
        return new SpiritAltarCraftParticlePacket(spirits, posX, posY, posZ);
    }

    public SpiritAltarCraftParticlePacket(ArrayList<String> spirits, double posX, double posY, double posZ)
    {
        this.spirits = spirits;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static SpiritAltarCraftParticlePacket decode(FriendlyByteBuf buf)
    {
        int strings = buf.readInt();
        ArrayList<String> spirits = new ArrayList<>();
        for (int i = 0; i < strings; i++)
        {
            spirits.add(buf.readUtf());
        }
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return new SpiritAltarCraftParticlePacket(spirits, posX, posY, posZ);
    }

    public void encode(FriendlyByteBuf buf)
    {
        buf.writeInt(spirits.size());
        for (String string : spirits)
        {
            buf.writeUtf(string);
        }
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> PacketEffects.altarCraftParticles(spirits, posX, posY, posZ));
        context.get().setPacketHandled(true);
    }
}