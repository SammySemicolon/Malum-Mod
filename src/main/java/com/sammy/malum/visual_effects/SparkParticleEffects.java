package com.sammy.malum.visual_effects;

import com.sammy.malum.client.SpiritBasedParticleBuilder;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.client.ParticleRegistry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.ParticleEffectSpawner;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.SparkBehaviorComponent;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

import java.util.function.Consumer;
import java.util.function.Function;

import static net.minecraft.util.Mth.nextFloat;

public class SparkParticleEffects {

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, MalumSpiritType spiritType) {
        return spiritMotionSparks(level, pos, spiritType, new WorldParticleOptions(ParticleRegistry.ROUND_SPARK));
    }

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, ColorParticleData colorData) {
        return spiritMotionSparks(level, pos, colorData, new WorldParticleOptions(ParticleRegistry.ROUND_SPARK));
    }

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, MalumSpiritType spiritType, WorldParticleOptions options) {
        return spiritMotionSparks(level, pos, options, o -> SpiritBasedParticleBuilder.create(o).setSpirit(spiritType));
    }

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, ColorParticleData colorData, WorldParticleOptions options) {
        return spiritMotionSparks(level, pos, options, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, WorldParticleOptions options, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier) {
        var lengthData = GenericParticleData.create(0.1f, 0.2f, 0f).setEasing(Easing.SINE_IN, Easing.SINE_IN_OUT).build();
        var builder = builderSupplier.apply(options.setBehaviorIfDefault(new SparkBehaviorComponent(lengthData)));
        var bloomBuilder = builderSupplier.apply(new WorldParticleOptions(LodestoneParticleRegistry.WISP_PARTICLE));
        return spiritMotionSparks(level, pos, builder, bloomBuilder);
    }

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, WorldParticleBuilder builder, WorldParticleBuilder bloomBuilder) {
        RandomSource rand = level.getRandom();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.05f, 0.1f)).randomSpinOffset(rand).build();
        final Consumer<LodestoneWorldParticle> slowDown = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.95f));
        int lifetime = RandomHelper.randomBetween(rand, 10, 20);
        final WorldParticleBuilder sparkParticleBuilder = builder
                .setTransparencyData(GenericParticleData.create(0.8f, 0f).build())
                .setScaleData(GenericParticleData.create(0.1f, RandomHelper.randomBetween(rand, 0.2f, 0.3f), 0).build())
                .setLifetime(lifetime)
                .enableNoClip()
                .addTickActor(slowDown);
        final WorldParticleBuilder bloomParticleBuilder = SpiritLightSpecs.spiritBloom(level, bloomBuilder, lifetime).setSpinData(spinData).addTickActor(slowDown);
        return new ParticleEffectSpawner(level, pos, sparkParticleBuilder, bloomParticleBuilder);
    }
}