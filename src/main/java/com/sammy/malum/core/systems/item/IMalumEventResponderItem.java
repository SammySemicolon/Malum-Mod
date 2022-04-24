package com.sammy.malum.core.systems.item;

import com.sammy.ortus.systems.item.IEventResponderItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IMalumEventResponderItem extends IEventResponderItem {
    default void pickupSpirit(LivingEntity attacker, ItemStack stack, boolean isNatural) {

    }
}