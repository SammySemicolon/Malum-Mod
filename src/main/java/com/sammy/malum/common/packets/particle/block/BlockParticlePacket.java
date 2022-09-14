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
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.SimpleParticleOptions;

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
        ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.12f, 0.16f, 0)
                .setLifetime(10)
                .setSpin(0.2f)
                .setScale(0.15f, 0.2f, 0)
                .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomMotion(0.001f, 0.001f)
                .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                .evenlyRepeatEdges(level, pos, 6, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

        ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.04f, 0.08f, 0)
                .setLifetime(15)
                .setSpin(0.1f)
                .setScale(0.35f, 0.4f, 0)
                .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                .setColor(color, color)
                .randomOffset(0.2f)
                .enableNoClip()
                .randomMotion(0.001f, 0.001f)
                .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                .evenlyRepeatEdges(level, pos, 8, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlockParticlePacket.class, BlockParticlePacket::encode, BlockParticlePacket::decode, BlockParticlePacket::handle);
    }

    public static BlockParticlePacket decode(FriendlyByteBuf buf) {
        return new BlockParticlePacket(new Color(buf.readInt(), buf.readInt(), buf.readInt()), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}