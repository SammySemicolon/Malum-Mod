package com.sammy.malum.core.handlers;

import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.common.item.tools.MalumScytheItem;
import com.sammy.malum.compability.tetra.TetraCompat;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import java.util.UUID;

public class SoulDataHandler {

    public float exposedSoulDuration;
    public boolean soulless;
    public boolean spawnerSpawned;

    public float soulSeparationProgress;
    public UUID soulThiefUUID;

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (exposedSoulDuration != 0) {
            tag.putFloat("exposedSoulDuration", exposedSoulDuration);
        }
        tag.putBoolean("soulless", soulless);
        tag.putBoolean("spawnerSpawned", spawnerSpawned);

        if (soulSeparationProgress != 0) {
            tag.putFloat("soulSeparationProgress", soulSeparationProgress);
        }
        if (soulThiefUUID != null) {
            tag.putUUID("soulThiefUUID", soulThiefUUID);
        }
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        exposedSoulDuration = tag.getFloat("exposedSoulDuration");
        soulless = tag.getBoolean("soulless");
        spawnerSpawned = tag.getBoolean("spawnerSpawned");

        soulSeparationProgress = tag.getFloat("soulSeparationProgress");
        if (tag.contains("soulThiefUUID")) {
            soulThiefUUID = tag.getUUID("soulThiefUUID");
        }
    }

    public static void markAsSpawnerSpawned(LivingSpawnEvent.SpecialSpawn event) {
        if (event.getSpawnReason() != null) {
            if (event.getEntity() instanceof LivingEntity livingEntity) {
                MalumLivingEntityDataCapability.getCapabilityOptional(livingEntity).ifPresent(ec -> {
                    SoulDataHandler soulData = ec.soulData;
                    if (event.getSpawnReason().equals(MobSpawnType.SPAWNER)) {
                        soulData.spawnerSpawned = true;
                    }
                });
            }
        }
    }

    public static void updateAi(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            MalumLivingEntityDataCapability.getCapabilityOptional(livingEntity).ifPresent(ec -> {
                SoulDataHandler soulData = ec.soulData;
                if (livingEntity instanceof Mob mob) {
                    if (soulData.soulless) {
                        removeSentience(mob);
                    }
                }
            });
        }
    }

    public static void preventTargeting(LivingChangeTargetEvent event) {
        if (event.getEntityLiving() instanceof Mob mob) {
            MalumLivingEntityDataCapability.getCapabilityOptional(mob).ifPresent(ec -> {
                SoulDataHandler soulData = ec.soulData;
                if (soulData.soulless) {
                    event.setNewTarget(null);
                }
            });
        }
    }

    public static void exposeSoul(LivingHurtEvent event) {
        //Here we expose an entity's soul if it is struck by a soul hunter weapon, or other means of shattering the soul
        if (event.isCanceled() || event.getAmount() <= 0) {
            return;
        }
        LivingEntity target = event.getEntityLiving();
        DamageSource source = event.getSource();
        SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(target).soulData;
        if (source.getEntity() instanceof LivingEntity attacker) {
            ItemStack stack = MalumScytheItem.getScytheItemStack(source, attacker);

            if (stack.is(ItemTagRegistry.SOUL_HUNTER_WEAPON) || (TetraCompat.LOADED && TetraCompat.LoadedOnly.hasSoulStrike(stack))) {
                soulData.exposedSoulDuration = 200;
            }
        }
        if (source.getDirectEntity() != null && source.getDirectEntity().getTags().contains("malum:soul_arrow")) {
            soulData.exposedSoulDuration = 200;
        }
    }

    public static void manageSoul(LivingEvent.LivingUpdateEvent event) {
        //here we tick down all the data and reset it overtime.
        LivingEntity entity = event.getEntityLiving();
        SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(entity).soulData;
        if (soulData.exposedSoulDuration > 0) {
            soulData.exposedSoulDuration--;
        }

        if (soulData.soulThiefUUID != null && soulData.soulSeparationProgress > 0) {
            Player soulThief = entity.level.getPlayerByUUID(soulData.soulThiefUUID);
            if (soulThief != null) {
                SoulHarvestHandler soulHarvestHandler = MalumPlayerDataCapability.getCapability(soulThief).soulHarvestHandler;

                //If an entity is being targeted by a soul staff, and the isn't using a staff when the entity is past the "targeted" point, we rapidly remove separation progress.
                if (!soulThief.isUsingItem() && soulData.soulSeparationProgress > SoulHarvestHandler.PRIMING_END) {
                    soulData.soulSeparationProgress -= 2f;
                }
                //If the entity isn't soulless, and is in the "targeted" point, we slowly remove separation progress and forget the soul thief.
                if (soulData.soulSeparationProgress <= SoulHarvestHandler.PRIMING_END && !soulData.soulless) {
                    if (soulHarvestHandler.targetedSoulUUID == null || !entity.getUUID().equals(soulHarvestHandler.targetedSoulUUID)) {
                        soulData.soulSeparationProgress -= 0.5f;
                        if (soulData.soulSeparationProgress == 0) {
                            soulData.soulThiefUUID = null;
                        }
                    }
                }
            }
        }
    }

    public static void removeSentience(Mob mob) {
        mob.goalSelector.getAvailableGoals().removeIf(g -> g.getGoal() instanceof LookAtPlayerGoal || g.getGoal() instanceof MeleeAttackGoal || g.getGoal() instanceof SwellGoal || g.getGoal() instanceof PanicGoal || g.getGoal() instanceof RandomLookAroundGoal || g.getGoal() instanceof AvoidEntityGoal);
    }
}
