package com.sammy.malum.core.handlers;

import com.sammy.malum.common.components.MalumComponents;
import com.sammy.malum.common.components.MalumLivingEntityDataComponent;
import com.sammy.malum.common.container.SpiritPouchContainer;
import com.sammy.malum.common.entity.spirit.SpiritItemEntity;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.SpiritPouchItem;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.listeners.SpiritDataReloadListener;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.core.systems.spirit.EntitySpiritDropData;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.DamageTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.ItemHelper;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.container.ItemInventory;

import java.util.*;
import java.util.stream.Collectors;

import static net.minecraft.world.entity.EquipmentSlot.MAINHAND;

public class SpiritHarvestHandler {


    public static boolean shatterSoul(LivingEntity target, DamageSource source, float v) {
        LivingEntity attacker = null;
        if (source.getEntity() instanceof LivingEntity directAttacker) {
            attacker = directAttacker;
        }
        if (attacker == null) {
            attacker = target.getLastHurtByMob();
        }
        if (attacker == null && source.is(DamageTypeRegistry.VOODOO)) {
            spawnSpirits(target);
            return true;
        }
        if (attacker != null) {
            ItemStack stack = SoulDataHandler.getSoulHunterWeapon(source, attacker);
            if (!(target instanceof Player)) {
                SoulDataHandler soulData = MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.get(target).soulData;
                if (CommonConfig.SOULLESS_SPAWNERS.getConfigValue() && soulData.spawnerSpawned) {
                    return true;
                }
                if (soulData.exposedSoulDuration > 0 && !soulData.soulless) {
                    spawnSpirits(target, attacker, stack);
                    soulData.soulless = true;
                }
            }
        }
        return true;
    }

    public static boolean modifyDroppedItems(LivingEntity entityLiving, DamageSource damageSource, Collection<ItemEntity> itemEntities, int i, boolean b) {
        MalumLivingEntityDataComponent capability = MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.get(entityLiving);
        if (capability.soulsToApplyToDrops != null) {
            getSpiritData(entityLiving).ifPresent(spiritData -> {
                Ingredient spiritItem = spiritData.spiritItem;
                if (spiritItem != null) {
                    for (ItemEntity itemEntity : itemEntities) {
                        if (spiritItem.test(itemEntity.getItem())) {
                            MalumComponents.MALUM_ITEM_COMPONENT.maybeGet(itemEntity).ifPresent((e) -> {
                                e.soulsToDrop = capability.soulsToApplyToDrops.stream().map(ItemStack::copy).collect(Collectors.toList());
                                e.attackerForSouls = capability.killerUUID;
                                e.totalSoulCount = spiritData.totalSpirits;
                            });
                            itemEntity.setNeverPickUp();
                            itemEntity.age = 6000 - 20;//Magic number
                            itemEntity.setNoGravity(true);
                            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().multiply(1, 0.5, 1));
                        }
                    }
                }
            });
        }
        return b;
    }


    public static int shatterItem(ItemEntity entity, ItemStack itemStack) {
        if (entity.level() instanceof ServerLevel level) {
            MalumComponents.MALUM_ITEM_COMPONENT.maybeGet(entity).ifPresent((e) -> {
                LivingEntity attacker = null;
                if (e.attackerForSouls != null) {
                    if (level.getEntity(e.attackerForSouls) instanceof LivingEntity living) {
                        attacker = living;
                    }
                }

                if (e.soulsToDrop != null) {
                    List<ItemStack> stacks = new ArrayList<>();
                    for (int i = 0; i < entity.getItem().getCount(); i++) {
                        e.soulsToDrop.stream().map(ItemStack::copy).forEach(stacks::add);
                    }
                    createSpiritEntities(level, stacks, entity.position(), e.totalSoulCount, attacker);
                }
            });
        }
        return -1;
    }

    public static void pickupSpirit(LivingEntity collector, ItemStack stack) {
        if (collector instanceof Player player) {
            AttributeInstance instance = player.getAttribute(AttributeRegistry.ARCANE_RESONANCE.get());
            ItemHelper.getEventResponders(collector).forEach(s -> {
                if (s.getItem() instanceof IMalumEventResponderItem eventItem) {
                    eventItem.pickupSpirit(collector, instance != null ? instance.getValue() : 0);
                }
            });
            for (NonNullList<ItemStack> playerInventory : player.getInventory().compartments) {
                for (ItemStack item : playerInventory) {
                    if (item.getItem() instanceof SpiritPouchItem) {
                        ItemInventory inventory = SpiritPouchItem.getInventory(item);
                        ItemStack result = inventory.addItem(stack);
                        if (result.isEmpty()) {
                            Level level = player.level();
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
        ItemHelper.giveItemToEntity(collector, stack);
    }

    public static void spawnSpirits(LivingEntity target) {
        spawnSpirits(getSpiritDropsRaw(target), target, null);
    }

    public static void spawnSpirits(LivingEntity target, LivingEntity attacker, ItemStack harvestStack) {
        spawnSpirits(getSpawnedSpirits(target, attacker, harvestStack), target, attacker);
    }

    public static void spawnSpirits(List<ItemStack> spirits, LivingEntity target, LivingEntity attacker) {
        if (spirits.isEmpty()) {
            return;
        }
        getSpiritData(target).ifPresent(data -> {
            if (data.spiritItem != null) {
                MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.maybeGet(target).ifPresent((e) -> {
                    e.soulsToApplyToDrops = spirits;
                    if (attacker != null) {
                        e.killerUUID = attacker.getUUID();
                    }
                });
            } else {
                spawnItemsAsSpirits(spirits, target, attacker);
            }
        });
    }

    public static void spawnItemAsSpirit(ItemStack spirit, LivingEntity target, LivingEntity attacker) {
        spawnItemsAsSpirits(List.of(spirit), target, attacker);
    }

    public static void spawnItemsAsSpirits(Collection<ItemStack> spirits, LivingEntity target, LivingEntity attacker) {
        createSpiritEntities(target.level(), spirits, target.position().add(0, target.getBbHeight() / 2f, 0), spirits.stream().mapToInt(ItemStack::getCount).sum(), attacker);
    }

    private static void createSpiritEntities(Level level, Collection<ItemStack> spirits, Vec3 position, float totalCount, @Nullable LivingEntity attacker) {
        if (attacker == null) {
            attacker = level.getNearestPlayer(position.x, position.y, position.z, 8, e -> true);
        }
        final UUID attackerUUID = attacker == null ? null : attacker.getUUID();
        float speed = 0.15f + 0.25f / (totalCount + 1);
        var random = level.random;
        for (ItemStack stack : spirits) {
            if (stack.isEmpty()) {
                continue;
            }
            boolean noFancySpirits = CommonConfig.NO_FANCY_SPIRITS.getConfigValue();
            for (int j = 0; j < stack.getCount(); j++) {
                if (noFancySpirits) {
                    ItemEntity itemEntity = new ItemEntity(level, position.x, position.y, position.z, stack);
                    itemEntity.setDefaultPickUpDelay();
                    itemEntity.setDeltaMovement(
                            Mth.nextFloat(random, -0.1F, 0.1F),
                            Mth.nextFloat(random, 0.25f, 0.5f),
                            Mth.nextFloat(random, -0.1F, 0.1F));
                    level.addFreshEntity(itemEntity);
                    continue;
                }
                SpiritItemEntity entity = new SpiritItemEntity(level, attackerUUID, ItemHelper.copyWithNewCount(stack, 1),
                        position.x,
                        position.y,
                        position.z,
                        RandomHelper.randomBetween(random, -speed, speed),
                        RandomHelper.randomBetween(random, 0.05f, 0.06f),
                        RandomHelper.randomBetween(random, -speed, speed));
                level.addFreshEntity(entity);
            }
        }
        level.playSound(null, position.x, position.y, position.z, SoundRegistry.SOUL_SHATTER.get(), SoundSource.PLAYERS, 1.0F, 0.7f + random.nextFloat() * 0.4f);
    }

    public static List<ItemStack> getSpawnedSpirits(LivingEntity entity, LivingEntity attacker, ItemStack harvestStack) {
        return getSpiritData(entity).map(data -> applySpiritLootBonuses(getSpiritDropsRaw(data), attacker, harvestStack)).orElse(Collections.emptyList());
    }

    public static List<ItemStack> applySpiritLootBonuses(List<ItemStack> spirits, LivingEntity attacker, ItemStack weapon) {
        if (spirits.isEmpty()) {
            return spirits;
        }
        int spiritBonus = 0;
        if (attacker.getAttribute(AttributeRegistry.SPIRIT_SPOILS.get()) != null) {
            spiritBonus += attacker.getAttributeValue(AttributeRegistry.SPIRIT_SPOILS.get());
        }
        if (!weapon.isEmpty()) {
            final int spiritPlunder = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.SPIRIT_PLUNDER.get(), weapon);
            if (spiritPlunder > 0) {
                weapon.hurtAndBreak(spiritPlunder, attacker, (e) -> e.broadcastBreakEvent(MAINHAND));
            }
            spiritBonus += spiritPlunder;
        }
        for (int i = 0; i < spiritBonus; i++) {
            int random = attacker.getRandom().nextInt(spirits.size());
            spirits.get(random).grow(1);
        }
        return spirits;
    }

    public static List<ItemStack> getSpiritDropsRaw(LivingEntity entity) {
        return getSpiritData(entity).map(SpiritHarvestHandler::getSpiritDropsRaw).orElse(Collections.emptyList());
    }

    public static List<ItemStack> getSpiritDropsRaw(EntitySpiritDropData data) {
        return data != null ? data.dataEntries.stream().map(SpiritWithCount::getStack).collect(Collectors.toList()) : (Collections.emptyList());
    }

    public static Optional<EntitySpiritDropData> getSpiritData(LivingEntity entity) {
        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (SpiritDataReloadListener.HAS_NO_DATA.contains(key))
            return Optional.empty();

        EntitySpiritDropData spiritData = SpiritDataReloadListener.SPIRIT_DATA.get(key);
        if (spiritData != null)
            return Optional.of(spiritData);

        if (!entity.canChangeDimensions())
            return Optional.of(SpiritDataReloadListener.DEFAULT_BOSS_SPIRIT_DATA);

        if (!CommonConfig.USE_DEFAULT_SPIRIT_VALUES.getConfigValue())
            return Optional.empty();

        return switch (entity.getType().getCategory()) {
            case MONSTER -> Optional.of(SpiritDataReloadListener.DEFAULT_MONSTER_SPIRIT_DATA);
            case CREATURE -> Optional.of(SpiritDataReloadListener.DEFAULT_CREATURE_SPIRIT_DATA);
            case AMBIENT -> Optional.of(SpiritDataReloadListener.DEFAULT_AMBIENT_SPIRIT_DATA);
            case AXOLOTLS -> Optional.of(SpiritDataReloadListener.DEFAULT_AXOLOTL_SPIRIT_DATA);
            case UNDERGROUND_WATER_CREATURE ->
                    Optional.of(SpiritDataReloadListener.DEFAULT_UNDERGROUND_WATER_CREATURE_SPIRIT_DATA);
            case WATER_CREATURE -> Optional.of(SpiritDataReloadListener.DEFAULT_WATER_CREATURE_SPIRIT_DATA);
            case WATER_AMBIENT -> Optional.of(SpiritDataReloadListener.DEFAULT_WATER_AMBIENT_SPIRIT_DATA);
            default -> Optional.empty();
        };
    }

    public static MalumSpiritType getSpiritType(String spirit) {
        return SpiritTypeRegistry.SPIRITS.getOrDefault(spirit, SpiritTypeRegistry.SACRED_SPIRIT);
    }

}