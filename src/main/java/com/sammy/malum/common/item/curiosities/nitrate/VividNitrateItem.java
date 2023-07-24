package com.sammy.malum.common.item.curiosities.nitrate;

import com.sammy.malum.common.entity.nitrate.VividNitrateEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.setup.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.ScreenParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;
import team.lodestar.lodestone.systems.particle.screen.*;
import team.lodestar.lodestone.systems.particle.screen.base.ScreenParticle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static com.sammy.malum.common.entity.nitrate.VividNitrateEntity.COLOR_FUNCTION;

public class VividNitrateItem extends AbstractNitrateItem {

    public VividNitrateItem(Properties pProperties) {
        super(pProperties, p -> new VividNitrateEntity(p, p.level));
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        float gameTime = (float) (level.getGameTime() + partialTick + Math.sin(((level.getGameTime() + partialTick) * 0.1f)));
        Color firstColor = COLOR_FUNCTION.apply(new VividNitrateEntity.ColorFunctionData(level, 40f, 0, partialTick)).brighter();
        Color secondColor = COLOR_FUNCTION.apply(new VividNitrateEntity.ColorFunctionData(level, 40f, 0.125f, partialTick)).darker();
        final SpinParticleData.SpinParticleDataBuilder spinParticleData = SpinParticleData.create(0, 1).setCoefficient(0.025f * gameTime % 6.28f).setEasing(Easing.EXPO_IN_OUT);
        ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.STAR, target)
                .setTransparencyData(GenericParticleData.create(0.04f, 0f).setEasing(Easing.QUINTIC_IN).build())
                .setLifetime(6)
                .setScaleData(GenericParticleData.create((float) (1.5f + Math.sin(gameTime * 0.1f) * 0.125f), 0).build())
                .setColorData(ColorParticleData.create(firstColor, secondColor).setCoefficient(1.25f).build())
                .setRandomOffset(0.05f)
                .setSpinData(spinParticleData.build())
                .spawnOnStack(-1, +4)
                .setScaleData(GenericParticleData.create((float) (1.4f - Math.sin(gameTime * 0.075f) * 0.125f), 0).build())
                .setColorData(ColorParticleData.create(secondColor, firstColor).build())
                .setSpinData(spinParticleData.setSpinOffset(0.785f - 0.01f * gameTime % 6.28f).build())
                .spawnOnStack( -1, +4);
    }
}