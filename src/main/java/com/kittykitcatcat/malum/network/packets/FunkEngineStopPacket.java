package com.kittykitcatcat.malum.network.packets;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.blocks.machines.funkengine.FunkEngineTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FunkEngineStopPacket
{
    private double x, y, z;

    public FunkEngineStopPacket(double x, double y, double z)
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
                DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
                {
                    World world = Minecraft.getInstance().world;
                    BlockPos pos = new BlockPos(x, y, z);
                    TileEntity tileEntity = world.getTileEntity(pos);
                    if (tileEntity instanceof FunkEngineTileEntity)
                    {
                        FunkEngineTileEntity funkEngineTileEntity = (FunkEngineTileEntity) tileEntity;
                        
                        MalumHelper.stopPlayingSound(funkEngineTileEntity.sound);
                        funkEngineTileEntity.sound = null;
                    }
                }));
        context.get().setPacketHandled(true);
    }

    public static FunkEngineStopPacket decode(PacketBuffer buf)
    {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new FunkEngineStopPacket(x, y, z);
    }
}