package com.sammy.malum.common.item.curiosities.curios.sets.alchemical;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class CurioAlchemicalRing extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioAlchemicalRing(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_extend_effect"));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, double arcaneResonance) {
        collector.getActiveEffectsMap().forEach((e, i) -> {
            float multiplier = MobEffectRegistry.ALCHEMICAL_PROFICIENCY_MAP.getOrDefault(ForgeRegistries.MOB_EFFECTS.getKey(e), 1f);
            if (e.isBeneficial()) {
                int base = 60 + (int) (arcaneResonance * 40);
                EntityHelper.extendEffect(i, collector, (int) (base * multiplier), 1200);
            } else if (e.getCategory().equals(MobEffectCategory.HARMFUL)) {
                int base = 100 + (int) (arcaneResonance * 60);
                EntityHelper.shortenEffect(i, collector, (int) (base * multiplier));
            }
        });
    }
}
