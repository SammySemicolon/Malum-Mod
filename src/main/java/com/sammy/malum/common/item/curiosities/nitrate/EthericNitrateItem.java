package com.sammy.malum.common.item.curiosities.nitrate;

import com.sammy.malum.common.entity.nitrate.EthericNitrateEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.render.ColorHelper;
import team.lodestar.lodestone.registry.common.particle.LodestoneScreenParticleRegistry;
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

public class EthericNitrateItem extends AbstractNitrateItem {

    public EthericNitrateItem(Properties pProperties) {
        super(pProperties, p -> new EthericNitrateEntity(p, p.level));
    }

    @Override
    public void spawnParticles(HashMap<LodestoneScreenParticleRenderType, ArrayList<ScreenParticle>> target, Level level, float partialTick, ItemStack stack, float x, float y) {
        float gameTime = (float) (level.getGameTime() + partialTick + Math.sin(((level.getGameTime() + partialTick) * 0.1f)));
        Color firstColor = ColorHelper.brighter(EthericNitrateEntity.FIRST_COLOR, 2);
        Color secondColor = EthericNitrateEntity.SECOND_COLOR;
        double scale = 1.5f + Math.sin(gameTime * 0.1f) * 0.125f + Math.sin((gameTime - 100) * 0.05f) * -0.5f;
        final SpinParticleData.SpinParticleDataBuilder spinParticleData = SpinParticleData.create(0, 1).setCoefficient(0.025f * gameTime % 6.28f).setEasing(Easing.EXPO_IN_OUT);
        ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.STAR, target)
                .setTransparencyData(GenericParticleData.create(0.04f, 0f).setEasing(Easing.QUINTIC_IN).build())
                .setLifetime(7)
                .setScaleData(GenericParticleData.create((float) scale, 0).build())
                .setColorData(ColorParticleData.create(firstColor, secondColor).setCoefficient(1.25f).build())
                .setRandomOffset(0.05f)
                .setSpinData(spinParticleData.build())
                .spawnOnStack(-1, +4)
                .setScaleData(GenericParticleData.create((float) (1.4f - Math.sin(gameTime * 0.075f) * 0.125f), 0).build())
                .setColorData(ColorParticleData.create(secondColor, firstColor).build())
                .setSpinData(spinParticleData.setSpinOffset(0.785f - 0.01f * gameTime % 6.28f).build())
                .spawnOnStack(-1, +4);
    }
}