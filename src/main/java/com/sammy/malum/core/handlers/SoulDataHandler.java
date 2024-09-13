package com.sammy.malum.core.handlers;

import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.entity.boomerang.*;
import com.sammy.malum.compability.tetra.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.living.*;

public class SoulDataHandler {

    public float exposedSoulDuration;
    public boolean soulless;
    public boolean spawnerSpawned;


    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (exposedSoulDuration != 0) {
            tag.putFloat("exposedSoulDuration", exposedSoulDuration);
        }
        tag.putBoolean("soulless", soulless);
        tag.putBoolean("spawnerSpawned", spawnerSpawned);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        exposedSoulDuration = tag.getFloat("exposedSoulDuration");
        soulless = tag.getBoolean("soulless");
        spawnerSpawned = tag.getBoolean("spawnerSpawned");
    }

    public static void markAsSpawnerSpawned(MobSpawnEvent.PositionCheck event) {
        if (event.getSpawner() != null) {
            MalumLivingEntityDataCapability.getCapabilityOptional(event.getEntity()).ifPresent(ec -> {
                SoulDataHandler soulData = ec.soulData;
                soulData.spawnerSpawned = true;
            });
        }
    }

    public static void updateAi(EntityJoinLevelEvent event) {
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
        if (event.getEntity() instanceof Mob mob) {
            MalumLivingEntityDataCapability.getCapabilityOptional(mob).ifPresent(ec -> {
                SoulDataHandler soulData = ec.soulData;
                if (soulData.soulless) {
                    event.setNewTarget(null);
                }
            });
        }
    }

    public static void exposeSoul(LivingHurtEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0) {
            return;
        }
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(target).soulData;
        if (source.getEntity() instanceof LivingEntity attacker) {
            ItemStack stack = getSoulHunterWeapon(source, attacker);
            if (stack.is(ItemTagRegistry.SOUL_HUNTER_WEAPON) || (TetraCompat.LOADED && TetraCompat.LoadedOnly.hasSoulStrike(stack))) {
                soulData.exposedSoulDuration = 200;
            }
        }
        if (source.getDirectEntity() != null && source.getDirectEntity().getTags().contains("malum:soul_arrow")) {
            soulData.exposedSoulDuration = 200;
        }
    }

    public static void manageSoul(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(entity).soulData;
        if (soulData.exposedSoulDuration > 0) {
            soulData.exposedSoulDuration--;
        }
    }

    public static void removeSentience(Mob mob) {
        mob.goalSelector.getAvailableGoals().removeIf(g -> g.getGoal() instanceof LookAtPlayerGoal || g.getGoal() instanceof MeleeAttackGoal || g.getGoal() instanceof SwellGoal || g.getGoal() instanceof PanicGoal || g.getGoal() instanceof RandomLookAroundGoal || g.getGoal() instanceof AvoidEntityGoal);
    }

    public static ItemStack getSoulHunterWeapon(DamageSource source, LivingEntity attacker) {
        ItemStack stack = attacker.getMainHandItem();

        if (source.getDirectEntity() instanceof ScytheBoomerangEntity scytheBoomerang) {
            stack = scytheBoomerang.getItem();
        }
        return stack;
    }
}
