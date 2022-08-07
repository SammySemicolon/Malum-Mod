package com.sammy.malum.common.packets.particle.block;

import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Supplier;

public class TotemBaseActivationParticlePacket extends LodestoneClientPacket {
    private final ArrayList<Color> colors;
    private final BlockPos pos;

    public TotemBaseActivationParticlePacket(ArrayList<Color> colors, BlockPos pos) {
        this.colors = colors;
        this.pos = pos;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(colors.size());
        for (Color color : colors) {
            buf.writeInt(color.getRed());
            buf.writeInt(color.getGreen());
            buf.writeInt(color.getBlue());
        }
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        for (int i = 0; i < colors.size(); i++) {
            Color color = colors.get(i);
            ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.1f, 0.22f, 0)
                    .setLifetime(15)
                    .setSpin(0.2f)
                    .setScale(0.15f, 0.2f, 0)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                    .setColor(color, color)
                    .enableNoClip()
                    .randomOffset(0.1f, 0.1f)
                    .randomMotion(0.001f, 0.001f)
                    .evenlyRepeatEdges(level, pos.above(i), 3, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

            ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.06f, 0.14f, 0)
                    .setLifetime(20)
                    .setSpin(0.1f)
                    .setScale(0.35f, 0.4f, 0)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                    .setColor(color, color)
                    .randomOffset(0.2f)
                    .enableNoClip()
                    .randomMotion(0.001f, 0.001f)
                    .evenlyRepeatEdges(level, pos.above(i), 5, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, TotemBaseActivationParticlePacket.class, TotemBaseActivationParticlePacket::encode, TotemBaseActivationParticlePacket::decode, TotemBaseActivationParticlePacket::handle);
    }

    public static TotemBaseActivationParticlePacket decode(FriendlyByteBuf buf) {
        ArrayList<Color> colors = new ArrayList<>();
        int colorCount = buf.readInt();
        for (int i = 0; i < colorCount; i++) {
            Color color = new Color(buf.readInt(), buf.readInt(), buf.readInt());
            colors.add(color);
        }
        return new TotemBaseActivationParticlePacket(colors, new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}