package com.sammy.malum.common.effect;

import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.ortus.helpers.ColorHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EarthenAura extends MobEffect {
    public EarthenAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.EARTHEN_SPIRIT.getColor()));
        addAttributeModifier(Attributes.ARMOR, "04448cbf-ee2c-4f36-b71f-e641a312834a", 3f, AttributeModifier.Operation.ADDITION);
        addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "dc5fc5d7-db54-403f-810d-a16de6293ffd", 1.5f, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}