package com.kittykitcatcat.malum.network.packets;

import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.sounds.FurnaceLoopSound;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FurnaceSoundStartPacket
{

    private double x, y, z;
    public FurnaceSoundStartPacket(double x, double y, double z)
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
                world.playSound(Minecraft.getInstance().player, pos, ModSounds.furnace_start, SoundCategory.BLOCKS, 1F, 1F);
                Minecraft.getInstance().getSoundHandler().play(new FurnaceLoopSound(world.getTileEntity(pos)));
            }));
        context.get().setPacketHandled(true);
    }

    public static FurnaceSoundStartPacket decode(PacketBuffer buf)
    {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new FurnaceSoundStartPacket(x, y, z);
    }
}