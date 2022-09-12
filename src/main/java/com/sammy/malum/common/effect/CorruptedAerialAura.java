package com.sammy.malum.common.effect;

import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import team.lodestar.lodestone.helpers.ColorHelper;

public class CorruptedAerialAura extends MobEffect {
    public CorruptedAerialAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.AERIAL_SPIRIT.getColor()));
        addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "e2306a3e-4ffc-45dc-b9c6-30acb18efab3", -0.30f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        MobEffectInstance effectInstance = entity.getEffect(MalumMobEffectRegistry.AETHERS_CHARM.get());
        if (effectInstance != null) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, effectInstance.getAmplifier() * 0.15f, 0));
        }
    }

    public static void onEntityFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        MobEffectInstance effectInstance = entity.getEffect(MalumMobEffectRegistry.AETHERS_CHARM.get());
        if (effectInstance != null) {
            event.setDistance(event.getDistance() / (6 + effectInstance.getAmplifier()));
        }
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return super.getAttributeModifierValue(Math.min(1, amplifier), modifier);
    }
}