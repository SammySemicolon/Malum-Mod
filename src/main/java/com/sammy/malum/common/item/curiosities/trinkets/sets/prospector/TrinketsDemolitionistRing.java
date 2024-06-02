package com.sammy.malum.common.item.curiosities.trinkets.sets.prospector;

import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.TrinketsHelper;

import java.util.function.Consumer;

public class TrinketsDemolitionistRing extends MalumTinketsItem {

    public TrinketsDemolitionistRing(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("bigger_explosions"));
    }

    public static float increaseExplosionRadius(LivingEntity source, float original) {
        if (source != null && TrinketsHelper.hasTrinketEquipped(source, ItemRegistry.RING_OF_THE_DEMOLITIONIST.get())) {
            return original + 1;
        }
        return original;
    }
}
