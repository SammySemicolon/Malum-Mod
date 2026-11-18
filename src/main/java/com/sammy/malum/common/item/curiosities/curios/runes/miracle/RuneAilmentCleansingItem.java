package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import team.lodestar.lodestone.helpers.CurioHelper;
import team.lodestar.lodestone.helpers.EntityHelper;

import java.util.function.Consumer;

public class RuneAilmentCleansingItem extends MiracleRuneCurioItem {

    public RuneAilmentCleansingItem(Properties builder) {
        super(builder, MalumSpiritTypes.AQUEOUS_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("ailment_cleansing_rune"));
    }

    public static void scaleEffectDuration(MobEffectEvent.Added event) {
        var entity = event.getEntity();
        if (event.getOldEffectInstance() != null) {
            return;
        }
        if (!CurioHelper.hasCurioEquipped(entity, MalumContent.Gear.RUNE_OF_AILMENT_CLEANSING.get())) {
            return;
        }
        var instance = event.getEffectInstance();
        assert instance != null;
        if (instance.getEffect().value().isBeneficial()) {
            return;
        }
        int removedDuration = instance.getDuration() / 2;
        EntityHelper.shortenEffect(instance, entity, removedDuration);
    }
}
