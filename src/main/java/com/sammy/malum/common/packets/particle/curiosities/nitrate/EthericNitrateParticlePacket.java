package com.sammy.malum.common.packets.particle.curiosities.nitrate;

import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.common.packets.particle.base.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;
import net.minecraftforge.network.simple.*;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.data.*;

import java.util.*;
import java.util.function.*;

public class EthericNitrateParticlePacket extends PositionBasedParticleEffectPacket {

    public EthericNitrateParticlePacket(double posX, double posY, double posZ) {
        super(posX, posY, posZ);
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
            WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.2f, 0.8f, 0).build())
                    .setLifetime(20)
                    .setSpinData(SpinParticleData.create(0.9f * spinDirection, 0).setSpinOffset(spinOffset).setCoefficient(1.25f).setEasing(Easing.CUBIC_IN).build())
                    .setScaleData(GenericParticleData.create(0.075f, 0.25f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT).build())
                    .setColorData(ColorParticleData.create(EthericNitrateEntity.FIRST_COLOR.brighter(), EthericNitrateEntity.SECOND_COLOR.darker()).build())
                    .enableNoClip()
                    .setRandomOffset(0.85f)
                    .setGravity(1.1f)
                    .addMotion(0, 0.3f + rand.nextFloat() * 0.15f * motionMultiplier, 0)
                    .disableNoClip()
                    .setRandomMotion(0.2f*motionMultiplier, 0.25f*motionMultiplier)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeat(level, posX, posY, posZ, 4);
        }
        int spinOffset = rand.nextInt(360);
        for (int i = 0; i < 3; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            float scaleMultiplier = (float) (1+Math.pow(rand.nextFloat(), 2)*0.5f);
            WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.35f, 0.07f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setLifetime(9)
                    .setSpinData(SpinParticleData.create((0.125f + rand.nextFloat() * 0.075f) * spinDirection).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(0.8f*scaleMultiplier, 0.5f, 0).setEasing(Easing.EXPO_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(EthericNitrateEntity.FIRST_COLOR.brighter(), EthericNitrateEntity.SECOND_COLOR.darker()).build())
                    .setRandomOffset(0.6f)
                    .enableNoClip()
                    .setRandomMotion(0.02f, 0.02f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeat(level, posX, posY, posZ, 5);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, EthericNitrateParticlePacket.class, EthericNitrateParticlePacket::encode, EthericNitrateParticlePacket::decode, EthericNitrateParticlePacket::handle);
    }
}