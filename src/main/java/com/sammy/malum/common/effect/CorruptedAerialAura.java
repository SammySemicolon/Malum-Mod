package com.sammy.malum.common.effect;

import com.sammy.malum.core.helper.ClientHelper;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.common.ForgeMod;

public class CorruptedAerialAura extends MobEffect
{
    public CorruptedAerialAura()
    {
        super(MobEffectCategory.BENEFICIAL, ClientHelper.getColor(SpiritTypeRegistry.AERIAL_SPIRIT_COLOR));
        addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "e2306a3e-4ffc-45dc-b9c6-30acb18efab3", -0.40f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier)
    {
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return super.getAttributeModifierValue(Math.min(1, amplifier), modifier);
    }
}