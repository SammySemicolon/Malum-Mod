package com.sammy.malum.visual_effects;

import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.reaping.*;
import com.sammy.malum.core.systems.spirit.SpiritLike;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.rendering.particle.standard.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.builder.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.color.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.data.spin.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.world.*;
import team.lodestar.lodestone.modules.rendering.particle.standard.world.options.*;

import java.util.function.*;

import static net.minecraft.util.Mth.*;

public class SpiritLightSpecs {

    public static void spiritParticles(SpiritItemEntity spirit) {
        Vec3 direction = spirit.getDeltaMovement().add(0, spirit.getYOffset(0.5f), 0).normalize();
        Vec3 motion = direction.scale(0.2f);
        Consumer<LodestoneWorldParticle> behavior = p -> {
            Vec3 spiritPosition = spirit.position().add(0, spirit.getYOffset(0.5f), 0);
            Vec3 toSpirit = spiritPosition.subtract(p.getParticlePosition()).normalize();
            double length = p.getParticleSpeed().length();
            float delta = 0.3f + (p.getAge() / (float)p.getLifetime()) * 0.7f;
            p.setParticleSpeed(p.getParticleSpeed().lerp(toSpirit.scale(length), delta));
        };
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(spirit.level(), spirit.getOffsetPosition(), spirit.getSpiritType());
        lightSpecs.getBuilder().setMotion(motion).addTickActor(behavior);
        lightSpecs.getBloomBuilder().setMotion(motion).addTickActor(behavior);
        lightSpecs.spawnParticles();
    }

    public static void coolLookingShinyThing(Level level, Vec3 pos, SpiritLike spirit) {
        var centralLightSpecs = spiritLightSpecs(level, pos, spirit, new WorldParticleOptions(MalumParticles.LIGHT_SPEC.get()));
        centralLightSpecs.getBuilder()
                .multiplyLifetime(0.6f)
                .modifyColorData(d -> d.multiplyCoefficient(0.5f))
                .modifyScaleData(d -> d.multiplyValue(6f))
                .modifyTransparencyData(d -> d.multiplyValue(3f));
        centralLightSpecs.getBloomBuilder()
                .multiplyLifetime(0.6f)
                .modifyColorData(d -> d.multiplyCoefficient(0.5f))
                .modifyScaleData(d -> d.multiplyValue(4f))
                .modifyTransparencyData(d -> d.multiplyValue(3f));
        centralLightSpecs.spawnParticles();
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, ColorParticleData colorData, float distance, int rotatingSpecs, Consumer<WorldParticleBuilder> modifier) {
        rotatingLightSpecs(level, pos, new WorldParticleOptions(MalumParticles.LIGHT_SPEC.get()), colorData, distance, rotatingSpecs, modifier);
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, WorldParticleOptions options, ColorParticleData colorData, float distance, int rotatingSpecs, Consumer<WorldParticleBuilder> modifier) {
        rotatingLightSpecs(level, pos, p -> spiritLightSpecs(level, p, colorData, options).act(modifier), distance, rotatingSpecs);
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, ColorParticleData colorData, float distance, int rotatingSpecs) {
        rotatingLightSpecs(level, pos, new WorldParticleOptions(MalumParticles.LIGHT_SPEC.get()), colorData, distance, rotatingSpecs);
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, WorldParticleOptions options, ColorParticleData colorData, float distance, int rotatingSpecs) {
        rotatingLightSpecs(level, pos, p -> spiritLightSpecs(level, p, colorData, options), distance, rotatingSpecs);
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, SpiritLike spirit, float distance, int rotatingSpecs) {
        rotatingLightSpecs(level, pos, new WorldParticleOptions(MalumParticles.LIGHT_SPEC.get()), spirit, distance, rotatingSpecs);
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, WorldParticleOptions options, SpiritLike spirit, float distance, int rotatingSpecs) {
        rotatingLightSpecs(level, pos, p -> spiritLightSpecs(level, p, spirit, options), distance, rotatingSpecs);
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, SpiritLike spirit, float distance, int rotatingSpecs, Consumer<WorldParticleBuilder> modifier) {
        rotatingLightSpecs(level, pos, new WorldParticleOptions(MalumParticles.LIGHT_SPEC.get()), spirit, distance, rotatingSpecs, modifier);
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, WorldParticleOptions options, SpiritLike spirit, float distance, int rotatingSpecs, Consumer<WorldParticleBuilder> modifier) {
        rotatingLightSpecs(level, pos, p -> spiritLightSpecs(level, p, spirit, options).act(modifier), distance, rotatingSpecs);
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, Function<Vec3, ParticleEffectSpawner> particleSpawner, float distance, int rotatingSpecs) {
        long gameTime = level.getGameTime();
        if (level.getGameTime() % 2L == 0) {
            for (int i = 0; i < rotatingSpecs; i++) {
                long offsetGameTime = gameTime + i * 120L;
                double yOffset = Math.sin((offsetGameTime % 360) / 30f) * 0.1f;
                Vec3 offsetPosition = VecHelper.rotatingRadialOffset(pos.add(0, yOffset, 0), distance, i, rotatingSpecs, gameTime, 160);
                var lightSpecs = particleSpawner.apply(offsetPosition);
                lightSpecs.getBuilder()
                        .setLifetimeModifier(2f)
                        .modifyScaleData(d -> d.multiplyValue(1.2f));
                lightSpecs.getBloomBuilder()
                        .setLifetimeModifier(1.4f)
                        .modifyScaleData(d -> d.multiplyValue(0.6f))
                        .modifyTransparencyData(d -> d.multiplyValue(0.6f));
                lightSpecs.spawnParticles();
            }
        }
        var lightSpecs = particleSpawner.apply(pos);
        lightSpecs.getBuilder()
                .multiplyLifetime(0.5f)
                .modifyScaleData(d -> d.multiplyValue(1.7f))
                .modifyTransparencyData(d -> d.multiplyValue(0.5f));

        lightSpecs.getBloomBuilder()
                .multiplyLifetime(0.5f)
                .modifyScaleData(d -> d.multiplyValue(1.3f))
                .modifyTransparencyData(d -> d.multiplyValue(0.75f));

        lightSpecs.spawnParticles();
    }

    public static ParticleEffectSpawner spiritLightSpecs(Level level, Vec3 pos, SpiritLike spirit) {
        return spiritLightSpecs(level, pos, spirit, new WorldParticleOptions(MalumParticles.LIGHT_SPEC));
    }

    public static ParticleEffectSpawner spiritLightSpecs(Level level, Vec3 pos, ColorParticleDataWrapper colorData) {
        return spiritLightSpecs(level, pos, colorData, new WorldParticleOptions(MalumParticles.LIGHT_SPEC));
    }

    public static ParticleEffectSpawner spiritLightSpecs(Level level, Vec3 pos, SpiritLike spirit, WorldParticleOptions options) {
        return spiritLightSpecs(level, pos, options, o -> SpiritBasedParticleBuilder.createSpirit(o).setSpirit(spirit));
    }

    public static ParticleEffectSpawner spiritLightSpecs(Level level, Vec3 pos, ColorParticleDataWrapper colorData, WorldParticleOptions options) {
        return spiritLightSpecs(level, pos, options, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner spiritLightSpecs(Level level, Vec3 pos, WorldParticleOptions options) {
        return spiritLightSpecs(level, pos, options, WorldParticleBuilder::create);
    }

    public static ParticleEffectSpawner spiritLightSpecs(Level level, Vec3 pos, WorldParticleOptions options, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier) {
        var builder = builderSupplier.apply(options);
        var bloomBuilder = builderSupplier.apply(new WorldParticleOptions(LodestoneParticleTypes.WISP_PARTICLE));
        return spiritLightSpecs(level, pos, builder, bloomBuilder);
    }

    public static ParticleEffectSpawner spiritLightSpecs(Level level, Vec3 pos, WorldParticleBuilder builder, WorldParticleBuilder bloomBuilder) {
        var rand = level.getRandom();
        SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.05f, 0.1f)).randomSpinOffset(rand).build();
        float friction = 0.95f;
        int lifetime = Easing.SINE_IN_OUT.asWeighedRandom(rand, 10, 20);
        WorldParticleBuilder worldParticleBuilder = builder
                .setScaleData(GenericParticleData.create(0.025f, Easing.SINE_IN_OUT.asWeighedRandom(rand, 0.2f, 0.3f), 0).build())
                .setTransparencyData(GenericParticleData.create(0.8f, 0f).build())
                .multiplyFriction(friction)
                .setSpinData(spinData)
                .setLifetime(lifetime)
                .enableNoClip();
        final WorldParticleBuilder bloomParticleBuilder = SpiritLightSpecs.spiritBloom(level, bloomBuilder, lifetime).setSpinData(spinData).setFriction(friction);
        return new ParticleEffectSpawner(level, pos, worldParticleBuilder, bloomParticleBuilder);
    }

    public static WorldParticleBuilder spiritBloom(Level level, SpiritLike spirit, int lifetime) {
        return spiritBloom(level, spirit, new WorldParticleOptions(LodestoneParticleTypes.WISP_PARTICLE), lifetime);
    }

    public static WorldParticleBuilder spiritBloom(Level level, ColorParticleData colorData, int lifetime) {
        return spiritBloom(level, colorData, new WorldParticleOptions(LodestoneParticleTypes.WISP_PARTICLE), lifetime);
    }

    public static WorldParticleBuilder spiritBloom(Level level, SpiritLike spirit, WorldParticleOptions options, int lifetime) {
        return spiritBloom(level, options, o -> SpiritBasedParticleBuilder.createSpirit(o).setSpirit(spirit), lifetime);
    }

    public static WorldParticleBuilder spiritBloom(Level level, ColorParticleData colorData, WorldParticleOptions options, int lifetime) {
        return spiritBloom(level, options, o -> WorldParticleBuilder.create(o).setColorData(colorData), lifetime);
    }

    public static WorldParticleBuilder spiritBloom(Level level, WorldParticleOptions options, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier, int lifetime) {
        return spiritBloom(level, builderSupplier.apply(options), lifetime);
    }

    public static WorldParticleBuilder spiritBloom(Level level, WorldParticleBuilder builder, int lifetime) {
        var rand = level.random;
        return builder
                .setScaleData(GenericParticleData.create(0.04f, Easing.SINE_IN_OUT.asWeighedRandom(rand, 0.08f, 0.14f), 0).setEasing(Easing.SINE_IN, Easing.SINE_IN_OUT).build())
                .setTransparencyData(GenericParticleData.create(0.35f, 0f).build())
                .setLifetime(lifetime)
                .enableNoClip();
    }
}