package com.sammy.malum.core.events;

import com.sammy.malum.common.capability.LivingEntityDataCapability;
import com.sammy.malum.common.capability.PlayerDataCapability;
import com.sammy.malum.common.effect.CorruptedAerialAura;
import com.sammy.malum.common.enchantment.ReboundEnchantment;
import com.sammy.malum.common.item.equipment.CeaselessImpetusItem;
import com.sammy.malum.common.item.equipment.curios.CurioTokenOfGratitude;
import com.sammy.malum.common.spiritaffinity.ArcaneAffinity;
import com.sammy.malum.common.spiritaffinity.EarthenAffinity;
import com.sammy.malum.compability.tetra.TetraCompat;
import com.sammy.malum.core.handlers.AttributeEventHandler;
import com.sammy.malum.core.handlers.ItemEventHandler;
import com.sammy.malum.core.handlers.SoulHarvestHandler;
import com.sammy.malum.core.handlers.SpiritHarvestHandler;
import com.sammy.malum.core.listeners.SpiritDataReloadListener;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RuntimeEvents {

    @SubscribeEvent
    public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        PlayerDataCapability.attachPlayerCapability(event);
        LivingEntityDataCapability.attachEntityCapability(event);
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinWorldEvent event) {
        PlayerDataCapability.playerJoin(event);
        CurioTokenOfGratitude.giveItem(event);
        SoulHarvestHandler.addEntity(event);
        if (TetraCompat.LOADED) {
            TetraCompat.LoadedOnly.fireArrow(event);
        }
    }

    @SubscribeEvent
    public static void onEntityJoin(LivingSpawnEvent.SpecialSpawn event) {
        SoulHarvestHandler.specialSpawn(event);
    }

    @SubscribeEvent
    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        CorruptedAerialAura.onEntityJump(event);
    }
    @SubscribeEvent
    public static void onEntityFall(LivingFallEvent event) {
        CorruptedAerialAura.onEntityFall(event);
    }

    @SubscribeEvent
    public static void onLivingTarget(LivingSetAttackTargetEvent event) {
        SoulHarvestHandler.entityTarget(event);
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        SoulHarvestHandler.entityTick(event);
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        LivingEntityDataCapability.syncEntityCapability(event);
        PlayerDataCapability.syncPlayerCapability(event);
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        PlayerDataCapability.playerClone(event);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        ArcaneAffinity.recoverSoulWard(event);
        EarthenAffinity.recoverHeartOfStone(event);
        SoulHarvestHandler.playerTick(event);
    }

    @SubscribeEvent
    public static void registerListeners(AddReloadListenerEvent event) {
        SpiritDataReloadListener.register(event);
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ReboundEnchantment.onRightClickItem(event);
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        ArcaneAffinity.consumeSoulWard(event);
        EarthenAffinity.consumeHeartOfStone(event);
        SpiritHarvestHandler.exposeSoul(event);
        ItemEventHandler.respondToHurt(event);
        AttributeEventHandler.processAttributes(event);
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        CeaselessImpetusItem.preventDeath(event);
        SpiritHarvestHandler.shatterSoul(event);
        ItemEventHandler.respondToDeath(event);
    }
}