package com.sammy.malum.common.packets.particle.base.color;

import com.sammy.malum.common.packets.particle.base.PositionBasedParticleEffectPacket;
import net.minecraft.network.FriendlyByteBuf;

import java.awt.*;

public abstract class ColorBasedParticleEffectPacket extends PositionBasedParticleEffectPacket {
    protected final Color color;

    public ColorBasedParticleEffectPacket(Color color, double posX, double posY, double posZ) {
        super(posX, posY, posZ);
        this.color = color;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(color.getRed());
        buf.writeInt(color.getGreen());
        buf.writeInt(color.getBlue());
        super.encode(buf);
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