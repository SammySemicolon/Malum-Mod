package com.sammy.malum.common.item;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.systems.item.IEventResponderItem;

public interface IMalumEventResponderItem extends IEventResponderItem {
    default void pickupSpirit(LivingEntity collector, double arcaneResonance) {

    }

    default float overrideSoulwardDamageAbsorbPercentage(LivingHurtEvent event, LivingEntity wardedEntity, ItemStack stack, float original) {
        return overrideSoulwardDamageAbsorbPercentage(wardedEntity, stack, original);
    }

    default float overrideSoulwardDamageAbsorbPercentage(LivingEntity wardedEntity, ItemStack stack, float original) {
        return original;
    }

    default void onSoulwardAbsorbDamage(LivingHurtEvent event, Player wardedPlayer, ItemStack stack, float soulwardLost, float damageAbsorbed) {
        onSoulwardAbsorbDamage(wardedPlayer, stack, soulwardLost, damageAbsorbed);
    }

    default void onSoulwardAbsorbDamage(Player wardedPlayer, ItemStack stack, float soulwardLost, float damageAbsorbed) {

    }
}