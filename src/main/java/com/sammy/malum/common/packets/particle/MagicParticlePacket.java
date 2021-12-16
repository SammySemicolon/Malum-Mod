package com.sammy.malum.common.packets.particle;

import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.particle.ParticleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.Level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class MagicParticlePacket {
    private final Color color;
    private final double posX;
    private final double posY;
    private final double posZ;

    public MagicParticlePacket(Color color, double posX, double posY, double posZ) {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static MagicParticlePacket decode(PacketBuffer buf) {
        Color color = new Color(buf.readInt(), buf.readInt(), buf.readInt());
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return new MagicParticlePacket(color, posX, posY, posZ);
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(color.getRed());
        buf.writeInt(color.getGreen());
        buf.writeInt(color.getBlue());
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientOnly.addParticles(new Vector3d(posX, posY, posZ), color);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, MagicParticlePacket.class, MagicParticlePacket::encode, MagicParticlePacket::decode, MagicParticlePacket::execute);
    }

    public static class ClientOnly {
        public static void addParticles(Vector3d pos, Color color) {
            Level Level = Minecraft.getInstance().level;
            ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.1f, 0f)
                    .setLifetime(10)
                    .setSpin(0.4f)
                    .setScale(0.4f, 0)
                    .setColor(color, color.darker())
                    .enableNoClip()
                    .randomOffset(0.2f, 0.2f)
                    .randomVelocity(0.01f, 0.01f)
                    .repeat(Level, pos.x, pos.y, pos.z, 12);

            ParticleManager.create(ParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.05f, 0f)
                    .setLifetime(20)
                    .setSpin(0.1f)
                    .setScale(0.6f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.4f)
                    .enableNoClip()
                    .randomVelocity(0.025f, 0.025f)
                    .repeat(Level, pos.x, pos.y, pos.z, 20);
        }
    }
}