package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.ritual_plinth.RitualPlinthBlockEntity;
import com.sammy.malum.common.block.curiosities.spirit_altar.SpiritAltarBlockEntity;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.core.Direction;
import net.minecraft.util.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;

import java.awt.*;
import java.util.function.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.spiritLightSpecs;
import static net.minecraft.util.Mth.nextFloat;

public class RitualPlinthParticleEffects {
    public static void passiveRitualPlinthParticles(RitualPlinthBlockEntity plinth) {
        Level level = plinth.getLevel();
        MalumSpiritType spiritType = plinth.ritualType.spirit;
        RandomSource random = level.random;
        for (Direction direction : Direction.values()) {
            if (direction.getAxis().equals(Direction.Axis.Y)) {
                continue;
            }
            Vec3 particlePosition = plinth.getParticlePositionPosition(direction);
            Vec3 particleVelocity = new Vec3(0.03f * direction.getStepX(), 0, 0.03f * direction.getStepZ());
            if (level.getGameTime() % 3L == 0) {
                Vec3 randomizedVelocity = particleVelocity.scale(RandomHelper.randomBetween(random, 0.8f, 1.2f));
                var lightSpecs = spiritLightSpecs(level, particlePosition, spiritType);
                lightSpecs.getBuilder().multiplyLifetime(1.75f).setMotion(randomizedVelocity);
                lightSpecs.getBloomBuilder().multiplyLifetime(1.5f).setMotion(randomizedVelocity);
                lightSpecs.spawnParticles();
            }
            if (level.getGameTime() % 6L == 0) {
                Vec3 randomizedVelocity = particleVelocity.scale(RandomHelper.randomBetween(random, 0.8f, 1.2f));
                Vec3 sparkPos = particlePosition.add(0.05f - random.nextFloat() * 0.1f, 0.05f - random.nextFloat() * 0.1f, 0.05f - random.nextFloat() * 0.1f);
                var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, sparkPos, spiritType);
                sparkParticles.getBuilder().setMotion(randomizedVelocity)
                        .modifyData(SparkParticleBuilder::getScaleData, d -> d.multiplyValue(1.25f))
                        .modifyData(SparkParticleBuilder::getLengthData, d -> d.multiplyValue(1.75f).multiplyCoefficient(0.75f))
                        .modifyColorData(c -> c.multiplyCoefficient(0.5f));
                sparkParticles.getBloomBuilder().setMotion(randomizedVelocity);
                sparkParticles.spawnParticlesRaw();
            }
            if (level.getGameTime() % 12L == 0) {
                DirectionalParticleBuilder.create(ParticleRegistry.CIRCLE)
                        .setTransparencyData(GenericParticleData.create(0.8f, 1f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                        .setSpinData(SpinParticleData.createRandomDirection(random, nextFloat(random, 0.05f, 0.1f)).randomSpinOffset(random).build())
                        .setScaleData(GenericParticleData.create(0f, 0.2f).setEasing(Easing.SINE_IN).build())
                        .setColorData(spiritType.createMainColorData().setCoefficient(0.75f).build())
                        .setLifetime(80)
                        .setDirection(particleVelocity.normalize())
                        .enableNoClip()
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .spawn(level, particlePosition.x, particlePosition.y, particlePosition.z);
                DirectionalParticleBuilder.create(ParticleRegistry.SQUARE)
                        .setTransparencyData(GenericParticleData.create(0.8f, 1f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                        .setSpinData(SpinParticleData.createRandomDirection(random, nextFloat(random, 0.05f, 0.1f)).randomSpinOffset(random).build())
                        .setScaleData(GenericParticleData.create(0f, 0.2f).setEasing(Easing.SINE_IN).build())
                        .setColorData(spiritType.createMainColorData().setCoefficient(0.75f).build())
                        .setLifetime(80)
                        .setDirection(particleVelocity.normalize())
                        .enableNoClip()
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .spawn(level, particlePosition.x, particlePosition.y, particlePosition.z);
            }
            Vec3 normalizedParticleVelocity = particleVelocity.normalize();
            float yRot = ((float) (Mth.atan2(normalizedParticleVelocity.x, normalizedParticleVelocity.z) * (double) (180F / (float) Math.PI)));
            float yaw = (float) Math.toRadians(yRot);
            Vec3 left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
            Vec3 up = left.cross(normalizedParticleVelocity);
            final Consumer<LodestoneWorldParticleActor> behavior = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.98f));
            float angle = ((level.getGameTime() % 40) / 40f) * (float) Math.PI * 2f;
            Vec3 randomizedVelocity = particleVelocity.scale(RandomHelper.randomBetween(random, 0.8f, 1.2f));
            Vec3 particleDirection = particleVelocity
                    .add(left.scale(Math.sin(angle)))
                    .add(up.scale(Math.cos(angle)))
                    .normalize();
            particlePosition = particlePosition.add(particleDirection.scale(0.075f));
            DirectionalParticleBuilder.create(ParticleRegistry.SHARP_SPARK)
                    .setTransparencyData(GenericParticleData.create(0.6f, 0.4f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                    .setSpinData(SpinParticleData.createRandomDirection(random, nextFloat(random, 0.05f, 0.1f)).randomSpinOffset(random).build())
                    .setScaleData(GenericParticleData.create(0.125f, 0.075f, 0).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                    .setColorData(spiritType.createMainColorData().build().multiplyCoefficient(1.5f))
                    .setLifetime(30)
                    .setMotion(randomizedVelocity)
                    .setDirection(particleDirection)
                    .enableNoClip()
                    .addTickActor(behavior)
                    .spawn(level, particlePosition.x, particlePosition.y, particlePosition.z);
        }
        if (level.getGameTime() % 16L == 0) {
            Vec3 particlePosition = plinth.getBlockPos().getCenter();
            for (int i = 0; i < 2; i++) {
                final GenericParticleData transparencyData = GenericParticleData.create(0f, 0.5f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build();
                final GenericParticleData scaleData = GenericParticleData.create(0.35f).setEasing(Easing.SINE_IN).build();
                final SpinParticleData spinData = SpinParticleData.create(0.0157f).setSpinOffset((i == 1 ? 0 : 0.785f) + 2.512f * (level.getGameTime() % 800) / 160f).build();
                final Color color = i == 0 ? spiritType.getPrimaryColor() : spiritType.getSecondaryColor();
                final ColorParticleData colorData = ColorParticleData.create(color, color).build();
                DirectionalParticleBuilder.create(ParticleRegistry.RITUAL_CIRCLE)
                        .setTransparencyData(transparencyData)
                        .setSpinData(spinData)
                        .setScaleData(scaleData)
                        .setColorData(colorData)
                        .setLifetime(40)
                        .setDirection(new Vec3(0, 1, 0))
                        .enableNoClip()
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .spawn(level, particlePosition.x, particlePosition.y + 0.6875f, particlePosition.z);
                DirectionalParticleBuilder.create(ParticleRegistry.RITUAL_CIRCLE_WISP)
                        .setTransparencyData(transparencyData.multiplyValue(0.25f))
                        .setSpinData(spinData)
                        .setScaleData(scaleData.multiplyValue(1.25f))
                        .setColorData(colorData)
                        .setLifetime(40)
                        .setDirection(new Vec3(0, 1, 0))
                        .enableNoClip()
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .spawn(level, particlePosition.x, particlePosition.y + 0.6875f, particlePosition.z);
            }
        }
    }
}
