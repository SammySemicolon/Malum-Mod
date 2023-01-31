package com.sammy.malum.common.packets.particle.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TotemBaseActivationParticlePacket extends LodestoneClientPacket {
    private final List<Color> colors;
    private final BlockPos pos;

    public TotemBaseActivationParticlePacket(List<Color> colors, BlockPos pos) {
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
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.1f, 0.22f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN_OUT).build())
                    .setSpinData(SpinParticleData.create(0.2f).build())
                    .setScaleData(GenericParticleData.create(0.15f, 0.2f, 0.1f).setCoefficient(0.7f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN_OUT).build())
                    .setColorData(ColorParticleData.create(color, color).build())
                    .setLifetime(25)
                    .enableNoClip()
                    .setRandomOffset(0.1f, 0.1f)
                    .setRandomMotion(0.001f, 0.001f)
                    .repeatSurroundBlock(level, pos.above(i), 3, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.06f, 0.14f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN_OUT).build())
                    .setSpinData(SpinParticleData.create(0.1f).build())
                    .setScaleData(GenericParticleData.create(0.35f, 0.4f, 0.1f).setCoefficient(0.7f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN_OUT).build())
                    .setColorData(ColorParticleData.create(color, color).build())
                    .setLifetime(30)
                    .setRandomOffset(0.2f)
                    .enableNoClip()
                    .setRandomMotion(0.001f, 0.001f)
                    .repeatSurroundBlock(level, pos.above(i), 5, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, TotemBaseActivationParticlePacket.class, TotemBaseActivationParticlePacket::encode, TotemBaseActivationParticlePacket::decode, TotemBaseActivationParticlePacket::handle);
    }

    public static TotemBaseActivationParticlePacket decode(FriendlyByteBuf buf) {
        List<Color> colors = new ArrayList<>();
        int colorCount = buf.readInt();
        for (int i = 0; i < colorCount; i++) {
            Color color = new Color(buf.readInt(), buf.readInt(), buf.readInt());
            colors.add(color);
        }
        return new TotemBaseActivationParticlePacket(colors, new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}
