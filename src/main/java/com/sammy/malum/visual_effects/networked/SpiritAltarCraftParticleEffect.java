package com.sammy.malum.visual_effects.networked;

import com.sammy.malum.core.systems.particle_effects.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;

import java.awt.*;
import java.util.function.*;

public class SpiritAltarCraftParticleEffect extends ParticleEffectType {

    public SpiritAltarCraftParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData) -> {
            double posX = positionData.posX;
            double posY = positionData.posY;
            double posZ = positionData.posZ;

            for (int i = 0; i < 8; i++) {
                final ColorEffectData.ColorRecord cyclingColorRecord = colorData.getCyclingColorRecord();
                Color primaryColor = colorData.getPrimaryColor(cyclingColorRecord);
                Color secondaryColor = colorData.getSecondaryColor(cyclingColorRecord);

                float spinStrength = 0.6f + random.nextFloat() * 0.2f;
                int spinDirection = (random.nextBoolean() ? 1 : -1);
                int spinOffset = random.nextInt(360);
                int lifetime = (int) (35 * Mth.nextFloat(random, 0.9f, 1.8f));
                WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.5f, 0.9f, 0).build())
                        .setSpinData(SpinParticleData.create(spinStrength * spinDirection, 0).setSpinOffset(spinOffset).setSpinOffset(1.25f).setEasing(Easing.CUBIC_IN).setCoefficient(1.5f).build())
                        .setScaleData(GenericParticleData.create(0.075f, 0.15f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT).build())
                        .setColorData(ColorParticleData.create(ColorHelper.brighter(primaryColor, 2), secondaryColor).setCoefficient(Mth.nextFloat(random, 0.6f, 1.1f)).build())
                        .setLifetime(lifetime)
                        .setRandomOffset(0.2f)
                        .enableNoClip()
                        .setGravityStrength(1.1f)
                        .addMotion(0, 0.25f + random.nextFloat() * 0.1f, 0)
                        .disableNoClip()
                        .setRandomMotion(0.1f, 0.12f)
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .repeat(level, posX, posY, posZ, 4);
            }
            final ColorEffectData.ColorRecord randomColorRecord = colorData.getRandomColorRecord();
            Color primaryColor = colorData.getPrimaryColor(randomColorRecord);
            Color secondaryColor = colorData.getSecondaryColor(randomColorRecord);
            for (int i = 0; i < 6; i++) {
                int spinDirection = (random.nextBoolean() ? 1 : -1);
                int spinOffset = random.nextInt(360);
                int lifetime = (int) (30 * Mth.nextFloat(random, 0.9f, 1.8f));
                WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.05f, 0.125f, 0.075f).setEasing(Easing.SINE_IN, Easing.SINE_IN).build())
                        .setSpinData(SpinParticleData.create((0.125f + random.nextFloat() * 0.075f) * spinDirection).setSpinOffset(spinOffset).build())
                        .setScaleData(GenericParticleData.create(0.4f, 0.5f, 0).setEasing(Easing.SINE_OUT, Easing.SINE_IN_OUT).setCoefficient(0.8f).build())
                        .setColorData(ColorParticleData.create(primaryColor.brighter(), secondaryColor.darker()).setCoefficient(0.85f).build())
                        .setLifetime(lifetime)
                        .setRandomOffset(0.4f)
                        .enableNoClip()
                        .setRandomMotion(0.01f, 0.01f)
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .repeat(level, posX, posY, posZ, 4);
            }
        };
    }
}