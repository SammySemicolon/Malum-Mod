package com.kittykitcatcat.malum.network.packets;

import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.particles.bonk.BonkParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BonkPacket
{

    private double x, y, z;
    public BonkPacket(double x, double y, double z)
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
                    playerEntity.world.addParticle(new BonkParticleData(), x, y, z, 0, 0, 0);

                }));
        context.get().setPacketHandled(true);
    }

    public static BonkPacket decode(PacketBuffer buf)
    {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new BonkPacket(x, y, z);
    }
}