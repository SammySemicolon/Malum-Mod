package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.magic.MalumSpiritTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import team.lodestar.lodestone.helpers.CurioHelper;
import team.lodestar.lodestone.helpers.EntityHelper;

import java.util.function.Consumer;

public class RuneTwinnedDurationItem extends MadnessRuneCurioItem {

    public RuneTwinnedDurationItem(Properties builder) {
        super(builder, MalumSpiritTypes.AQUEOUS_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(TooltipComponentHelper.positiveCurioEffect("extend_positive_effect"));
    }

    public static void scaleEffectDuration(MobEffectEvent.Added event) {
        var entity = event.getEntity();
        if (event.getOldEffectInstance() != null) {
            return;
        }
        if (!CurioHelper.hasCurioEquipped(entity, MalumContent.Gear.RUNE_OF_TWINNED_DURATION.get())) {
            return;
        }
        var effect = event.getEffectInstance();
        assert effect != null;
        var type = effect.getEffect().value();
        if (!type.isBeneficial()) {
            return;
        }
        EntityHelper.extendEffect(effect, entity, effect.getDuration());
    }
}
