package com.sammy.malum.events;

import com.sammy.malum.common.block.storage.jar.*;
import com.sammy.malum.common.data.attachment.AvariceMarkData;
import com.sammy.malum.common.data.listener.banner.MalumBannerPatternReloadListener;
import com.sammy.malum.common.data.listener.malignant_conversion.MalignantConversionReloadListener;
import com.sammy.malum.common.data.listener.reaping.ReapingDataReloadListener;
import com.sammy.malum.common.data.listener.rite.*;
import com.sammy.malum.common.data.listener.spellweaving.SpellweavingEqualityReloadListener;
import com.sammy.malum.common.data.listener.spirit.SpiritDataReloadListener;
import com.sammy.malum.common.effect.ascension.*;
import com.sammy.malum.common.effect.rite.aura.*;
import com.sammy.malum.common.effect.rite.aura.soulwood.*;
import com.sammy.malum.common.effect.gluttony.*;
import com.sammy.malum.common.entity.activator.*;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.common.entity.soulTag.SoulTagEntity;
import com.sammy.malum.common.geas.pact.aerial.*;
import com.sammy.malum.common.geas.pact.infernal.*;
import com.sammy.malum.common.geas.pact.earthen.ProfaneAsceticGeas;
import com.sammy.malum.common.geas.pact.sacred.*;
import com.sammy.malum.common.geas.pact.wicked.*;
import com.sammy.malum.common.item.curiosities.curios.runes.madness.*;
import com.sammy.malum.common.item.curiosities.curios.runes.miracle.*;
import com.sammy.malum.common.item.curiosities.curios.sets.elemental.*;
import com.sammy.malum.common.item.curiosities.curios.sets.esoteric.*;
import com.sammy.malum.common.item.curiosities.curios.sets.rotten.*;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.*;
import com.sammy.malum.common.item.curiosities.pouch.*;
import com.sammy.malum.common.item.curiosities.tools.spellweaver.*;
import com.sammy.malum.common.item.curiosities.weapons.greatsword.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import com.sammy.malum.core.handlers.*;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.event.entity.*;
import net.neoforged.neoforge.event.entity.item.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.level.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.wayward_attributes.WaywardTags;

import java.util.List;

@EventBusSubscriber()
public class RuntimeEventHandler {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        SoulDataHandler.syncData(event);
        GeasEffectHandler.syncGeas(event);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        double RANGE = 8.0D;

        LivingEntity damagedEntity = event.getEntity();

        if (damagedEntity.level().isClientSide()) {
            return;
        }
        if(!event.getSource().is(WaywardTags.DamageTypeTags.IS_MAGIC)) {
            return;
        }

        if (event.getNewDamage() <= 0.0F) {
            return;
        }

        double rangeSquared = RANGE * RANGE;

        List<SoulTagEntity> soulTags =
                damagedEntity.level().getEntitiesOfClass(
                        SoulTagEntity.class,
                        damagedEntity.getBoundingBox().inflate(RANGE),
                        SoulTagEntity::isAlive
                );

        for (SoulTagEntity soulTag : soulTags) {

            if (soulTag.distanceToSqr(damagedEntity) <= rangeSquared) {

                soulTag.createSoulTag(
                        damagedEntity
                );
                break;
            }
        }
    }

    @SubscribeEvent
    public static void playerLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        SpiritJarBlock.cancelBreak(event);
    }

    @SubscribeEvent
    public static void sweepAttack(SweepAttackEvent event) {
        MalumScytheItem.enableScytheSweeping(event);
    }

    @SubscribeEvent
    public static void onEntityJoin(MobSpawnEvent.PositionCheck event) {
        SoulDataHandler.markAsSpawnerSpawned(event);
    }

    @SubscribeEvent
    public static void onEntityFall(LivingFallEvent event) {
        AscensionEffect.onEntityFall(event);
        LiftedEffect.onEntityFall(event);
        SkyTetherEffect.onEntityFall(event);
    }

    @SubscribeEvent
    public static void onLivingTarget(LivingChangeTargetEvent event) {
        SoulDataHandler.preventTargeting(event);
    }

    @SubscribeEvent
    public static void onLivingVisibility(LivingEvent.LivingVisibilityEvent event) {
        CurioConcealingRing.preventDetection(event);
        WarlockGeas.increaseDetection(event);
    }

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Pre event) {
        AvariceMarkData.entityTick(event);
        SoulDataHandler.entityTick(event);
        SoulWardHandler.entityTick(event);
        WindTunnelHandler.entityTick(event);
        GeasEffectHandler.entityTick(event);
        CurioWatcherNecklace.entityTick(event);
        TouchOfDarknessHandler.entityTick(event);
        CurioHiddenBladeNecklace.entityTick(event);
        VindictiveBrandSwordItem.entityTick(event);
        MalignantConversionHandler.entityTick(event);
        WeepingWellRejectionHandler.entityTick(event);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        StaffAbilityHandler.recoverStaffCharges(event);
    }

    @SubscribeEvent
    public static void modifyBlockDrops(BlockDropsEvent event) {
        AvariceMarkData.applyAvariceMark(event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void modifyBlockDropsLate(BlockDropsEvent event) {
        SpellweaverToolEffectActivator.redirectDrops(event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void breakBlock(BlockEvent.BreakEvent event) {
        SpellweavingPickaxeItem.triggerSpellweavingEffect(event);
    }

    @SubscribeEvent
    public static void registerListeners(AddReloadListenerEvent event) {
        SpiritDataReloadListener.register(event);
        SpiritRiteTypeReloadListener.register(event);

        MalumBannerPatternReloadListener.register(event);

        SpellweavingEqualityReloadListener.register(event);
        ReapingDataReloadListener.register(event);
        MalignantConversionReloadListener.register(event);
    }

    @SubscribeEvent
    public static void isPotionApplicable(MobEffectEvent.Applicable event) {
        GluttonyEffect.canApplyPotion(event);
    }

    @SubscribeEvent
    public static void onPotionApplied(MobEffectEvent.Added event) {
        GluttonyEffect.removeExistingHunger(event);
        RuneTwinnedDurationItem.scaleDuration(event);
        RuneAilmentCleansingItem.scaleDuration(event);
    }

    @SubscribeEvent
    public static void onPotionExpired(MobEffectEvent.Expired event) {
    }

    @SubscribeEvent
    public static void onStartUsingItem(LivingEntityUseItemEvent.Start event) {
        ProfaneAsceticGeas.modifyEating(event);
        CurioVoraciousRing.modifyEating(event);
    }

    @SubscribeEvent
    public static void onPickupItem(ItemEntityPickupEvent.Pre event) {
        SoulwovenPouchItem.trySwallowItem(event);
        RavenousPouchItem.trySwallowItem(event);
    }

    @SubscribeEvent
    public static void onPickupItem(ItemEntityPickupEvent.Post event) {
        AvariceMarkData.pickupItem(event);
    }

    @SubscribeEvent
    public static void onHurt(LivingDamageEvent.Post event) {
        SoulDataHandler.exposeSoul(event);
        DesperateNeedEffect.spawnLocusts(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        MalignantConversionHandler.shieldPlayer(event);
    }

    @SubscribeEvent
    public static void onHurt(LivingDamageEvent.Pre event) {
        SoulWardHandler.shieldPlayer(event);
        OakenMightEffect.increaseDamage(event);
        StoneWardEffect.reduceDamage(event);
        MalumAttributeEventHandler.processAttributes(event);
    }

    @SubscribeEvent
    public static void onKnockback(LivingKnockBackEvent event) {
        WindsweptGeas.scaleKnockback(event);
        SkyBreakerGeas.scaleKnockback(event);
    }

    @SubscribeEvent
    public static void onHeal(LivingHealEvent event) {
        LifeweaverGeas.onHeal(event);
        TrialOfFaithEffect.spawnLocusts(event);
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        SoulHarvestHandler.onDeath(event);
        GluttonyEffect.onDeath(event);
    }

    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event) {
        EnsouledItemHarvestHandler.onDrops(event);
    }

    @SubscribeEvent
    public static void onItemExpire(ItemExpireEvent event) {
        EnsouledItemHarvestHandler.onItemExpire(event);
    }

    @SubscribeEvent
    public static void onItemUseBlock(UseItemOnBlockEvent event) {
        BlockTappingHandler.drainBlock(event);
    }
    @SubscribeEvent
    public static void onItemUseStart(LivingEntityUseItemEvent.Start event) {
        ContinuingShotGeas.onItemUseStart(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        NitrateExplosion.processExplosion(event);
        BlastweaverGeas.processExplosion(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onExplosionKnockback(ExplosionKnockbackEvent event) {
        CurioWindweaverNecklace.onExplosionKnockback(event);
    }
}