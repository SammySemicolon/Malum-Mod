package com.sammy.malum.client;

import com.sammy.malum.registry.client.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;

import java.awt.*;
import java.util.Random;

import static net.minecraft.util.Mth.nextFloat;

public class ParticleEffects {
    public static void spawnSpiritGlimmerParticles(Level level, double x, double y, double z, Color color, Color endColor) {
        spawnSpiritGlimmerParticles(level, x, y, z, 1, Vec3.ZERO, color, endColor);
    }

    public static void spawnSpiritGlimmerParticles(Level level, double x, double y, double z, float alphaMultiplier, Vec3 extraVelocity, Color color, Color endColor) {
        Random rand = level.getRandom();
        WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.4f * alphaMultiplier, 0f).build())
                .setScaleData(GenericParticleData.create(0.25f + rand.nextFloat() * 0.1f, 0).build())
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setLifetime(5 + rand.nextInt(4))
                .setRandomOffset(0.05f)
                .enableNoClip()
                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .setRandomMotion(0.02f, 0.02f)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.INVISIBLE)
                .spawn(level, x, y, z);

        spiritLightSpecs(level, x, y, z, 1, extraVelocity, color, endColor);
    }

    public static void spiritLightSpecs(Level level, double x, double y, double z, Color color, Color endColor) {
        spiritLightSpecs(level, x, y, z, 1, Vec3.ZERO, color, endColor);
    }

    public static void spiritLightSpecs(Level level, double x, double y, double z, float alphaMultiplier, Vec3 extraVelocity, Color color, Color endColor) {
        Random rand = level.getRandom();
        final ColorParticleData colorData = ColorParticleData.create(color, endColor).setCoefficient(1.25f).build();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.05f, 0.1f)).randomSpinOffset(rand).build();

        WorldParticleBuilder.create(rand.nextFloat() < 0.8 ? ParticleRegistry.LIGHT_SPEC_SMALL : ParticleRegistry.LIGHT_SPEC_LARGE)
                .setTransparencyData(GenericParticleData.create(0.8f, 0f).build())
                .setSpinData(spinData)
                .setScaleData(GenericParticleData.create(0.025f, 0.25f + rand.nextFloat() * 0.05f, 0).build())
                .setColorData(colorData)
                .setLifetime(10 + rand.nextInt(5))
                .enableNoClip()
                .setRandomOffset(0.01f)
                .addMotion(extraVelocity)
                .addActor(p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.95f)))
                .spawn(level, x, y, z);

        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.4f * alphaMultiplier, 0f).build())
                .setSpinData(spinData)
                .setScaleData(GenericParticleData.create(0.05f, 0.1f + rand.nextFloat() * 0.05f, 0).build())
                .setColorData(colorData)
                .setLifetime(10 + rand.nextInt(5))
                .enableNoClip()
                .addMotion(extraVelocity)
                .addActor(p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.95f)))
                .spawn(level, x, y, z)
                .setScaleData(GenericParticleData.create(0.075f, 0.15f + rand.nextFloat() * 0.05f, 0).build())
                .spawn(level, x, y, z);
    }
}
