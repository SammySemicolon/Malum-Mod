package com.sammy.malum.common.packets.particle.block.functional;

import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.network.OrtusClientPacket;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FunctionalBlockItemAbsorbParticlePacket extends OrtusClientPacket {
    protected final ItemStack stack;
    protected final List<String> spirits;
    protected final double posX;
    protected final double posY;
    protected final double posZ;
    protected final double altarPosX;
    protected final double altarPosY;
    protected final double altarPosZ;

    public FunctionalBlockItemAbsorbParticlePacket(ItemStack stack, List<String> spirits, double posX, double posY, double posZ, double altarPosX, double altarPosY, double altarPosZ) {
        this.stack = stack;
        this.spirits = spirits;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.altarPosX = altarPosX;
        this.altarPosY = altarPosY;
        this.altarPosZ = altarPosZ;
    }

    public static <T extends FunctionalBlockItemAbsorbParticlePacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        ItemStack stack = buf.readItem();
        int strings = buf.readInt();
        ArrayList<String> spirits = new ArrayList<>();
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

    public interface PacketProvider<T extends FunctionalBlockItemAbsorbParticlePacket> {
        T getPacket(ItemStack stack, List<String> spirits, double posX, double posY, double posZ, double altarPosX, double altarPosY, double altarPosZ);
    }
}