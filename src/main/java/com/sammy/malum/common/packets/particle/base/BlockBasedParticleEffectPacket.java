package com.sammy.malum.common.packets.particle.base;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;

public abstract class BlockBasedParticleEffectPacket extends LodestoneClientPacket {
    protected final BlockPos pos;

    public BlockBasedParticleEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }
}