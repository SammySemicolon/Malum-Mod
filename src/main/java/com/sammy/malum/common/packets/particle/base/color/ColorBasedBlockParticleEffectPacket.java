package com.sammy.malum.common.packets.particle.base.color;

import com.sammy.malum.common.packets.particle.base.BlockBasedParticleEffectPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

import java.awt.*;

public abstract class ColorBasedBlockParticleEffectPacket extends BlockBasedParticleEffectPacket {
    protected final Color color;

    public ColorBasedBlockParticleEffectPacket(Color color, BlockPos pos) {
        super(pos);
        this.color = color;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(color.getRed());
        buf.writeInt(color.getGreen());
        buf.writeInt(color.getBlue());
        super.encode(buf);
    }

    public static <T extends ColorBasedBlockParticleEffectPacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        Color color = new Color(buf.readInt(), buf.readInt(), buf.readInt());
        int posX = buf.readInt();
        int posY = buf.readInt();
        int posZ = buf.readInt();
        return provider.getPacket(color, new BlockPos(posX, posY, posZ));
    }

    public interface PacketProvider<T extends ColorBasedBlockParticleEffectPacket> {
        T getPacket(Color color, BlockPos pos);
    }

}