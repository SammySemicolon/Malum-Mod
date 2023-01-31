package com.sammy.malum.common.packets.particle;

import net.minecraft.network.FriendlyByteBuf;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;

import java.awt.*;

public abstract class ColorBasedParticleEffectPacket extends LodestoneClientPacket {
    protected final Color color;
    protected final double posX;
    protected final double posY;
    protected final double posZ;

    public ColorBasedParticleEffectPacket(Color color, double posX, double posY, double posZ) {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(color.getRed());
        buf.writeInt(color.getGreen());
        buf.writeInt(color.getBlue());
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }

    public static <T extends ColorBasedParticleEffectPacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        Color color = new Color(buf.readInt(), buf.readInt(), buf.readInt());
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return provider.getPacket(color, posX, posY, posZ);
    }

    public interface PacketProvider<T extends ColorBasedParticleEffectPacket> {
        T getPacket(Color color, double posX, double posY, double posZ);
    }

}