package com.sammy.malum.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.sammy.malum.ClientHandler.setHusk;

public class FinishFunkEngineDisc
{
    public BlockPos funkEnginePos;
    public FinishFunkEngineDisc(BlockPos funkEnginePos)
    {
        this.funkEnginePos = funkEnginePos;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeBlockPos(funkEnginePos);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
                DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
                {
                
                }));
        context.get().setPacketHandled(true);
    }

    public static FinishFunkEngineDisc decode(PacketBuffer buf)
    {
        BlockPos funkEnginePos = buf.readBlockPos();
        return new FinishFunkEngineDisc(funkEnginePos);
    }
}