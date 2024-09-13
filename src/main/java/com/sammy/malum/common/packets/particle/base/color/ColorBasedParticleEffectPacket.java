package com.sammy.malum.common.packets.particle.base.color;

import com.sammy.malum.common.packets.particle.base.PositionBasedParticleEffectPacket;
import net.minecraft.network.FriendlyByteBuf;

import java.awt.*;

public abstract class ColorBasedParticleEffectPacket extends PositionBasedParticleEffectPacket {
    protected final Color color;

    public ColorBasedParticleEffectPacket(FriendlyByteBuf buf) {
        super(buf);
        this.color = new Color(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeInt(color.getRed());
        buf.writeInt(color.getGreen());
        buf.writeInt(color.getBlue());
        super.serialize(buf);
    }
}