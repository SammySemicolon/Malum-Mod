package com.sammy.malum.common.effect;

import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.ortus.helpers.ColorHelper;
import com.sammy.ortus.setup.OrtusAttributeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class StarvationEffect extends MobEffect {
    public StarvationEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(88, 86, 60));
        addAttributeModifier(AttributeRegistry.SCYTHE_PROFICIENCY.get(), "4d82fd0a-24b6-45f5-8d7a-983f99fd6783", 2f, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}