package com.sammy.malum.common.item.curiosities.trinkets.sets.weeping;

import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.helpers.RandomHelper;

import java.util.function.Consumer;

public class TrinketsGrowingFleshRing extends MalumTinketsItem implements IVoidItem, IMalumEventResponderItem {
    public TrinketsGrowingFleshRing(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_add_health"));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, double arcaneResonance) {
        var cancerousGrowth = MobEffectRegistry.CANCEROUS_GROWTH.get();
        var effect = collector.getEffect(cancerousGrowth);
        int addedDuration = (int) (150 * arcaneResonance);
        if (effect == null) {
            collector.addEffect(new MobEffectInstance(cancerousGrowth, addedDuration*4, 0, true, true, true));
        } else {
            EntityHelper.extendEffect(effect, collector, addedDuration, 72000);
            EntityHelper.amplifyEffect(effect, collector, 1, 19);
        }
        collector.playSound(SoundRegistry.FLESH_RING_ABSORBS.get(), 0.3f, RandomHelper.randomBetween(collector.getRandom(), 1.5f, 2f));
    }
}
