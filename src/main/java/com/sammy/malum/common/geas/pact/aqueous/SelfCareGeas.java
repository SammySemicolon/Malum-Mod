package com.sammy.malum.common.geas.pact.aqueous;

import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class SelfCareGeas extends GeasEffect {

    public SelfCareGeas() {
        super(MalumGeasEffectTypes.PACT_OF_SELF_CARE.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("more_saturation"));
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("food_effect_cleanse"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("faster_starving"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    public static void onEat(Level level, LivingEntity livingEntity, ItemStack food) {
        if (level.isClientSide) {
            return;
        }
        if (livingEntity instanceof Player player) {
            if (GeasEffectHandler.hasGeas(player, MalumGeasEffectTypes.PACT_OF_SELF_CARE)) {
                player.getFoodData().add(0, 1.5f);
                for (MobEffectInstance instance : livingEntity.getActiveEffectsMap().values()) {
                    var e = instance.getEffect().value();
                    if (e.getCategory().equals(MobEffectCategory.HARMFUL)) {
                        EntityHelper.shortenEffect(instance, livingEntity, 80);
                    }
                }
            }
        }
    }

    public static int accelerateHunger(LivingEntity entity, int foodLevel) {
        if (GeasEffectHandler.hasGeas(entity, MalumGeasEffectTypes.PACT_OF_SELF_CARE)) {
            if (foodLevel == 0) {
                return 3;
            }
            if (foodLevel < 3) {
                return 1;
            }
            return entity.getHealth() < entity.getMaxHealth()/4f ? 2 : 1;
        }
        return 0;
    }
}