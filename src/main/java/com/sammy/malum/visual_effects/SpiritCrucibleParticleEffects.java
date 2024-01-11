package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.*;
import com.sammy.malum.common.item.catalyzer_augment.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;

import java.util.function.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.*;

public class SpiritCrucibleParticleEffects {

    public static void passiveCrucibleParticles(SpiritCrucibleCoreBlockEntity crucible) {
        MalumSpiritType activeSpiritType = crucible.getActiveSpiritType();
        if (activeSpiritType == null) {
            return;
        }
        Level level = crucible.getLevel();
        RandomSource random = level.random;
        Vec3 itemPos = crucible.getItemPos();
        LodestoneBlockEntityInventory spiritInventory = crucible.spiritInventory;
        LodestoneBlockEntityInventory augmentInventory = crucible.augmentInventory;
        SpiritFocusingRecipe recipe = crucible.recipe;
        if (recipe != null) {
            for (ICrucibleAccelerator accelerator : crucible.acceleratorData.accelerators) {
                if (accelerator != null) {
                    accelerator.addParticles(crucible, activeSpiritType);
                }
            }
        }
        if (recipe != null) {
            var lightSpecs = spiritLightSpecs(level, itemPos, activeSpiritType, ParticleRegistry.STAR);
            lightSpecs.getBuilder()
                    .setSpinData(SpinParticleData.create(0).setSpinOffset((level.getGameTime() * 0.05f) % 6.28f).build())
                    .modifyData(WorldParticleBuilder::getScaleData, d -> d.multiplyValue(2f))
                    .modifyData(WorldParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.25f));
            lightSpecs.getBloomBuilder()
                    .modifyData(WorldParticleBuilder::getScaleData, d -> d.multiplyValue(2f))
                    .modifyData(WorldParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.5f));
            lightSpecs.spawnParticles();
        }

        int spiritsRendered = 0;
        for (int i = 0; i < spiritInventory.slotCount; i++) {
            ItemStack item = spiritInventory.getStackInSlot(i);
            if (item.getItem() instanceof SpiritShardItem spiritSplinterItem) {
                Vec3 offset = crucible.getSpiritItemOffset(spiritsRendered++, 0);
                var spiritType = spiritSplinterItem.type;
                BlockPos blockPos = crucible.getBlockPos();
                Vec3 spiritPosition = new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
                if (recipe != null) {
                    Vec3 velocity = itemPos.subtract(spiritPosition).normalize().scale(RandomHelper.randomBetween(random, 0.03f, 0.06f));
                    if (random.nextFloat() < 0.85f) {
                        var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, spiritPosition, spiritType);
                        sparkParticles.getBuilder().setMotion(velocity).modifyData(SparkParticleBuilder::getScaleData, d -> d.multiplyValue(1.2f));
                        sparkParticles.getBloomBuilder().setMotion(velocity);
                        sparkParticles.spawnParticles();
                    }
                    if (random.nextFloat() < 0.85f) {
                        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, spiritPosition, spiritType);
                        lightSpecs.getBuilder().multiplyLifetime(0.8f).setMotion(velocity.scale(1.5f)).modifyData(WorldParticleBuilder::getScaleData, d -> d.multiplyValue(1.6f));
                        lightSpecs.getBloomBuilder().setMotion(velocity);
                        lightSpecs.spawnParticles();
                    }
                }
            }
        }

        if (level.getGameTime() % 4L == 0) {
            int augmentsRendered = 0;
            for (int i = 0; i < augmentInventory.slotCount; i++) {
                ItemStack item = augmentInventory.getStackInSlot(i);
                if (item.getItem() instanceof AbstractAugmentItem augmentItem) {
                    Vec3 offset = crucible.getAugmentItemOffset(augmentsRendered++, 0);
                    var spiritType = augmentItem.spiritType;
                    BlockPos blockPos = crucible.getBlockPos();
                    Vec3 particlePosition = new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
                    if (recipe != null) {
                        Vec3 velocity = itemPos.subtract(particlePosition).normalize().scale(RandomHelper.randomBetween(random, 0.01f, 0.02f));
                        if (random.nextFloat() < 0.15f) {
                            var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, particlePosition, spiritType);
                            sparkParticles.getBuilder().multiplyLifetime(2.5f).setMotion(velocity).modifyData(SparkParticleBuilder::getScaleData, d -> d.multiplyValue(1.2f));
                            sparkParticles.getBloomBuilder().multiplyLifetime(1.5f).setMotion(velocity);
                            sparkParticles.spawnParticles();
                        }
                        if (random.nextFloat() < 0.15f) {
                            var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, particlePosition, spiritType);
                            lightSpecs.getBuilder().multiplyLifetime(2.5f).setMotion(velocity.scale(1.5f)).modifyData(WorldParticleBuilder::getScaleData, d -> d.multiplyValue(1.6f));
                            lightSpecs.getBloomBuilder().multiplyLifetime(1.5f).setMotion(velocity);
                            lightSpecs.spawnParticles();
                        }
                    }
                    var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, particlePosition, spiritType);
                    lightSpecs.getBuilder().multiplyLifetime(2.5f).modifyData(WorldParticleBuilder::getScaleData, d -> d.multiplyValue(1.3f));
                    lightSpecs.getBloomBuilder().multiplyLifetime(1.5f);
                    lightSpecs.spawnParticles();
                }
            }
        }
    }

    public static void activeSpiritCatalyzerParticles(SpiritCatalyzerCoreBlockEntity catalyzer, ICatalyzerAccelerationTarget target, MalumSpiritType spiritType) {
        Level level = catalyzer.getLevel();
        BlockPos catalyzerPos = catalyzer.getBlockPos();
        Vec3 startPos = catalyzer.getItemOffset().add(catalyzerPos.getX(), catalyzerPos.getY(), catalyzerPos.getZ());
        RandomSource random = level.random;
        Vec3 targetPos = target.getAccelerationPoint();
        if (level.getGameTime() % 2L == 0) {
            Vec3 velocity = targetPos.subtract(startPos).normalize().scale(RandomHelper.randomBetween(random, 0.06f, 0.12f));
            Vec3 sparkPos = startPos.add(0.05f - random.nextFloat() * 0.1f, 0.05f - random.nextFloat() * 0.1f, 0.05f - random.nextFloat() * 0.1f);
            var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, sparkPos, spiritType);
            sparkParticles.getBuilder().setMotion(velocity)
                    .modifyData(SparkParticleBuilder::getScaleData, d -> d.multiplyValue(1.5f))
                    .modifyData(SparkParticleBuilder::getLengthData, d -> d.multiplyValue(2f).multiplyCoefficient(0.75f))
                    .modifyColorData(c -> c.multiplyCoefficient(0.8f));
            sparkParticles.getBloomBuilder().setMotion(velocity);
            sparkParticles.spawnParticlesRaw();
        }
        if (level.getGameTime() % 10L == 0) {
            Vec3 velocity = targetPos.subtract(startPos).normalize().scale(0.02f * targetPos.distanceTo(startPos));
            final Consumer<LodestoneWorldParticleActor> behavior = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.98f));
            final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, RandomHelper.randomBetween(random, 0.1f, 0.2f)).randomSpinOffset(random).build();
            DirectionalParticleBuilder.create(ParticleRegistry.HEXAGON)
                    .setTransparencyData(GenericParticleData.create(0.6f, 0.4f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                    .setSpinData(spinData)
                    .setScaleData(GenericParticleData.create(0.15f, 0).setEasing(Easing.SINE_IN_OUT).build())
                    .setColorData(spiritType.createMainColorData().build())
                    .setLifetime(60)
                    .setMotion(velocity)
                    .setDirection(velocity.normalize())
                    .enableNoClip()
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                    .addTickActor(behavior)
                    .spawn(level, startPos.x, startPos.y, startPos.z);
        }

        if (level.getGameTime() % 4L == 0) {
            ItemStack item = catalyzer.augmentInventory.getStackInSlot(0);
            if (item.getItem() instanceof AbstractAugmentItem augmentItem) {
                Vec3 offset = catalyzer.getAugmentOffset();
                var augmentSpiritType = augmentItem.spiritType;
                BlockPos blockPos = catalyzer.getBlockPos();
                Vec3 particlePosition = new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
                Vec3 velocity = targetPos.subtract(particlePosition).normalize().scale(RandomHelper.randomBetween(random, 0.03f, 0.06f));
                if (random.nextFloat() < 0.15f) {
                    var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, particlePosition, augmentSpiritType);
                    sparkParticles.getBuilder().multiplyLifetime(2.5f).setMotion(velocity).modifyData(SparkParticleBuilder::getScaleData, d -> d.multiplyValue(1.2f));
                    sparkParticles.getBloomBuilder().multiplyLifetime(1.5f).setMotion(velocity);
                    sparkParticles.spawnParticles();
                }
                if (random.nextFloat() < 0.15f) {
                    var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, particlePosition, augmentSpiritType);
                    lightSpecs.getBuilder().multiplyLifetime(2.5f).setMotion(velocity.scale(1.5f)).modifyData(WorldParticleBuilder::getScaleData, d -> d.multiplyValue(1.6f));
                    lightSpecs.getBloomBuilder().multiplyLifetime(1.5f).setMotion(velocity);
                    lightSpecs.spawnParticles();
                }
            }
        }
    }
    public static void passiveSpiritCatalyzerParticles(SpiritCatalyzerCoreBlockEntity catalyzer) {
        Level level = catalyzer.getLevel();
        RandomSource random = level.random;
        if (level.getGameTime() % 16L == 0) {
            ItemStack item = catalyzer.augmentInventory.getStackInSlot(0);
            if (item.getItem() instanceof AbstractAugmentItem augmentItem) {
                Vec3 offset = catalyzer.getAugmentOffset().add(
                        Mth.nextFloat(random, -0.1f, 0.1f),
                        Mth.nextFloat(random, -0.1f, 0.1f),
                        Mth.nextFloat(random, -0.1f, 0.1f));
                var augmentSpiritType = augmentItem.spiritType;
                BlockPos blockPos = catalyzer.getBlockPos();
                Vec3 particlePosition = new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
                var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, particlePosition, augmentSpiritType);
                lightSpecs.getBuilder().multiplyLifetime(2.5f).modifyData(WorldParticleBuilder::getScaleData, d -> d.multiplyValue(1.3f));
                lightSpecs.getBloomBuilder().multiplyLifetime(1.5f);
                lightSpecs.spawnParticles();
            }
        }
    }
}
