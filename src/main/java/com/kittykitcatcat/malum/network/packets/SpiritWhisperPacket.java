package com.kittykitcatcat.malum.network.packets;

import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.sounds.FurnaceLoopSound;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SpiritWhisperPacket
{

    private double x, y, z;
    public SpiritWhisperPacket(double x, double y, double z)
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
                World world = Minecraft.getInstance().world;
                BlockPos pos = new BlockPos(x,y,z);
                world.playSound(Minecraft.getInstance().player, pos, ModSounds.spirit_whisper, SoundCategory.BLOCKS, 0.6f, MathHelper.nextFloat(world.rand, 0.9f, 1.6f));
            }));
        context.get().setPacketHandled(true);
    }

    public static SpiritWhisperPacket decode(PacketBuffer buf)
    {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new SpiritWhisperPacket(x, y, z);
    }
}