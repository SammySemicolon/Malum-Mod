package com.sammy.malum.common.enchantment;

import com.sammy.malum.common.item.curiosities.weapons.staff.AbstractStaffItem;
import com.sammy.malum.common.packets.SyncStaffCooldownChangesPacket;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import io.github.fabricators_of_create.porting_lib.enchant.CustomEnchantingTableBehaviorEnchantment;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class ReplenishingEnchantment extends Enchantment implements CustomEnchantingTableBehaviorEnchantment {
    public ReplenishingEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public static void replenishStaffCooldown(LivingEntity attacker, ItemStack stack) {
        if (attacker instanceof ServerPlayer player && stack.getItem() instanceof AbstractStaffItem staff) {
            ItemCooldowns cooldowns = player.getCooldowns();
            if (cooldowns.isOnCooldown(staff) && player.getAttackStrengthScale(0) > 0.8f) {
                int level = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.REPLENISHING.get(), stack);
                replenishStaffCooldown(staff, player, level);
                MALUM_CHANNEL.sendToClient(new SyncStaffCooldownChangesPacket(staff, level), player);
            }
        }
    }

    public static void replenishStaffCooldown(AbstractStaffItem staff, Player player, int pLevel) {
        ItemCooldowns cooldowns = player.getCooldowns();
        int ratio = (int) (staff.getCooldownDuration(player.level(), player) * (0.25f * pLevel));
        cooldowns.tickCount += ratio;
        for (Map.Entry<Item, ItemCooldowns.CooldownInstance> itemCooldownInstanceEntry : cooldowns.cooldowns.entrySet()) {
            if (itemCooldownInstanceEntry.getKey().equals(staff)) {
                continue;
            }
            ItemCooldowns.CooldownInstance value = itemCooldownInstanceEntry.getValue();
            ItemCooldowns.CooldownInstance cooldownInstance = new ItemCooldowns.CooldownInstance(value.startTime + ratio, value.endTime + ratio);
            cooldowns.cooldowns.put(itemCooldownInstanceEntry.getKey(), cooldownInstance);
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.is(ItemTagRegistry.STAFF);
    }
}