package com.sammy.malum.core.helper;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.common.entity.spirit.PlayerBoundItemEntity;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.listeners.SpiritDataReloadListener;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.setup.content.item.MalumEnchantments;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.Minecraft;
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
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.setup.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

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
            createSpiritEntities(spirits, spirits.stream().mapToInt(ItemStack::getCount).sum(), target.level, target.position().add(0, target.getEyeHeight() / 2f, 0), attacker);
        }
    }

    public static void createSpiritEntities(Collection<ItemStack> spirits, LivingEntity target, LivingEntity attacker) {
        createSpiritEntities(spirits, target, 1, attacker);
    }

    public static void createSpiritEntities(Collection<ItemStack> spirits, LivingEntity target, float speedMultiplier, LivingEntity attacker) {
        if (spirits.isEmpty()) {
            return;
        }
        createSpiritEntities(spirits, spirits.stream().mapToInt(ItemStack::getCount).sum(), target.level, target.position().add(0, target.getEyeHeight() / 2f, 0), speedMultiplier, attacker);
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
        Random random = level.random;
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
                PlayerBoundItemEntity entity = new PlayerBoundItemEntity(level, attacker == null ? null : attacker.getUUID(), ItemHelper.copyWithNewCount(stack, 1),
                        position.x,
                        position.y,
                        position.z,
                        nextFloat(MalumMod.RANDOM, -speed, speed),
                        nextFloat(MalumMod.RANDOM, 0.05f, 0.06f),
                        nextFloat(MalumMod.RANDOM, -speed, speed));
                level.addFreshEntity(entity);
            }
        }
        level.playSound(null, position.x, position.y, position.z, SoundRegistry.SPIRIT_HARVEST.get(), SoundSource.PLAYERS, 1.0F, 0.7f + random.nextFloat() * 0.4f);
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
            int spiritPlunder = EnchantmentHelper.getItemEnchantmentLevel(MalumEnchantments.SPIRIT_PLUNDER.get(), harvestStack);
            if (spiritPlunder > 0) {
                harvestStack.hurtAndBreak(spiritPlunder, attacker, (e) -> e.broadcastBreakEvent(MAINHAND));
            }
            spiritBonus += spiritPlunder;
        }
        for (int i = 0; i < spiritBonus * spoilsMultiplier; i++) {
            int random = attacker.level.random.nextInt(spirits.size());
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
            spirits.add(new ItemStack(spiritWithCount.type.getSplinterItem(), spiritWithCount.count));
        }
        return spirits;
    }

    public static void spawnSpiritGlimmerParticles(Level level, double x, double y, double z, Color color, Color endColor) {
        spawnSpiritGlimmerParticles(level, x, y, z, 1, Vec3.ZERO, color, endColor);
    }

    public static void spawnSpiritGlimmerParticles(Level level, double x, double y, double z, float alphaMultiplier, Vec3 extraVelocity, Color color, Color endColor) {
        Random rand = level.getRandom();
        ParticleBuilders.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                .setAlpha(0.4f * alphaMultiplier, 0f)
                .setLifetime(5 + rand.nextInt(4))
                .setScale(0.25f + rand.nextFloat() * 0.1f, 0)
                .setColor(color, endColor)
                .setColorCoefficient(2f)
                .randomOffset(0.05f)
                .enableNoClip()
                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .randomMotion(0.02f, 0.02f)
                .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.INVISIBLE)
                .repeat(level, x, y, z, 1);

        spawnSpiritParticles(level, x, y, z, 1, extraVelocity, color, endColor);
    }

    public static void spawnSpiritParticles(Level level, double x, double y, double z, Color color, Color endColor) {
        spawnSpiritParticles(level, x, y, z, 1, Vec3.ZERO, color, endColor);
    }

    public static void spawnSpiritParticles(Level level, double x, double y, double z, float alphaMultiplier, Vec3 extraVelocity, Color color, Color endColor) {
        Random rand = level.getRandom();
        ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.275f * alphaMultiplier, 0f)
                .setLifetime(15 + rand.nextInt(4))
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setScale(0.05f + rand.nextFloat() * 0.025f, 0)
                .setColor(color, endColor)
                .setColorCoefficient(1.25f)
                .randomOffset(0.02f)
                .enableNoClip()
                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .randomMotion(0.01f, 0.01f)
                .repeat(level, x, y, z, 1)
                .setAlpha(0.2f * alphaMultiplier, 0f)
                .setLifetime(10 + rand.nextInt(2))
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setScale(0.15f + rand.nextFloat() * 0.05f, 0f)
                .randomMotion(0.01f, 0.01f)
                .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.INVISIBLE)
                .repeat(level, x, y, z, 1);

    }

    public static void spawnSoulParticles(Level level, double x, double y, double z, Color color, Color endColor) {
        spawnSoulParticles(level, x, y, z, 1, 1, Vec3.ZERO, color, endColor);
    }

    public static void spawnSoulParticles(Level level, double x, double y, double z, float alphaMultiplier, float scaleMultiplier, Vec3 extraVelocity, Color color, Color endColor) {
        Random rand = level.getRandom();
        ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.1f * alphaMultiplier, 0)
                .setLifetime(8 + rand.nextInt(5))
                .setScale((0.2f + rand.nextFloat() * 0.2f) * scaleMultiplier, 0)
                .setScaleEasing(Easing.QUINTIC_IN)
                .setColor(color, endColor)
                .randomOffset(0.05f)
                .enableNoClip()
                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .randomMotion(0.01f * scaleMultiplier, 0.01f * scaleMultiplier)
                .repeat(level, x, y, z, 1);

        ParticleBuilders.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0, 0.05f * alphaMultiplier, 0f)
                .setAlphaEasing(Easing.CUBIC_IN, Easing.CUBIC_OUT)
                .setLifetime(80 + rand.nextInt(10))
                .setSpin(0, nextFloat(rand, 0.05f, 0.4f))
                .setSpinOffset(0.05f * level.getGameTime() % 6.28f)
                .setSpinEasing(Easing.CUBIC_OUT)
                .setScale((0.2f + rand.nextFloat() * 0.1f) * scaleMultiplier, 0.1f * scaleMultiplier)
                .setScaleEasing(Easing.QUINTIC_IN)
                .setColor(color, endColor)
                .randomOffset(0.1f)
                .enableNoClip()
                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .randomMotion(0.01f * scaleMultiplier, 0.01f * scaleMultiplier)
                .repeat(level, x, y, z, 1)
                .setAlpha(0.12f * alphaMultiplier, 0f)
                .setLifetime(10 + rand.nextInt(5))
                .setSpin(0, nextFloat(rand, 0.1f, 0.5f))
                .setScale((0.15f + rand.nextFloat() * 0.1f) * scaleMultiplier, 0.1f * scaleMultiplier)
                .randomMotion(0.03f * scaleMultiplier, 0.03f * scaleMultiplier)
                .repeat(level, x, y, z, 2);

        ParticleBuilders.create(LodestoneParticleRegistry.STAR_PARTICLE)
                .setAlpha((rand.nextFloat() * 0.1f + 0.1f) * alphaMultiplier, 0f)
                .setLifetime(20 + rand.nextInt(10))
                .setSpinOffset(0.025f * level.getGameTime() % 6.28f)
                .setSpin(0, nextFloat(rand, 0.05f, 0.4f))
                .setScale((0.7f + rand.nextFloat() * 0.1f) * scaleMultiplier, 0.1f * scaleMultiplier)
                .setColor(color, endColor)
                .randomOffset(0.01f)
                .enableNoClip()
                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .repeat(level, x, y, z, 1)
                .setLifetime(10 + rand.nextInt(5))
                .setAlpha((rand.nextFloat() * 0.05f + 0.05f) * alphaMultiplier, 0f)
                .setSpin(0, nextFloat(rand, 0.1f, 0.5f))
                .setScale((0.5f + rand.nextFloat() * 0.1f) * scaleMultiplier, 0.1f * scaleMultiplier)
                .repeat(level, x, y, z, 1);
    }

    public static void spawnSpiritScreenParticles(Color color, Color endColor, ItemStack stack, float pXPosition, float pYPosition, ScreenParticle.RenderOrder renderOrder) {
        Random rand = Minecraft.getInstance().level.getRandom();
        ParticleBuilders.create(LodestoneScreenParticleRegistry.SPARKLE)
                .setAlpha(0.04f, 0f)
                .setLifetime(10 + rand.nextInt(10))
                .setScale(0.8f + rand.nextFloat() * 0.1f, 0)
                .setColor(color, endColor)
                .setColorCoefficient(2f)
                .randomOffset(0.05f)
                .randomMotion(0.05f, 0.05f)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack)
                .repeat(pXPosition, pYPosition, 1);

        ParticleBuilders.create(LodestoneScreenParticleRegistry.WISP)
                .setAlpha(0.02f, 0f)
                .setLifetime(20 + rand.nextInt(8))
                .setSpin(nextFloat(rand, 0.2f, 0.4f))
                .setScale(0.6f + rand.nextFloat() * 0.4f, 0)
                .setColor(color, endColor)
                .setColorCoefficient(1.25f)
                .randomOffset(0.1f)
                .randomMotion(0.4f, 0.4f)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack)
                .repeat(pXPosition, pYPosition, 1)
                .setLifetime(10 + rand.nextInt(2))
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setScale(0.8f + rand.nextFloat() * 0.4f, 0f)
                .randomMotion(0.01f, 0.01f)
                .repeat(pXPosition, pYPosition, 1);
    }
}