package com.sammy.malum.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import team.lodestar.lodestone.helpers.ColorHelper;

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