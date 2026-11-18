package com.sammy.malum.common.geas.pact.aerial;

import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class ContinuingShotGeas extends GeasEffect {

    public static final int MAX_DRAW_SPEED_STACKS = 5;

    public ContinuingShotGeas() {
        super(MalumGeasEffectTypes.PACT_OF_THE_CONTINUING_SHOT.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("faster_draw_time"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("missing_halts_draw_time"));
    }

    @Override
    public void finalizedOutgoingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var projectile = event.getSource().getDirectEntity();
        if ((projectile instanceof AbstractArrow arrow && arrow.isCritArrow()) || projectile instanceof AbstractBoltProjectile) {
            applyEffect(attacker, true);
        }
    }

    public static void projectileHitBlock(Projectile projectile) {
        if (projectile.level().isClientSide) {
            return;
        }
        if (projectile instanceof AbstractArrow || projectile instanceof AbstractBoltProjectile) {
            if (projectile.getOwner() instanceof LivingEntity owner) {
                if (GeasEffectHandler.hasGeas(owner, MalumGeasEffectTypes.PACT_OF_THE_CONTINUING_SHOT)) {
                    applyEffect(owner, false);
                }
            }
        }
    }

//    public static void onItemUseStart(LivingEntityUseItemEvent.Start event) {
//        LivingEntity entity = event.getEntity();
//        GeasEffect geasEffect = GeasEffectHandler.getGeasEffect(entity, MalumGeasEffectTypes.PACT_OF_THE_CONTINUING_SHOT);
//        if (geasEffect instanceof ContinuingShotGeas) {
//            int duration = event.getDuration();
//            int drawSpeedStacks = 0;
//            var positive = entity.getEffect(MalumMobEffects.TRUE_SHOT);
//            var negative = entity.getEffect(MalumMobEffects.FLAWED_AIM);
//            if (positive != null) {
//                drawSpeedStacks += positive.getAmplifier() + 1;
//            }
//            if (negative != null) {
//                drawSpeedStacks -= negative.getAmplifier() + 1;
//            }
//            if (drawSpeedStacks < 0) {
//                duration -= drawSpeedStacks * 5;
//            } else {
//                duration -= (int) (drawSpeedStacks * 1.5f);
//            }
//            event.setDuration(duration);
//        }
//    }

    public static void applyEffect(LivingEntity entity, boolean isPositive) {
        entity.removeEffect(isPositive ? MalumMobEffects.FLAWED_AIM : MalumMobEffects.TRUE_SHOT);
        var effect = isPositive ? MalumMobEffects.TRUE_SHOT : MalumMobEffects.FLAWED_AIM;
        var instance = entity.getEffect(effect);
        if (instance == null) {
            entity.addEffect(new MobEffectInstance(effect, 200, 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(instance, entity, 1, MAX_DRAW_SPEED_STACKS - 1);
            EntityHelper.extendEffect(instance, entity, 40, 400);
        }
    }
}