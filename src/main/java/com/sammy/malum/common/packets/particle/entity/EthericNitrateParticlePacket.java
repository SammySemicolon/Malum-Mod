package com.sammy.malum.common.packets.particle.entity;

import com.sammy.malum.common.entity.nitrate.EthericNitrateEntity;
import com.sammy.malum.common.packets.particle.ColorBasedParticleEffectPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.SimpleParticleOptions;

import java.awt.*;
import java.util.Random;
import java.util.function.Supplier;

public class EthericNitrateParticlePacket extends LodestoneClientPacket {

    protected final double posX;
    protected final double posY;
    protected final double posZ;

    public EthericNitrateParticlePacket(double posX, double posY, double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }


    public static EthericNitrateParticlePacket decode(FriendlyByteBuf buf) {
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return new EthericNitrateParticlePacket(posX, posY, posZ);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        RandomSource rand = level.random;
        for (int i = 0; i < 3; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            float motionMultiplier = (float) (1+Math.pow(rand.nextFloat(), 2));
            ParticleBuilders.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setAlpha(0.2f, 0.8f, 0)
                    .setLifetime(12)
                    .setSpinOffset(spinOffset)
                    .setSpinCoefficient(1.25f)
                    .setSpin(0.9f * spinDirection, 0)
                    .setSpinEasing(Easing.CUBIC_IN)
                    .setScale(0.075f, 0.25f, 0)
                    .setScaleCoefficient(0.8f)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT)
                    .setColor(EthericNitrateEntity.FIRST_COLOR.brighter(), EthericNitrateEntity.SECOND_COLOR.darker())
                    .enableNoClip()
                    .randomOffset(0.85f)
                    .setGravity(1.1f)
                    .addMotion(0, 0.3f + rand.nextFloat() * 0.15f * motionMultiplier, 0)
                    .disableNoClip()
                    .randomMotion(0.2f*motionMultiplier, 0.25f*motionMultiplier)
                    .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                    .repeat(level, posX, posY, posZ, 4);
        }
        int spinOffset = rand.nextInt(360);
        for (int i = 0; i < 3; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            float scaleMultiplier = (float) (1+Math.pow(rand.nextFloat(), 2)*0.5f);
            ParticleBuilders.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setAlpha(0.35f, 0.07f, 0)
                    .setAlphaEasing(Easing.SINE_IN, Easing.CIRC_IN)
                    .setLifetime(9)
                    .setSpinOffset(spinOffset)
                    .setSpin((0.125f + rand.nextFloat() * 0.075f) * spinDirection)
                    .setScale(0.8f*scaleMultiplier, 0.5f, 0)
                    .setScaleEasing(Easing.EXPO_OUT, Easing.SINE_IN)
                    .setColor(EthericNitrateEntity.FIRST_COLOR.brighter(), EthericNitrateEntity.SECOND_COLOR.darker())
                    .randomOffset(0.6f)
                    .enableNoClip()
                    .randomMotion(0.02f, 0.02f)
                    .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                    .repeat(level, posX, posY, posZ, 5);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, EthericNitrateParticlePacket.class, EthericNitrateParticlePacket::encode, EthericNitrateParticlePacket::decode, EthericNitrateParticlePacket::handle);
    }
}