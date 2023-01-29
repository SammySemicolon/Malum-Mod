package com.sammy.malum.common.item.nitrate;

import com.sammy.malum.common.entity.nitrate.EthericNitrateEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.setup.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.screen.ScreenParticleRenderType;
import team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EthericNitrateItem extends AbstractNitrateItem {

    public EthericNitrateItem(Properties pProperties) {
        super(pProperties, p -> new EthericNitrateEntity(p, p.level));
    }

    @Override
    public void spawnParticles(HashMap<ScreenParticleRenderType, ArrayList<ScreenParticle>> target, Level level, float partialTick, ItemStack stack, float x, float y) {
        float gameTime = (float) (level.getGameTime() + partialTick + Math.sin(((level.getGameTime() + partialTick) * 0.1f)));
        Color firstColor = ColorHelper.brighter(EthericNitrateEntity.FIRST_COLOR, 2);
        Color secondColor = EthericNitrateEntity.SECOND_COLOR;
        double scale = 1.5f + Math.sin(gameTime * 0.1f) * 0.125f + Math.sin((gameTime - 100) * 0.05f) * -0.5f;
        ParticleBuilders.create(LodestoneScreenParticleRegistry.STAR, target)
                .setAlpha(0.04f, 0f)
                .setLifetime(7)
                .setScale((float) scale, 0)
                .setColor(firstColor, secondColor)
                .setColorCoefficient(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * gameTime % 6.28f)
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .spawn(x-1, y+4)
                .setScale((float) (1.4f - Math.sin(gameTime * 0.075f) * 0.125f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f - 0.01f * gameTime % 6.28f)
                .spawn(x-1, y+4);
    }
}