package com.sammy.malum.common.packets.particle;

import com.sammy.malum.common.packets.particle.base.spirit.*;
import net.minecraft.network.*;
import net.minecraft.world.item.*;

import java.util.*;

public abstract class ItemAbsorbParticleEffectPacket extends SpiritBasedParticleEffectPacket {
    protected final ItemStack stack;
    protected final double altarPosX;
    protected final double altarPosY;
    protected final double altarPosZ;

    public ItemAbsorbParticleEffectPacket(ItemStack stack, List<String> spirits, double posX, double posY, double posZ, double altarPosX, double altarPosY, double altarPosZ) {
        super(spirits, posX, posY, posZ);
        this.stack = stack;
        this.altarPosX = altarPosX;
        this.altarPosY = altarPosY;
        this.altarPosZ = altarPosZ;
    }

    public static <T extends ItemAbsorbParticleEffectPacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        ItemStack stack = buf.readItem();
        int strings = buf.readInt();
        List<String> spirits = new ArrayList<>();
        for (int i = 0; i < strings; i++) {
            spirits.add(buf.readUtf());
        }
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        double altarPosX = buf.readDouble();
        double altarPosY = buf.readDouble();
        double altarPosZ = buf.readDouble();
        return provider.getPacket(stack, spirits, posX, posY, posZ, altarPosX, altarPosY, altarPosZ);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeItem(stack);
        buf.writeInt(spirits.size());
        for (String string : spirits) {
            buf.writeUtf(string);
        }
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
        buf.writeDouble(altarPosX);
        buf.writeDouble(altarPosY);
        buf.writeDouble(altarPosZ);
    }

    public interface PacketProvider<T extends ItemAbsorbParticleEffectPacket> {
        T getPacket(ItemStack stack, List<String> spirits, double posX, double posY, double posZ, double altarPosX, double altarPosY, double altarPosZ);
    }
}
