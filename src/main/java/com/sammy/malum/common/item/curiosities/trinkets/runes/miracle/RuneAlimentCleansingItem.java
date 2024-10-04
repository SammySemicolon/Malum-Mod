package com.sammy.malum.common.item.curiosities.trinkets.runes.miracle;

import com.sammy.malum.common.item.curiosities.trinkets.runes.AbstractRuneTrinketsItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.helpers.TrinketsHelper;

import java.util.function.Consumer;

public class RuneAlimentCleansingItem extends AbstractRuneTrinketsItem {

    public RuneAlimentCleansingItem(Properties builder) {
        super(builder, SpiritTypeRegistry.AQUEOUS_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("shorten_negative_effect"));
    }

    public static boolean onPotionApplied(LivingEntity livingEntity, MobEffectInstance mobEffectInstance, MobEffectInstance mobEffectInstance1, Entity entity) {
        if (mobEffectInstance == null && TrinketsHelper.hasTrinketEquipped(livingEntity, ItemRegistry.RUNE_OF_ALIMENT_CLEANSING.get())) {
            MobEffectInstance effect = mobEffectInstance1;
            MobEffect type = effect.getEffect();
            if (type.getCategory().equals(MobEffectCategory.HARMFUL) && entity instanceof LivingEntity livingEntity1) {
                EntityHelper.shortenEffect(effect, livingEntity1, effect.getDuration() / 4);
            }
        }
        return true;
    }
}
