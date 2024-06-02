package com.sammy.malum.common.packets.particle.base.spirit;

import com.sammy.malum.common.packets.particle.base.PositionBasedParticleEffectPacket;
import com.sammy.malum.core.handlers.SpiritHarvestHandler;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

public abstract class SpiritBasedParticleEffectPacket extends PositionBasedParticleEffectPacket {
    protected final List<String> spirits;

    public SpiritBasedParticleEffectPacket(List<String> spirits, double posX, double posY, double posZ) {
        super(posX, posY, posZ);
        this.spirits = spirits;
    }

    public SpiritBasedParticleEffectPacket(FriendlyByteBuf buf) {
        super(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.spirits = readSpirits(buf);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(spirits.size());
        for (String string : spirits) {
            buf.writeUtf(string);
        }
        super.encode(buf);
    }

    // Static method to read spirits from the buffer
    protected static List<String> readSpirits(FriendlyByteBuf buf) {
        int strings = buf.readInt();
        List<String> spirits = new ArrayList<>();
        for (int i = 0; i < strings; i++) {
            spirits.add(buf.readUtf());
        }
        return spirits;
    }

    @Override
    public void executeClient(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel) {
        super.executeClient(client, listener, responseSender, channel);
        for (String string : spirits) {
            execute(client, listener, responseSender, channel, SpiritHarvestHandler.getSpiritType(string));
        }
    }

    @Environment(EnvType.CLIENT)
    protected abstract void execute(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel, MalumSpiritType spiritType);

    public static <T extends SpiritBasedParticleEffectPacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        int strings = buf.readInt();
        List<String> spirits = new ArrayList<>();
        for (int i = 0; i < strings; i++) {
            spirits.add(buf.readUtf());
        }
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return provider.getPacket(spirits, posX, posY, posZ);
    }

    public interface PacketProvider<T extends SpiritBasedParticleEffectPacket> {
        T getPacket(List<String> spirits, double posX, double posY, double posZ);
    }
}
