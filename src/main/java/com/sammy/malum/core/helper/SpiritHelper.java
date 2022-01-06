package com.sammy.malum.core.helper;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.entity.spirit.SpiritItemEntity;
import com.sammy.malum.common.item.spirit.SpiritPouchItem;
import com.sammy.malum.core.registry.AttributeRegistry;
import com.sammy.malum.core.registry.ParticleRegistry;
import com.sammy.malum.core.registry.SoundRegistry;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
import com.sammy.malum.core.registry.enchantment.MalumEnchantments;
import com.sammy.malum.core.systems.container.ItemInventory;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import static net.minecraft.util.Mth.nextFloat;
import static net.minecraft.world.entity.EquipmentSlot.MAINHAND;


public class SpiritHelper {

    public static void playerSummonSpirits(LivingEntity target, LivingEntity attacker, ItemStack harvestStack) {
        if (target instanceof Player) {
            return;
        }
        ArrayList<ItemStack> spirits = getSpiritItemStacks(target);
        if (spirits.isEmpty()) {
            return;
        }
        int spiritSpoils = (int) attacker.getAttributeValue(AttributeRegistry.SPIRIT_SPOILS);
        int spiritPlunder = EnchantmentHelper.getItemEnchantmentLevel(MalumEnchantments.SPIRIT_PLUNDER.get(), harvestStack);

        for (int i = 0; i < spiritSpoils+spiritPlunder; i++) {
            int random = attacker.level.random.nextInt(spirits.size());
            spirits.get(random).grow(1);
        }
        if (spiritPlunder > 0) {
            harvestStack.hurtAndBreak(spiritPlunder, attacker, (e) -> e.broadcastBreakEvent(MAINHAND));
        }
        createSpiritEntities(spirits, target, attacker);
    }

    public static void createSpiritEntities(LivingEntity target) {
        ArrayList<ItemStack> spirits = getSpiritItemStacks(target);
        if (!spirits.isEmpty()) {
            createSpiritEntities(spirits, target, null);
        }
    }

    public static void createSpiritEntities(ArrayList<ItemStack> spirits, LivingEntity target, LivingEntity attacker) {
        createSpiritEntities(spirits, spirits.stream().mapToInt(ItemStack::getCount).sum(), target.level, target.position().add(0, target.getEyeHeight()/2f, 0), attacker);
   }

    public static void createSpiritEntities(MalumEntitySpiritData data, Level level, Vec3 position, LivingEntity attacker) {
        createSpiritEntities(getSpiritItemStacks(data), data.totalCount, level, position, attacker);
    }

    public static void createSpiritEntities(ArrayList<ItemStack> spirits, float totalCount, Level level, Vec3 position, @Nullable LivingEntity attacker) {
        if (attacker == null) {
            attacker = level.getNearestPlayer(position.x, position.y, position.z, 8, e -> true);
        }
        float speed = 0.125f + 0.4f / (totalCount + 1);
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
                        nextFloat(MalumMod.RANDOM, 0.05f, 0.05f),
                        nextFloat(MalumMod.RANDOM, -speed, speed));
                level.addFreshEntity(entity);
            }
        }
        level.playSound(null, position.x, position.y, position.z, SoundRegistry.SPIRIT_HARVEST, SoundSource.PLAYERS, 1.0F, 0.7f + level.random.nextFloat() * 0.4f);
    }


    public static void pickupSpirit(ItemStack stack, LivingEntity collector) {
        if (collector instanceof Player playerEntity) {
            for (NonNullList<ItemStack> playerInventory : playerEntity.getInventory().compartments) {
                for (ItemStack item : playerInventory) {
                    if (item.getItem() instanceof SpiritPouchItem) {
                        ItemInventory inventory = SpiritPouchItem.getInventory(item);
                        ItemStack result = inventory.addItem(stack);
                        if (result.isEmpty()) {
                            Level level = playerEntity.level;
                            level.playSound(null, playerEntity.getX(), playerEntity.getY() + 0.5, playerEntity.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((level.random.nextFloat() - level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                        }
                    }
                }
            }
            ItemHelper.eventResponders(collector).forEach(s -> {
                if (s.getItem() instanceof IEventResponderItem eventItem)
                {
                    eventItem.pickupSpirit(collector, stack, true);
                }
            });
        }
        ItemHelper.giveItemToEntity(stack, collector);
    }

    public static MalumSpiritType getSpiritType(String spirit) {
        Optional<MalumSpiritType> type = SpiritTypeRegistry.SPIRITS.stream().filter(s -> s.identifier.equals(spirit)).findFirst();
        if (type.isEmpty()) {
            return SpiritTypeRegistry.ELDRITCH_SPIRIT;
        }
        return type.get();
    }

    public static MalumEntitySpiritData getEntitySpirits(LivingEntity entity) {
        return SpiritTypeRegistry.SPIRIT_DATA.get(entity.getType().getRegistryName());
    }

    public static int getEntitySpiritCount(LivingEntity entity) {
        MalumEntitySpiritData bundle = getEntitySpirits(entity);
        if (bundle == null) {
            return 0;
        }
        return bundle.totalCount;
    }

    public static ArrayList<ItemStack> getSpiritItemStacks(LivingEntity entity) {
        return getSpiritItemStacks(getEntitySpirits(entity));
    }
    public static ArrayList<ItemStack> getSpiritItemStacks(MalumEntitySpiritData bundle) {
        ArrayList<ItemStack> spirits = new ArrayList<>();
        if (bundle == null) {
            return spirits;
        }
        for (MalumEntitySpiritData.DataEntry data : bundle.data) {
            spirits.add(new ItemStack(data.type.getSplinterItem(), data.count));
        }
        return spirits;
    }

    public static void spawnSpiritParticles(Level level, double x, double y, double z, Color color, Color endColor) {
        spawnSpiritParticles(level, x, y,z, 1, Vec3.ZERO, color, endColor);
    }
    public static void spawnSpiritParticles(Level level, double x, double y, double z, float alphaMultiplier, Vec3 extraVelocity, Color color, Color endColor) {
        Random rand = level.getRandom();
        RenderUtilities.create(ParticleRegistry.TWINKLE_PARTICLE)
                .setAlpha(0.18f*alphaMultiplier, 0f)
                .setLifetime(10 + rand.nextInt(4))
                .setScale(0.3f + rand.nextFloat() * 0.1f, 0)
                .setColor(color, endColor)
                .randomOffset(0.05f)
                .enableNoClip()
                .addVelocity(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .randomVelocity(0.02f, 0.02f)
                .repeat(level, x, y, z, 1);

        RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.1f*alphaMultiplier, 0f)
                .setLifetime(20 + rand.nextInt(4))
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setScale(0.2f + rand.nextFloat() * 0.05f, 0)
                .setColor(color, endColor)
                .randomOffset(0.1f)
                .enableNoClip()
                .addVelocity(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .randomVelocity(0.02f, 0.02f)
                .repeat(level, x, y, z, 1);

        RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.2f*alphaMultiplier, 0f)
                .setLifetime(10 + rand.nextInt(2))
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setScale(0.15f + rand.nextFloat() * 0.05f, 0f)
                .setColor(color, endColor)
                .enableNoClip()
                .addVelocity(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .randomVelocity(0.01f, 0.01f)
                .repeat(level, x, y, z, 1);
    }

    public static void spawnSoulParticles(Level level, double x, double y, double z, Color color, Color endColor) {
        spawnSoulParticles(level, x, y,z, 1, Vec3.ZERO, color, endColor);
    }
    public static void spawnSoulParticles(Level level, double x, double y, double z, float alphaMultiplier, Vec3 extraVelocity, Color color, Color endColor) {
        Random rand = level.getRandom();
        RenderUtilities.create(ParticleRegistry.SPARKLE_PARTICLE)
                .setAlpha(0.25f*alphaMultiplier, 0.04f*alphaMultiplier)
                .setLifetime(8 + rand.nextInt(5))
                .setScale(0.3f + rand.nextFloat() * 0.2f, 1)
                .setColor(color, endColor)
                .randomOffset(0.05f)
                .enableNoClip()
                .addVelocity(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .randomVelocity(0.01f, 0.01f)
                .repeat(level, x, y, z, 1);

        RenderUtilities.create(ParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.1f*alphaMultiplier, 0f)
                .setLifetime(20 + rand.nextInt(10))
                .setSpin(nextFloat(rand, 0.05f, 0.4f))
                .setScale(0.2f + rand.nextFloat() * 0.1f, 0.1f)
                .setColor(color, endColor)
                .randomOffset(0.1f)
                .enableNoClip()
                .addVelocity(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .randomVelocity(0.04f, 0.04f)
                .repeat(level, x, y, z, 1);

        RenderUtilities.create(ParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.19f*alphaMultiplier, 0f)
                .setLifetime(10 + rand.nextInt(5))
                .setSpin(nextFloat(rand, 0.05f, 0.4f))
                .setScale(0.15f + rand.nextFloat() * 0.1f, 0.1f)
                .setColor(color, endColor)
                .enableNoClip()
                .addVelocity(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .randomVelocity(0.03f, 0.03f)
                .repeat(level, x, y, z, 1);
    }
}
