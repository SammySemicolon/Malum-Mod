package com.sammy.malum.common.effect;

import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import team.lodestar.lodestone.helpers.ColorHelper;

public class SacrificialEmpowermentEffect extends MobEffect {
    public SacrificialEmpowermentEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.WICKED_SPIRIT.getPrimaryColor()));
        addAttributeModifier(AttributeRegistry.SCYTHE_PROFICIENCY.get(), "5149e7f4-8f69-404e-9011-27396058db80", 4f, AttributeModifier.Operation.ADDITION);

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return false;
    }
}