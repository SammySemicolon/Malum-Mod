package com.sammy.malum.common.packets.particle.block;

import com.sammy.malum.common.packets.particle.ColorBasedParticleEffectPacket;
import com.sammy.malum.common.packets.particle.SpiritBasedParticleEffectPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
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
import team.lodestar.lodestone.systems.rendering.particle.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.SimpleParticleOptions;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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
            ParticleBuilders.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setAlpha(0.2f, 1f, 0)
                    .setLifetime(30)
                    .setSpinOffset(spinOffset)
                    .setSpinCoefficient(1.25f)
                    .setSpin(0.9f * spinDirection, 0)
                    .setSpinEasing(Easing.CUBIC_IN)
                    .setSpinCoefficient(1.2f)
                    .setScale(0.075f, 0.25f, 0)
                    .setScaleCoefficient(0.8f)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT)
                    .setColor(ColorHelper.brighter(color, 2), color)
                    .enableNoClip()
                    .randomOffset(0.85f)
                    .setGravity(1.1f)
                    .addMotion(0, 0.3f + rand.nextFloat() * 0.15f * motionMultiplier, 0)
                    .disableNoClip()
                    .randomMotion(extraMotion, extraMotion)
                    .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                    .overwriteRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .repeat(level, posX, posY, posZ, 6);
        }
        int spinOffset = rand.nextInt(360);
        for (int i = 0; i < 4; i++) {
            float multiplier = Mth.nextFloat(rand, 0.1f, 1.5f);
            Color color = new Color((int)(4*multiplier), (int)(2*multiplier), (int)(4*multiplier));
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            float scaleMultiplier = (float) (1+Math.pow(rand.nextFloat(), 2));
            ParticleBuilders.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setAlpha(0.7f, 0.5f, 0)
                    .setAlphaEasing(Easing.SINE_IN, Easing.CIRC_IN)
                    .setLifetime(25)
                    .setSpinOffset(spinOffset)
                    .setSpin((0.125f + rand.nextFloat() * 0.075f) * spinDirection)
                    .setScale(1.8f*scaleMultiplier, 0.6f, 0)
                    .setScaleEasing(Easing.EXPO_OUT, Easing.SINE_IN)
                    .setColor(color.brighter(), color.darker())
                    .randomOffset(0.6f)
                    .enableNoClip()
                    .randomMotion(0.02f, 0.02f)
                    .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                    .overwriteRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
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