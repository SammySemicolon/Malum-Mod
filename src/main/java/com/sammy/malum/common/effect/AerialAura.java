package com.sammy.malum.common.effect;

import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import team.lodestar.lodestone.helpers.render.ColorHelper;

public class AerialAura extends MobEffect {
    public AerialAura() {
        super(MobEffectCategory.BENEFICIAL, SpiritTypeRegistry.AERIAL_SPIRIT.getPrimaryColor().getRGB());
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "e3f9c028-d6cc-4cf2-86a6-d5b5efd86be6", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}