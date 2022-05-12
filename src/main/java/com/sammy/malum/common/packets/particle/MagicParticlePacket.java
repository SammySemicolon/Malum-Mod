package com.sammy.malum.common.packets.particle;

import com.sammy.malum.core.setup.client.ParticleRegistry;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

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

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, MagicParticlePacket.class, MagicParticlePacket::encode, MagicParticlePacket::decode, MagicParticlePacket::execute);
    }

    public static MagicParticlePacket decode(FriendlyByteBuf buf) {
        Color color = new Color(buf.readInt(), buf.readInt(), buf.readInt());
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return new MagicParticlePacket(color, posX, posY, posZ);
    }

    public void encode(FriendlyByteBuf buf) {
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
                ClientOnly.addParticles(new Vec3(posX, posY, posZ), color);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static class ClientOnly {
        public static void addParticles(Vec3 pos, Color color) {
            Level level = Minecraft.getInstance().level;
            ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.1f, 0f)
                    .setLifetime(10)
                    .setSpin(0.4f)
                    .setScale(0.4f, 0)
                    .setColor(color, color.darker())
                    .enableNoClip()
                    .randomOffset(0.2f, 0.2f)
                    .randomMotion(0.01f, 0.01f)
                    .repeat(level, pos.x, pos.y, pos.z, 12);

            ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.05f, 0f)
                    .setLifetime(20)
                    .setSpin(0.1f)
                    .setScale(0.6f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.4f)
                    .enableNoClip()
                    .randomMotion(0.025f, 0.025f)
                    .repeat(level, pos.x, pos.y, pos.z, 20);
        }
    }
}