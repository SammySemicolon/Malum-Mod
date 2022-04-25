package com.sammy.malum.common.packets.particle.altar;

import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.client.ParticleRegistry;
import com.sammy.ortus.setup.OrtusParticles;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AltarCraftParticlePacket {
    private final List<String> spirits;
    private final double posX;
    private final double posY;
    private final double posZ;

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, AltarCraftParticlePacket.class, AltarCraftParticlePacket::encode, AltarCraftParticlePacket::decode, AltarCraftParticlePacket::execute);
    }

    public AltarCraftParticlePacket(List<String> spirits, double posX, double posY, double posZ) {
        this.spirits = spirits;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static AltarCraftParticlePacket decode(FriendlyByteBuf buf) {
        int strings = buf.readInt();
        ArrayList<String> spirits = new ArrayList<>();
        for (int i = 0; i < strings; i++) {
            spirits.add(buf.readUtf());
        }
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return new AltarCraftParticlePacket(spirits, posX, posY, posZ);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(spirits.size());
        for (String string : spirits) {
            buf.writeUtf(string);
        }
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ClientOnly.addParticles(spirits, posX, posY, posZ));
        context.get().setPacketHandled(true);
    }
    public static class ClientOnly {
        public static void addParticles(List<String> spirits, double posX, double posY, double posZ) {
            Level level = Minecraft.getInstance().level;
            ArrayList<MalumSpiritType> types = new ArrayList<>();
            for (String string : spirits) {
                types.add(SpiritHelper.getSpiritType(string));
            }
            for (MalumSpiritType type : types) {
                Color color = type.color;
                Color endColor = type.endColor;
                ParticleBuilders.create(OrtusParticles.TWINKLE_PARTICLE)
                        .setAlpha(0.6f, 0f)
                        .setLifetime(80)
                        .setScale(0.15f, 0)
                        .setColor(color, endColor)
                        .randomOffset(0.1f)
                        .addMotion(0, 0.26f, 0)
                        .randomMotion(0.03f, 0.04f)
                        .enableGravity()
                        .repeat(level, posX, posY, posZ, 32);

                ParticleBuilders.create(OrtusParticles.WISP_PARTICLE)
                        .setAlpha(0.2f, 0f)
                        .setLifetime(60)
                        .setScale(0.4f, 0)
                        .setColor(color, endColor)
                        .randomOffset(0.25f, 0.1f)
                        .randomMotion(0.004f, 0.004f)
                        .enableNoClip()
                        .repeat(level, posX, posY, posZ, 12);

                ParticleBuilders.create(OrtusParticles.SPARKLE_PARTICLE)
                        .setAlpha(0.05f, 0f)
                        .setLifetime(30)
                        .setScale(0.2f, 0)
                        .setColor(color, endColor)
                        .randomOffset(0.05f, 0.05f)
                        .randomMotion(0.02f, 0.02f)
                        .enableNoClip()
                        .repeat(level, posX, posY, posZ, 8);
            }
        }
    }
}