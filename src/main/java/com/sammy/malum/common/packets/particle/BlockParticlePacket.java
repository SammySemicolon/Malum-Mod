package com.sammy.malum.common.packets.particle;

import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class BlockParticlePacket {
    private final Color color;
    private final int posX;
    private final int posY;
    private final int posZ;

    public BlockParticlePacket(Color color, int posX, int posY, int posZ) {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlockParticlePacket.class, BlockParticlePacket::encode, BlockParticlePacket::decode, BlockParticlePacket::execute);
    }

    public static BlockParticlePacket decode(FriendlyByteBuf buf) {
        Color color = new Color(buf.readInt(), buf.readInt(), buf.readInt());
        int posX = buf.readInt();
        int posY = buf.readInt();
        int posZ = buf.readInt();
        return new BlockParticlePacket(color, posX, posY, posZ);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(color.getRed());
        buf.writeInt(color.getGreen());
        buf.writeInt(color.getBlue());
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
    }

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientOnly.addParticles(new BlockPos(posX, posY, posZ), color);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static class ClientOnly {
        public static void addParticles(BlockPos pos, Color color) {
            Level level = Minecraft.getInstance().level;
            ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.08f, 0.16f, 0)
                    .setLifetime(15)
                    .setSpin(0.2f)
                    .setScale(0.15f, 0.2f, 0)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                    .setColor(color, color)
                    .enableNoClip()
                    .randomOffset(0.1f, 0.1f)
                    .randomMotion(0.001f, 0.001f)
                    .evenlyRepeatEdges(level, pos, 6, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

            ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.04f, 0.08f, 0)
                    .setLifetime(20)
                    .setSpin(0.1f)
                    .setScale(0.35f, 0.4f, 0)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                    .setColor(color, color)
                    .randomOffset(0.2f)
                    .enableNoClip()
                    .randomMotion(0.001f, 0.001f)
                    .evenlyRepeatEdges(level, pos, 8, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
        }
    }
}