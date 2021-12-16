package com.sammy.malum.common.packets.particle.altar;

import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.network.PacketEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class SpiritAltarConsumeParticlePacket
{
    ItemStack stack;
    ArrayList<String> spirits;
    double posX;
    double posY;
    double posZ;
    double altarPosX;
    double altarPosY;
    double altarPosZ;

    public static SpiritAltarConsumeParticlePacket fromSpirits(ItemStack stack, ArrayList<MalumSpiritType> spiritTypes, double posX, double posY, double posZ, double altarPosX, double altarPosY, double altarPosZ)
    {
        ArrayList<String> spirits = new ArrayList<>();
        for (MalumSpiritType spirit : spiritTypes)
        {
            spirits.add(spirit.identifier);
        }
        return new SpiritAltarConsumeParticlePacket(stack, spirits, posX, posY, posZ, altarPosX, altarPosY, altarPosZ);
    }

    public SpiritAltarConsumeParticlePacket(ItemStack stack, ArrayList<String> spirits, double posX, double posY, double posZ, double altarPosX, double altarPosY, double altarPosZ)
    {
        this.stack = stack;
        this.spirits = spirits;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.altarPosX = altarPosX;
        this.altarPosY = altarPosY;
        this.altarPosZ = altarPosZ;
    }

    public static SpiritAltarConsumeParticlePacket decode(FriendlyByteBuf buf)
    {
        ItemStack stack = buf.readItem();
        int strings = buf.readInt();
        ArrayList<String> spirits = new ArrayList<>();
        for (int i = 0; i < strings; i++)
        {
            spirits.add(buf.readUtf());
        }
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        double altarPosX = buf.readDouble();
        double altarPosY = buf.readDouble();
        double altarPosZ = buf.readDouble();
        return new SpiritAltarConsumeParticlePacket(stack, spirits, posX, posY, posZ,altarPosX, altarPosY,altarPosZ);
    }

    public void encode(FriendlyByteBuf buf)
    {
        buf.writeItem(stack);
        buf.writeInt(spirits.size());
        for (String string : spirits)
        {
            buf.writeUtf(string);
        }
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
        buf.writeDouble(altarPosX);
        buf.writeDouble(altarPosY);
        buf.writeDouble(altarPosZ);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> PacketEffects.altarConsumeParticles(stack, spirits, posX, posY, posZ, altarPosX, altarPosY, altarPosZ));
        context.get().setPacketHandled(true);
    }
}