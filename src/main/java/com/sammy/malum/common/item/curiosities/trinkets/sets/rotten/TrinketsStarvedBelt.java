package com.sammy.malum.common.item.curiosities.trinkets.sets.rotten;

import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.common.item.food.ConcentratedGluttonyItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.EntityHelper;

import java.util.function.Consumer;

public class TrinketsStarvedBelt extends MalumTinketsItem implements IMalumEventResponderItem {

    public TrinketsStarvedBelt(Properties builder) {
        super(builder, MalumTrinketType.ROTTEN);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_gluttony"));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, double arcaneResonance) {
        MobEffect gluttony = MobEffectRegistry.GLUTTONY.get();
        MobEffectInstance effect = collector.getEffect(gluttony);
        if (effect == null) {
            collector.addEffect(new MobEffectInstance(gluttony, 600 + (int) (arcaneResonance * 600), 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, collector, 1, 9);
        }
        Level level = collector.level();
        collector.playSound(SoundRegistry.HUNGRY_BELT_FEEDS.get(), 0.7f, 1.5f + level.random.nextFloat() * 0.5f);
        collector.playSound(SoundEvents.GENERIC_EAT, 0.7f, 0.8f + level.random.nextFloat() * 0.4f);
        if (!level.isClientSide) {
            ConcentratedGluttonyItem.createGluttonyVFX(collector, 0.5f);
        }

    }
}
