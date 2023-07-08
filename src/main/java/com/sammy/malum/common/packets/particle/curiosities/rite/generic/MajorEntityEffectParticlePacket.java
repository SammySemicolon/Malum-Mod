package com.sammy.malum.common.packets.particle.curiosities.rite.generic;

import com.sammy.malum.common.packets.particle.base.color.ColorBasedParticleEffectPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.helpers.render.ColorHelper;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;

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
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0f, 0.125f, 0).build())
                    .setSpinData(SpinParticleData.create(0.025f * spinDirection, (0.2f + rand.nextFloat() * 0.05f) * spinDirection, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setScaleData(GenericParticleData.create(0.025f, 0.1f + rand.nextFloat() * 0.075f, 0.35f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color, color.darker()).build())
                    .setLifetime(25)
                    .enableNoClip()
                    .setRandomOffset(0.2f, 0.2f)
                    .setRandomMotion(0.02f)
                    .addActor(p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.95f)))
                    .repeat(level, posX, posY, posZ, 8);
        }
        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0f, 0.06f, 0).build())
                .setSpinData(SpinParticleData.create(0.1f, 0.4f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.4f, 0.35f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setLifetime(20)
                .setColorData(ColorParticleData.create(color, color.darker()).build())
                .enableNoClip()
                .setRandomOffset(0.05f, 0.05f)
                .setRandomMotion(0.05f)
                .addActor(p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.5f)))
                .repeat(level, posX, posY, posZ, 12);

        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0f, 0.06f, 0).build())
                .setSpinData(SpinParticleData.create(0.1f, 0.25f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.45f, 0.35f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(color.darker(), ColorHelper.darker(color, 3)).build())
                .setLifetime(25)
                .enableNoClip()
                .setRandomOffset(0.15f, 0.15f)
                .setRandomMotion(0.015f, 0.015f)
                .addActor(p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.92f)))
                .repeat(level, posX, posY, posZ, 20);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, MajorEntityEffectParticlePacket.class, MajorEntityEffectParticlePacket::encode, MajorEntityEffectParticlePacket::decode, MajorEntityEffectParticlePacket::handle);
    }

    public static MajorEntityEffectParticlePacket decode(FriendlyByteBuf buf) {
        return decode(MajorEntityEffectParticlePacket::new, buf);
    }
}