package com.sammy.malum.common.effect;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class GrowingFleshEffect extends MobEffect {
    public GrowingFleshEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(126, 25, 95)));
        addAttributeModifier(Attributes.MAX_HEALTH, "04448cbf-ee2c-4f36-b71f-e641a312834a", 0.05f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}