package com.sammy.malum.common.packets.particle.entity;

import com.sammy.malum.common.packets.particle.ColorBasedParticleEffectPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;

import java.awt.*;
import java.util.Random;
import java.util.function.Supplier;

public class MajorEntityEffectParticlePacket extends ColorBasedParticleEffectPacket {

    public MajorEntityEffectParticlePacket(Color color, double posX, double posY, double posZ) {
        super(color, posX, posY, posZ);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        RandomSource rand = level.random;
        for (int i = 0; i <= 3; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0f, 0.125f, 0)
                    .setLifetime(25)
                    .setSpin(0.025f * spinDirection, (0.2f + rand.nextFloat() * 0.05f) * spinDirection, 0)
                    .setSpinEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                    .setScale(0.025f, 0.1f + rand.nextFloat() * 0.075f, 0.35f)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                    .setColor(color, color.darker())
                    .enableNoClip()
                    .randomOffset(0.2f, 0.2f)
                    .setMotionCoefficient(0.95f)
                    .randomMotion(0.02f)
                    .repeat(level, posX, posY, posZ, 8);
        }
        ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setAlpha(0f, 0.06f, 0)
                .setLifetime(20)
                .setSpin(0.1f, 0.4f, 0)
                .setSpinEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                .setScale(0.15f, 0.4f, 0.35f)
                .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                .setColor(color, color.darker())
                .enableNoClip()
                .randomOffset(0.05f, 0.05f)
                .randomMotion(0.05f)
                .setMotionCoefficient(0.5f)
                .repeat(level, posX, posY, posZ, 12);

        ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0f, 0.06f, 0)
                .setLifetime(25)
                .setSpin(0.1f, 0.25f, 0)
                .setSpinEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                .setScale(0.15f, 0.45f, 0.35f)
                .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                .setColor(color.darker(), ColorHelper.darker(color, 3))
                .enableNoClip()
                .randomOffset(0.15f, 0.15f)
                .randomMotion(0.015f, 0.015f)
                .setMotionCoefficient(0.92f)
                .repeat(level, posX, posY, posZ, 20);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, MajorEntityEffectParticlePacket.class, MajorEntityEffectParticlePacket::encode, MajorEntityEffectParticlePacket::decode, MajorEntityEffectParticlePacket::handle);
    }

    public static MajorEntityEffectParticlePacket decode(FriendlyByteBuf buf) {
        return decode(MajorEntityEffectParticlePacket::new, buf);
    }
}