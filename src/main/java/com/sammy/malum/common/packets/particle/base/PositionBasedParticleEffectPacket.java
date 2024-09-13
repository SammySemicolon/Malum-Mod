package com.sammy.malum.common.packets.particle.base;

import net.minecraft.network.FriendlyByteBuf;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;

public abstract class PositionBasedParticleEffectPacket extends OneSidedPayloadData {
    protected final double posX;
    protected final double posY;
    protected final double posZ;

    public PositionBasedParticleEffectPacket(FriendlyByteBuf buf) {
        super(buf);
        this.posX = buf.readDouble();
        this.posY = buf.readDouble();
        this.posZ = buf.readDouble();
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }
}
