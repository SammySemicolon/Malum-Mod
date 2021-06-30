package com.sammy.malum.network.packets.particle;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.network.PacketEffects;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class BurstParticlePacket
{
    ArrayList<String> spirits;
    double posX;
    double posY;
    double posZ;

    public static BurstParticlePacket fromSpirits(double posX, double posY, double posZ, MalumSpiritType... spirits)
    {
        return fromSpirits(posX, posY,posZ, MalumHelper.toArrayList(spirits));
    }
    public static BurstParticlePacket fromSpirits(double posX, double posY, double posZ, ArrayList<MalumSpiritType> spirits)
    {
        ArrayList<String> strings = new ArrayList<>();
        for (MalumSpiritType type : spirits)
        {
            strings.add(type.identifier);
        }
        return new BurstParticlePacket(strings, posX, posY, posZ);
    }
    public BurstParticlePacket(ArrayList<String> spirits, double posX, double posY, double posZ)
    {
        this.spirits = spirits;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static BurstParticlePacket decode(PacketBuffer buf)
    {
        int strings = buf.readInt();
        ArrayList<String> spirits = new ArrayList<>();
        for (int i = 0; i < strings; i++)
        {
            spirits.add(buf.readString());
        }
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return new BurstParticlePacket(spirits, posX, posY, posZ);
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeInt(spirits.size());
        for (String string : spirits)
        {
            buf.writeString(string);
        }
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }
    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> PacketEffects.burstParticles(spirits, new Vector3d(posX,posY,posZ)));
        context.get().setPacketHandled(true);
    }
}
