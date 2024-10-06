package com.sammy.malum.common.item.curiosities.curios.sets.rotten;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.common.item.food.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class CurioStarvedBelt extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioStarvedBelt(Properties builder) {
        super(builder, MalumTrinketType.ROTTEN);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_gluttony"));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, double arcaneResonance) {
        var gluttony = MobEffectRegistry.GLUTTONY.get();
        var effect = collector.getEffect(gluttony);
        if (effect == null) {
            collector.addEffect(new MobEffectInstance(gluttony, (int) (600 * arcaneResonance), 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, collector, 1, 9);
        }
        SoundHelper.playSound(collector, SoundRegistry.HUNGRY_BELT_FEEDS.get(), 0.7f, RandomHelper.randomBetween(collector.getRandom(), 1.5f, 2f));
        SoundHelper.playSound(collector, SoundEvents.GENERIC_EAT, 0.7f, RandomHelper.randomBetween(collector.getRandom(), 0.8f, 1.2f));
        if (!collector.level().isClientSide) {
            ConcentratedGluttonyItem.createGluttonyVFX(collector, 0.5f);
        }

    }
}
