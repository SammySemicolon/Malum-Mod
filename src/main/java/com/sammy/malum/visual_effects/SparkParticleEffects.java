package com.sammy.malum.visual_effects;

import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.client.ParticleRegistry;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.LodestoneWorldParticleActor;
import team.lodestar.lodestone.systems.particle.builder.SparkParticleBuilder;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.util.function.Consumer;

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
