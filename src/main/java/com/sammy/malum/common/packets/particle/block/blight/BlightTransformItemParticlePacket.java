package com.sammy.malum.common.packets.particle.block.blight;

import com.sammy.malum.common.packets.particle.SpiritBasedParticleEffectPacket;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
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
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.rendering.particle.SimpleParticleOptions;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class BlightTransformItemParticlePacket extends SpiritBasedParticleEffectPacket {
    public BlightTransformItemParticlePacket(List<String> spirits, Vec3 vec3) {
        super(spirits, vec3);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        Random rand = level.random;
        List<MalumSpiritType> types = new ArrayList<>();
        for (String string : spirits) {
            types.add(SpiritHelper.getSpiritType(string));
        }
        for (MalumSpiritType type : types) {
            Color color = type.getColor();
            for (int i = 0; i < 3; i++) {
                int spinDirection = (rand.nextBoolean() ? 1 : -1);
                int spinOffset = rand.nextInt(360);
                ParticleBuilders.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setAlpha(0.4f, 0.8f, 0)
                    .setLifetime(20)
                    .setSpinOffset(spinOffset)
                    .setSpinCoefficient(1.25f)
                    .setSpin(0.7f*spinDirection, 0)
                    .setSpinEasing(Easing.CUBIC_IN)
                    .setScale(0.075f, 0.15f, 0)
                    .setScaleCoefficient(0.8f)
                    .setScaleEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT)
                    .setColor(ColorHelper.brighter(color, 2), color)
                    .enableNoClip()
                    .randomOffset(0.6f)
                    .setGravity(1.1f)
                    .addMotion(0, 0.28f+rand.nextFloat()*0.15f, 0)
                    .disableNoClip()
                    .randomMotion(0.1f, 0.15f)
                    .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                    .repeat(level, posX, posY, posZ, 2);
            }
            int spinOffset = rand.nextInt(360);
            for (int i = 0; i < 3; i++) {
                int spinDirection = (rand.nextBoolean() ? 1 : -1);
                ParticleBuilders.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                    .setAlpha(0.12f, 0.06f, 0)
                    .setAlphaEasing(Easing.SINE_IN, Easing.CIRC_IN)
                    .setLifetime(30)
                    .setSpinOffset(spinOffset)
                    .setSpin((0.125f+rand.nextFloat()*0.075f)*spinDirection)
                    .setScale(0.85f, 0.5f, 0)
                    .setScaleEasing(Easing.EXPO_OUT, Easing.SINE_IN)
                    .setColor(color.brighter(), color.darker())
                    .randomOffset(0.4f)
                    .enableNoClip()
                    .randomMotion(0.01f, 0.01f)
                    .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                    .repeat(level, posX, posY, posZ, 5);
            }
        }

        for (int i = 0; i < 3; i++) {
            float multiplier = Mth.nextFloat(level.random, 0.4f, 1f);
            float timeMultiplier = Mth.nextFloat(level.random, 0.9f, 1.4f);
            Color color = new Color((int)(31*multiplier), (int)(19*multiplier), (int)(31*multiplier));
            boolean spinDirection = level.random.nextBoolean();
            ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.4f, 1f, 0)
                .setLifetime((int) (45*timeMultiplier))
                .setSpin(0.2f*(spinDirection ? 1 : -1))
                .setScale(0.15f, 0.2f, 0)
                .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomMotion(0.01f, 0.02f)
                .addMotion(0, 0.01f, 0)
                .overwriteRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .repeat(level, posX, posY, posZ, 2);

            ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.35f, 0.55f, 0)
                .setLifetime((int) (50*timeMultiplier))
                .setSpin(0.1f*(spinDirection ? 1 : -1))
                .setScale(0.35f, 0.4f, 0)
                .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                .setColor(color, color)
                .randomOffset(0.2f, 0)
                .enableNoClip()
                .randomMotion(0.015f, 0.015f)
                .addMotion(0, 0.01f, 0)
                .overwriteRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                .repeat(level, posX, posY, posZ, 3);

            color = new Color((int)(80*multiplier), (int)(40*multiplier), (int)(80*multiplier));
            ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.1f, 0.15f, 0)
                .setLifetime((int) (50*timeMultiplier))
                .setSpin(0.1f*(spinDirection ? 1 : -1))
                .setScale(0.35f, 0.4f, 0)
                .setScaleEasing(Easing.QUINTIC_OUT, Easing.SINE_IN)
                .setColor(color, color)
                .randomOffset(0.2f, 0)
                .enableNoClip()
                .randomMotion(0.02f, 0.01f)
                .addMotion(0, 0.01f, 0)
                .repeat(level, posX, posY, posZ, 2);
        }
    }


    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlightTransformItemParticlePacket.class, BlightTransformItemParticlePacket::encode, BlightTransformItemParticlePacket::decode, BlightTransformItemParticlePacket::handle);
    }

    public static BlightTransformItemParticlePacket decode(FriendlyByteBuf buf) {
        return decode(BlightTransformItemParticlePacket::new, buf);
    }
}
