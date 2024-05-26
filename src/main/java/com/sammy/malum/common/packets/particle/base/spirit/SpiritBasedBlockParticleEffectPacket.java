package com.sammy.malum.common.packets.particle.base.spirit;

import com.sammy.malum.common.packets.particle.base.BlockBasedParticleEffectPacket;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.sammy.malum.common.packets.particle.base.spirit.SpiritBasedParticleEffectPacket.readSpirits;

public abstract class SpiritBasedBlockParticleEffectPacket extends BlockBasedParticleEffectPacket {
    protected final List<String> spirits;

    public SpiritBasedBlockParticleEffectPacket(List<String> spirits, BlockPos pos) {
        super(pos);
        this.spirits = spirits;
    }

    public SpiritBasedBlockParticleEffectPacket(FriendlyByteBuf buf) {
        super(buf);
        this.spirits = readSpirits(buf);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(spirits.size());
        for (String string : spirits) {
            buf.writeUtf(string);
        }
        super.encode(buf);
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

    public static <T extends SpiritBasedBlockParticleEffectPacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        int strings = buf.readInt();
        List<String> spirits = new ArrayList<>();
        for (int i = 0; i < strings; i++) {
            spirits.add(buf.readUtf());
        }
        double posX = buf.readInt();
        double posY = buf.readInt();
        double posZ = buf.readInt();
        return provider.getPacket(spirits, new BlockPos((int) posX, (int) posY, (int) posZ));
    }

    public interface PacketProvider<T extends SpiritBasedBlockParticleEffectPacket> {
        T getPacket(List<String> spirits, BlockPos pos);
    }
}
