package com.sammy.malum.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import team.lodestar.lodestone.helpers.ColorHelper;

public class GrimCertaintyEffect extends MobEffect {
    public GrimCertaintyEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(235, 207, 249));
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return false;
    }
}
