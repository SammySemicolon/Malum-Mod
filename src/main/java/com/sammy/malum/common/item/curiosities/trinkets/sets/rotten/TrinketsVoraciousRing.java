package com.sammy.malum.common.item.curiosities.trinkets.sets.rotten;

import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.helpers.TrinketsHelper;

import java.util.function.Consumer;

import static com.sammy.malum.registry.common.item.ItemTagRegistry.GROSS_FOODS;

public class TrinketsVoraciousRing extends MalumTinketsItem {

    public TrinketsVoraciousRing(Properties builder) {
        super(builder, MalumTrinketType.ROTTEN);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("eat_rotten"));
        consumer.accept(positiveEffect("growing_gluttony"));
    }

    public static ItemStack finishEating(LivingEntity livingEntity, ItemStack itemStack, int i, ItemStack stack) {
        if (TrinketsHelper.hasTrinketEquipped(livingEntity, ItemRegistry.RING_OF_DESPERATE_VORACITY.get())) {
            if (stack.is(GROSS_FOODS)) {
                Level level = livingEntity.level();
                MobEffectInstance gluttony = livingEntity.getEffect(MobEffectRegistry.GLUTTONY.get());
                MobEffectInstance hunger = livingEntity.getEffect(MobEffects.HUNGER);
                if (gluttony != null) {
                    EntityHelper.extendEffect(gluttony, livingEntity, 300, 3000);
                }
                if (hunger != null) {
                    EntityHelper.shortenEffect(hunger, livingEntity, 150);
                }
                if (livingEntity instanceof Player player) {
                    player.getFoodData().eat(1, 1f);
                }
                livingEntity.playSound(SoundRegistry.VORACIOUS_RING_FEEDS.get(), 0.5f, RandomHelper.randomBetween(level.random, 1.2f, 1.6f));

            }
        }
        return itemStack;
    }

    public static int accelerateEating(LivingEntity livingEntity, ItemStack itemStack, int i) {
        if (TrinketsHelper.hasTrinketEquipped(livingEntity, ItemRegistry.RING_OF_DESPERATE_VORACITY.get())) {
            if (itemStack.is(GROSS_FOODS)) {
                return (int) (i * 0.5f);
            }
        }
        return i;
    }
}
