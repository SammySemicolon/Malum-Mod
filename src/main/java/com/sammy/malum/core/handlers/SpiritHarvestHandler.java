package com.sammy.malum.core.handlers;

import com.sammy.malum.common.capability.LivingEntityDataCapability;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.spirit.SpiritPouchItem;
import com.sammy.malum.compability.tetra.TetraCompat;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.content.damage.DamageSourceRegistry;
import com.sammy.malum.core.setup.content.item.ItemTagRegistry;
import com.sammy.malum.core.systems.container.ItemInventory;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class SpiritHarvestHandler {

    public static void exposeSoul(LivingHurtEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0) {
            return;
        }
        LivingEntity target = event.getEntityLiving();
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemStack stack = attacker.getMainHandItem();
            if (event.getSource().getDirectEntity() instanceof ScytheBoomerangEntity) {
                stack = ((ScytheBoomerangEntity) event.getSource().getDirectEntity()).scythe;
            }

            if (stack.is(ItemTagRegistry.SOUL_HUNTER_WEAPON) || (TetraCompat.LOADED && TetraCompat.LoadedOnly.hasSoulStrike(stack))) {
                LivingEntityDataCapability.getCapability(target).ifPresent(e -> e.exposedSoul = 200);
            }
        }
        if (event.getSource().getDirectEntity() != null && event.getSource().getDirectEntity().getTags().contains("malum:soul_arrow")) {
            LivingEntityDataCapability.getCapability(target).ifPresent(e -> e.exposedSoul = 200);
        }
    }

    public static void shatterSoul(LivingDeathEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (event.getSource().getMsgId().equals(DamageSourceRegistry.FORCED_SHATTER.getMsgId())) {
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
                if (!event.getSource().getMsgId().equals(DamageSourceRegistry.FORCED_SHATTER_DAMAGE)) {
                    LivingEntityDataCapability.getCapability(target).ifPresent(e -> {
                        if (e.exposedSoul > 0 && !e.soulless && (!CommonConfig.SOULLESS_SPAWNERS.get() || (CommonConfig.SOULLESS_SPAWNERS.get() && !e.spawnerSpawned))) {
                            SpiritHelper.createSpiritsFromWeapon(target, finalAttacker, finalStack);
                            e.soulless = true;
                        }
                    });
                }
            }
        }
    }

    public static void pickupSpirit(ItemStack stack, LivingEntity collector) {
        if (collector instanceof Player playerEntity) {
            ItemHelper.getEventResponders(collector).forEach(s -> {
                if (s.getItem() instanceof IEventResponderItem eventItem) {
                    eventItem.pickupSpirit(collector, stack, true);
                }
            });
            for (NonNullList<ItemStack> playerInventory : playerEntity.getInventory().compartments) {
                for (ItemStack item : playerInventory) {
                    if (item.getItem() instanceof SpiritPouchItem) {
                        ItemInventory inventory = SpiritPouchItem.getInventory(item);
                        ItemStack result = inventory.addItem(stack);
                        if (result.isEmpty()) {
                            Level level = playerEntity.level;
                            level.playSound(null, playerEntity.getX(), playerEntity.getY() + 0.5, playerEntity.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((level.random.nextFloat() - level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                            return;
                        }
                    }
                }
            }
        }
        ItemHelper.giveItemToEntity(stack, collector);
    }
}