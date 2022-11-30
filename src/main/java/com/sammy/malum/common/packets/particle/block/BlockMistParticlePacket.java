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
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;

import java.awt.*;
import java.util.function.Supplier;

public class BlockMistParticlePacket extends BlockParticlePacket {
    public BlockMistParticlePacket(Color color, BlockPos pos) {
        super(color, pos);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.08f, 0.32f, 0)
                .setLifetime(15)
                .setSpin(0.2f)
                .setScale(0.15f, 0.2f, 0)
                .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0f)
                .randomMotion(0.001f, 0.002f)
                .repeatSurroundBlock(level, pos, 6, Direction.UP, Direction.DOWN);

        ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.04f, 0.16f, 0)
                .setLifetime(20)
                .setSpin(0.1f)
                .setScale(0.35f, 0.4f, 0)
                .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                .setColor(color, color)
                .randomOffset(0.2f, 0)
                .enableNoClip()
                .randomMotion(0.001f, 0.002f)
                .repeatSurroundBlock(level, pos, 8, Direction.UP, Direction.DOWN);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlockMistParticlePacket.class, BlockMistParticlePacket::encode, BlockMistParticlePacket::decode, BlockMistParticlePacket::handle);
    }

    public static BlockMistParticlePacket decode(FriendlyByteBuf buf) {
        return new BlockMistParticlePacket(new Color(buf.readInt(), buf.readInt(), buf.readInt()), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}