package com.sammy.malum.common.effect;

import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.ortus.helpers.ColorHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class InfernalAura extends MobEffect {
    public InfernalAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.INFERNAL_SPIRIT.getColor()));
        addAttributeModifier(Attributes.ATTACK_SPEED, "0a74b987-a6ec-4b9f-815e-a589bf435b93", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}