package com.sammy.malum.common.effect;

import com.sammy.malum.registry.common.MobEffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;

public class GluttonyEffect extends MobEffect {
    public GluttonyEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(88, 86, 60));
        addAttributeModifier(LodestoneAttributeRegistry.MAGIC_PROFICIENCY.get(), "4d82fd0a-24b6-45f5-8d7a-983f99fd6783", 2f, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof Player player) {
            player.causeFoodExhaustion(0.004f * (amplifier + 1));
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    public static boolean canApplyPotion(LivingEntity livingEntity, MobEffectInstance mobEffectInstance) {
        return !mobEffectInstance.getEffect().equals(MobEffects.HUNGER) || !livingEntity.hasEffect(MobEffectRegistry.GLUTTONY.get());
    }
}