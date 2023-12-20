package com.sammy.malum.visual_effects;

import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.LodestoneWorldParticleActor;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.ItemCrumbParticleBuilder;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.util.function.Consumer;

import static net.minecraft.util.Mth.nextFloat;

public class ItemCrumbleParticleEffects {

    public static ParticleEffectSpawner<ItemCrumbParticleBuilder> spawnItemCrumbs(Level level, Vec3 pos, ItemStack stack) {
        var rand = level.getRandom();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, 0, nextFloat(rand, 0.5f, 0.75f), 0).setCoefficient(0.75f).randomSpinOffset(rand).build();
        final Consumer<LodestoneWorldParticleActor> slowDown = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.925f));
        int lifetime = RandomHelper.randomBetween(rand, 30, 40);
        final ItemCrumbParticleBuilder worldParticleBuilder = makeCrumbles(rand, stack, spinData, lifetime, slowDown);
        return new ParticleEffectSpawner<>(level, pos, worldParticleBuilder);
    }

    public static ParticleEffectSpawner<ItemCrumbParticleBuilder> spawnGlowingItemCrumbs(Level level, Vec3 pos, ItemStack stack, MalumSpiritType spiritType) {
        var rand = level.getRandom();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, 0, nextFloat(rand, 0.5f, 0.75f), 0).setCoefficient(0.75f).randomSpinOffset(rand).build();
        final Consumer<LodestoneWorldParticleActor> slowDown = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.925f));
        int lifetime = RandomHelper.randomBetween(rand, 30, 40);
        final ItemCrumbParticleBuilder worldParticleBuilder = makeCrumbles(rand, stack, spinData, lifetime, slowDown);
        final WorldParticleBuilder bloomParticleBuilder = SpiritLightSpecs.spiritBloom(level, spiritType, spinData, lifetime).addTickActor(slowDown);
        return new ParticleEffectSpawner<>(level, pos, worldParticleBuilder, bloomParticleBuilder);
    }

    public static ItemCrumbParticleBuilder makeCrumbles(RandomSource rand, ItemStack stack, SpinParticleData spinData, int lifetime, Consumer<LodestoneWorldParticleActor> slowDown) {
        return ItemCrumbParticleBuilder.create(LodestoneParticleRegistry.ITEM_PARTICLE, stack)
                .setSpinData(spinData)
                .setTransparencyData(GenericParticleData.create(0.5f, 1f, 0.25f).setEasing(Easing.EXPO_IN, Easing.SINE_IN_OUT).build())
                .setScaleData(GenericParticleData.create(0.05f, RandomHelper.randomBetween(rand, 0.06f, 0.07f), 0.025f).build())
                .setLifetime(lifetime)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .setRenderType(ParticleRenderType.TERRAIN_SHEET)
                .addTickActor(slowDown);
    }
}
