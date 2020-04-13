package com.kittykitcatcat.malum.network.packets;

import com.kittykitcatcat.malum.MalumMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SpiritInfusionStopLoopSoundPacket
{

    private double x, y, z;
    public SpiritInfusionStopLoopSoundPacket(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
            DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft.getInstance().getSoundHandler().stop(new ResourceLocation(MalumMod.MODID, "spirit_infusion_loop"), SoundCategory.BLOCKS);
            }));
        context.get().setPacketHandled(true);
    }

    public static SpiritInfusionStopLoopSoundPacket decode(PacketBuffer buf)
    {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new SpiritInfusionStopLoopSoundPacket(x, y, z);
    }
}