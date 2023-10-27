package com.sammy.malum.visual_effects;

import com.sammy.malum.common.item.ether.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.screen.*;
import team.lodestar.lodestone.systems.particle.screen.base.*;

import java.awt.*;
import java.util.*;
import java.util.logging.*;

import static net.minecraft.util.Mth.nextFloat;

public class ScreenParticleEffects {
    public static void spawnSpiritShardScreenParticles(ScreenParticleHolder target, Color color, Color endColor) {
        var rand = Minecraft.getInstance().level.getRandom();
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

    public static void spawnVoidItemScreenParticles(ScreenParticleHolder target, Level level, float intensity, float partialTick) {
        float colorMultiplier = Mth.nextFloat(level.random, 0.4f, 1.2f);
        float timeMultiplier = Mth.nextFloat(level.random, 0.9f, 1.4f);
        Color color = new Color((int)(31*colorMultiplier), (int)(19*colorMultiplier), (int)(31*colorMultiplier));
        Color endColor = new Color((int)(111*colorMultiplier), (int)(31*colorMultiplier), (int)(121*colorMultiplier));
        float gameTime = level.getGameTime() + partialTick;
        var rand = Minecraft.getInstance().level.getRandom();
        boolean spinDirection = level.random.nextBoolean();
        SpinParticleData spinParticleData = SpinParticleData.create(0, spinDirection ? 1 : -2).setSpinOffset(0.025f * gameTime % 6.28f).setEasing(Easing.EXPO_IN_OUT).build();
        ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.STAR, target)
                .setScaleData(GenericParticleData.create(1.2f * intensity + rand.nextFloat() * 0.1f * intensity, 0).setEasing(Easing.SINE_IN_OUT, Easing.BOUNCE_IN_OUT).build())
                .setTransparencyData(GenericParticleData.create(0.1f, 0.5f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                .setColorData(ColorParticleData.create(color, endColor).setCoefficient(2f).build())
                .setSpinData(spinParticleData)
                .setLifetime((int) ((10 + rand.nextInt(10))*timeMultiplier))
                .setRandomOffset(0.05f)
                .setRandomMotion(0.05f, 0.05f)
                .setRenderType(LodestoneScreenParticleRenderType.LUMITRANSPARENT)
                .spawnOnStack(0, 0);

        ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.WISP, target)
                .setScaleData(GenericParticleData.create(0.8f * intensity + rand.nextFloat() * 0.6f * intensity, 0).setEasing(Easing.EXPO_OUT).build())
                .setTransparencyData(GenericParticleData.create(0.1f, 0.15f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                .setColorData(ColorParticleData.create(color, endColor.darker()).setCoefficient(1.25f).build())
                .setSpinData(spinParticleData)
                .setLifetime(20 + rand.nextInt(8))
                .setRandomOffset(0.1f)
                .setRandomMotion(0.4f, 0.4f)
                .setRenderType(LodestoneScreenParticleRenderType.LUMITRANSPARENT)
                .spawnOnStack(0, 0)
                .setLifetime((int) ((10 + rand.nextInt(2))*timeMultiplier))
                .setSpinData(SpinParticleData.create(nextFloat(rand, 0.05f, 0.1f)).build())
                .setScaleData(GenericParticleData.create(0.8f + rand.nextFloat() * 0.4f, 0f).build())
                .setRandomMotion(0.01f, 0.01f)
                .spawnOnStack(0, 0);
    }
}
