package com.sammy.malum.common.item.curiosities.curios.sets.rotten;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
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

public class CurioVoraciousRing extends MalumCurioItem {

    public CurioVoraciousRing(Properties builder) {
        super(builder, MalumTrinketType.ROTTEN);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("eat_rotten"));
        consumer.accept(positiveEffect("growing_gluttony"));
    }

    public static ItemStack finishEating(LivingEntity livingEntity, ItemStack itemStack, int i, ItemStack stack) {
        if (livingEntity instanceof Player player) {
            if (TrinketsHelper.hasTrinketEquipped(player, ItemRegistry.RING_OF_DESPERATE_VORACITY.get())) {
                if (stack.is(GROSS_FOODS)) {
                    Level level = player.level();
                    MobEffectInstance gluttony = player.getEffect(MobEffectRegistry.GLUTTONY.get());
                    MobEffectInstance hunger = player.getEffect(MobEffects.HUNGER);
                    if (gluttony != null) {
                        EntityHelper.extendEffect(gluttony, player, 300, 600);
                        level.playSound(null, player.blockPosition(), SoundRegistry.HUNGRY_BELT_FEEDS.get(), SoundSource.PLAYERS, 0.75f, RandomHelper.randomBetween(level.random, 0.8f, 1.2f));
                    }
                    if (hunger != null) {
                        EntityHelper.shortenEffect(hunger, player, 150);
                    }
                    player.getFoodData().eat(1, 1f);
                }
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
