package com.sammy.malum.common.item.curiosities.trinkets.sets.weeping;

import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.helpers.RandomHelper;

import java.util.function.*;

public class TrinketsEchoingArcanaRing extends MalumTinketsItem implements IVoidItem, IMalumEventResponderItem {
    public TrinketsEchoingArcanaRing(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_buff_spirit_collection"));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, double arcaneResonance) {
        var arcaneReverberation = MobEffectRegistry.ARCANE_REVERBERATION.get();
        var effect = collector.getEffect(arcaneReverberation);
        int addedDuration = (int) (150 * arcaneResonance);
        if (effect == null) {
            collector.addEffect(new MobEffectInstance(arcaneReverberation, addedDuration*4, 0, true, true, true));
        } else {
            EntityHelper.extendEffect(effect, collector, addedDuration, 72000);
            EntityHelper.amplifyEffect(effect, collector, 1, 19);
        }
        collector.playSound(SoundRegistry.ECHOING_RING_ABSORBS.get(), 0.3f, RandomHelper.randomBetween(collector.getRandom(), 1.5f, 2f));
    }
}