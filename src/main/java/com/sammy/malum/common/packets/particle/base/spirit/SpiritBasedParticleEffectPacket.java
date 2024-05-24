package com.sammy.malum.common.packets.particle.base.spirit;

import com.sammy.malum.common.packets.particle.base.PositionBasedParticleEffectPacket;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.network.FriendlyByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class SpiritBasedParticleEffectPacket extends PositionBasedParticleEffectPacket {
    protected final List<String> spirits;

    public SpiritBasedParticleEffectPacket(List<String> spirits, double posX, double posY, double posZ) {
        super(posX, posY, posZ);
        this.spirits = spirits;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(spirits.size());
        for (String string : spirits) {
            buf.writeUtf(string);
        }
        super.encode(buf);
    }

    @Environment(EnvType.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        for (String string : spirits) {
            execute(context, SpiritHarvestHandler.getSpiritType(string));
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract void execute(Supplier<NetworkEvent.Context> context, MalumSpiritType spiritType);

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
