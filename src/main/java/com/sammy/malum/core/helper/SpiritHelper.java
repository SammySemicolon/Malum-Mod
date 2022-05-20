package com.sammy.malum.core.helper;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.entity.spirit.SpiritItemEntity;
import com.sammy.malum.core.listeners.SpiritDataReloadListener;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.setup.content.item.MalumEnchantments;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.ortus.helpers.ItemHelper;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.setup.OrtusScreenParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import com.sammy.ortus.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static net.minecraft.util.Mth.nextFloat;
import static net.minecraft.world.entity.EquipmentSlot.MAINHAND;


public class SpiritHelper {

    public static void createSpiritsFromSoul(MalumEntitySpiritData data, Level level, Vec3 position, LivingEntity attacker) {
        ArrayList<ItemStack> spirits = getSpiritItemStacks(data, attacker, ItemStack.EMPTY, 2);
        createSpiritEntities(spirits, data.totalCount, level, position, attacker);
    }

    public static void createSpiritsFromWeapon(LivingEntity target, LivingEntity attacker, ItemStack harvestStack) {
        ArrayList<ItemStack> spirits = getSpiritItemStacks(target, attacker, harvestStack, 1);
        createSpiritEntities(spirits, target, attacker);
    }

    public static void createSpiritEntities(LivingEntity target, LivingEntity attacker) {
        ArrayList<ItemStack> spirits = getSpiritItemStacks(target);
        if (!spirits.isEmpty()) {
            createSpiritEntities(spirits, target, attacker);
        }
    }

    public static void createSpiritEntities(LivingEntity target) {
        ArrayList<ItemStack> spirits = getSpiritItemStacks(target);
        if (!spirits.isEmpty()) {
            createSpiritEntities(spirits, target, null);
        }
    }

    public static void createSpiritEntities(ArrayList<ItemStack> spirits, LivingEntity target, LivingEntity attacker) {
        if (spirits.isEmpty()) {
            return;
        }
        createSpiritEntities(spirits, spirits.stream().mapToInt(ItemStack::getCount).sum(), target.level, target.position().add(0, target.getEyeHeight() / 2f, 0), attacker);
    }

    public static void createSpiritEntities(MalumEntitySpiritData data, Level level, Vec3 position, LivingEntity attacker) {
        createSpiritEntities(getSpiritItemStacks(data), data.totalCount, level, position, attacker);
    }

    public static void createSpiritEntities(ArrayList<ItemStack> spirits, float totalCount, Level level, Vec3 position, @Nullable LivingEntity attacker) {
        if (attacker == null) {
            attacker = level.getNearestPlayer(position.x, position.y, position.z, 8, e -> true);
        }
        float speed = 0.1f + 0.2f / (totalCount + 1);
        for (ItemStack stack : spirits) {
            int count = stack.getCount();
            if (count == 0) {
                continue;
            }
            for (int j = 0; j < count; j++) {
                SpiritItemEntity entity = new SpiritItemEntity(level, attacker == null ? null : attacker.getUUID(), ItemHelper.copyWithNewCount(stack, 1),
                        position.x,
                        position.y,
                        position.z,
                        nextFloat(MalumMod.RANDOM, -speed, speed),
                        nextFloat(MalumMod.RANDOM, 0.015f, 0.05f),
                        nextFloat(MalumMod.RANDOM, -speed, speed));
                level.addFreshEntity(entity);
            }
        }
        level.playSound(null, position.x, position.y, position.z, SoundRegistry.SPIRIT_HARVEST.get(), SoundSource.PLAYERS, 1.0F, 0.7f + level.random.nextFloat() * 0.4f);
    }


    public static MalumSpiritType getSpiritType(String spirit) {
        MalumSpiritType type = SpiritTypeRegistry.SPIRITS.get(spirit);
        return type == null ? SpiritTypeRegistry.SACRED_SPIRIT : type;
    }

    public static MalumEntitySpiritData getEntitySpiritData(LivingEntity entity) {
        return SpiritDataReloadListener.SPIRIT_DATA.get(entity.getType().getRegistryName());
    }

    public static int getEntitySpiritCount(LivingEntity entity) {
        MalumEntitySpiritData bundle = getEntitySpiritData(entity);
        if (bundle == null) {
            return 0;
        }
        return bundle.totalCount;
    }

    public static ArrayList<ItemStack> getSpiritItemStacks(LivingEntity entity, LivingEntity attacker, ItemStack harvestStack, float spoilsMultiplier) {
        return getSpiritItemStacks(getEntitySpiritData(entity), attacker, harvestStack, spoilsMultiplier);
    }

    public static ArrayList<ItemStack> getSpiritItemStacks(MalumEntitySpiritData data, LivingEntity attacker, ItemStack harvestStack, float spoilsMultiplier) {
        ArrayList<ItemStack> spirits = getSpiritItemStacks(data);
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

    public static ArrayList<ItemStack> getSpiritItemStacks(LivingEntity entity) {
        return getSpiritItemStacks(getEntitySpiritData(entity));
    }

    public static ArrayList<ItemStack> getSpiritItemStacks(MalumEntitySpiritData data) {
        ArrayList<ItemStack> spirits = new ArrayList<>();
        if (data == null) {
            return spirits;
        }
        for (SpiritWithCount spiritWithCount : data.dataEntries) {
            spirits.add(new ItemStack(spiritWithCount.type.getSplinterItem(), spiritWithCount.count));
        }
        return spirits;
    }

    public static void spawnSpiritParticles(Level level, double x, double y, double z, Color color, Color endColor) {
        spawnSpiritParticles(level, x, y, z, 1, Vec3.ZERO, color, endColor);
    }

    public static void spawnSpiritParticles(Level level, double x, double y, double z, float alphaMultiplier, Vec3 extraVelocity, Color color, Color endColor) {
        Random rand = level.getRandom();
        ParticleBuilders.create(OrtusParticleRegistry.TWINKLE_PARTICLE)
                .setAlpha(0.21f * alphaMultiplier, 0f)
                .setLifetime(10 + rand.nextInt(4))
                .setScale(0.3f + rand.nextFloat() * 0.1f, 0)
                .setColor(color, endColor)
                .setColorCoefficient(2f)
                .randomOffset(0.05f)
                .enableNoClip()
                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .randomMotion(0.02f, 0.02f)
                .repeat(level, x, y, z, 1);

        ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.1f * alphaMultiplier, 0f)
                .setLifetime(20 + rand.nextInt(4))
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setScale(0.2f + rand.nextFloat() * 0.05f, 0)
                .setColor(color, endColor)
                .setColorCoefficient(1.25f)
                .randomOffset(0.1f)
                .enableNoClip()
                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .randomMotion(0.02f, 0.02f)
                .repeat(level, x, y, z, 1)
                .setAlpha(0.2f * alphaMultiplier, 0f)
                .setLifetime(10 + rand.nextInt(2))
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setScale(0.15f + rand.nextFloat() * 0.05f, 0f)
                .randomMotion(0.01f, 0.01f)
                .repeat(level, x, y, z, 1);
    }

    public static void spawnSoulParticles(Level level, double x, double y, double z, Color color, Color endColor) {
        spawnSoulParticles(level, x, y, z, 1, 1, Vec3.ZERO, color, endColor);
    }

    public static void spawnSoulParticles(Level level, double x, double y, double z, float alphaMultiplier, float scaleMultiplier, Vec3 extraVelocity, Color color, Color endColor) {
        Random rand = level.getRandom();
        ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
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

        ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
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

        ParticleBuilders.create(OrtusParticleRegistry.STAR_PARTICLE)
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
        ParticleBuilders.create(OrtusScreenParticleRegistry.SPARKLE)
                .setAlpha(0.04f, 0f)
                .setLifetime(10 + rand.nextInt(10))
                .setScale(0.8f + rand.nextFloat()*0.1f, 0)
                .setColor(color, endColor)
                .setColorCoefficient(2f)
                .randomOffset(0.05f)
                .randomMotion(0.05f, 0.05f)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack)
                .repeat(pXPosition, pYPosition, 1);

        ParticleBuilders.create(OrtusScreenParticleRegistry.WISP)
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