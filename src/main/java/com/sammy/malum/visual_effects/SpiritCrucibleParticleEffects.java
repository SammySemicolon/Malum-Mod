package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;

import java.util.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.spiritLightSpecs;

public class SpiritCrucibleParticleEffects {

    public static void passiveCrucibleParticles(SpiritCrucibleCoreBlockEntity crucible) {
        MalumSpiritType activeSpiritType = crucible.getActiveSpiritType();
        if (activeSpiritType == null) {
            return;
        }
        Level level = crucible.getLevel();
        Random random = level.random;
        Vec3 itemPos = crucible.getItemPos();
        LodestoneBlockEntityInventory spiritInventory = crucible.spiritInventory;
        SpiritFocusingRecipe recipe = crucible.recipe;
        if (recipe != null && crucible.acceleratorData != null) {
            for (ICrucibleAccelerator accelerator : crucible.acceleratorData.accelerators) {
                if (accelerator != null) {
                    accelerator.addParticles(crucible, activeSpiritType);
                }
            }
        }
        if (recipe != null) {
            var lightSpecs = spiritLightSpecs(level, itemPos, activeSpiritType, LodestoneParticleRegistry.STAR_PARTICLE);
            lightSpecs.getBuilder()
                    .setSpinData(SpinParticleData.create(0).setSpinOffset((level.getGameTime() * 0.05f) % 6.28f).setEasing(Easing.CUBIC_IN, Easing.EXPO_IN).build())
                    .modifyData(WorldParticleBuilder::getScaleData, d -> d.multiplyValue(1.7f))
                    .modifyData(WorldParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.5f));
            lightSpecs.getBloomBuilder()
                    .modifyData(WorldParticleBuilder::getScaleData, d -> d.multiplyValue(1.3f))
                    .modifyData(WorldParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.75f));
            lightSpecs.spawnParticles();
        }

        int spiritsRendered = 0;
        for (int i = 0; i < spiritInventory.slotCount; i++) {
            ItemStack item = spiritInventory.getStackInSlot(i);
            if (item.getItem() instanceof SpiritShardItem spiritSplinterItem) {
                Vec3 offset = crucible.getSpiritItemOffset(spiritsRendered++, 0);
                activeSpiritType = spiritSplinterItem.type;
                BlockPos blockPos = crucible.getBlockPos();
                Vec3 spiritPosition = new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
                if (recipe != null) {
                    Vec3 velocity = itemPos.subtract(spiritPosition).normalize().scale(RandomHelper.randomBetween(random, 0.03f, 0.06f));
                    if (random.nextFloat() < 0.85f) {
                        var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, spiritPosition, activeSpiritType);
                        sparkParticles.getBuilder().setMotion(velocity).modifyData(SparkParticleBuilder::getScaleData, d -> d.multiplyValue(1.2f));
                        sparkParticles.getBloomBuilder().setMotion(velocity);
                        sparkParticles.spawnParticles();
                    }
                    if (random.nextFloat() < 0.85f) {
                        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, spiritPosition, activeSpiritType);
                        lightSpecs.getBuilder().multiplyLifetime(0.8f).setMotion(velocity.scale(1.5f)).modifyData(WorldParticleBuilder::getScaleData, d -> d.multiplyValue(1.6f));
                        lightSpecs.getBloomBuilder().setMotion(velocity);
                        lightSpecs.spawnParticles();
                    }
                }
            }
        }
    }

    public static void spiritCatalyzerParticles(SpiritCatalyzerCoreBlockEntity catalyzer, ICatalyzerAccelerationTarget target, MalumSpiritType spiritType) {
        Level level = catalyzer.getLevel();
        BlockPos catalyzerPos = catalyzer.getBlockPos();
        Vec3 startPos = catalyzer.getItemOffset().add(catalyzerPos.getX(), catalyzerPos.getY(), catalyzerPos.getZ());
        Random random = level.random;
        Vec3 targetPos = target.getAccelerationPoint();
        Vec3 velocity = targetPos.subtract(startPos).normalize().scale(RandomHelper.randomBetween(random, 0.06f, 0.12f));
        if (level.getGameTime() % 2L == 0) {
            Vec3 sparkPos = startPos.add(0.05f - random.nextFloat() * 0.1f, 0.05f - random.nextFloat() * 0.1f, 0.05f - random.nextFloat() * 0.1f);
            var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, sparkPos, spiritType);
            sparkParticles.getBuilder().setMotion(velocity)
                    .modifyData(SparkParticleBuilder::getScaleData, d -> d.multiplyValue(1.5f))
                    .modifyData(SparkParticleBuilder::getLengthData, d -> d.multiplyValue(2f).multiplyCoefficient(0.75f))
                    .modifyColorData(c -> c.multiplyCoefficient(0.8f));
            sparkParticles.getBloomBuilder().setMotion(velocity);
            sparkParticles.spawnParticlesRaw();
        }
    }
}
