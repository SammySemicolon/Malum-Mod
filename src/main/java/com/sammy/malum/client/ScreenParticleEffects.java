package com.sammy.malum.client;

import com.sammy.malum.common.item.ether.*;
import net.minecraft.client.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.screen.*;
import team.lodestar.lodestone.systems.particle.screen.base.*;

import java.awt.*;
import java.util.*;
import java.util.logging.*;

import static net.minecraft.util.Mth.nextFloat;

public class ScreenParticleEffects {
    public static void spawnSpiritShardScreenParticles(ScreenParticleHolder target, Color color, Color endColor) {
        Random rand = Minecraft.getInstance().level.getRandom();
        ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.SPARKLE, target)
                .setTransparencyData(GenericParticleData.create(0.04f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                .setScaleData(GenericParticleData.create(0.8f + rand.nextFloat() * 0.1f, 0).setEasing(Easing.SINE_IN_OUT, Easing.BOUNCE_IN_OUT).build())
                .setColorData(ColorParticleData.create(color, endColor).setCoefficient(2f).build())
                .setLifetime(10 + rand.nextInt(10))
                .setRandomOffset(0.05f)
                .setRandomMotion(0.05f, 0.05f)
                .spawnOnStack(0, 0);

        ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.WISP, target)
                .setTransparencyData(GenericParticleData.create(0.02f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                .setSpinData(SpinParticleData.create(nextFloat(rand, 0.2f, 0.4f)).setEasing(Easing.EXPO_OUT).build())
                .setScaleData(GenericParticleData.create(0.6f + rand.nextFloat() * 0.4f, 0).setEasing(Easing.EXPO_OUT).build())
                .setColorData(ColorParticleData.create(color, endColor).setCoefficient(1.25f).build())
                .setLifetime(20 + rand.nextInt(8))
                .setRandomOffset(0.1f)
                .setRandomMotion(0.4f, 0.4f)
                .spawnOnStack(0, 0)
                .setLifetime(10 + rand.nextInt(2))
                .setSpinData(SpinParticleData.create(nextFloat(rand, 0.05f, 0.1f)).build())
                .setScaleData(GenericParticleData.create(0.8f + rand.nextFloat() * 0.4f, 0f).build())
                .setRandomMotion(0.01f, 0.01f)
                .spawnOnStack(0, 0);
    }

    public static void spawnVoidItemScreenParticles(ScreenParticleHolder target, Level level, float partialTick) {
        float gameTime = level.getGameTime() + partialTick;
        final SpinParticleData.SpinParticleDataBuilder spinDataBuilder = SpinParticleData.create(0, 1).setSpinOffset(0.025f * gameTime % 6.28f).setEasing(Easing.EXPO_IN_OUT);
        ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.SMOKE, target)
                .setTransparencyData(GenericParticleData.create(0.11f, 0f).setEasing(Easing.QUINTIC_IN).build())
                .setScaleData(GenericParticleData.create((float) (0.75f + Math.sin(gameTime * 0.05f) * 0.125f), 0).build())
                .setColorData(ColorParticleData.create(Color.BLACK, Color.BLACK).setCoefficient(1.25f).build())
                .setSpinData(spinDataBuilder.build())
                .setLifetime(7)
                .setRandomOffset(0.05f)
                .spawnOnStack(0, -1)
                .setScaleData(GenericParticleData.create((float) (0.75f - Math.sin(gameTime * 0.075f) * 0.125f), 0).build())
                .setColorData(ColorParticleData.create(Color.BLACK, Color.BLACK).build())
                .setSpinData(spinDataBuilder.setSpinOffset(0.785f - 0.01f * gameTime % 6.28f).build())
                .spawnOnStack(0, -1);
    }
}
