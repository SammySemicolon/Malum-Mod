package com.sammy.malum.visual_effects;

import com.sammy.malum.registry.client.*;
import net.minecraft.core.particles.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.world.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.*;

import static net.minecraft.util.Mth.nextFloat;

public class SpiritLightSpecs {

    public static void rotatingLightSpecs(Level level, Vec3 pos, Color color, Color endColor, float distance, int rotatingSpecs) {
        rotatingLightSpecs(level, pos, color, endColor, distance, rotatingSpecs, b -> {});
    }
    public static void rotatingLightSpecs(Level level, Vec3 pos, Color color, Color endColor, float distance, int rotatingSpecs, Consumer<WorldParticleBuilder> sharedModifier) {
        rotatingLightSpecs(level, pos, color, endColor, distance, rotatingSpecs, sharedModifier, sharedModifier);
    }
    public static void rotatingLightSpecs(Level level, Vec3 pos, Color color, Color endColor, float distance, int rotatingSpecs, Consumer<WorldParticleBuilder> lightSpecModifier, Consumer<WorldParticleBuilder> bloomModifier) {
        long gameTime = level.getGameTime();
        if (level.getGameTime() % 2L == 0) {
            for (int i = 0; i < rotatingSpecs; i++) {
                long offsetGameTime = gameTime + i * 120L;
                double yOffset = Math.sin((offsetGameTime % 360) / 30f) * 0.1f;
                Vec3 offsetPosition = DataHelper.rotatingRadialOffset(pos.add(0, yOffset, 0), distance, i, rotatingSpecs, gameTime, 160);
                spiritLightSpecs(level, offsetPosition, color, endColor,
                        b -> b.act(lightSpecModifier)
                                .multiplyLifetime(2f)
                                .modifyData(b::getScaleData, d -> d.multiplyValue(1.2f)),
                        b -> b.act(bloomModifier)
                                .multiplyLifetime(1.4f)
                                .modifyData(List.of(b::getScaleData, b::getTransparencyData), d -> d.multiplyValue(0.6f))
                );
            }
        }
        spiritLightSpecs(level, pos, color, endColor,
                b -> b.act(lightSpecModifier)
                        .modifyData(b::getScaleData, d -> d.multiplyValue(1.7f))
                        .modifyData(b::getTransparencyData, d -> d.multiplyValue(0.5f)),
                b -> b.act(bloomModifier)
                        .modifyData(b::getScaleData, d -> d.multiplyValue(1.3f))
                        .modifyData(b::getTransparencyData, d -> d.multiplyValue(0.75f))
        );
    }

    public static void spiritLightSpecs(Level level, Vec3 pos, Color color, Color endColor) {
        spiritLightSpecs(level, pos, color, endColor, b -> {
        });
    }

    public static void spiritLightSpecs(Level level, Vec3 pos, Color color, Color endColor, Consumer<WorldParticleBuilder> sharedModifier) {
        spiritLightSpecs(level, pos, color, endColor, sharedModifier, sharedModifier);
    }

    public static void spiritLightSpecs(Level level, Vec3 pos, Color color, Color endColor, Consumer<WorldParticleBuilder> lightSpecModifier, Consumer<WorldParticleBuilder> bloomModifier) {
        Random rand = level.getRandom();
        final ColorParticleData colorData = ColorParticleData.create(color, endColor).setCoefficient(0.7f).build();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.05f, 0.1f)).randomSpinOffset(rand).build();
        final Consumer<GenericParticle> slowDown = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.95f));
        int lifetime = RandomHelper.randomBetween(rand, 10, 20);
        WorldParticleBuilder.create(rand.nextFloat() < 0.9 ? ParticleRegistry.LIGHT_SPEC_SMALL : ParticleRegistry.LIGHT_SPEC_LARGE)
                .setTransparencyData(GenericParticleData.create(0.8f, 0f).build())
                .setSpinData(spinData)
                .setScaleData(GenericParticleData.create(0.025f, RandomHelper.randomBetween(rand, 0.2f, 0.3f), 0).build())
                .setColorData(colorData)
                .setLifetime(lifetime)
                .enableNoClip()
                .addActor(slowDown)
                .act(lightSpecModifier)
                .spawn(level, pos.x, pos.y, pos.z);

        spiritBloom(level, pos, colorData, spinData, lifetime, slowDown, bloomModifier);
    }

    public static void spiritMotionSparks(Level level, Vec3 pos, Color color, Color endColor, Consumer<SparkParticleBuilder> sparkModifier) {
        spiritMotionSparks(level, pos, color, endColor, sparkModifier, b -> {});
    }
    public static void spiritMotionSparks(Level level, Vec3 pos, Color color, Color endColor, Consumer<SparkParticleBuilder> sparkModifier, Consumer<WorldParticleBuilder> bloomModifier) {
        Random rand = level.getRandom();
        final ColorParticleData colorData = ColorParticleData.create(color, endColor).setCoefficient(1.25f).build();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.05f, 0.1f)).randomSpinOffset(rand).build();
        final Consumer<GenericParticle> slowDown = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.95f));
        int lifetime = RandomHelper.randomBetween(rand, 10, 20);
        SparkParticleBuilder.create(ParticleRegistry.ROUND_SPARK)
                .setLengthData(GenericParticleData.create(0.1f, 0.2f, 0f).setEasing(Easing.SINE_IN, Easing.SINE_IN_OUT).build())
                .setTransparencyData(GenericParticleData.create(0.8f, 0f).build())
                .setScaleData(GenericParticleData.create(0.025f, RandomHelper.randomBetween(rand, 0.2f, 0.3f), 0).build())
                .setColorData(colorData)
                .setLifetime(lifetime)
                .enableNoClip()
                .addActor(slowDown)
                .act(sparkModifier)
                .spawn(level, pos.x, pos.y, pos.z);

        spiritBloom(level, pos, colorData, spinData, lifetime, slowDown, bloomModifier);
    }

    public static void spiritBloom(Level level, Vec3 pos, ColorParticleData colorData, SpinParticleData spinData, int lifetime, Consumer<GenericParticle> actor, Consumer<WorldParticleBuilder> bloomModifier) {
        Random rand = level.random;
        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.25f, 0f).build())
                .setSpinData(spinData)
                .setScaleData(GenericParticleData.create(0.04f, RandomHelper.randomBetween(rand, 0.08f, 0.125f), 0).build())
                .setColorData(colorData)
                .setLifetime(lifetime)
                .enableNoClip()
                .addActor(actor)
                .act(bloomModifier)
                .spawn(level, pos.x, pos.y, pos.z)
                .setScaleData(GenericParticleData.create(0.065f, RandomHelper.randomBetween(rand, 0.12f, 0.22f), 0).build())
                .act(bloomModifier)
                .spawn(level, pos.x, pos.y, pos.z);
    }
}