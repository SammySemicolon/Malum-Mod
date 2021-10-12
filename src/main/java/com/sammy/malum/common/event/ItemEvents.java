package com.sammy.malum.common.event;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.tools.spirittools.ISpiritTool;
import com.sammy.malum.common.item.tools.spirittools.ScytheItem;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_systems.spirit.SpiritHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("all")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemEvents {
    @SubscribeEvent
    public static void onEntityKill(LivingDeathEvent event) {
        if (event.getSource().getTrueSource() instanceof LivingEntity) {
            ItemStack stack = ItemStack.EMPTY;
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            if (event.getSource().getImmediateSource() instanceof ScytheBoomerangEntity) {
                ScytheBoomerangEntity entity = (ScytheBoomerangEntity) event.getSource().getImmediateSource();
                stack = entity.scythe;
            } else {
                stack = MalumHelper.heldItem(attacker, s -> s.getItem() instanceof ISpiritTool);
            }
            if (stack != null) {
                if (stack.getItem() instanceof ISpiritTool && event.getSource().isProjectile()) {
                    return;
                }
                SpiritHelper.playerSummonSpirits(event.getEntityLiving(), attacker, stack);
            }
        }
    }

    @SubscribeEvent
    public static void increaseScytheDamage(LivingHurtEvent event) {
        float extraDamage = 1.0f;
        LivingEntity attacker = null;
        ItemStack stack = ItemStack.EMPTY;
        if (event.getSource().getImmediateSource() instanceof LivingEntity) {
            attacker = (LivingEntity) event.getSource().getImmediateSource();
        }
        if (event.getSource().getImmediateSource() instanceof ScytheBoomerangEntity) {
            ScytheBoomerangEntity entity = (ScytheBoomerangEntity) event.getSource().getImmediateSource();
            attacker = entity.owner();
            stack = entity.scythe;
        }
        if (attacker != null) {
            if (MalumHelper.hasArmorSet(attacker, MalumItems.SOUL_HUNTER_CLOAK.get())) {
                extraDamage += 0.25f;
            }
            if (MalumHelper.hasCurioEquipped(attacker, MalumItems.SACRIFICIAL_NECKLACE)) {
                extraDamage += 0.5f;
            }
        }
        event.setAmount(event.getAmount() * extraDamage);
    }

    @SubscribeEvent
    public static void increaseMagicDamage(LivingHurtEvent event) {
        if (!event.getSource().isMagicDamage()) {
            return;
        }
        float extraDamage = 1.0f;
        LivingEntity attacker = null;
        LivingEntity attacked = event.getEntityLiving();
        if (attacked == null) {
            return;
        }
        ItemStack stack = ItemStack.EMPTY;
        if (event.getSource().getImmediateSource() instanceof LivingEntity) {
            attacker = (LivingEntity) event.getSource().getImmediateSource();
            stack = MalumHelper.heldItem(attacker, s -> s.getItem() instanceof ScytheItem);
        }
        if (event.getSource().getImmediateSource() instanceof ScytheBoomerangEntity) {
            ScytheBoomerangEntity entity = (ScytheBoomerangEntity) event.getSource().getImmediateSource();
            attacker = entity.owner();
            stack = entity.scythe;
        }
        if (attacker != null) {
            if (MalumHelper.hasArmorSet(attacker, MalumItems.SOUL_HUNTER_CLOAK.get())) {
                extraDamage += 0.25f;
            }
            if (stack.getItem().equals(MalumItems.SOUL_STAINED_STEEL_SCYTHE.get())) {
                extraDamage += 0.25f;
            }

            if (MalumHelper.hasArmorSet(attacked, MalumItems.SOUL_STAINED_STEEL_HELMET.get())) {
                extraDamage *= 0.5f;
            }
            if (MalumHelper.hasCurioEquipped(attacker, MalumItems.NECKLACE_OF_BATTLE_HARMONY)) {
                if (attacker instanceof PlayerEntity) {
                    PlayerEntity playerEntity = (PlayerEntity) attacker;
                    if (playerEntity.getCooldownTracker().hasCooldown(MalumItems.NECKLACE_OF_BATTLE_HARMONY.get())) {
                        return;
                    }
                    playerEntity.getCooldownTracker().setCooldown(MalumItems.NECKLACE_OF_BATTLE_HARMONY.get(), 40);
                }
                SpiritHelper.collectSpirit(attacker);
            }
        }
        event.setAmount(event.getAmount() * extraDamage);
    }
}