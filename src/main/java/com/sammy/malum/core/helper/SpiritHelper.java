package com.sammy.malum.core.helper;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.common.entity.spirit.SpiritItemEntity;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.listeners.SpiritDataReloadListener;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.ItemHelper;

import javax.annotation.Nullable;
import java.util.*;
import java.util.List;

import static net.minecraft.util.Mth.nextFloat;
import static net.minecraft.world.entity.EquipmentSlot.MAINHAND;


public class SpiritHelper {

    public static void createSpiritsFromSoul(MalumEntitySpiritData data, Level level, Vec3 position, LivingEntity attacker) {
        List<ItemStack> spirits = getSpiritItemStacks(data, attacker, ItemStack.EMPTY, 2);
        createSpiritEntities(spirits, data.totalSpirits, level, position, attacker);
    }

    public static void createSpiritsFromWeapon(LivingEntity target, LivingEntity attacker, ItemStack harvestStack) {
        List<ItemStack> spirits = getSpiritItemStacks(target, attacker, harvestStack, 1);
        createSpiritEntities(spirits, target, attacker);
    }

    public static void createSpiritEntities(LivingEntity target, LivingEntity attacker) {
        List<ItemStack> spirits = getSpiritItemStacks(target);
        if (!spirits.isEmpty()) {
            createSpiritEntities(spirits, target, attacker);
        }
    }

    public static void createSpiritEntities(LivingEntity target) {
        List<ItemStack> spirits = getSpiritItemStacks(target);
        if (!spirits.isEmpty()) {
            createSpiritEntities(spirits, target, null);
        }
    }

    public static void createSpiritEntities(List<ItemStack> spirits, LivingEntity target, LivingEntity attacker) {
        if (spirits.isEmpty()) {
            return;
        }
        MalumEntitySpiritData data = getEntitySpiritData(target);

        if (data.spiritItem != null) {
            MalumLivingEntityDataCapability.getCapabilityOptional(target).ifPresent((e) -> {
                e.soulsToApplyToDrops = spirits;
                if (attacker != null) {
                    e.killerUUID = attacker.getUUID();
                }
            });
        } else {
            createSpiritEntities(spirits, spirits.stream().mapToInt(ItemStack::getCount).sum(), target.level(), target.position().add(0, target.getEyeHeight() / 2f, 0), attacker);
        }
    }

    public static void createSpiritEntities(Collection<ItemStack> spirits, LivingEntity target, LivingEntity attacker) {
        createSpiritEntities(spirits, target, 1, attacker);
    }

    public static void createSpiritEntities(Collection<ItemStack> spirits, LivingEntity target, float speedMultiplier, LivingEntity attacker) {
        if (spirits.isEmpty()) {
            return;
        }
        createSpiritEntities(spirits, spirits.stream().mapToInt(ItemStack::getCount).sum(), target.level(), target.position().add(0, target.getEyeHeight() / 2f, 0), speedMultiplier, attacker);
    }

    public static void createSpiritEntities(MalumEntitySpiritData data, Level level, Vec3 position, LivingEntity attacker) {
        createSpiritEntities(getSpiritItemStacks(data), data.totalSpirits, level, position, attacker);
    }

    public static void createSpiritEntities(Collection<ItemStack> spirits, float totalCount, Level level, Vec3 position, @Nullable LivingEntity attacker) {
        createSpiritEntities(spirits, totalCount, level, position, 1f, attacker);
    }

    public static void createSpiritEntities(Collection<ItemStack> spirits, float totalCount, Level level, Vec3 position, float speedMultiplier, @Nullable LivingEntity attacker) {
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


    public static MalumSpiritType getSpiritType(String spirit) {
        MalumSpiritType type = SpiritTypeRegistry.SPIRITS.get(spirit);
        return type == null ? SpiritTypeRegistry.SACRED_SPIRIT : type;
    }

    public static MalumEntitySpiritData getEntitySpiritData(LivingEntity entity) {
        ResourceLocation key = entity.getType().getRegistryName();
        if (SpiritDataReloadListener.HAS_NO_DATA.contains(key))
            return null;

        MalumEntitySpiritData spiritData = SpiritDataReloadListener.SPIRIT_DATA.get(key);
        if (spiritData != null)
            return spiritData;

        if (!entity.canChangeDimensions())
            return SpiritDataReloadListener.DEFAULT_BOSS_SPIRIT_DATA;

        if (!CommonConfig.USE_DEFAULT_SPIRIT_VALUES.getConfigValue())
            return null;

        return switch (entity.getType().getCategory()) {
            case MONSTER -> SpiritDataReloadListener.DEFAULT_MONSTER_SPIRIT_DATA;
            case CREATURE -> SpiritDataReloadListener.DEFAULT_CREATURE_SPIRIT_DATA;
            case AMBIENT -> SpiritDataReloadListener.DEFAULT_AMBIENT_SPIRIT_DATA;
            case AXOLOTLS -> SpiritDataReloadListener.DEFAULT_AXOLOTL_SPIRIT_DATA;
            case UNDERGROUND_WATER_CREATURE -> SpiritDataReloadListener.DEFAULT_UNDERGROUND_WATER_CREATURE_SPIRIT_DATA;
            case WATER_CREATURE -> SpiritDataReloadListener.DEFAULT_WATER_CREATURE_SPIRIT_DATA;
            case WATER_AMBIENT -> SpiritDataReloadListener.DEFAULT_WATER_AMBIENT_SPIRIT_DATA;
            default -> null;
        };
    }

    public static int getEntitySpiritCount(LivingEntity entity) {
        MalumEntitySpiritData bundle = getEntitySpiritData(entity);
        if (bundle == null) {
            return 0;
        }
        return bundle.totalSpirits;
    }

    public static List<ItemStack> getSpiritItemStacks(LivingEntity entity, LivingEntity attacker, ItemStack harvestStack, float spoilsMultiplier) {
        return getSpiritItemStacks(getEntitySpiritData(entity), attacker, harvestStack, spoilsMultiplier);
    }

    public static List<ItemStack> getSpiritItemStacks(MalumEntitySpiritData data, LivingEntity attacker, ItemStack harvestStack, float spoilsMultiplier) {
        List<ItemStack> spirits = getSpiritItemStacks(data);
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

    public static List<ItemStack> getSpiritItemStacks(LivingEntity entity) {
        return getSpiritItemStacks(getEntitySpiritData(entity));
    }

    public static List<ItemStack> getSpiritItemStacks(MalumEntitySpiritData data) {
        List<ItemStack> spirits = new ArrayList<>();
        if (data == null) {
            return spirits;
        }
        for (SpiritWithCount spiritWithCount : data.dataEntries) {
            spirits.add(new ItemStack(spiritWithCount.type.spiritShard.get(), spiritWithCount.count));
        }
        return spirits;
    }

}