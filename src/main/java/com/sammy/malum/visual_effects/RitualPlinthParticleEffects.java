package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.ritual_plinth.RitualPlinthBlockEntity;
import com.sammy.malum.common.block.curiosities.spirit_altar.SpiritAltarBlockEntity;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.particle.builder.SparkParticleBuilder;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.spiritLightSpecs;

public class RitualPlinthParticleEffects {
    public static void passiveRitualPlinthParticles(RitualPlinthBlockEntity plinth) {
        Level level = plinth.getLevel();
        MalumSpiritType spiritType = SpiritTypeRegistry.INFERNAL_SPIRIT;
        RandomSource random = level.random;
        for (Direction direction : Direction.values()) {
            if (direction.getAxis().equals(Direction.Axis.Y)) {
                continue;
            }
            Vec3 particlePosition = plinth.getParticlePositionPosition(direction);
            Vec3 particleVelocity = new Vec3(0.03f * direction.getStepX(), 0, 0.03f * direction.getStepZ());
            if (level.getGameTime() % 3L == 0) {
                Vec3 randomizedVelocity = particleVelocity.scale(RandomHelper.randomBetween(random, 0.8f, 1f));
                var lightSpecs = spiritLightSpecs(level, particlePosition, spiritType);
                lightSpecs.getBuilder().setMotion(randomizedVelocity);
                lightSpecs.getBloomBuilder().setMotion(randomizedVelocity);
                lightSpecs.spawnParticles();
            }
            if (level.getGameTime() % 4L == 0) {
                Vec3 randomizedVelocity = particleVelocity.scale(RandomHelper.randomBetween(random, 0.8f, 1f));
                Vec3 sparkPos = particlePosition.add(0.05f - random.nextFloat() * 0.1f, 0.05f - random.nextFloat() * 0.1f, 0.05f - random.nextFloat() * 0.1f);
                var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, sparkPos, spiritType);
                sparkParticles.getBuilder().setMotion(randomizedVelocity)
                        .modifyData(SparkParticleBuilder::getScaleData, d -> d.multiplyValue(1.5f))
                        .modifyData(SparkParticleBuilder::getLengthData, d -> d.multiplyValue(2f).multiplyCoefficient(0.75f))
                        .modifyColorData(c -> c.multiplyCoefficient(0.8f));
                sparkParticles.getBloomBuilder().setMotion(randomizedVelocity);
                sparkParticles.spawnParticlesRaw();
            }

        }
    }
}
