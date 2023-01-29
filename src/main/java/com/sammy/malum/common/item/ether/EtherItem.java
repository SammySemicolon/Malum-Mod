package com.sammy.malum.common.item.ether;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import team.lodestar.lodestone.setup.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.screen.ScreenParticleRenderType;
import team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EtherItem extends AbstractEtherItem {
    public EtherItem(Block blockIn, Properties builder, boolean iridescent) {
        super(blockIn, builder, iridescent);
    }


    @Override
    public void spawnParticles(HashMap<ScreenParticleRenderType, ArrayList<ScreenParticle>> target, Level level, float partialTick, ItemStack stack, float x, float y) {
        float time = level.getGameTime() + partialTick;
        AbstractEtherItem etherItem = (AbstractEtherItem) stack.getItem();
        Color firstColor = new Color(etherItem.getFirstColor(stack));
        Color secondColor = new Color(etherItem.getSecondColor(stack));
        float alphaMultiplier = etherItem.iridescent ? 0.75f : 0.5f;
        final int yOffset = etherItem.iridescent ? 3 : 4;
        final int xOffset = etherItem.iridescent ? -1 : 0;
        ParticleBuilders.create(LodestoneScreenParticleRegistry.STAR, target)
                .setAlpha(0.09f*alphaMultiplier, 0f)
                .setLifetime(6)
                .setScale((float) (1.5f + Math.sin(time * 0.1f) * 0.125f), 0)
                .setColor(firstColor, secondColor)
                .setColorCoefficient(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * time % 6.28f)
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .spawn(x+xOffset, y+yOffset)
                .setScale((float) (1.4f - Math.sin(time * 0.075f) * 0.125f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f-0.01f * time % 6.28f)
                .spawn(x+xOffset, y+yOffset);
    }
}