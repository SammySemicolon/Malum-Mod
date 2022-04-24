package com.sammy.malum.common.enchantment;

import com.sammy.malum.core.setup.content.item.MalumEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class SpiritPlunderEnchantment extends Enchantment {
    public SpiritPlunderEnchantment() {
        super(Rarity.COMMON, MalumEnchantments.SOUL_HUNTER_WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

}