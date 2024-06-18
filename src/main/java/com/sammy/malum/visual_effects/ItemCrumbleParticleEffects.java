package com.sammy.malum.visual_effects;

import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.ParticleEffectSpawner;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.BedrockDirectionalBehaviorComponent;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.LodestoneBehaviorComponent;
import team.lodestar.lodestone.systems.particle.world.options.LodestoneItemCrumbsParticleOptions;

import java.util.function.Consumer;

import static net.minecraft.util.Mth.nextFloat;

public class ItemCrumbleParticleEffects {

    public static ParticleEffectSpawner spawnItemCrumbs(Level level, Vec3 pos, ItemStack stack) {
        var rand = level.getRandom();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.5f, 0.75f), 0).setCoefficient(0.6f).randomSpinOffset(rand).build();
        final Consumer<LodestoneWorldParticle> slowDown = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.925f));
        int lifetime = RandomHelper.randomBetween(rand, 30, 40);
        var crumbs = makeCrumbs(rand, stack, spinData, lifetime, slowDown);
        return new ParticleEffectSpawner(level, pos, crumbs);
    }

    public static ParticleEffectSpawner spawnGlowingItemCrumbs(Level level, Vec3 pos, ItemStack stack, MalumSpiritType spiritType) {
        var rand = level.getRandom();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, 0, nextFloat(rand, 0.5f, 0.75f), 0).setCoefficient(0.6f).randomSpinOffset(rand).build();
        final Consumer<LodestoneWorldParticle> slowDown = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.925f));
        int lifetime = RandomHelper.randomBetween(rand, 30, 40);
        var crumbs = makeCrumbs(rand, stack, spinData, lifetime, slowDown);
        var bloom = SpiritLightSpecs.spiritBloom(level, spiritType, lifetime).setSpinData(spinData).addTickActor(slowDown);
        return new ParticleEffectSpawner(level, pos, crumbs, bloom);
    }

    public static WorldParticleBuilder makeCrumbs(RandomSource rand, ItemStack stack, SpinParticleData spinData, int lifetime, Consumer<LodestoneWorldParticle> slowDown) {
        var options = new LodestoneItemCrumbsParticleOptions(LodestoneParticleRegistry.ITEM_PARTICLE.get(), stack);
        return WorldParticleBuilder.create(options)
                .setSpinData(spinData)
                .setTransparencyData(GenericParticleData.create(0.5f, 1f, 0.25f).setEasing(Easing.EXPO_IN, Easing.SINE_IN_OUT).build())
                .setScaleData(GenericParticleData.create(0.05f, RandomHelper.randomBetween(rand, 0.06f, 0.07f), 0f).build())
                .setLifetime(lifetime)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .setRenderType(ParticleRenderType.TERRAIN_SHEET)
                .addTickActor(slowDown);
    }

    public static LodestoneBehaviorComponent makeCrumbParticleBehavior(Level level) {
        return level.getRandom().nextBoolean() ? new BedrockDirectionalBehaviorComponent() : null;
    }
}
