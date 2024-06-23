package com.sammy.malum.visual_effects.networked.nitrate;

import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.visual_effects.SparkParticleEffects;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.AbstractParticleBuilder;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.SparkBehaviorComponent;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.spiritLightSpecs;
import static net.minecraft.util.Mth.nextFloat;

public class EthericNitrateImpactParticleEffect extends ParticleEffectType {

    public EthericNitrateImpactParticleEffect(String id) {
        super(id);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            double posX = positionData.posX;
            double posY = positionData.posY;
            double posZ = positionData.posZ;
            Vec3 pos = new Vec3(posX, posY, posZ);
            final Color primaryColor = colorData.getDefaultColorRecord().primaryColor();
            final Color secondaryColor = colorData.getDefaultColorRecord().secondaryColor();
            ColorParticleData colorParticleData = ColorParticleData.create(primaryColor, secondaryColor).setEasing(Easing.SINE_IN_OUT).setCoefficient(0.9f).build();
            for (int i = 0; i < 16; i++) {
                float lifetimeMultiplier = RandomHelper.randomBetween(random, 2f, 2.5f);
                float gravityStrength = RandomHelper.randomBetween(random, 0.01f, 0.05f);
                double horizontalAngle = random.nextDouble() * Math.PI * 2;
                double x = (Math.cos(horizontalAngle));
                double y = Mth.nextFloat(random, -1, 1);
                double z = (Math.sin(horizontalAngle));
                Vec3 direction = new Vec3(x, y, z);
                Vec3 motion = direction.scale(RandomHelper.randomBetween(random, 1f, 2f));
                Vec3 spawnPosition = pos.add(direction.scale(0.25f));
                final Consumer<LodestoneWorldParticle> slowDown = p -> {
                    Vec3 velocity = p.getParticleSpeed().scale(0.8f);
                    if (velocity.equals(Vec3.ZERO)) {
                        velocity = p.getParticleSpeed();
                    }
                    p.setParticleSpeed(velocity.x, (velocity.y - gravityStrength) * 0.98f, velocity.z);
                };
                boolean star = random.nextFloat() < 0.2f;
                if (random.nextFloat() < 0.8f) {
                    var lightSpecs = spiritLightSpecs(level, spawnPosition, colorParticleData, new WorldParticleOptions(star ? ParticleRegistry.STAR.get() : ParticleRegistry.LIGHT_SPEC_SMALL.get()));
                    lightSpecs.getBuilder()
                            .multiplyLifetime(lifetimeMultiplier)
                            .enableForcedSpawn()
                            .addTickActor(slowDown)
                            .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(2f))
                            .setMotion(motion);
                    lightSpecs.getBloomBuilder()
                            .multiplyLifetime(lifetimeMultiplier)
                            .addTickActor(slowDown)
                            .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(star ? 3f : 2f))
                            .setMotion(motion);
                    lightSpecs.spawnParticles();
                }
                if (!star && random.nextFloat() < 0.8f) {
                    float scalar = RandomHelper.randomBetween(random, 0.8f, 1.1f);
                    var lengthData = GenericParticleData.create(2f * scalar, 0.5f * scalar, 0f).setEasing(Easing.QUARTIC_OUT, Easing.SINE_IN_OUT).build();
                    var sparks = SparkParticleEffects.spiritMotionSparks(level, spawnPosition, colorParticleData).act(b -> b.getParticleOptions().setBehavior(new SparkBehaviorComponent(lengthData)));
                    sparks.getBuilder()
                            .multiplyLifetime(lifetimeMultiplier)
                            .enableForcedSpawn()
                            .addTickActor(slowDown)
                            .setMotion(motion)
                            .setScaleData(GenericParticleData.create(0.4f * scalar, 0.2f * scalar, 0f).setEasing(Easing.SINE_IN, Easing.QUAD_IN).build());
                    sparks.getBloomBuilder()
                            .multiplyLifetime(lifetimeMultiplier)
                            .addTickActor(slowDown)
                            .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(2f))
                            .setMotion(motion);
                    sparks.spawnParticles();
                }
            }
            for (int i = 0; i < 3; i++) {
                final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, nextFloat(random, 0.05f, 0.1f)).randomSpinOffset(random).build();
                float scaleMultiplier = (float) (1 + Math.pow(random.nextFloat(), 2) * 0.5f);
                WorldParticleBuilder.create(LodestoneParticleRegistry.SPARKLE_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.35f, 0.07f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                        .setLifetime(15)
                        .setSpinData(spinData)
                        .setScaleData(GenericParticleData.create(1.6f * scaleMultiplier, 0.5f, 0).setEasing(Easing.EXPO_OUT, Easing.SINE_IN).build())
                        .setColorData(colorParticleData)
                        .setRandomOffset(0.6f)
                        .enableNoClip()
                        .setRandomMotion(0.02f, 0.02f)
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .repeat(level, posX, posY, posZ, 5);
            }
        };
    }
}