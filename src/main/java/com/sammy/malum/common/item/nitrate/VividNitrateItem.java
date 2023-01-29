package com.sammy.malum.common.item.nitrate;

import com.sammy.malum.common.entity.nitrate.VividNitrateEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.setup.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.screen.ScreenParticleRenderType;
import team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle;

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
    public void spawnParticles(HashMap<ScreenParticleRenderType, ArrayList<ScreenParticle>> target, Level level, float partialTick, ItemStack stack, float x, float y) {
        float time = (float) (level.getGameTime() + partialTick + Math.sin(((level.getGameTime() + partialTick) * 0.1f)));
        Color firstColor = COLOR_FUNCTION.apply(new VividNitrateEntity.ColorFunctionData(level, 40f, 0, partialTick)).brighter();
        Color secondColor = COLOR_FUNCTION.apply(new VividNitrateEntity.ColorFunctionData(level, 40f, 0.125f, partialTick)).darker();
        ParticleBuilders.create(LodestoneScreenParticleRegistry.STAR, target)
                .setAlpha(0.04f, 0f)
                .setLifetime(6)
                .setScale((float) (1.5f + Math.sin(time * 0.1f) * 0.125f), 0)
                .setColor(firstColor, secondColor)
                .setColorCoefficient(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * time % 6.28f)
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .spawn(x - 1, y + 4)
                .setScale((float) (1.4f - Math.sin(time * 0.075f) * 0.125f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f - 0.01f * time % 6.28f)
                .spawn(x - 1, y + 4);
    }
}