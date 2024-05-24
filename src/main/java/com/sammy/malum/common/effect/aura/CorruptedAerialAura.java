package com.sammy.malum.common.effect.aura;

import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import team.lodestar.lodestone.helpers.ColorHelper;

public class CorruptedAerialAura extends MobEffect {
    public CorruptedAerialAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.AERIAL_SPIRIT.getPrimaryColor()));
        addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "e2306a3e-4ffc-45dc-b9c6-30acb18efab3", -0.30f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static void onEntityJump(LivingEntity livingEntity) {
        MobEffectInstance effectInstance = livingEntity.getEffect(MobEffectRegistry.AETHERS_CHARM.get());
        if (effectInstance != null) {
            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0, 0.1f + effectInstance.getAmplifier() * 0.05f, 0));
        }
    }

    public static void onEntityFall(LivingEntityEvents.Fall.FallEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            MobEffectInstance effectInstance = livingEntity.getEffect(MobEffectRegistry.AETHERS_CHARM.get());
            if (effectInstance != null) {
                event.setDistance(event.getDistance() / (6 + effectInstance.getAmplifier()));
            }
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