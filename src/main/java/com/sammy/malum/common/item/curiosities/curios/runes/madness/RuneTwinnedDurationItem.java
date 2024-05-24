package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.sammy.malum.common.item.curiosities.curios.runes.AbstractRuneCurioItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.helpers.TrinketsHelper;

import java.util.function.Consumer;

public class RuneTwinnedDurationItem extends AbstractRuneCurioItem {

    public RuneTwinnedDurationItem(Properties builder) {
        super(builder, SpiritTypeRegistry.AQUEOUS_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("extend_positive_effect"));
    }

    public static boolean onPotionApplied(LivingEntity livingEntity, MobEffectInstance mobEffectInstance, MobEffectInstance mobEffectInstance1, Entity entity) {
        if (mobEffectInstance == null && TrinketsHelper.hasCurioEquipped(livingEntity, ItemRegistry.RUNE_OF_TWINNED_DURATION.get())) {
            MobEffectInstance effect = mobEffectInstance1;
            MobEffect type = effect.getEffect();
            float multiplier = MobEffectRegistry.ALCHEMICAL_PROFICIENCY_MAP.getOrDefault(BuiltInRegistries.MOB_EFFECT.getKey(type), 1f);
            if (type.isBeneficial()) {
                EntityHelper.extendEffect(effect, livingEntity, (int) (effect.getDuration() * multiplier));
            }
        }
        return true;
    }
}
