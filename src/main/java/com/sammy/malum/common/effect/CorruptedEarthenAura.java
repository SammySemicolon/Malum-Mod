package com.sammy.malum.common.effect;

import com.sammy.malum.core.helper.ColorHelper;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class CorruptedEarthenAura extends MobEffect
{
    public CorruptedEarthenAura()
    {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getDecimal(SpiritTypeRegistry.EARTHEN_SPIRIT_COLOR));
        addAttributeModifier(Attributes.ATTACK_DAMAGE, "e2a25284-a8b1-41a5-9472-90cc83793d44", 2, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }
}