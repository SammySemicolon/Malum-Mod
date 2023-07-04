package com.sammy.malum.common.item.ether;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import team.lodestar.lodestone.setup.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.ScreenParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;
import team.lodestar.lodestone.systems.particle.screen.LodestoneScreenParticleRenderType;
import team.lodestar.lodestone.systems.particle.screen.base.ScreenParticle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EtherItem extends AbstractEtherItem {
    public EtherItem(Block blockIn, Properties builder, boolean iridescent) {
        super(blockIn, builder, iridescent);
    }


    @Override
    public void spawnParticles(HashMap<LodestoneScreenParticleRenderType, ArrayList<ScreenParticle>> target, Level level, float partialTick, ItemStack stack, float x, float y) {
        float time = level.getGameTime() + partialTick;
        AbstractEtherItem etherItem = (AbstractEtherItem) stack.getItem();
        Color firstColor = new Color(etherItem.getFirstColor(stack));
        Color secondColor = new Color(etherItem.getSecondColor(stack));
        float alphaMultiplier = etherItem.iridescent ? 0.75f : 0.5f;
        final int yOffset = etherItem.iridescent ? 3 : 4;
        final int xOffset = etherItem.iridescent ? -1 : 0;
        final SpinParticleData.SpinParticleDataBuilder spinDataBuilder = SpinParticleData.create(0, 1).setSpinOffset(0.025f * time % 6.28f).setEasing(Easing.EXPO_IN_OUT);
        ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.STAR, target)
                .setTransparencyData(GenericParticleData.create(0.09f*alphaMultiplier, 0f).setEasing(Easing.QUINTIC_IN).build())
                .setScaleData(GenericParticleData.create((float) (1.5f + Math.sin(time * 0.1f) * 0.125f), 0).build())
                .setColorData(ColorParticleData.create(firstColor, secondColor).setCoefficient(1.25f).build())
                .setLifetime(6)
                .setRandomOffset(0.05f)
                .setSpinData(spinDataBuilder.build())
                .spawnOnStack(xOffset, yOffset)
                .setScaleData(GenericParticleData.create((float) (1.4f - Math.sin(time * 0.075f) * 0.125f), 0).build())
                .setColorData(ColorParticleData.create(secondColor, firstColor).build())
                .setSpinData(spinDataBuilder.setSpinOffset(0.785f-0.01f * time % 6.28f).build())
                .spawnOnStack(xOffset, yOffset);
    }
}