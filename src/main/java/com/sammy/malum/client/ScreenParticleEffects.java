package com.sammy.malum.client;

import net.minecraft.client.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.screen.*;
import team.lodestar.lodestone.systems.particle.screen.base.*;

import java.awt.*;
import java.util.*;

import static net.minecraft.util.Mth.nextFloat;

public class ScreenParticleEffects {
    public static void spawnSpiritShardScreenParticles(HashMap<LodestoneScreenParticleRenderType, ArrayList<ScreenParticle>> target, Color color, Color endColor, ItemStack stack, float pXPosition, float pYPosition) {
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
}
