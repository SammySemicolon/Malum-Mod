package com.sammy.malum.common.packets.particle.block.blight;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.LodestoneWorldParticleRenderType;

import java.awt.*;
import java.util.function.Supplier;

public class BlightMistParticlePacket extends LodestoneClientPacket {
    protected final BlockPos pos;
    public BlightMistParticlePacket(BlockPos pos) {
        this.pos = pos;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        for (int i = 0; i < 3; i++) {
            float multiplier = Mth.nextFloat(level.random, 0.4f, 1f);
            float timeMultiplier = Mth.nextFloat(level.random, 0.9f, 1.4f);
            Color color = new Color((int)(31*multiplier), (int)(19*multiplier), (int)(31*multiplier));
            boolean spinDirection = level.random.nextBoolean();
            ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.15f, 1f, 0)
                    .setLifetime((int) (45*timeMultiplier))
                    .setSpin(0.2f*(spinDirection ? 1 : -1))
                    .setScale(0.15f, 0.2f, 0)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                    .setColor(color, color)
                    .enableNoClip()
                    .randomOffset(0.1f, 0f)
                    .randomMotion(0.005f, 0.01f)
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .repeatSurroundBlock(level, pos, 2, Direction.UP);

            ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.25f, 0.55f, 0)
                    .setLifetime((int) (50*timeMultiplier))
                    .setSpin(0.1f*(spinDirection ? 1 : -1))
                    .setScale(0.35f, 0.4f, 0)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                    .setColor(color, color)
                    .randomOffset(0.2f, 0)
                    .enableNoClip()
                    .randomMotion(0.005f, 0.005f)
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .repeatSurroundBlock(level, pos, 2, Direction.UP);

            color = new Color((int)(80*multiplier), (int)(40*multiplier), (int)(80*multiplier));
            ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.02f, 0.15f, 0)
                    .setLifetime((int) (50*timeMultiplier))
                    .setSpin(0.1f*(spinDirection ? 1 : -1))
                    .setScale(0.35f, 0.4f, 0)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                    .setColor(color, color)
                    .randomOffset(0.2f, 0)
                    .enableNoClip()
                    .randomMotion(0.01f, 0.005f)
                    .repeatSurroundBlock(level, pos, 2, Direction.UP);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlightMistParticlePacket.class, BlightMistParticlePacket::encode, BlightMistParticlePacket::decode, BlightMistParticlePacket::handle);
    }

    public static BlightMistParticlePacket decode(FriendlyByteBuf buf) {
        return new BlightMistParticlePacket(new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}