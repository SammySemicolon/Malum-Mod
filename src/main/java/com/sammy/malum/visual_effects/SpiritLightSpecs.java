package com.sammy.malum.visual_effects;

import com.sammy.malum.client.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.world.*;
import team.lodestar.lodestone.systems.particle.world.options.*;

import java.util.*;
import java.util.function.*;

import static net.minecraft.util.Mth.*;

public class SpiritLightSpecs {

    public static void coolLookingShinyThing(Level level, Vec3 pos, MalumSpiritType spiritType) {
        var centralLightSpecs = spiritLightSpecs(level, pos, spiritType, new WorldParticleOptions(ParticleRegistry.LIGHT_SPEC_SMALL.get()));
        centralLightSpecs.getBuilder()
                .multiplyLifetime(0.6f)
                .modifyColorData(d -> d.multiplyCoefficient(0.5f))
                .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(6f))
                .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(3f));
        centralLightSpecs.getBloomBuilder()
                .multiplyLifetime(0.6f)
                .modifyColorData(d -> d.multiplyCoefficient(0.5f))
                .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(4f))
                .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(3f));
        centralLightSpecs.spawnParticles();
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, MalumSpiritType spiritType, float distance, int rotatingSpecs) {
        rotatingLightSpecs(level, pos, spiritType, distance, rotatingSpecs, b -> {
        });
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, MalumSpiritType spiritType, float distance, int rotatingSpecs, Consumer<WorldParticleBuilder> sharedModifier) {
        rotatingLightSpecs(level, pos, spiritType, distance, rotatingSpecs, new WorldParticleOptions(ParticleRegistry.LIGHT_SPEC_SMALL.get()), sharedModifier);
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, MalumSpiritType spiritType, float distance, int rotatingSpecs, WorldParticleOptions options, Consumer<WorldParticleBuilder> sharedModifier) {
        rotatingLightSpecs(level, pos, spiritType, distance, rotatingSpecs, options, sharedModifier, sharedModifier);
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, MalumSpiritType spiritType, float distance, int rotatingSpecs, Consumer<WorldParticleBuilder> lightSpecModifier, Consumer<WorldParticleBuilder> bloomModifier) {
        rotatingLightSpecs(level, pos, spiritType, distance, rotatingSpecs, new WorldParticleOptions(ParticleRegistry.LIGHT_SPEC_SMALL.get()), lightSpecModifier, bloomModifier);
    }

    public static void rotatingLightSpecs(Level level, Vec3 pos, MalumSpiritType spiritType, float distance, int rotatingSpecs, WorldParticleOptions options, Consumer<WorldParticleBuilder> lightSpecModifier, Consumer<WorldParticleBuilder> bloomModifier) {
        long gameTime = level.getGameTime();
        if (level.getGameTime() % 2L == 0) {
            for (int i = 0; i < rotatingSpecs; i++) {
                long offsetGameTime = gameTime + i * 120L;
                double yOffset = Math.sin((offsetGameTime % 360) / 30f) * 0.1f;
                Vec3 offsetPosition = DataHelper.rotatingRadialOffset(pos.add(0, yOffset, 0), distance, i, rotatingSpecs, gameTime, 160);

                var lightSpecs = spiritLightSpecs(level, offsetPosition, spiritType, options);
                lightSpecs.getBuilder().act(lightSpecModifier).multiplyLifetime(2f).modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.2f));
                lightSpecs.getBloomBuilder().act(bloomModifier).act(b -> b.multiplyLifetime(1.4f).modifyData(List.of(b::getScaleData, b::getTransparencyData), d -> d.multiplyValue(0.6f)));
                lightSpecs.spawnParticles();
            }
        }
        var lightSpecs = spiritLightSpecs(level, pos, spiritType, options);
        lightSpecs.getBuilder().act(lightSpecModifier)
                .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.7f))
                .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.5f));

        lightSpecs.getBloomBuilder().act(bloomModifier)
                .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.3f))
                .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.75f));

        lightSpecs.spawnParticles();
    }

    public static ParticleEffectSpawner spiritLightSpecs(Level level, Vec3 pos, MalumSpiritType spiritType) {
        return spiritLightSpecs(level, pos, spiritType, new WorldParticleOptions(ParticleRegistry.LIGHT_SPEC_SMALL.get()));
    }

    public static ParticleEffectSpawner spiritLightSpecs(Level level, Vec3 pos, MalumSpiritType spiritType, WorldParticleOptions options) {
        return spiritLightSpecs(level, pos, spiritType.createColorData().build(), options);
    }

    public static ParticleEffectSpawner spiritLightSpecs(Level level, Vec3 pos, ColorParticleData colorData) {
        return spiritLightSpecs(level, pos, colorData, new WorldParticleOptions(ParticleRegistry.LIGHT_SPEC_SMALL.get()));
    }

    public static ParticleEffectSpawner spiritLightSpecs(Level level, Vec3 pos, ColorParticleData colorData, WorldParticleOptions options) {
        var rand = level.getRandom();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.05f, 0.1f)).randomSpinOffset(rand).build();
        final Consumer<LodestoneWorldParticle> slowDown = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.95f));
        int lifetime = RandomHelper.randomBetween(rand, 10, 20);
        final WorldParticleBuilder worldParticleBuilder = WorldParticleBuilder.create(options)
                .setTransparencyData(GenericParticleData.create(0.8f, 0f).build())
                .setSpinData(spinData)
                .setScaleData(GenericParticleData.create(0.025f, RandomHelper.randomBetween(rand, 0.2f, 0.3f), 0).build())
                .setColorData(colorData)
                .setLifetime(lifetime)
                .enableNoClip()
                .addTickActor(slowDown);
        final SpiritBasedParticleBuilder bloomParticleBuilder = SpiritLightSpecs.spiritBloom(level, new WorldParticleOptions(LodestoneParticleRegistry.WISP_PARTICLE.get()), colorData, spinData, lifetime).addTickActor(slowDown);
        return new ParticleEffectSpawner(level, pos, worldParticleBuilder, bloomParticleBuilder);
    }

    public static SpiritBasedParticleBuilder spiritBloom(Level level, MalumSpiritType spiritType, SpinParticleData spinParticleData, int lifetime) {
        return spiritBloom(level, new WorldParticleOptions(LodestoneParticleRegistry.WISP_PARTICLE.get()), spiritType, spinParticleData, lifetime);
    }

    public static SpiritBasedParticleBuilder spiritBloom(Level level, WorldParticleOptions options, MalumSpiritType spiritType, SpinParticleData spinParticleData, int lifetime) {
        return spiritBloom(level, options, spiritType.createColorData().build(), spinParticleData, lifetime).setSpirit(spiritType);
    }

    public static SpiritBasedParticleBuilder spiritBloom(Level level, ColorParticleData colorData, SpinParticleData spinParticleData, int lifetime) {
        return spiritBloom(level, new WorldParticleOptions(LodestoneParticleRegistry.WISP_PARTICLE.get()), colorData, spinParticleData, lifetime);
    }

    public static SpiritBasedParticleBuilder spiritBloom(Level level, WorldParticleOptions options, ColorParticleData colorData, SpinParticleData spinParticleData, int lifetime) {
        var rand = level.random;
        return SpiritBasedParticleBuilder.create(options)
                .setTransparencyData(GenericParticleData.create(0.35f, 0f).build())
                .setSpinData(spinParticleData)
                .setScaleData(GenericParticleData.create(0.04f, RandomHelper.randomBetween(rand, 0.08f, 0.14f), 0).setEasing(Easing.SINE_IN, Easing.SINE_IN_OUT).build())
                .setColorData(colorData)
                .setLifetime(lifetime)
                .enableNoClip();
    }
}