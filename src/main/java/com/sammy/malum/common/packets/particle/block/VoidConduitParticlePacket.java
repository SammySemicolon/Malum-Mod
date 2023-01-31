package com.sammy.malum.common.packets.particle.block;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticleRenderType;

import java.awt.*;
import java.util.Random;
import java.util.function.Supplier;

public class VoidConduitParticlePacket extends LodestoneClientPacket {

    protected final double posX;
    protected final double posY;
    protected final double posZ;
    public VoidConduitParticlePacket(double posX, double posY, double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        Random rand = level.random;
        for (int i = 0; i < 8; i++) {
            float multiplier = Mth.nextFloat(rand, 0.1f, 1.5f);
            Color color = new Color((int)(12*multiplier), (int)(4*multiplier), (int)(14*multiplier));

            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            float motionMultiplier = (float) (1+Math.pow(rand.nextFloat()+i*0.2f, 2));
            float extraMotion = 0.2f * motionMultiplier * ((8 - i) / 8f);
            WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.2f, 1f, 0).build())
                    .setSpinData(SpinParticleData.create(0.9f * spinDirection, 0).setSpinOffset(spinOffset).setCoefficient(1.25f).setEasing(Easing.CUBIC_IN).build())
                    .setScaleData(GenericParticleData.create(0.075f, 0.25f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT).build())
                    .setColorData(ColorParticleData.create(ColorHelper.brighter(color, 2), color).build())
                    .setLifetime(30)
                    .enableNoClip()
                    .setRandomOffset(0.85f)
                    .setGravity(1.1f)
                    .addMotion(0, 0.3f + rand.nextFloat() * 0.15f * motionMultiplier, 0)
                    .disableNoClip()
                    .setRandomMotion(extraMotion, extraMotion)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .repeat(level, posX, posY, posZ, 6);
        }
        int spinOffset = rand.nextInt(360);
        for (int i = 0; i < 4; i++) {
            float multiplier = Mth.nextFloat(rand, 0.1f, 1.5f);
            Color color = new Color((int)(4*multiplier), (int)(2*multiplier), (int)(4*multiplier));
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            float scaleMultiplier = (float) (1+Math.pow(rand.nextFloat(), 2));
            WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.7f, 0.5f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.create((0.125f + rand.nextFloat() * 0.075f) * spinDirection).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(1.8f*scaleMultiplier, 0.6f, 0).setEasing(Easing.EXPO_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color.brighter(), color.darker()).build())
                    .setLifetime(25)
                    .setRandomOffset(0.6f)
                    .enableNoClip()
                    .setRandomMotion(0.02f, 0.02f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .repeat(level, posX, posY, posZ, 5);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, VoidConduitParticlePacket.class, VoidConduitParticlePacket::encode, VoidConduitParticlePacket::decode, VoidConduitParticlePacket::handle);
    }

    public static VoidConduitParticlePacket decode(FriendlyByteBuf buf) {
        double posX = buf.readDouble();
        double posY = buf.readDouble();
        double posZ = buf.readDouble();
        return new VoidConduitParticlePacket(posX, posY, posZ);
    }
}