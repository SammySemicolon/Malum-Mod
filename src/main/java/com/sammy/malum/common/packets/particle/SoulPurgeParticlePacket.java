package com.sammy.malum.common.packets.particle;

import com.sammy.malum.core.registry.ParticleRegistry;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class SoulPurgeParticlePacket {
    private final Color color;
    private final Color endColor;
    private final double posX;
    private final double posY;
    private final double posZ;

    public SoulPurgeParticlePacket(Color color, Color endColor, double posX, double posY, double posZ) {
        this.color = color;
        this.endColor = endColor;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static SoulPurgeParticlePacket decode(FriendlyByteBuf buf) {
        Color color = new Color(buf.readInt(), buf.readInt(), buf.readInt());
        Color endColor = new Color(buf.readInt(), buf.readInt(), buf.readInt());
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return new SoulPurgeParticlePacket(color, endColor, posX, posY, posZ);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(color.getRed());
        buf.writeInt(color.getGreen());
        buf.writeInt(color.getBlue());
        buf.writeInt(endColor.getRed());
        buf.writeInt(endColor.getGreen());
        buf.writeInt(endColor.getBlue());
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientOnly.addParticles(new Vec3(posX, posY, posZ), color, endColor);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SoulPurgeParticlePacket.class, SoulPurgeParticlePacket::encode, SoulPurgeParticlePacket::decode, SoulPurgeParticlePacket::execute);
    }

    public static class ClientOnly {
        public static void addParticles(Vec3 pos, Color color, Color endColor) {
            Level level = Minecraft.getInstance().level;
            RenderUtilities.create(ParticleRegistry.SPARKLE_PARTICLE)
                    .setAlpha(1.0f, 0).setScale(0.4f, 0).setLifetime(20)
                    .randomOffset(0.5, 0).randomVelocity(0, 0.125f)
                    .addVelocity(0, 0.28f, 0)
                    .setColor(color,endColor)
                    .setSpin(0.4f)
                    .enableGravity()
                    .repeat(level, pos.x, pos.y - 0.2f, pos.z, 40);

            RenderUtilities.create(ParticleRegistry.SPARKLE_PARTICLE)
                    .setAlpha(0.75f, 0).setScale(0.2f, 0).setLifetime(40)
                    .randomOffset(0.5, 0.5).randomVelocity(0.125f, 0.05)
                    .addVelocity(0, 0.15f, 0)
                    .setColor(color,endColor)
                    .setSpin(0.3f)
                    .enableGravity()
                    .repeat(level, pos.x, pos.y - 0.2f, pos.z, 30);
        }
    }
}