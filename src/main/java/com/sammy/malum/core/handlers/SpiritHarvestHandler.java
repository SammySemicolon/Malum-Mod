package com.sammy.malum.core.handlers;

import com.sammy.malum.common.capability.MalumItemDataCapability;
import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.common.container.SpiritPouchContainer;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.spirit.SpiritPouchItem;
import com.sammy.malum.compability.tetra.TetraCompat;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.DamageSourceRegistry;
import com.sammy.malum.core.setup.content.item.ItemTagRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import team.lodestar.lodestone.helpers.ItemHelper;
import team.lodestar.lodestone.systems.container.ItemInventory;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpiritHarvestHandler {

    public static void exposeSoul(LivingHurtEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0) {
            return;
        }
        LivingEntity target = event.getEntityLiving();
        DamageSource source = event.getSource();
        if (source.getEntity() instanceof LivingEntity attacker) {
            ItemStack stack = attacker.getMainHandItem();
            if (source.getDirectEntity() instanceof ScytheBoomerangEntity) {
                stack = ((ScytheBoomerangEntity) source.getDirectEntity()).scythe;
            }

            if (stack.is(ItemTagRegistry.SOUL_HUNTER_WEAPON) || (TetraCompat.LOADED && TetraCompat.LoadedOnly.hasSoulStrike(stack))) {
                MalumLivingEntityDataCapability.getCapabilityOptional(target).ifPresent(e -> e.exposedSoul = 200);
            }
        }
        if (source.getDirectEntity() != null && source.getDirectEntity().getTags().contains("malum:soul_arrow")) {
            MalumLivingEntityDataCapability.getCapabilityOptional(target).ifPresent(e -> e.exposedSoul = 200);
        }
    }

    public static void shatterSoul(LivingDeathEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (event.getSource().getMsgId().equals(DamageSourceRegistry.SOUL_STRIKE_IDENTIFIER)) {
            SpiritHelper.createSpiritEntities(event.getEntityLiving());
            return;
        }
        LivingEntity target = event.getEntityLiving();
        LivingEntity attacker = null;
        if (event.getSource().getEntity() instanceof LivingEntity directAttacker) {
            attacker = directAttacker;
        }
        if (attacker == null) {
            attacker = target.getLastHurtByMob();
        }
        if (attacker != null) {
            LivingEntity finalAttacker = attacker;
            ItemStack stack = attacker.getMainHandItem();
            DamageSource source = event.getSource();
            if (source.getDirectEntity() instanceof ScytheBoomerangEntity scytheBoomerang) {
                stack = scytheBoomerang.scythe;
            }
            if (!(target instanceof Player)) {
                ItemStack finalStack = stack;
                MalumLivingEntityDataCapability.getCapabilityOptional(target).ifPresent(e -> {
                    if (e.exposedSoul > 0 && !e.soulless && (!CommonConfig.SOULLESS_SPAWNERS.getConfigValue() || (CommonConfig.SOULLESS_SPAWNERS.getConfigValue() && !e.spawnerSpawned))) {
                        SpiritHelper.createSpiritsFromWeapon(target, finalAttacker, finalStack);
                        e.soulless = true;
                    }
                });
            }
        }
    }

    public static void modifyDroppedItems(LivingDropsEvent event) {
        if (event.isCanceled()) {
            return;
        }
        LivingEntity entityLiving = event.getEntityLiving();
        MalumLivingEntityDataCapability capability = MalumLivingEntityDataCapability.getCapability(entityLiving);
        if (capability.soulsToApplyToDrops != null) {
            MalumEntitySpiritData spiritData = SpiritHelper.getEntitySpiritData(entityLiving);

            Ingredient spiritItem = spiritData.spiritItem;
            if (spiritItem != null) {
                for (ItemEntity itemEntity : event.getDrops()) {
                    if (spiritItem.test(itemEntity.getItem())) {
                        MalumItemDataCapability.getCapabilityOptional(itemEntity).ifPresent((e) -> {
                            e.soulsToDrop = capability.soulsToApplyToDrops.stream().map(ItemStack::copy).collect(Collectors.toList());
                            e.attackerForSouls = capability.killerUUID;
                            e.totalSoulCount = spiritData.totalSpirits;
                        });
                        itemEntity.setNeverPickUp();
                        itemEntity.age = itemEntity.lifespan - 20;
                        itemEntity.setNoGravity(true);
                        itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().multiply(1, 0.5, 1));
                    }
                }
            }
        }
    }

    public static void shatterItem(ItemExpireEvent event) {
        if (event.isCanceled()) {
            return;
        }

        ItemEntity entity = event.getEntityItem();
        if (entity.level instanceof ServerLevel level) {
            MalumItemDataCapability.getCapabilityOptional(entity).ifPresent((e) -> {
                // And here is where particles would go.
                // IF I HAD ANY
                LivingEntity attacker = null;
                if (e.attackerForSouls != null) {
                    Entity candidate = level.getEntity(e.attackerForSouls);
                    if (candidate instanceof LivingEntity living) {
                        attacker = living;
                    }
                }

                if (e.soulsToDrop != null) {
                    List<ItemStack> stacks = new ArrayList<>();
                    for (int i = 0; i < entity.getItem().getCount(); i++)
                        e.soulsToDrop.stream().map(ItemStack::copy).forEach(stacks::add);
                    SpiritHelper.createSpiritEntities(stacks, e.totalSoulCount, level, entity.position(), attacker);
                }
            });
        }
    }

    public static void pickupSpirit(ItemStack stack, LivingEntity collector) {
        if (collector instanceof Player player) {
            AttributeInstance instance = player.getAttribute(AttributeRegistry.ARCANE_RESONANCE.get());
            ItemHelper.getEventResponders(collector).forEach(s -> {
                if (s.getItem() instanceof IMalumEventResponderItem eventItem) {
                    eventItem.pickupSpirit(collector, stack, instance != null ? instance.getValue() : 0);
                }
            });
            for (NonNullList<ItemStack> playerInventory : player.getInventory().compartments) {
                for (ItemStack item : playerInventory) {
                    if (item.getItem() instanceof SpiritPouchItem) {
                        ItemInventory inventory = SpiritPouchItem.getInventory(item);
                        ItemStack result = inventory.addItem(stack);
                        if (result.isEmpty()) {
                            Level level = player.level;
                            level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((level.random.nextFloat() - level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                            if (player.containerMenu instanceof SpiritPouchContainer pouchMenu) {
                                pouchMenu.update(inventory);
                            }
                            return;
                        }
                    }
                }
            }
        }
        ItemHelper.giveItemToEntity(stack, collector);
    }
}