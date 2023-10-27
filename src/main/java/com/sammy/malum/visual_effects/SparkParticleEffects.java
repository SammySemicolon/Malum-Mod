package com.sammy.malum.visual_effects;

import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;

import java.util.*;
import java.util.function.*;

import static net.minecraft.util.Mth.nextFloat;

public class SparkParticleEffects {

    public static ParticleEffectSpawner<SparkParticleBuilder> spiritMotionSparks(Level level, Vec3 pos, MalumSpiritType spiritType) {
        var rand = level.getRandom();
        final ColorParticleData colorData = spiritType.createMainColorData(1.25f).build();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.05f, 0.1f)).randomSpinOffset(rand).build();
        final Consumer<LodestoneWorldParticleActor> slowDown = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.95f));
        int lifetime = RandomHelper.randomBetween(rand, 10, 20);
        final SparkParticleBuilder sparkParticleBuilder = SparkParticleBuilder.create(ParticleRegistry.ROUND_SPARK)
                .setLengthData(GenericParticleData.create(0.1f, 0.2f, 0f).setEasing(Easing.SINE_IN, Easing.SINE_IN_OUT).build())
                .setTransparencyData(GenericParticleData.create(0.8f, 0f).build())
                .setScaleData(GenericParticleData.create(0.1f, RandomHelper.randomBetween(rand, 0.2f, 0.3f), 0).build())
                .setColorData(colorData)
                .setLifetime(lifetime)
                .enableNoClip()
                .addActor(slowDown);
        final WorldParticleBuilder bloomParticleBuilder = SpiritLightSpecs.spiritBloom(level, spiritType, spinData, lifetime).addActor(slowDown);

        return new ParticleEffectSpawner<>(level, pos, sparkParticleBuilder, bloomParticleBuilder);
    }
}
