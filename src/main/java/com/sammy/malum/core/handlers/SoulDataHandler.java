package com.sammy.malum.core.handlers;

import com.sammy.malum.common.components.MalumComponents;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import net.minecraft.nbt.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class SoulDataHandler {

    public static final String SOUL_SHATTER_ENTITY_TAG = "malum:can_shatter_souls";

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

    public static boolean markAsSpawnerSpawned(Mob mob, LevelAccessor levelAccessor, double v, double v1, double v2, BaseSpawner baseSpawner, MobSpawnType mobSpawnType) {
        if (baseSpawner != null && mobSpawnType == MobSpawnType.SPAWNER) {
            MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.maybeGet(mob).ifPresent(ec -> {
                SoulDataHandler soulData = ec.soulData;
                soulData.spawnerSpawned = true;
            });
        }
        return true;
    }


    public static boolean updateAi(Entity entity, Level level, boolean b) {
        if (entity instanceof LivingEntity livingEntity) {
            MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.maybeGet(livingEntity).ifPresent(ec -> {
                SoulDataHandler soulData = ec.soulData;
                if (livingEntity instanceof Mob mob) {
                    if (soulData.soulless) {
                        removeSentience(mob);
                    }
                }
            });
        }
        return true;
    }

    public static void preventTargeting(LivingEntity targeting, LivingEntity target) {
        if (targeting instanceof Mob mob) {
            MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.maybeGet(mob).ifPresent(ec -> {
                SoulDataHandler soulData = ec.soulData;
                if (soulData.soulless) {
                    //TODO portinglib issuemob.setTarget(null);
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
        SoulDataHandler soulData = MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.get(target).soulData;
        if (source.getEntity() instanceof LivingEntity attacker) {
            ItemStack stack = getSoulHunterWeapon(source, attacker);
            if (stack.is(ItemTagRegistry.SOUL_HUNTER_WEAPON) /*|| (TetraCompat.LOADED && TetraCompat.LoadedOnly.hasSoulStrike(stack))*/) {
                soulData.exposedSoulDuration = 200;
            }
        }
        var directEntity = source.getDirectEntity();
        if (directEntity != null && directEntity.getTags().contains(SOUL_SHATTER_ENTITY_TAG)) {
            exposeSoul(target);
        }
    }
    public static void exposeSoul(LivingEntity entity) {
        SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(entity).soulData;
        soulData.exposedSoulDuration = 200;

    }

    public static void manageSoul(LivingEntityEvents.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        SoulDataHandler soulData = MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.get(entity).soulData;
        if (soulData.exposedSoulDuration > 0) {
            soulData.exposedSoulDuration--;
        }
    }

    public static void removeSentience(Mob mob) {
        mob.goalSelector.getAvailableGoals().removeIf(g -> g.getGoal() instanceof LookAtPlayerGoal || g.getGoal() instanceof MeleeAttackGoal || g.getGoal() instanceof SwellGoal || g.getGoal() instanceof PanicGoal || g.getGoal() instanceof RandomLookAroundGoal || g.getGoal() instanceof AvoidEntityGoal);
    }

    public static ItemStack getScytheWeapon(DamageSource source, LivingEntity attacker) {
        var soulHunterWeapon = getSoulHunterWeapon(source, attacker);
        return soulHunterWeapon.getItem() instanceof MalumScytheItem ? soulHunterWeapon : ItemStack.EMPTY;
    }

    public static ItemStack getSoulHunterWeapon(DamageSource source, LivingEntity attacker) {
        ItemStack stack = attacker.getMainHandItem();

        if (source.getDirectEntity() instanceof ScytheBoomerangEntity scytheBoomerang) {
            stack = scytheBoomerang.getItem();
        }
        return stack;
    }
}
