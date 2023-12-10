package com.sammy.malum.common.enchantment;

import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.*;

public class ReplenishingEnchantment extends Enchantment {
    public ReplenishingEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentRegistry.STAFF, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

}