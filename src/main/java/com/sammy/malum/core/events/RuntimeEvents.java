package com.sammy.malum.core.events;

import com.sammy.malum.common.block.storage.jar.*;
import com.sammy.malum.common.components.*;
import com.sammy.malum.common.effect.*;
import com.sammy.malum.common.effect.aura.*;
import com.sammy.malum.common.enchantment.*;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.common.item.cosmetic.curios.*;
import com.sammy.malum.common.item.curiosities.curios.runes.madness.*;
import com.sammy.malum.common.item.curiosities.curios.runes.miracle.*;
import com.sammy.malum.common.item.curiosities.curios.sets.misc.*;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.*;
import com.sammy.malum.common.item.curiosities.curios.sets.rotten.*;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.listeners.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;

public class RuntimeEvents {


    @SubscribeEvent
    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        InfernalAura.increaseDigSpeed(event);
        RuneFervorItem.increaseDigSpeed(event);
    }

    @SubscribeEvent
    public static void registerListeners(AddReloadListenerEvent event) {
        SpiritDataReloadListener.register(event);
        ReapingDataReloadListener.register(event);
        RitualRecipeReloadListener.register(event);
        MalignantConversionReloadListener.register(event);
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ReboundEnchantment.onRightClickItem(event);
    }

    @SubscribeEvent
    public static void onSwapEquipment(LivingEquipmentChangeEvent event) {
    }

    @SubscribeEvent
    public static void addItemAttributes(ItemAttributeModifierEvent event) {
        HauntedEnchantment.addMagicDamage(event);
    }

    @SubscribeEvent
    public static void isPotionApplicable(MobEffectEvent.Applicable event) {
        GluttonyEffect.canApplyPotion(event);
    }

    @SubscribeEvent
    public static void onPotionApplied(MobEffectEvent.Added event) {
        RuneTwinnedDurationItem.onPotionApplied(event);
        RuneAlimentCleansingItem.onPotionApplied(event);
    }


    @SubscribeEvent
    public static void onStartUsingItem(LivingEntityUseItemEvent.Start event) {
        CurioVoraciousRing.accelerateEating(event);
    }

    @SubscribeEvent
    public static void onItemExpire(ItemExpireEvent event) {
        SpiritHarvestHandler.shatterItem(event);
    }

}

