package com.sammy.malum.common.packets.particle;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Supplier;

public class TotemParticlePacket {
    private final ArrayList<Color> colors;
    private final int posX;
    private final int posY;
    private final int posZ;

    public TotemParticlePacket(ArrayList<Color> colors, int posX, int posY, int posZ) {
        this.colors = colors;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static TotemParticlePacket decode(FriendlyByteBuf buf) {
        ArrayList<Color> colors = new ArrayList<>();
        int colorCount = buf.readInt();
        for (int i = 0; i < colorCount; i++) {
            Color color = new Color(buf.readInt(), buf.readInt(), buf.readInt());
            colors.add(color);
        }
        int posX = buf.readInt();
        int posY = buf.readInt();
        int posZ = buf.readInt();
        return new TotemParticlePacket(colors, posX, posY, posZ);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(colors.size());
        for (Color color : colors) {
            buf.writeInt(color.getRed());
            buf.writeInt(color.getGreen());
            buf.writeInt(color.getBlue());
        }
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
    }

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientOnly.addParticles(new BlockPos(posX, posY, posZ), colors);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, TotemParticlePacket.class, TotemParticlePacket::encode, TotemParticlePacket::decode, TotemParticlePacket::execute);
    }

    public static class ClientOnly {
        public static void addParticles(BlockPos pos, ArrayList<Color> colors) {
            for (int i = 0; i < colors.size(); i++) {
                BlockParticlePacket.ClientOnly.addParticles(pos.above(i), colors.get(i));
            }
        }
    }
}