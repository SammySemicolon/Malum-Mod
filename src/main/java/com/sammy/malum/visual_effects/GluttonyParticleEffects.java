package com.sammy.malum.visual_effects;

import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.client.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;
import team.lodestar.lodestone.systems.particle.world.options.*;

import java.awt.*;

public class GluttonyParticleEffects {

    private static final Color GLUTTONY_GREEN = new Color(47, 81, 28);
    private static final Color GLUTTONY_DARK = new Color(31, 35, 30);
    private static final Color GLUTTONY_SHADE = new Color(14, 14, 16);

    public static void thrownGluttonySplash(PositionEffectData positionData) {
        Level level = Minecraft.getInstance().level;
        var random = level.random;

        for (int i = 0; i < 4; i++) {
            int lifetime = RandomHelper.randomBetween(random, 30, 40);
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE, LodestoneBehaviorComponent.DIRECTIONAL)
                    .setTransparencyData(GenericParticleData.create(0.2f, 0.7f, 0).build())
                    .setSpinData(SpinParticleData.createRandomDirection(random, 0.05f).build())
                    .setScaleData(GenericParticleData.create(2f, 0f).setEasing(Easing.EXPO_IN).build())
                    .setLifetime(lifetime)
                    .setColorData(ColorParticleData.create(GLUTTONY_DARK, GLUTTONY_SHADE).setCoefficient(2f).build())
                    .enableNoClip()
                    .setRandomOffset(1f, 0f)
                    .setMotion(0, 0.001f, 0)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT.withDepthFade())
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeat(level, positionData.posX, positionData.posY, positionData.posZ, 2);
        }
        var ring = gluttonyRing(positionData.getAsVector(), new WorldParticleOptions(LodestoneParticleRegistry.THIN_EXTRUDING_SPARK_PARTICLE), 1.2f, 32);
        ring.spawnParticles();
        ring = gluttonyRing(positionData.getAsVector(), new WorldParticleOptions(LodestoneParticleRegistry.THIN_EXTRUDING_SPARK_PARTICLE), 0.4f, 16, 0.5f);
        ring.getBuilder().replaceExistingBehavior(SparkBehaviorComponent.class, c -> new ExtrudingSparkBehaviorComponent(c.getLengthData().multiplyValue(0.5f).bake()));
        ring.spawnParticles();
    }

    public static ParticleEffectSpawner gluttonyRing(Vec3 center, WorldParticleOptions options, float distance, int count) {
        return gluttonyRing(center, options, distance, count, 1f);
    }

    public static ParticleEffectSpawner gluttonyRing(Vec3 center, WorldParticleOptions options, float distance, int count, float lifetimeScalar) {
        var lengthData = GenericParticleData.create(0.1f, 0.5f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN_OUT).setCoefficient(1.25f).build();
        var builder = WorldParticleBuilder.create(options.setBehaviorIfDefault(new ExtrudingSparkBehaviorComponent(lengthData)));
        return gluttonyRing(center, builder, distance, count, lifetimeScalar);
    }

    public static ParticleEffectSpawner gluttonyRing(Vec3 center, WorldParticleBuilder builder, float distance, int count) {
        return gluttonyRing(center, builder, distance, count, 1f);
    }

    public static ParticleEffectSpawner gluttonyRing(Vec3 center, WorldParticleBuilder builder, float distance, int count, float lifetimeScalar) {
        Level level = Minecraft.getInstance().level;
        long gameTime = level.getGameTime();
        var random = level.random;
        return new ParticleEffectSpawner(builder, b -> {
            for (int i = 0; i < count; i++) {
                Vec3 offsetPosition = DataHelper.rotatingRadialOffset(center, distance, i, count, gameTime, 320);
                for (int j = 0; j < 3; j++) {
                    boolean isAdditive = j == 0;
                    Color bright = j < 2 ? GLUTTONY_GREEN : GLUTTONY_DARK;
                    Color dark = j < 2 ? GLUTTONY_GREEN : GLUTTONY_SHADE;
                    float alphaMultiplier = isAdditive ? 1.5f : 3f;
                    float scaleMultiplier = isAdditive ? 1.25f : 2.5f;
                    float lengthMultiplier = isAdditive ? 0.75f : 1.25f;
                    float colorCoefficient = isAdditive ? 1f : 1.75f;
                    var renderType = isAdditive ? LodestoneWorldParticleRenderType.ADDITIVE : LodestoneWorldParticleRenderType.LUMITRANSPARENT;
                    var renderTarget = j < 2 ? RenderHandler.LATE_DELAYED_RENDER : RenderHandler.DELAYED_RENDER;
                    builder
                            .modifyOptionalData(b.getBehaviorComponent(SparkBehaviorComponent.class).map(SparkBehaviorComponent::getLengthData), d -> d.overrideValueMultiplier(lengthMultiplier))
                            .setRenderTarget(renderTarget)
                            .setRenderType(renderType)
                            .setMotion(0, 0.001f, 0)
                            .setScaleData(GenericParticleData.create(0.025f, RandomHelper.randomBetween(random, 0.2f, 0.3f), 0).build().multiplyValue(scaleMultiplier))
                            .setTransparencyData(GenericParticleData.create(0.8f * alphaMultiplier, 0f).build())
                            .setColorData(ColorParticleData.create(bright, dark).setCoefficient(colorCoefficient).build())
                            .setLifetime((int) (RandomHelper.randomBetween(random, 30, 60) * lifetimeScalar))
                            .enableNoClip()
                            .spawn(level, offsetPosition.x, offsetPosition.y, offsetPosition.z);
                }
            }

        });
    }
}