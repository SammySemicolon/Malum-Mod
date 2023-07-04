package com.sammy.malum.common.effect;

import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import team.lodestar.lodestone.helpers.ColorHelper;

public class EarthenAura extends MobEffect {
    public EarthenAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.EARTHEN_SPIRIT.getPrimaryColor()));
        addAttributeModifier(Attributes.ARMOR, "04448cbf-ee2c-4f36-b71f-e641a312834a", 2f, AttributeModifier.Operation.ADDITION);
        addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "dc5fc5d7-db54-403f-810d-a16de6293ffd", 1f, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}