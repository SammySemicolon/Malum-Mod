package com.sammy.malum.common.packets.particle.base;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;

public abstract class BlockBasedParticleEffectPacket extends OneSidedPayloadData {
    protected final BlockPos pos;

    public BlockBasedParticleEffectPacket(FriendlyByteBuf buf) {
        super(buf);
        this.pos = buf.readBlockPos();
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }
}