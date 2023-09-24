package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.obelisk.*;
import com.sammy.malum.common.block.curiosities.spirit_altar.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.world.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.spiritLightSpecs;

public class SpiritAltarParticleEffects {

    public static MalumSpiritType getCentralSpiritType(SpiritAltarBlockEntity altar) {
        final LodestoneBlockEntityInventory spiritInventory = altar.spiritInventory;
        int spiritCount = spiritInventory.nonEmptyItemAmount;
        Item currentItem = spiritInventory.getStackInSlot(0).getItem();
        if (spiritCount > 1) {
            float duration = 30f * spiritCount;
            float gameTime = (altar.getLevel().getGameTime() % duration) / 30f;
            currentItem = spiritInventory.getStackInSlot(Mth.floor(gameTime)).getItem();
        }
        if (!(currentItem instanceof SpiritShardItem spiritItem)) {
            return null;
        }
        return spiritItem.type;
    }

    public static void passiveSpiritAltarParticles(SpiritAltarBlockEntity altar) {
        MalumSpiritType activeSpiritType = getCentralSpiritType(altar);
        if (activeSpiritType == null) {
            return;
        }
        Level level = altar.getLevel();
        Random random = level.random;
        Vec3 itemPos = SpiritAltarBlockEntity.getItemPos(altar);
        LodestoneBlockEntityInventory spiritInventory = altar.spiritInventory;
        SpiritInfusionRecipe recipe = altar.recipe;
        if (recipe != null) {
            for (IAltarAccelerator accelerator : altar.accelerators) {
                if (accelerator != null) {
                    accelerator.addParticles(altar, activeSpiritType);
                }
            }
            Color color = activeSpiritType.getPrimaryColor();
            Color endColor = activeSpiritType.getSecondaryColor();
            SpiritLightSpecs.rotatingLightSpecs(level, itemPos, color, endColor, 0.5f, 3, b -> b.multiplyLifetime(1.2f).modifyData(b::getScaleData, d -> d.multiplyValue(1.2f)));
        }

        int spiritsRendered = 0;
        for (int i = 0; i < spiritInventory.slotCount; i++) {
            ItemStack item = spiritInventory.getStackInSlot(i);
            if (item.getItem() instanceof SpiritShardItem spiritSplinterItem) {
                Vec3 offset = altar.getSpiritItemOffset(spiritsRendered++, 0);
                Color color = spiritSplinterItem.type.getPrimaryColor();
                Color endColor = spiritSplinterItem.type.getSecondaryColor();
                BlockPos blockPos = altar.getBlockPos();
                Vec3 spiritPosition = new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
                spiritLightSpecs(level, spiritPosition, color, endColor);
                if (recipe != null) {
                    Vec3 velocity = itemPos.subtract(spiritPosition).normalize().scale(RandomHelper.randomBetween(random, 0.03f, 0.06f));
                    if (random.nextFloat() < 0.85f) {
                        SpiritLightSpecs.spiritMotionSparks(level, spiritPosition, color, endColor, b -> b.setMotion(velocity), b -> b.setMotion(velocity));
                    }
                    if (random.nextFloat() < 0.85f) {
                        spiritLightSpecs(level, spiritPosition, color, endColor, b -> b.multiplyLifetime(0.8f).modifyData(b::getScaleData, d -> d.multiplyValue(1.4f)).setMotion(velocity.scale(1.5f)));
                    }
                }
            }
        }
    }

    public static void craftItemParticles(SpiritAltarBlockEntity altar) {
        MalumSpiritType activeSpiritType = getCentralSpiritType(altar);
        if (activeSpiritType == null) {
            return;
        }
        Level level = altar.getLevel();
        Color color = activeSpiritType.getPrimaryColor();
        Color endColor = activeSpiritType.getSecondaryColor();
        long gameTime = level.getGameTime();
        BlockPos altarPos = altar.getBlockPos();
        Vec3 targetPos = altar.getCentralItemOffset().add(altarPos.getX(),altarPos.getY(), altarPos.getZ());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                int finalI = i + 2;
                Vec3 offsetPosition = DataHelper.rotatingRadialOffset(targetPos, 0.65f, i, 8, gameTime, 160);
                int finalJ = j;
                Consumer<WorldParticleBuilder> behavior = b -> b.addActor(p -> {
                    if (level.getGameTime() > gameTime + finalI * 4 && level.getGameTime() < gameTime + finalI * 8) {
                        p.setParticleSpeed(p.getParticleSpeed().add(0, 0.01f * (1- finalJ *0.4f), 0));
                    }
                });
                spiritLightSpecs(level, offsetPosition, color, endColor,
                        b -> b
                                .act(behavior)
                                .setColorData(b.getColorData().copy().setCoefficient(0.35f).build())
                                .modifyData(b::getScaleData, d -> d.multiplyValue(3f).multiplyCoefficient(0.9f))
                                .modifyData(b::getTransparencyData, d -> d.multiplyCoefficient(0.9f))
                                .multiplyLifetime(1.5f)
                                .setLifetime(b.getParticleOptions().lifetimeSupplier.get() + finalI * 2),
                        b -> b
                                .act(behavior)
                                .setColorData(b.getColorData().copy().setCoefficient(0.35f).build())
                                .modifyData(b::getScaleData, d -> d.multiplyValue(1.2f).multiplyCoefficient(0.9f))
                                .modifyData(b::getTransparencyData, d -> d.multiplyCoefficient(0.9f))
                                .setLifetime(b.getParticleOptions().lifetimeSupplier.get() + finalI * 2)
                );
            }
        }
    }
    public static void runewoodObeliskParticles(RunewoodObeliskBlockEntity obelisk, SpiritAltarBlockEntity altar, MalumSpiritType spiritType) {
        Level level = obelisk.getLevel();
        Color color = spiritType.getPrimaryColor();
        Color endColor = spiritType.getSecondaryColor();
        BlockPos obeliskPos = obelisk.getBlockPos();
        Vec3 startPos = obelisk.getParticleOffset().add(obeliskPos.getX(), obeliskPos.getY(), obeliskPos.getZ());
        spiritLightSpecs(level, startPos, color, endColor);
        if (level.getGameTime() % 2L == 0) {
            Random random = level.random;
            long gameTime = level.getGameTime();
            BlockPos altarPos = altar.getBlockPos();
            Vec3 targetPos = altar.getCentralItemOffset().add(altarPos.getX(),altarPos.getY(), altarPos.getZ());
            Vec3 velocity = targetPos.subtract(startPos).normalize().scale(RandomHelper.randomBetween(random, 0.01f, 0.02f));
            double yOffset = Math.sin((gameTime % 360) / 30f) * 0.1f;
            Vec3 offsetPosition = DataHelper.rotatingRadialOffset(startPos.add(0, yOffset, 0), 0.45f, 0, 1, gameTime, 30);
            Consumer<WorldParticleBuilder> behavior = b -> b.addActor(p -> {
                if (gameTime % 6L == 0) {
                    p.setParticleSpeed(p.getParticleSpeed().scale(1.05f));
                }
            });
            spiritLightSpecs(level, offsetPosition, color, endColor,
                    b -> b
                            .act(behavior)
                            .setMotion(velocity)
                            .multiplyLifetime(2f)
                            .modifyData(b::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 1f, 2f))),
                    b -> b
                            .act(behavior)
                            .setMotion(velocity)
                            .multiplyLifetime(1.5f)
                            .modifyData(b::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 0.6f, 1.5f)))
            );

        }
    }
}