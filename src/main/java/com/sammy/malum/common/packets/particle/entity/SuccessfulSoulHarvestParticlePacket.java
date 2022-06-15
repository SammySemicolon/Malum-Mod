package com.sammy.malum.common.packets.particle.entity;

import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.network.OrtusClientPacket;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
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

public class SuccessfulSoulHarvestParticlePacket extends OrtusClientPacket {
    private final Color color;
    private final Color endColor;
    private final double posX;
    private final double posY;
    private final double posZ;

    public SuccessfulSoulHarvestParticlePacket(Color color, Color endColor, double posX, double posY, double posZ) {
        this.color = color;
        this.endColor = endColor;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
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
        Level level = Minecraft.getInstance().level;
        ParticleBuilders.create(OrtusParticleRegistry.SPARKLE_PARTICLE)
                .setAlpha(1.0f, 0).setScale(0.4f, 0).setLifetime(20)
                .randomOffset(0.5, 0).randomMotion(0, 0.125f)
                .addMotion(0, 0.28f, 0)
                .setColor(color, endColor)
                .setSpin(0.4f)
                .setGravity(1)
                .repeat(level, posX, posY, posZ, 40);

        ParticleBuilders.create(OrtusParticleRegistry.SPARKLE_PARTICLE)
                .setAlpha(0.75f, 0).setScale(0.2f, 0).setLifetime(40)
                .randomOffset(0.5, 0.5).randomMotion(0.125f, 0.05)
                .addMotion(0, 0.15f, 0)
                .setColor(color, endColor)
                .setSpin(0.3f)
                .setGravity(1)
                .repeat(level, posX, posY, posZ, 30);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SuccessfulSoulHarvestParticlePacket.class, SuccessfulSoulHarvestParticlePacket::encode, SuccessfulSoulHarvestParticlePacket::decode, SuccessfulSoulHarvestParticlePacket::handle);
    }

    public static SuccessfulSoulHarvestParticlePacket decode(FriendlyByteBuf buf) {
        return new SuccessfulSoulHarvestParticlePacket(new Color(buf.readInt(), buf.readInt(), buf.readInt()), new Color(buf.readInt(), buf.readInt(), buf.readInt()), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }
}