package com.sammy.malum.visual_effects;

import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.world.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.*;

import java.util.function.*;

import static net.minecraft.util.Mth.*;

public class SparkParticleEffects {

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, MalumSpiritType spiritType) {
        return spiritMotionSparks(level, pos, spiritType.createColorData(1.25f).build(), spiritType.createColorData().build());
    }

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, ColorParticleData colorData) {
        return spiritMotionSparks(level, pos, colorData, colorData);
    }
    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, ColorParticleData colorData, ColorParticleData bloomColorData) {
        RandomSource rand = level.getRandom();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.05f, 0.1f)).randomSpinOffset(rand).build();
        final Consumer<LodestoneWorldParticle> slowDown = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.95f));
        int lifetime = RandomHelper.randomBetween(rand, 10, 20);
        final WorldParticleBuilder sparkParticleBuilder = WorldParticleBuilder.create(ParticleRegistry.ROUND_SPARK.get(), new SparkParticleBehavior())
                .setTransparencyData(GenericParticleData.create(0.8f, 0f).build())
                .setScaleData(GenericParticleData.create(0.1f, RandomHelper.randomBetween(rand, 0.2f, 0.3f), 0).build())
                .setLengthData(GenericParticleData.create(0.1f, 0.2f, 0f).setEasing(Easing.SINE_IN, Easing.SINE_IN_OUT).build())
                .setColorData(colorData)
                .setLifetime(lifetime)
                .enableNoClip()
                .addTickActor(slowDown);
        final WorldParticleBuilder bloomParticleBuilder = SpiritLightSpecs.spiritBloom(level, bloomColorData, spinData, lifetime).addTickActor(slowDown);

        return new ParticleEffectSpawner(level, pos, sparkParticleBuilder, bloomParticleBuilder);
    }
}
