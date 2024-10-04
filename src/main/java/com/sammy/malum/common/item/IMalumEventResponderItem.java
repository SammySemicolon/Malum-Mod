package com.sammy.malum.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import team.lodestar.lodestone.systems.item.IEventResponderItem;

public interface IMalumEventResponderItem extends IEventResponderItem {
    default void pickupSpirit(LivingEntity collector, double arcaneResonance) {

    }

    default float adjustSoulWardDamageAbsorption(LivingHurtEvent event, LivingEntity wardedEntity, ItemStack stack, float original) {
        return adjustSoulWardDamageAbsorption(wardedEntity, stack, original);
    }

    default float adjustSoulWardDamageAbsorption(LivingEntity wardedEntity, ItemStack stack, float original) {
        return original;
    }

    default void onSoulwardAbsorbDamage(LivingHurtEvent event, Player wardedPlayer, ItemStack stack, double soulwardLost, float damageAbsorbed) {
        onSoulwardAbsorbDamage(wardedPlayer, stack, soulwardLost, damageAbsorbed);
    }

    default void onSoulwardAbsorbDamage(Player wardedPlayer, ItemStack stack, double soulwardLost, float damageAbsorbed) {

    }
}