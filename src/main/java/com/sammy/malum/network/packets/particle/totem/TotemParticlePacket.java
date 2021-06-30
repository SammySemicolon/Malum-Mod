package com.sammy.malum.network.packets.particle.totem;

import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.network.PacketEffects;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class TotemParticlePacket
{
    ArrayList<String> spirits;
    int posX;
    int posY;
    int posZ;
    boolean success;
    public static TotemParticlePacket fromSpirits(ArrayList<MalumSpiritType> spiritIngredients, BlockPos pos, boolean success)
    {
        ArrayList<String> spirits = new ArrayList<>();
        for (MalumSpiritType type : spiritIngredients)
        {
            spirits.add(type.identifier);
        }
        return new TotemParticlePacket(spirits, pos.getX(),pos.getY(),pos.getZ(), success);
    }

    public TotemParticlePacket(ArrayList<String> spirits, int posX, int posY, int posZ, boolean success)
    {
        this.spirits = spirits;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.success = success;
    }

    public static TotemParticlePacket decode(PacketBuffer buf)
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
        boolean sparkles = buf.readBoolean();
        return new TotemParticlePacket(spirits, posX, posY, posZ, sparkles);
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
        buf.writeBoolean(success);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> PacketEffects.totemParticles(spirits, new BlockPos(posX,posY,posZ), success));
        context.get().setPacketHandled(true);
    }
}