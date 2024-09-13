package com.sammy.malum.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.systems.item.IEventResponderItem;

public interface IMalumEventResponderItem extends IEventResponderItem {
    default void pickupSpirit(LivingEntity collector, double arcaneResonance) {

    }

    default float overrideSoulwardDamageAbsorbPercentage(LivingDamageEvent.Post event, LivingEntity wardedEntity, ItemStack stack, float original) {
        return overrideSoulwardDamageAbsorbPercentage(wardedEntity, stack, original);
    }

    default float overrideSoulwardDamageAbsorbPercentage(LivingEntity wardedEntity, ItemStack stack, float original) {
        return original;
    }

    default void onSoulwardAbsorbDamage(LivingDamageEvent.Post event, Player wardedPlayer, ItemStack stack, float soulwardLost, float damageAbsorbed) {
        onSoulwardAbsorbDamage(wardedPlayer, stack, soulwardLost, damageAbsorbed);
    }

    default void onSoulwardAbsorbDamage(Player wardedPlayer, ItemStack stack, float soulwardLost, float damageAbsorbed) {

    }
}