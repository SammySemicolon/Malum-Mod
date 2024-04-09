package com.sammy.malum.core.handlers;

import com.sammy.malum.*;
import com.sammy.malum.common.capability.MalumItemDataCapability;
import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.common.container.SpiritPouchContainer;
import com.sammy.malum.common.entity.spirit.*;
import com.sammy.malum.common.item.curiosities.SpiritPouchItem;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.core.listeners.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.helpers.ItemHelper;
import team.lodestar.lodestone.systems.container.ItemInventory;

import javax.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

import static net.minecraft.util.Mth.nextFloat;
import static net.minecraft.world.entity.EquipmentSlot.MAINHAND;

public class SpiritHarvestHandler {

    public static void shatterSoul(LivingDeathEvent event) {
        if (event.isCanceled()) {
            return;
        }
        DamageSource source = event.getSource();
        LivingEntity target = event.getEntity();
        LivingEntity attacker = null;
        if (event.getSource().getEntity() instanceof LivingEntity directAttacker) {
            attacker = directAttacker;
        }
        if (attacker == null) {
            attacker = target.getLastHurtByMob();
        }
        if (attacker == null && source.is(DamageTypeRegistry.VOODOO)) {
            createSpirits(event.getEntity());
            return;
        }
        if (attacker != null) {
            ItemStack stack = SoulDataHandler.getSoulHunterWeapon(source, attacker);
            if (!(target instanceof Player)) {
                SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(target).soulData;
                if (soulData.exposedSoulDuration > 0 && !soulData.soulless && (!CommonConfig.SOULLESS_SPAWNERS.getConfigValue() || (CommonConfig.SOULLESS_SPAWNERS.getConfigValue() && !soulData.spawnerSpawned))) {
                    createSpirits(target, attacker, stack);
                    soulData.soulless = true;
                }
            }
        }
    }

    public static void modifyDroppedItems(LivingDropsEvent event) {
        if (event.isCanceled()) {
            return;
        }
        LivingEntity entityLiving = event.getEntity();
        MalumLivingEntityDataCapability capability = MalumLivingEntityDataCapability.getCapability(entityLiving);
        if (capability.soulsToApplyToDrops != null) {
            getSpiritData(entityLiving).ifPresent(spiritData -> {
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
            });
        }
    }

    public static void shatterItem(ItemExpireEvent event) {
        if (event.isCanceled()) {
            return;
        }

        ItemEntity entity = event.getEntity();
        if (entity.level() instanceof ServerLevel level) {
            MalumItemDataCapability.getCapabilityOptional(entity).ifPresent((e) -> {
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
                    createSpirits(stacks, e.totalSoulCount, level, entity.position(), attacker);
                }
            });
        }
    }

    public static void pickupSpirit(LivingEntity collector, ItemStack stack) {
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

    public static Optional<MalumEntitySpiritData> getSpiritData(LivingEntity entity) {
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (SpiritDataReloadListener.HAS_NO_DATA.contains(key))
            return Optional.empty();

        MalumEntitySpiritData spiritData = SpiritDataReloadListener.SPIRIT_DATA.get(key);
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
            case UNDERGROUND_WATER_CREATURE -> Optional.of(SpiritDataReloadListener.DEFAULT_UNDERGROUND_WATER_CREATURE_SPIRIT_DATA);
            case WATER_CREATURE -> Optional.of(SpiritDataReloadListener.DEFAULT_WATER_CREATURE_SPIRIT_DATA);
            case WATER_AMBIENT -> Optional.of(SpiritDataReloadListener.DEFAULT_WATER_AMBIENT_SPIRIT_DATA);
            default -> Optional.empty();
        };
    }

    public static int getSpiritCount(LivingEntity entity) {
        return getSpiritData(entity).map(d -> d.totalSpirits).orElse(0);
    }

    public static void createSpirits(LivingEntity target, LivingEntity attacker, ItemStack harvestStack) {
        createSpirits(getSpiritItems(target, attacker, harvestStack, 1), target, attacker);
    }

    public static void createSpirits(LivingEntity target) {
        List<ItemStack> spirits = getSpiritItems(target);
        if (!spirits.isEmpty()) {
            createSpirits(spirits, target, null);
        }
    }

    public static void createSpirits(List<ItemStack> spirits, LivingEntity target, LivingEntity attacker) {
        if (spirits.isEmpty()) {
            return;
        }
        getSpiritData(target).ifPresent(data -> {
            if (data.spiritItem != null) {
                MalumLivingEntityDataCapability.getCapabilityOptional(target).ifPresent((e) -> {
                    e.soulsToApplyToDrops = spirits;
                    if (attacker != null) {
                        e.killerUUID = attacker.getUUID();
                    }
                });
            } else {
                createSpirits(spirits, spirits.stream().mapToInt(ItemStack::getCount).sum(), target.level(), target.position().add(0, target.getEyeHeight() / 2f, 0), attacker);
            }
        });
    }

    public static void createSpirits(Collection<ItemStack> spirits, LivingEntity target, float speedMultiplier, LivingEntity attacker) {
        if (spirits.isEmpty()) {
            return;
        }
        createSpirits(spirits, spirits.stream().mapToInt(ItemStack::getCount).sum(), target.level(), target.position().add(0, target.getEyeHeight() / 2f, 0), speedMultiplier, attacker);
    }

    public static void createSpirits(Collection<ItemStack> spirits, float totalCount, Level level, Vec3 position, @Nullable LivingEntity attacker) {
        createSpirits(spirits, totalCount, level, position, 1f, attacker);
    }

    public static void createSpirits(Collection<ItemStack> spirits, float totalCount, Level level, Vec3 position, float speedMultiplier, @Nullable LivingEntity attacker) {
        if (attacker == null) {
            attacker = level.getNearestPlayer(position.x, position.y, position.z, 8, e -> true);
        }
        float speed = (0.15f + 0.25f / (totalCount + 1)) * speedMultiplier;
        var random = level.random;
        for (ItemStack stack : spirits) {
            int count = stack.getCount();
            if (count == 0) {
                continue;
            }
            for (int j = 0; j < count; j++) {
                if (CommonConfig.NO_FANCY_SPIRITS.getConfigValue()) {
                    ItemEntity itemEntity = new ItemEntity(level, position.x, position.y, position.z, stack);
                    itemEntity.setDefaultPickUpDelay();
                    itemEntity.setDeltaMovement(Mth.nextFloat(random, -0.1F, 0.1F), Mth.nextFloat(random, 0.25f, 0.5f), Mth.nextFloat(random, -0.1F, 0.1F));
                    level.addFreshEntity(itemEntity);
                    continue;
                }
                SpiritItemEntity entity = new SpiritItemEntity(level, attacker == null ? null : attacker.getUUID(), ItemHelper.copyWithNewCount(stack, 1),
                        position.x,
                        position.y,
                        position.z,
                        nextFloat(MalumMod.RANDOM, -speed, speed),
                        nextFloat(MalumMod.RANDOM, 0.05f, 0.06f),
                        nextFloat(MalumMod.RANDOM, -speed, speed));
                level.addFreshEntity(entity);
            }
        }
        level.playSound(null, position.x, position.y, position.z, SoundRegistry.SOUL_SHATTER.get(), SoundSource.PLAYERS, 1.0F, 0.7f + random.nextFloat() * 0.4f);
    }

    public static List<ItemStack> getSpiritItems(LivingEntity entity, LivingEntity attacker, ItemStack harvestStack, float spoilsMultiplier) {
        return getSpiritData(entity).map(data -> getSpiritItems(data, attacker, harvestStack, spoilsMultiplier)).orElse(Collections.emptyList());
    }

    public static List<ItemStack> getSpiritItems(MalumEntitySpiritData data, LivingEntity attacker, ItemStack harvestStack, float spoilsMultiplier) {
        List<ItemStack> spirits = getSpiritItems(data);
        if (spirits.isEmpty()) {
            return spirits;
        }
        int spiritBonus = 0;
        if (attacker.getAttribute(AttributeRegistry.SPIRIT_SPOILS.get()) != null) {
            spiritBonus += attacker.getAttributeValue(AttributeRegistry.SPIRIT_SPOILS.get());
        }
        if (!harvestStack.isEmpty()) {
            int spiritPlunder = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.SPIRIT_PLUNDER.get(), harvestStack);
            if (spiritPlunder > 0) {
                harvestStack.hurtAndBreak(spiritPlunder, attacker, (e) -> e.broadcastBreakEvent(MAINHAND));
            }
            spiritBonus += spiritPlunder;
        }
        for (int i = 0; i < spiritBonus * spoilsMultiplier; i++) {
            int random = attacker.level().random.nextInt(spirits.size());
            spirits.get(random).grow(1);
        }
        return spirits;
    }

    public static List<ItemStack> getSpiritItems(LivingEntity entity) {
        return getSpiritData(entity).map(SpiritHarvestHandler::getSpiritItems).orElse(Collections.emptyList());
    }

    public static List<ItemStack> getSpiritItems(MalumEntitySpiritData data) {
        return data != null ? data.dataEntries.stream().map(SpiritWithCount::getStack).collect(Collectors.toList()) : (Collections.emptyList());
    }

    public static MalumSpiritType getSpiritType(String spirit) {
        MalumSpiritType type = SpiritTypeRegistry.SPIRITS.get(spirit);
        return type == null ? SpiritTypeRegistry.SACRED_SPIRIT : type;
    }
}