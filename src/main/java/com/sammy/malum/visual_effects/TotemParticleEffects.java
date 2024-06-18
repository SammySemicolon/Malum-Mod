package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlockEntity;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.particle.builder.AbstractParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.spiritLightSpecs;

public class TotemParticleEffects {

    public static void activeTotemPoleParticles(TotemPoleBlockEntity totemPoleBlockEntity) {
        MalumSpiritType spiritType = totemPoleBlockEntity.type;
        Level level = totemPoleBlockEntity.getLevel();
        long gameTime = level.getGameTime();
        var random = level.random;
        if (gameTime % 12L == 0) {
            int offset = (totemPoleBlockEntity.getBlockPos().getY() - totemPoleBlockEntity.totemBaseYLevel) * 40;
            gameTime += offset;
            final float time = 480;
            for (int i = 0; i < 2; i++) {
                float velocity = RandomHelper.randomBetween(random, 0.005f, 0.015f);
                Vec3 offsetPosition = DataHelper.rotatingRadialOffset(totemPoleBlockEntity.getBlockPos().getCenter(), 0.9f, i, 2, gameTime, time);
                offsetPosition = offsetPosition.add(0, (Math.cos(((gameTime + i * 240) % time) / time) * 0.25f) - 0.25f, 0);
                var lightSpecs = spiritLightSpecs(level, offsetPosition, spiritType);
                lightSpecs.getBuilder()
                        .multiplyLifetime(4.5f)
                        .setMotion(0, velocity, 0)
                        .setTransparencyData(GenericParticleData.create(0.2f, 0.8f, 0f).build())
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 1f, 2f)));
                lightSpecs.getBloomBuilder()
                        .multiplyLifetime(3f)
                        .setMotion(0, velocity, 0)
                        .setTransparencyData(GenericParticleData.create(0.05f, 0.35f, 0f).build())
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 0.5f, 1f)));
                lightSpecs.spawnParticles();
            }
        }
    }

    public static void activateTotemPoleParticles(TotemPoleBlockEntity totemPoleBlockEntity) {
        MalumSpiritType spiritType = totemPoleBlockEntity.type;
        Level level = totemPoleBlockEntity.getLevel();
        long gameTime = level.getGameTime();
        var random = level.random;
        final float time = 16;
        for (int i = 0; i < 16; i++) {
            float velocity = RandomHelper.randomBetween(random, 0.005f, 0.015f);
            Vec3 offsetPosition = DataHelper.rotatingRadialOffset(totemPoleBlockEntity.getBlockPos().getCenter(), 0.85f, i, 16, gameTime, time);
            offsetPosition = offsetPosition.add(0, (Math.cos(((gameTime + i * 240) % time) / time) * 0.25f) - 0.25f, 0);
            var lightSpecs = spiritLightSpecs(level, offsetPosition, spiritType);
            lightSpecs.getBuilder()
                    .multiplyLifetime(2.5f)
                    .setMotion(0, velocity, 0)
                    .setLifeDelay(i)
                    .setTransparencyData(GenericParticleData.create(0.2f, 0.8f, 0f).build())
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 1f, 2f)));
            lightSpecs.getBloomBuilder()
                    .multiplyLifetime(1.5f)
                    .setMotion(0, velocity, 0)
                    .setLifeDelay(i)
                    .setTransparencyData(GenericParticleData.create(0.05f, 0.35f, 0f).build())
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 0.5f, 1f)));
            lightSpecs.spawnParticles();
        }
    }
}