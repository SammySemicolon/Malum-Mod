package com.sammy.malum.common.geas.pact.aerial;

import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.function.*;

public class SkyBreakerGeas extends GeasEffect {

    private final ArrayList<LivingEntity> cachedTargets = new ArrayList<>();

    public SkyBreakerGeas() {
        super(MalumGeasEffectTypes.PACT_OF_THE_SKYBREAKER.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("incoming_fall_damage_auto_attack"));
        tooltipAcceptor.accept(TooltipComponentHelper.positiveGeasEffect("outgoing_fall_damage_auto_attack"));
        tooltipAcceptor.accept(TooltipComponentHelper.negativeGeasEffect("more_knockback"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    public static void scaleKnockback(LivingKnockBackEvent event) {
        final LivingEntity entity = event.getEntity();
        var geas = GeasEffectHandler.getGeasEffect(entity, MalumGeasEffectTypes.PACT_OF_THE_SKYBREAKER);
        if (geas != null) {
            event.setStrength(event.getStrength() * 3);
        }
    }

    @Override
    public void finalizedOutgoingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker instanceof Player player) {
            if (event.getSource().is(DamageTypes.FALL)) {
                attack(player, target);
            }
        }
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, @Nullable LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (!(target instanceof ServerPlayer player)) {
            return;
        }
        if (!event.getSource().is(DamageTypes.FALL)) {
            return;
        }
        var aabb = player.getBoundingBox().inflate(4, 1f, 4);
        cachedTargets.clear();
        for (Entity nearbyTarget : player.serverLevel().getEntities(player, aabb, t -> skyBreakerCanHitEntity(player, t))) {
            if (nearbyTarget instanceof LivingEntity living) {
                cachedTargets.add(living);
                event.setNewDamage(event.getNewDamage() * 0.8f);
            }
        }
    }

    @Override
    public void finalizedIncomingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (!(target instanceof ServerPlayer player)) {
            return;
        }
        if (!event.getSource().is(DamageTypes.FALL)) {
            return;
        }
        for (LivingEntity cachedTarget : cachedTargets) {
            if (cachedTarget.isAlive()) {
                attack(player, cachedTarget);
            }
        }
    }

    public void attack(Player player, Entity target) {
        if (!target.isAlive()) {
            return;
        }
        player.attackStrengthTicker = 1000;
        player.swing(InteractionHand.MAIN_HAND, true);
        target.invulnerableTime = 0;
        player.attack(target);
        target.invulnerableTime = 0;
    }

    protected static boolean skyBreakerCanHitEntity(Player attacker, Entity pTarget) {
        if (pTarget instanceof TamableAnimal tamableAnimal) {
            if (tamableAnimal.isTame()) {
                return false;
            }
        }
        if (!pTarget.canBeHitByProjectile()) {
            return false;
        } else {
            return pTarget != attacker && !attacker.isPassengerOfSameVehicle(pTarget);
        }
    }
}