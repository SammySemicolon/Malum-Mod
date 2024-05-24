package com.sammy.malum.common.item.curiosities.curios.sets.misc;

import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class CurioHarmonyNecklace extends MalumCurioItem {
    public CurioHarmonyNecklace(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("friendly_enemies"));
    }

    public static double preventDetection(LivingEntity target, Entity entity, double v) {
        if (entity instanceof LivingEntity watcher) {
            if (TrinketsHelper.hasCurioEquipped(target, ItemRegistry.NECKLACE_OF_BLISSFUL_HARMONY.get())) {
                float visibilityModifier = SpiritHarvestHandler.getSpiritData(watcher).map(data -> 0.5f / (1 + data.dataEntries.stream().map(s -> s.type.equals(SpiritTypeRegistry.WICKED_SPIRIT) ? 1 : 0).count())).orElse(0.5f);
                if (target.hasEffect(MobEffects.INVISIBILITY)) {
                    visibilityModifier *= 0.5f;
                }
                return v * visibilityModifier;
            }
        }
        return v;
    }
}
