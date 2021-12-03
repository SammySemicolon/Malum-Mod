package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.entity.spirit.PlayerHomingItemEntity;
import com.sammy.malum.common.item.equipment.SpiritPouchItem;
import com.sammy.malum.common.item.equipment.curios.MalumCurioItem;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.registry.enchantment.MalumEnchantments;
import com.sammy.malum.core.registry.particle.ParticleRegistry;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.container.ItemInventory;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import com.sammy.malum.core.systems.particle.ParticleManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static net.minecraft.util.math.MathHelper.nextFloat;

public class SpiritHelper {
    public static DamageSource voodooDamageSource() {
        return new DamageSource("voodoo").setMagicDamage();
    }

    public static DamageSource voodooDamageSource(Entity attacker) {
        return new EntityDamageSource("voodoo", attacker).setMagicDamage();
    }

    public static void causeVoodooDamage(LivingEntity attacker, LivingEntity target, float amount) {
        target.hurtResistantTime = 0;
        target.attackEntityFrom(voodooDamageSource(attacker), amount);
    }

    public static void playerSummonSpirits(LivingEntity target, LivingEntity attacker, ItemStack harvestStack) {
        if (target instanceof PlayerEntity) {
            return;
        }
        ArrayList<ItemStack> spirits = getSpiritItemStacks(target);
        if (spirits.isEmpty()) {
            return;
        }
        int spiritSpoils = (int) attacker.getAttributeValue(AttributeRegistry.SPIRIT_SPOILS);
        int spiritPlunder = EnchantmentHelper.getEnchantmentLevel(MalumEnchantments.SPIRIT_PLUNDER.get(), harvestStack);

        for (int i = 0; i < spiritSpoils+spiritPlunder; i++) {
            int random = attacker.world.rand.nextInt(spirits.size());
            spirits.get(random).grow(1);
        }
        if (spiritPlunder > 0)
        {
            if (attacker.world.rand.nextInt(2) == 0) {
                harvestStack.damageItem(spiritPlunder, attacker, (e) -> e.sendBreakAnimation(EquipmentSlotType.MAINHAND));
            }
        }
        ArrayList<MalumCurioItem> equippedMalumCurios = MalumHelper.equippedMalumCurios(attacker);
        Optional<MalumCurioItem> firstModifier = equippedMalumCurios.stream().filter(s -> !s.spiritReplacementStack(ItemStack.EMPTY).equals(ItemStack.EMPTY)).findFirst();
        if (firstModifier.isPresent()) {
            ArrayList<ItemStack> newSpirits = new ArrayList<>();
            for (ItemStack stack : spirits) {
                newSpirits.add(firstModifier.get().spiritReplacementStack(stack));
            }
            spirits = newSpirits;
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
        if (attacker == null) {
            attacker = target.world.getClosestPlayer(target.getPosX(), target.getPosY(), target.getPosZ(), 8, e -> true);
        }
        float speed = 0.2f + 0.5f / (getEntitySpiritCount(target) + 1);
        for (ItemStack stack : spirits) {
            int count = stack.getCount();
            if (count == 0) {
                continue;
            }
            if (attacker == null) {
                for (int j = 0; j < count; j++) {
                    ItemEntity itemEntity = new ItemEntity(target.world, target.getPosXRandom(1), target.getPosYRandom(), target.getPosZRandom(1), MalumHelper.copyWithNewCount(stack, 1));
                    target.world.addEntity(itemEntity);
                }
                continue;
            }
            for (int j = 0; j < count; j++) {
                PlayerHomingItemEntity entity = new PlayerHomingItemEntity(target.world, attacker.getUniqueID(), MalumHelper.copyWithNewCount(stack, 1),
                        target.getPositionVec().x,
                        target.getPositionVec().y + target.getHeight() / 2f,
                        target.getPositionVec().z,
                        nextFloat(MalumMod.RANDOM, -speed, speed),
                        nextFloat(MalumMod.RANDOM, 0.05f, 0.05f),
                        nextFloat(MalumMod.RANDOM, -speed, speed));
                target.world.addEntity(entity);
            }
        }
        target.world.playSound(null, target.getPosX(), target.getPosY(), target.getPosZ(), SoundRegistry.SPIRIT_HARVEST, target.getSoundCategory(), 1.0F, 0.9f + target.world.rand.nextFloat() * 0.2f);
    }


    public static void pickupSpirit(ItemStack stack, LivingEntity collector) {
        if (collector instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) collector;
            for (NonNullList<ItemStack> playerInventory : playerEntity.inventory.allInventories) {
                for (ItemStack item : playerInventory) {
                    if (item.getItem() instanceof SpiritPouchItem) {
                        ItemInventory inventory = SpiritPouchItem.getInventory(item);
                        ItemStack result = inventory.addItem(item);
                        if (result.isEmpty()) {
                            World world = playerEntity.world;
                            world.playSound(null, playerEntity.getPosX(), playerEntity.getPosY() + 0.5, playerEntity.getPosZ(),
                                    SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);

                            return;
                        }
                    }
                }
            }
            Item item = stack.getItem();
            if (item instanceof IEventResponderItem) {
                IEventResponderItem eventItem = (IEventResponderItem) item;
                eventItem.pickupSpirit(collector, stack);
            }
            collector.getArmorInventoryList().forEach(s ->{
                if (s.getItem() instanceof IEventResponderItem)
                {
                    IEventResponderItem eventItem = (IEventResponderItem) s.getItem();
                    eventItem.pickupSpirit(collector, stack);
                }
            });
            ArrayList<ItemStack> curios = MalumHelper.equippedCurios(collector, p -> p.getItem() instanceof IEventResponderItem);
            curios.forEach(s -> ((IEventResponderItem)s.getItem()).pickupSpirit(collector, s));
        }
        MalumHelper.giveItemToEntity(stack, collector);
    }

    public static MalumSpiritType getSpiritType(String spirit) {
        ArrayList<MalumSpiritType> type = (ArrayList<MalumSpiritType>) SpiritTypeRegistry.SPIRITS.stream().filter(s -> s.identifier.equals(spirit)).collect(Collectors.toList());
        if (type.isEmpty()) {
            return SpiritTypeRegistry.ELDRITCH_SPIRIT;
        }
        return type.get(0);
    }

    public static MalumEntitySpiritDataBundle getEntitySpirits(LivingEntity entity) {
        return MalumSpiritType.SPIRIT_DATA.get(entity.getType().getRegistryName());
    }

    public static int getEntitySpiritCount(LivingEntity entity) {
        MalumEntitySpiritDataBundle bundle = getEntitySpirits(entity);
        if (bundle == null) {
            return 0;
        }
        return bundle.totalCount;
    }

    public static ArrayList<ItemStack> getSpiritItemStacks(LivingEntity entity) {
        ArrayList<ItemStack> spirits = new ArrayList<>();
        MalumEntitySpiritDataBundle bundle = getEntitySpirits(entity);
        if (bundle == null) {
            return spirits;
        }
        for (MalumEntitySpiritDataBundle.MalumEntitySpiritData data : bundle.data) {
            spirits.add(new ItemStack(data.type.splinterItem(), data.count));
        }
        return spirits;
    }

    public static void spawnSpiritParticles(World world, double x, double y, double z, Color color) {
        Random rand = world.rand;
        ParticleManager.create(ParticleRegistry.TWINKLE_PARTICLE)
                .setAlpha(0.18f, 0f)
                .setLifetime(10 + rand.nextInt(4))
                .setScale(0.3f + rand.nextFloat() * 0.1f, 0)
                .setColor(color, color.darker())
                .randomOffset(0.05f)
                .enableNoClip()
                .randomVelocity(0.02f, 0.02f)
                .repeat(world, x, y, z, 1);

        ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(20 + rand.nextInt(4))
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setScale(0.2f + rand.nextFloat() * 0.05f, 0)
                .setColor(color, color.darker())
                .randomOffset(0.1f)
                .enableNoClip()
                .randomVelocity(0.02f, 0.02f)
                .repeat(world, x, y, z, 1);

        ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.2f, 0f)
                .setLifetime(10 + rand.nextInt(2))
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setScale(0.15f + rand.nextFloat() * 0.05f, 0f)
                .setColor(color, color.darker())
                .enableNoClip()
                .randomVelocity(0.01f, 0.01f)
                .repeat(world, x, y, z, 1);
    }
}