package com.sammy.malum.common.packets.particle.base;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;

public abstract class BlockBasedParticleEffectPacket extends OneSidedPayloadData {
    protected final BlockPos pos;

    public BlockBasedParticleEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public BlockBasedParticleEffectPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }
}