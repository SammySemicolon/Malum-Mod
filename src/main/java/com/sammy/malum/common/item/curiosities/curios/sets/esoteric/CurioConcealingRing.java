package com.sammy.malum.common.item.curiosities.curios.sets.esoteric;

import com.sammy.malum.common.data.listener.spirit.EntitySpiritDropData;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.event.entity.living.LivingEvent.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class CurioConcealingRing extends MalumCurioItem {

    public CurioConcealingRing(Properties builder) {
        super(builder, MalumTrinketFamily.ORNATE);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("friendly_enemies"));
    }

    public static void preventDetection(LivingVisibilityEvent event) {
        if (event.getLookingEntity() instanceof LivingEntity watcher) {
            LivingEntity target = event.getEntity();
            if (CurioHelper.hasCurioEquipped(target, MalumContent.Gear.RING_OF_ESOTERIC_SHADOW.get())) {
                float weight = EntitySpiritDropData.getSpiritData(watcher).map(data -> data.countSpirit(MalumSpiritTypes.ELDRITCH_SPIRIT)).orElse(0);
                if (weight > 0) {
                    float delta = 1 - Math.min(weight / 4, 1);
                    event.modifyVisibility(1 - 0.75f  * delta);
                }
                else {
                    event.modifyVisibility(0.25f);
                }
            }
        }
    }
}
