package com.sammy.malum.common.effect;

import com.sammy.malum.core.helper.ColorHelper;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.setup.content.potion.EffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent;

public class CorruptedAerialAura extends MobEffect
{
    public CorruptedAerialAura()
    {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getDecimal(SpiritTypeRegistry.AERIAL_SPIRIT_COLOR));
        addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "e2306a3e-4ffc-45dc-b9c6-30acb18efab3", -0.40f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        MobEffectInstance effectInstance = entity.getEffect(EffectRegistry.CORRUPTED_AERIAL_AURA.get());
        if (effectInstance != null)
        {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, effectInstance.getAmplifier()*0.1f, 0));
        }
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