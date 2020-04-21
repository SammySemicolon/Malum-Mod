package com.kittykitcatcat.malum.network.packets;

import com.kittykitcatcat.malum.particles.bonk.BonkParticleData;
import com.kittykitcatcat.malum.particles.boom.BoomParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BoomPacket
{

    private double x, y, z;
    public BoomPacket(double x, double y, double z)
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
                    PlayerEntity playerEntity = Minecraft.getInstance().player;
                    playerEntity.world.addParticle(new BoomParticleData(), x, y, z, 0, 0, 0);

                }));
        context.get().setPacketHandled(true);
    }

    public static BoomPacket decode(PacketBuffer buf)
    {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new BoomPacket(x, y, z);
    }
}