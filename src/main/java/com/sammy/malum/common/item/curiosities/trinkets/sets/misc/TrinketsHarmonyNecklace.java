package com.sammy.malum.common.item.curiosities.trinkets.sets.misc;

import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.core.handlers.SpiritHarvestHandler;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.TrinketsHelper;

import java.util.function.Consumer;

public class TrinketsHarmonyNecklace extends MalumTinketsItem {
    public TrinketsHarmonyNecklace(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("friendly_enemies"));
    }

    public static double preventDetection(LivingEntity target, Entity entity, double v) {
        if (entity instanceof LivingEntity watcher) {
            if (TrinketsHelper.hasTrinketEquipped(target, ItemRegistry.NECKLACE_OF_BLISSFUL_HARMONY.get())) {
                float visibilityModifier = SpiritHarvestHandler.getSpiritData(watcher).map(data -> 0.5f / (1 + data.droppedSpirits.stream().map(s -> s.type.equals(SpiritTypeRegistry.WICKED_SPIRIT) ? 1 : 0).count())).orElse(0.5f);
                if (target.hasEffect(MobEffects.INVISIBILITY)) {
                    visibilityModifier *= 0.5f;
                }
                return v * visibilityModifier;
            }
        }
        return v;
    }
}
