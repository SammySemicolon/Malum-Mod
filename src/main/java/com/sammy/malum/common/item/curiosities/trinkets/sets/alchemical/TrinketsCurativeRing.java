package com.sammy.malum.common.item.curiosities.trinkets.sets.alchemical;

import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;

public class TrinketsCurativeRing extends MalumTinketsItem implements IMalumEventResponderItem {
    public TrinketsCurativeRing(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_heal"));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, double arcaneResonance) {
        collector.heal((float) (collector.getMaxHealth() * 0.05f * arcaneResonance));
    }
}
