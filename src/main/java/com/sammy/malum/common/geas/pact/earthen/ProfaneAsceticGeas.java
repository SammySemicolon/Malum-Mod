package com.sammy.malum.common.geas.pact.earthen;

import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

import static com.sammy.malum.registry.common.MalumTags.Items.GROSS_FOODS;

public class ProfaneAsceticGeas extends GeasEffect {

    public ProfaneAsceticGeas() {
        super(MalumGeasEffectTypes.PACT_OF_THE_PROFANE_ASCETIC.get());
    }

    @Override
    public void modifyGluttonyPropertiesEvent(ModifyGluttonyPropertiesEvent event, LivingEntity collector) {
        event.getProperties()
                .scaleDuration(3)
                .replaceEffectType(MalumMobEffects.TRIAL_OF_FAITH);
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("trial_of_faith"));
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("rotten_healing"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("no_passive_healing"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("no_normal_foods"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    public static boolean isProfaneAscetic(LivingEntity entity) {
        return GeasEffectHandler.hasGeas(entity, MalumGeasEffectTypes.PACT_OF_THE_PROFANE_ASCETIC);
    }

    public static void modifyEating(LivingEntityUseItemEvent.Start event) {
        if (isProfaneAscetic(event.getEntity())) {
            if (event.getItem().getFoodProperties(event.getEntity()) != null) {
                float multiplier = event.getItem().is(GROSS_FOODS) ? 0.66f : 2f;
                event.setDuration((int) (event.getDuration() * multiplier));
            }
        }
    }

    public static void onEat(Level level, LivingEntity livingEntity, ItemStack food) {
        if (level.isClientSide) {
            return;
        }
        if (isProfaneAscetic(livingEntity)) {
            if (food.is(GROSS_FOODS)) {
                livingEntity.heal(livingEntity.getMaxHealth() / 5f);
            } else {
                livingEntity.hurt(DamageTypeHelper.create(level, MalumDamageTypes.KARMIC), livingEntity.getMaxHealth() / 2);
            }
        }
    }
}