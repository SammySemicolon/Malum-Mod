package com.sammy.malum.common.item.curiosities.trinkets.sets.weeping;

import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.helpers.TrinketsHelper;

import java.util.function.Consumer;

import static com.sammy.malum.registry.common.item.ItemTagRegistry.GROSS_FOODS;

public class TrinketsGruesomeConcentrationRing extends MalumTinketsItem implements IVoidItem {
    public TrinketsGruesomeConcentrationRing(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("rotten_gluttony"));
    }

    public static ItemStack finishEating(LivingEntity livingEntity, ItemStack itemStack, int i, ItemStack stack) {
        if (TrinketsHelper.hasTrinketEquipped(livingEntity, ItemRegistry.RING_OF_GRUESOME_CONCENTRATION.get())) {
            if (stack.is(GROSS_FOODS)) {
                MobEffect gluttony = MobEffectRegistry.GLUTTONY.get();
                MobEffectInstance effect = livingEntity.getEffect(MobEffectRegistry.GLUTTONY.get());
                if (effect != null) {
                    EntityHelper.amplifyEffect(effect, livingEntity, 2, 9);
                } else {
                    livingEntity.addEffect(new MobEffectInstance(gluttony, 600, 1, true, true, true));
                }
                livingEntity.playSound(SoundRegistry.GRUESOME_RING_FEEDS.get(), 0.5f, RandomHelper.randomBetween(livingEntity.level().random, 0.8f, 1.2f));
            }
        }
        return itemStack;
    }
}