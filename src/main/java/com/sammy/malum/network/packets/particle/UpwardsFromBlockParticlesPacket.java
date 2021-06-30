package com.sammy.malum.network.packets.particle;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.network.PacketEffects;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class UpwardsFromBlockParticlesPacket
{
    ArrayList<String> spirits;
    int posX;
    int posY;
    int posZ;

    public static UpwardsFromBlockParticlesPacket fromSpirits(int posX, int posY, int posZ, MalumSpiritType... spirits)
    {
        return fromSpirits(posX, posY,posZ, MalumHelper.toArrayList(spirits));
    }
    public static UpwardsFromBlockParticlesPacket fromSpirits(int posX, int posY, int posZ, ArrayList<MalumSpiritType> spirits)
    {
        ArrayList<String> strings = new ArrayList<>();
        for (MalumSpiritType type : spirits)
        {
            strings.add(type.identifier);
        }
        return new UpwardsFromBlockParticlesPacket(strings, posX, posY, posZ);
    }
    public UpwardsFromBlockParticlesPacket(ArrayList<String> spirits, int posX, int posY, int posZ)
    {
        this.spirits = spirits;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static UpwardsFromBlockParticlesPacket decode(PacketBuffer buf)
    {
        int strings = buf.readInt();
        ArrayList<String> spirits = new ArrayList<>();
        for (int i = 0; i < strings; i++)
        {
            spirits.add(buf.readString());
        }
        int posX = buf.readInt();
        int posY = buf.readInt();
        int posZ = buf.readInt();
        return new UpwardsFromBlockParticlesPacket(spirits, posX, posY, posZ);
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeInt(spirits.size());
        for (String string : spirits)
        {
            buf.writeString(string);
        }
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
    }
    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> PacketEffects.upwardsBlockParticles(spirits, new BlockPos(posX,posY,posZ)));
        context.get().setPacketHandled(true);
    }
}
