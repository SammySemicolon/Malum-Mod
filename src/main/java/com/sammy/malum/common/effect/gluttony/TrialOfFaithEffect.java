package com.sammy.malum.common.effect.gluttony;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.wayward_attributes.core.registry.WaywardAttributeTypes;

public class TrialOfFaithEffect extends MobEffect {
    public TrialOfFaithEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(88, 86, 60));
        addAttributeModifier(WaywardAttributeTypes.HEALING_MULTIPLIER, MalumMod.malumPath("trial_of_faith"), 0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof Player player) {
            player.causeFoodExhaustion(0.005f * (amplifier + 1));
        }
        return true;
    }

    public static void spawnLocusts(LivingHealEvent event) {
        var entity = event.getEntity();
        var instance = entity.getEffect(MalumMobEffects.TRIAL_OF_FAITH);
        if (instance == null) {
            return;
        }

        float recoveredHealth = Math.min(event.getAmount(), entity.getMaxHealth() - entity.getHealth());
        if (recoveredHealth > 0) {
            int amount = 1 + Mth.floor(recoveredHealth / 2f) + instance.getAmplifier();
            if (GluttonyEffect.spawnLocusts(entity, entity, amount)) {
                entity.removeEffect(MalumMobEffects.TRIAL_OF_FAITH);
            }
        }
    }
}