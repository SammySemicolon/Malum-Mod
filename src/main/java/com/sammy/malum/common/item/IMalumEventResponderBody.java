package com.sammy.malum.common.item;

import com.sammy.malum.core.systems.events.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.handlers.item_events.IEventResponderBody;

public interface IMalumEventResponderBody extends IEventResponderBody {

    default void malignantCritEvent(MalignantCritEvent.Pre event, LivingEntity attacker) {

    }

    default void finalizedMalignantCritEvent(MalignantCritEvent.Post event, LivingEntity attacker) {

    }

    default void modifyGluttonyPropertiesEvent(ModifyGluttonyPropertiesEvent event, LivingEntity collector) {

    }

    default void spiritCollectionEvent(CollectSpiritEvent event, LivingEntity collector, double arcaneResonance) {

    }

    default void modifySpiritSpoilsEvent(ModifySpiritSpoilsEvent event, LivingEntity attacker) {

    }

    default void modifySoulWardPropertiesEvent(ModifySoulWardPropertiesEvent event, LivingEntity wardedEntity, ItemStack stack) {

    }

    default void soulWardDamageEvent(SoulWardDamageEvent event, LivingEntity wardedEntity, ItemStack stack) {

    }
}