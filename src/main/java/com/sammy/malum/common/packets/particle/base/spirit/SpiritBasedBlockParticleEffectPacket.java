package com.sammy.malum.common.packets.particle.base.spirit;

import com.sammy.malum.common.packets.particle.base.BlockBasedParticleEffectPacket;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class SpiritBasedBlockParticleEffectPacket extends BlockBasedParticleEffectPacket {
    protected final List<String> spirits;

    public SpiritBasedBlockParticleEffectPacket(List<String> spirits, BlockPos pos) {
        super(pos);
        this.spirits = spirits;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(spirits.size());
        for (String string : spirits) {
            buf.writeUtf(string);
        }
        super.encode(buf);
    }

    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        for (String string : spirits) {
            execute(context, SpiritHelper.getSpiritType(string));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void execute(Supplier<NetworkEvent.Context> context, MalumSpiritType spiritType);

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
