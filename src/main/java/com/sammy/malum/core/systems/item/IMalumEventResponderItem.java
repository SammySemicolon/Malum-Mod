package com.sammy.malum.core.systems.item;

import com.sammy.ortus.systems.item.IEventResponderItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface IMalumEventResponderItem extends IEventResponderItem {
    default void pickupSpirit(LivingEntity collector, ItemStack stack, boolean isNatural) {

    }
    default void soulwardDamageAbsorb(LivingHurtEvent event, LivingEntity wardedEntity, ItemStack stack) {
        soulwardDamageAbsorb(wardedEntity, stack);
    }
    default void soulwardDamageAbsorb(LivingEntity wardedEntity, ItemStack stack) {

    }
}