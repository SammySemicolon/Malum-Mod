package com.sammy.malum.common.enchantment;

import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;

import java.util.*;

public class ReplenishingEnchantment extends Enchantment {
    public ReplenishingEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentRegistry.STAFF, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if (pAttacker instanceof Player player) {
            ItemStack stack = pAttacker.getMainHandItem();
            if (stack.getItem() instanceof AbstractStaffItem staff) {
                ItemCooldowns cooldowns = player.getCooldowns();
                if (cooldowns.isOnCooldown(staff)) {
                    int ratio = (int) (staff.getCooldownDuration(player.level(), player) * (0.1f * pLevel));
                    cooldowns.tickCount+=ratio;
                    for (Map.Entry<Item, ItemCooldowns.CooldownInstance> itemCooldownInstanceEntry : cooldowns.cooldowns.entrySet()) {
                        if (itemCooldownInstanceEntry.getKey() == staff) continue;
                        ItemCooldowns.CooldownInstance value = itemCooldownInstanceEntry.getValue();
                        ItemCooldowns.CooldownInstance cooldownInstance = new ItemCooldowns.CooldownInstance(value.startTime+ratio, value.endTime+ratio);
                        cooldowns.cooldowns.put(itemCooldownInstanceEntry.getKey(), cooldownInstance);
                        cooldowns.onCooldownStarted(itemCooldownInstanceEntry.getKey(), cooldownInstance);
                    }
                }
            }
        }
    }
}