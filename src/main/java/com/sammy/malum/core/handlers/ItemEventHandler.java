package com.sammy.malum.core.handlers;

import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemEventHandler {

    public static void respondToDeath(LivingDeathEvent event) {
        if (event.isCanceled()) {
            return;
        }
        LivingEntity target = event.getEntityLiving();
        LivingEntity attacker = null;
        if (event.getSource().getEntity() instanceof LivingEntity directAttacker) {
            attacker = directAttacker;
        }
        if (attacker == null) {
            attacker = target.getLastHurtByMob();
        }
        if (attacker != null) {
            LivingEntity finalAttacker = attacker;
            ItemHelper.getEventResponders(attacker).forEach(s -> ((IEventResponderItem) s.getItem()).killEvent(event, finalAttacker, target, s));
        }
    }

    public static void respondToHurt(LivingHurtEvent event) {
        if (event.isCanceled()) {
            return;
        }
        LivingEntity target = event.getEntityLiving();
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemHelper.getEventResponders(attacker).forEach(s -> ((IEventResponderItem) s.getItem()).hurtEvent(event, attacker, target, s));
            ItemHelper.getEventResponders(target).forEach(s -> ((IEventResponderItem) s.getItem()).takeDamageEvent(event, target, attacker, s));
        }
    }
}