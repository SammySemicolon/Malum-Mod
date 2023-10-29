package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.weeping_well.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.core.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.*;

import static com.sammy.malum.common.entity.nitrate.VividNitrateEntity.COLOR_FUNCTION;
import static com.sammy.malum.visual_effects.SpiritLightSpecs.spiritLightSpecs;

public class WeepingWellParticleEffects {

    public static final List<Color> RADIANT_COLORS = List.of(
            new Color(179, 35, 218),
            new Color(224, 210, 68),
            new Color(42, 146, 218));

    private static final VoxelShape WELL_SHAPE = Block.box(-16.0D, 11.0D, -16.0D, 32.0D, 13.0D, 32.0D);
    private static final GenericParticleData SMOKE_TRANSPARENCY = GenericParticleData.create(0, 0.2f, 0f).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build();

    public static Color getWeepingWellSmokeColor(RandomSource rand) {
        float colorMultiplier = RandomHelper.randomBetween(rand, 0.6f, 1.2f);
        return new Color((int) (12 * colorMultiplier), (int) (3 * colorMultiplier), (int) (12 * colorMultiplier));
    }

    public static void passiveWeepingWellParticles(VoidConduitBlockEntity voidConduit) {
        Level level = voidConduit.getLevel();
        if (level.getGameTime() % 6L == 0) {
            final BlockPos blockPos = voidConduit.getBlockPos();
            var rand = level.random;
            int lifetime = RandomHelper.randomBetween(rand, 40, 80);
            float yMotion = RandomHelper.randomBetween(rand, 0f, 0.02f);
            Color color = getWeepingWellSmokeColor(rand);
            ColorParticleData colorData = ColorParticleData.create(color, color.darker()).setCoefficient(0.5f).build();
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setTransparencyData(SMOKE_TRANSPARENCY)
                    .setSpinData(SpinParticleData.create(0.1f, 0.4f, 0).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build())
                    .setScaleData(GenericParticleData.create(0f, 0.9f, 0.5f).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build())
                    .setColorData(colorData)
                    .setLifetime(lifetime)
                    .addMotion(0, yMotion, 0)
                    .enableNoClip()
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .surroundVoxelShape(level, blockPos, WELL_SHAPE, 12);
            if (rand.nextFloat() < 0.75f) {
                int rotation = rand.nextInt(16);
                Vec3 offsetPosition = DataHelper.rotatingRadialOffset(new Vec3(blockPos.getX()+0.5f, blockPos.getY()+0.75f, blockPos.getZ()+0.5f), 1.1f, rotation, 16, voidConduit.getLevel().getGameTime(), 640);
                final float acceleration = RandomHelper.randomBetween(rand, 0.002f, 0.02f);
                final long gameTime = level.getGameTime();
                final Consumer<LodestoneWorldParticleActor> behavior = p -> {
                    if (level.getGameTime() < gameTime + 10) {
                        p.setParticleMotion(p.getParticleSpeed().add(0, acceleration, 0));
                    }
                };
                for (int i = 0; i < 2; i++) {
                    var lightSpecs = weepingWellSpecs(level, offsetPosition);
                    lightSpecs.getBuilder().addActor(behavior);
                    lightSpecs.getBloomBuilder().addActor(behavior);
                    lightSpecs.spawnParticles();
                }
            }
        }
    }

    public static void radiantWeepingWellParticles(VoidConduitBlockEntity voidConduit) {
        Level level = voidConduit.getLevel();
        Random rand = level.random;
        Color color = RADIANT_COLORS.get(((int) level.getGameTime() % 27) / 9);
        final BlockPos blockPos = voidConduit.getBlockPos();
        final ColorParticleData colorData = ColorParticleData.create(color.brighter(), color).setCoefficient(0.5f).build();
        if (level.getGameTime() % 3L == 0) {
            final GenericParticleData scaleData = GenericParticleData.create(0.1f, RandomHelper.randomBetween(rand, 1.7f, 1.8f), 0.5f).setEasing(Easing.SINE_OUT, Easing.SINE_IN).setCoefficient(RandomHelper.randomBetween(rand, 1f, 1.25f)).build();
            final Consumer<LodestoneWorldParticleActor> slowDown = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.95f));
            float yMotion = RandomHelper.randomBetween(rand, 0.04f, 0.06f);
            Vec3 motion = new Vec3(0f, yMotion, 0f);
            DirectionalParticleBuilder.create(ParticleRegistry.SQUARE)
                    .setTransparencyData(GenericParticleData.create(0.9f, 0.05f, 0f).setEasing(Easing.CUBIC_OUT, Easing.EXPO_IN).build())
                    .setScaleData(scaleData)
                    .setColorData(colorData)
                    .setLifetime(100)
                    .setMotion(motion)
                    .setDirection(motion.normalize())
                    .enableNoClip()
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                    .addActor(slowDown)
                    .spawn(level, blockPos.getX() + 0.5f, blockPos.getY() + 0.75f, blockPos.getZ() + 0.5f)
                    .setTransparencyData(GenericParticleData.create(0.1f, 0.6f, 0f).setEasing(Easing.CUBIC_OUT, Easing.EXPO_OUT).build())
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .spawn(level, blockPos.getX() + 0.5f, blockPos.getY() + 0.65f, blockPos.getZ() + 0.5f);
        }

        final float acceleration = RandomHelper.randomBetween(rand, 0.002f, 0.02f);
        final long gameTime = level.getGameTime();
        final Consumer<LodestoneWorldParticleActor> behavior = p -> {
            if (level.getGameTime() < gameTime + 5) {
                p.setParticleMotion(p.getParticleSpeed().add(0, acceleration, 0));
            }
        };
        if (level.getGameTime() % 2L == 0) {
            int rotation = (int) ((level.getGameTime() / 2f) % 16);
            Vec3 offsetPosition = DataHelper.rotatingRadialOffset(new Vec3(blockPos.getX() + 0.5f, blockPos.getY() + 0.75f, blockPos.getZ() + 0.5f), 1.1f, rotation, 16, voidConduit.getLevel().getGameTime(), 640);
            var lightSpecs = weepingWellSpecs(level, offsetPosition, colorData, LodestoneWorldParticleRenderType.ADDITIVE);
            lightSpecs.getBuilder().addActor(behavior);
            lightSpecs.getBloomBuilder().addActor(behavior);
            lightSpecs.spawnParticles();
        }
    }

    public static ParticleEffectSpawner<WorldParticleBuilder> weepingWellSpecs(Level level, Vec3 pos) {
        var rand = level.random;
        Color color = getWeepingWellSmokeColor(rand);
        ColorParticleData colorData = ColorParticleData.create(color, color.darker()).setCoefficient(0.5f).build();
        return weepingWellSpecs(level, pos, colorData, LodestoneWorldParticleRenderType.LUMITRANSPARENT);
    }
    public static ParticleEffectSpawner<WorldParticleBuilder> weepingWellSpecs(Level level, Vec3 pos, ColorParticleData colorData, LodestoneWorldParticleRenderType renderType) {
        var rand = level.random;
        var lightSpecs = spiritLightSpecs(level, pos, colorData, colorData, ParticleRegistry.LIGHT_SPEC_SMALL);
        lightSpecs.getBuilder().act(b -> b
                .setRenderType(renderType)
                .multiplyLifetime(6f)
                .modifyData(b::getTransparencyData, d -> d.multiplyValue(RandomHelper.randomBetween(rand, 0.75f, 1f)))
                .modifyData(b::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(rand, 1.5f, 3.5f))));
        lightSpecs.getBloomBuilder().act(b -> b
                .setRenderType(renderType)
                .multiplyLifetime(6f)
                .setTransparencyData(GenericParticleData.create(0f, 0.75f, 0.25f).build())
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .modifyData(b::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(rand, 1f, 1.25f))));
        return lightSpecs;
    }
}
