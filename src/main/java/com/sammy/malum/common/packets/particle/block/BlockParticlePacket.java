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
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;

import java.awt.*;
import java.util.function.Supplier;

public class BlockParticlePacket extends LodestoneClientPacket {
    protected final Color color;
    protected final BlockPos pos;

    public BlockParticlePacket(Color color, BlockPos pos) {
        this.color = color;
        this.pos = pos;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(color.getRed());
        buf.writeInt(color.getGreen());
        buf.writeInt(color.getBlue());
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.12f, 0.16f, 0).build())
                .setSpinData(SpinParticleData.create(0.2f).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.2f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(color, color).build())
                .setLifetime(10)
                .enableNoClip()
                .setRandomOffset(0.1f, 0.1f)
                .setRandomMotion(0.001f, 0.001f)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .repeatSurroundBlock(level, pos, 6, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.04f, 0.08f, 0).build())
                .setSpinData(SpinParticleData.create(0.1f).build())
                .setScaleData(GenericParticleData.create(0.35f, 0.4f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(color, color).build())
                .setLifetime(15)
                .setRandomOffset(0.2f)
                .enableNoClip()
                .setRandomMotion(0.001f, 0.001f)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .repeatSurroundBlock(level, pos, 8, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlockParticlePacket.class, BlockParticlePacket::encode, BlockParticlePacket::decode, BlockParticlePacket::handle);
    }

    public static BlockParticlePacket decode(FriendlyByteBuf buf) {
        return new BlockParticlePacket(new Color(buf.readInt(), buf.readInt(), buf.readInt()), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}