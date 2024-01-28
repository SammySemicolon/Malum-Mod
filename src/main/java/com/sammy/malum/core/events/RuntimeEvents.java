package com.sammy.malum.core.events;

import com.sammy.malum.common.block.storage.jar.SpiritJarBlock;
import com.sammy.malum.common.capability.MalumItemDataCapability;
import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.common.effect.CorruptedAerialAura;
import com.sammy.malum.common.effect.GluttonyEffect;
import com.sammy.malum.common.effect.InfernalAura;
import com.sammy.malum.common.effect.WickedIntentEffect;
import com.sammy.malum.common.enchantment.*;
import com.sammy.malum.common.entity.nitrate.EthericExplosion;
import com.sammy.malum.common.item.cosmetic.curios.CurioTokenOfGratitude;
import com.sammy.malum.common.item.curiosities.curios.alchemical.CurioAlchemicalRing;
import com.sammy.malum.common.item.curiosities.curios.misc.CurioHarmonyNecklace;
import com.sammy.malum.common.item.curiosities.curios.prospector.CurioProspectorBelt;
import com.sammy.malum.common.item.curiosities.curios.rotten.CurioVoraciousRing;
import com.sammy.malum.common.item.curiosities.curios.weeping.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.listeners.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RuntimeEvents {

    @SubscribeEvent
    public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        MalumPlayerDataCapability.attachPlayerCapability(event);
        MalumLivingEntityDataCapability.attachEntityCapability(event);
        MalumItemDataCapability.attachItemCapability(event);
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        MalumPlayerDataCapability.playerJoin(event);
        CurioTokenOfGratitude.giveItem(event);
        SoulDataHandler.updateAi(event);
        /*
        if (TetraCompat.LOADED) {
            TetraCompat.LoadedOnly.fireArrow(event);
        }

         */
    }


    @SubscribeEvent
    public static void playerLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof SpiritJarBlock jarBlock) {
            Player player = event.getEntity();
            BlockHitResult target = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
            if (target.getType() == HitResult.Type.BLOCK && target.getBlockPos().equals(pos) && target.getDirection().getAxis() == Direction.Axis.X) {
                if (player.isCreative()) {
                    event.setCanceled(jarBlock.handleAttack(level, pos, player));
                }
            }
        }
    }


    @SubscribeEvent
    public static void onEntityJoin(MobSpawnEvent.PositionCheck event) {
        SoulDataHandler.markAsSpawnerSpawned(event);
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
    public static void onLivingTarget(LivingChangeTargetEvent event) {
        SoulDataHandler.preventTargeting(event);
    }

    @SubscribeEvent
    public static void onLivingVisibility(LivingEvent.LivingVisibilityEvent event) {
        CurioHarmonyNecklace.preventDetection(event);
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        SoulDataHandler.manageSoul(event);
        TouchOfDarknessHandler.entityTick(event);
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        MalumLivingEntityDataCapability.syncEntityCapability(event);
        MalumPlayerDataCapability.syncPlayerCapability(event);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        MalumPlayerDataCapability.playerClone(event);
    }

    @SubscribeEvent
    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        InfernalAura.increaseDigSpeed(event);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        ReserveStaffChargeHandler.recoverStaffCharges(event);
        SoulWardHandler.recoverSoulWard(event);
    }

    @SubscribeEvent
    public static void registerListeners(AddReloadListenerEvent event) {
        SpiritDataReloadListener.register(event);
        ReapingDataReloadListener.register(event);
        RitualRecipeReloadListener.register(event);
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ReboundEnchantment.onRightClickItem(event);
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
        CurioAlchemicalRing.onPotionApplied(event);
    }

    @SubscribeEvent
    public static void onStartUsingItem(LivingEntityUseItemEvent.Start event) {
        CurioVoraciousRing.accelerateEating(event);
    }

    @SubscribeEvent
    public static void onFinishUsingItem(LivingEntityUseItemEvent.Finish event) {
        CurioGruesomeSatiationRing.finishEating(event);
        CurioVoraciousRing.finishEating(event);
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        MalumAttributeEventHandler.processAttributes(event);
        SoulDataHandler.exposeSoul(event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLateHurt(LivingHurtEvent event) {
        SoulWardHandler.shieldPlayer(event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLateDamage(LivingDamageEvent event) {
        WickedIntentEffect.removeWickedIntent(event);
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        EsotericReapingHandler.tryCreateReapingDrops(event);
        SpiritHarvestHandler.shatterSoul(event);
    }

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {
        SpiritHarvestHandler.modifyDroppedItems(event);
    }

    @SubscribeEvent
    public static void onItemExpire(ItemExpireEvent event) {
        SpiritHarvestHandler.shatterItem(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        CurioProspectorBelt.processExplosion(event);
        EthericExplosion.processExplosion(event);
    }
}

