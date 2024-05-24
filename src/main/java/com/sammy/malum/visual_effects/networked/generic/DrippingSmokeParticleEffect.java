package com.sammy.malum.visual_effects.networked.generic;


import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.LodestoneWorldParticleActor;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DrippingSmokeParticleEffect extends ParticleEffectType {

    //Downwards spiraling mist of particles
    public final float intensity;

    public DrippingSmokeParticleEffect(String id, float intensity) {
        super(id);
        this.intensity = intensity;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            ColorEffectData.ColorRecord colorRecord = colorData.getDefaultColorRecord();
            Color primaryColor = colorData.getPrimaryColor(colorRecord);
            Color secondaryColor = colorData.getSecondaryColor(colorRecord);
            double posX = positionData.posX + 0.5f;
            double posY = positionData.posY + 0.65f;
            double posZ = positionData.posZ + 0.5f;

            int adjustedLifespan = (int) (60 * (intensity) * 0.6f);
            Function<Float, Consumer<LodestoneWorldParticleActor>> actorFunction = f -> (p -> p.setParticleMotion(p.getParticleSpeed().add(new Vec3(posX, posY, posZ).subtract(p.getParticlePosition()).normalize().multiply(f, 0, f))));

            for (int i = 0; i < 2; i++) {
                int spinDirection = (random.nextBoolean() ? 1 : -1);
                int spinOffset = random.nextInt(360);
                WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0, 0.06f, 0).setCoefficient(1.2f).setEasing(Easing.SINE_IN_OUT, Easing.QUAD_IN).build())
                        .setSpinData(SpinParticleData.create(0.05f * spinDirection, 0.2f * spinDirection, 0).setSpinOffset(spinOffset).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                        .setScaleData(GenericParticleData.create(0.8f, 0.4f, 0f).setEasing(Easing.QUARTIC_IN_OUT, Easing.QUAD_IN).build())
                        .setColorData(ColorParticleData.create(primaryColor, secondaryColor).setEasing(Easing.BOUNCE_IN_OUT).build())
                        .setLifetime((int) (adjustedLifespan * Mth.nextFloat(random, 1.15f, 1.4f)))
                        .enableNoClip()
                        .setRandomOffset(0.2f, 0.4f)
                        .setRandomMotion(0.02f)
                        .addMotion(0, -0.0075f - random.nextFloat() * 0.01f, 0)
                        .addTickActor(actorFunction.apply(0.015f))
                        .repeat(level, posX, posY, posZ, (int) (3 * intensity));
            }
            for (int i = 0; i < 2; i++) {
                int spinDirection = (random.nextBoolean() ? 1 : -1);
                int spinOffset = random.nextInt(360);
                WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.02f, 0.14f, 0).setEasing(Easing.SINE_IN_OUT, Easing.EXPO_OUT).build())
                        .setSpinData(SpinParticleData.create(0.1f * spinDirection, 0.25f * spinDirection, 0).setSpinOffset(spinOffset).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                        .setScaleData(GenericParticleData.create(0.6f, 0.3f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.EXPO_OUT).build())
                        .setColorData(ColorParticleData.create(primaryColor, secondaryColor).setEasing(Easing.BOUNCE_IN_OUT).build())
                        .setLifetime((int) (adjustedLifespan * Mth.nextFloat(random, 0.9f, 1.2f)))
                        .enableNoClip()
                        .setRandomOffset(0.2f, 0.4f)
                        .setRandomMotion(0.01f, 0)
                        .addMotion(0, -0.03f - random.nextFloat() * 0.015f, 0)
                        .addTickActor(actorFunction.apply(0.04f))
                        .repeat(level, posX, posY, posZ, (int) (2 * intensity));
            }
        };
    }
}