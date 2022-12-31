package com.sammy.malum.core.events;

import com.sammy.malum.common.block.storage.SpiritJarBlock;
import com.sammy.malum.common.capability.MalumItemDataCapability;
import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.common.effect.CorruptedAerialAura;
import com.sammy.malum.common.effect.GluttonyEffect;
import com.sammy.malum.common.effect.InfernalAura;
import com.sammy.malum.common.effect.WickedIntentEffect;
import com.sammy.malum.common.enchantment.ReboundEnchantment;
import com.sammy.malum.common.entity.nitrate.EthericExplosion;
import com.sammy.malum.common.item.equipment.curios.*;
import com.sammy.malum.compability.create.CreateCompat;
import com.sammy.malum.compability.tetra.TetraCompat;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.listeners.ReapingDataReloadListener;
import com.sammy.malum.core.listeners.SpiritDataReloadListener;
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
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.ExplosionEvent;
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
    public static void onEntityJoin(EntityJoinWorldEvent event) {
        MalumPlayerDataCapability.playerJoin(event);
        CurioTokenOfGratitude.giveItem(event);
        SoulHarvestHandler.addEntity(event);
        if (TetraCompat.LOADED) {
            TetraCompat.LoadedOnly.fireArrow(event);
        }
    }


    @SubscribeEvent
    public static void playerLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        BlockPos pos = event.getPos();
        Level level = event.getWorld();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof SpiritJarBlock jarBlock) {
            Player player = event.getPlayer();
            BlockHitResult target = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
            if (target.getType() == HitResult.Type.BLOCK && target.getBlockPos().equals(pos) && target.getDirection().getAxis() == Direction.Axis.X) {
                if (player.isCreative()) {
                    event.setCanceled(jarBlock.handleAttack(level, pos, player));
                }
            }
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
    public static void onLivingVisibility(LivingEvent.LivingVisibilityEvent event) {
        CurioHarmonyNecklace.preventDetection(event);
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        SoulHarvestHandler.entityTick(event);
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
        SoulWardHandler.recoverSoulWard(event);
        SoulHarvestHandler.playerTick(event);
    }

    @SubscribeEvent
    public static void registerListeners(AddReloadListenerEvent event) {
        SpiritDataReloadListener.register(event);
        ReapingDataReloadListener.register(event);
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ReboundEnchantment.onRightClickItem(event);
    }

    @SubscribeEvent
    public static void isPotionApplicable(PotionEvent.PotionApplicableEvent event) {
        GluttonyEffect.canApplyPotion(event);
    }

    @SubscribeEvent
    public static void onPotionApplied(PotionEvent.PotionAddedEvent event) {
        CurioAlchemicalRing.onPotionApplied(event);
    }

    @SubscribeEvent
    public static void onStartUsingItem(LivingEntityUseItemEvent.Start event) {
        CurioVoraciousRing.accelerateEating(event);
    }

    @SubscribeEvent
    public static void onFinishUsingItem(LivingEntityUseItemEvent.Finish event) {
        CurioVoraciousRing.finishEating(event);
        GluttonyEffect.finishEating(event);
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        if (CreateCompat.LOADED) {
            CreateCompat.LoadedOnly.convertCaramelToMagicDamage(event);
        }
        MalumAttributeEventHandler.processAttributes(event);
        SpiritHarvestHandler.exposeSoul(event);
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
        ReapingHandler.tryCreateReapingDrops(event);
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

