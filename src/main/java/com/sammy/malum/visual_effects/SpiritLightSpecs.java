package com.sammy.malum.visual_effects;

import com.sammy.malum.registry.client.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.function.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;
import team.lodestar.lodestone.systems.particle.world.*;

import java.awt.*;
import java.util.*;
import java.util.function.*;

import static net.minecraft.util.Mth.nextFloat;

public class SpiritLightSpecs {

    public static void stationarySpiritLightSpecs(Level level, Vec3 pos, Color color, Color endColor) {
        float distance = 0.4f;
        int rotatingSpecs = 2;
        long gameTime = level.getGameTime();
        BiFunction<Float, Float, Consumer<AbstractWorldParticleBuilder>> scaleTransparencyScalar = (s, t) -> b -> b.setScaleData(b.getScaleData().scale(s).build()).setTransparencyData(b.getTransparencyData().scale(t).build());
        if (level.getGameTime() % 2L == 0) {
            for (int i = 0; i < rotatingSpecs; i++) {
                long offsetGameTime = gameTime + i * 120;
                double yOffset = Math.sin((offsetGameTime % 360) / 30f) * 0.1f;
                Vec3 offsetPosition = DataHelper.rotatingRadialOffset(pos.add(0, yOffset, 0), distance, i, rotatingSpecs, gameTime, 160);
                spiritLightSpecs(level, offsetPosition, color, endColor, scaleTransparencyScalar.apply(1.2f, 1f).andThen(b -> b.setLifetime(b.getParticleOptions().lifetimeSupplier.get() * 2)), scaleTransparencyScalar.apply(0.5f, 0.25f));
            }
        }
        spiritLightSpecs(level, pos, color, endColor, scaleTransparencyScalar.apply(1.7f, 0.5f), scaleTransparencyScalar.apply(1.4f, 0.8f));
    }

    public static void spiritLightSpecs(Level level, Vec3 pos, Color color, Color endColor) {
        spiritLightSpecs(level, pos, color, endColor, b -> {});
    }

    public static void spiritLightSpecs(Level level, Vec3 pos, Color color, Color endColor, Consumer<AbstractWorldParticleBuilder> consumer) {
        spiritLightSpecs(level, pos, color, endColor, consumer, consumer);
    }

    public static void spiritLightSpecs(Level level, Vec3 pos, Color color, Color endColor, Consumer<AbstractWorldParticleBuilder> lightSpecConsumer, Consumer<AbstractWorldParticleBuilder> bloomParticleConsumer) {
        Random rand = level.getRandom();
        final ColorParticleData colorData = ColorParticleData.create(color, endColor).setCoefficient(1.25f).build();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.05f, 0.1f)).randomSpinOffset(rand).build();
        final Consumer<GenericParticle> slowDown = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.95f));
        int lifetime = RandomHelper.randomBetween(rand, 10, 20);
        SparkParticleBuilder.create(ParticleRegistry.ROUND_SPARK)
                .setLengthData(GenericParticleData.create(0.1f, 0.2f).build())
                .setTransparencyData(GenericParticleData.create(0.8f, 0f).build())
                .setScaleData(GenericParticleData.create(0.025f, RandomHelper.randomBetween(rand, 0.2f, 0.3f), 0).build())
                .setColorData(colorData)
                .setLifetime(lifetime)
                .enableNoClip()
                .addActor(slowDown)
                .act(AbstractWorldParticleBuilder.class, lightSpecConsumer)
                .spawn(level, pos.x, pos.y, pos.z);

        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.25f, 0f).build())
                .setSpinData(spinData)
                .setScaleData(GenericParticleData.create(0.05f, RandomHelper.randomBetween(rand, 0.1f, 0.15f), 0).build())
                .setColorData(colorData)
                .setLifetime(lifetime)
                .enableNoClip()
                .addActor(slowDown)
                .act(AbstractWorldParticleBuilder.class, bloomParticleConsumer)
                .spawn(level, pos.x, pos.y, pos.z)
                .setScaleData(GenericParticleData.create(0.075f, RandomHelper.randomBetween(rand, 0.15f, 0.25f), 0).build())
                .act(AbstractWorldParticleBuilder.class, bloomParticleConsumer)
                .spawn(level, pos.x, pos.y, pos.z);
    }
}
