package com.sammy.malum.asm;

import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import net.minecraft.world.item.Item;

public class EnchantmentCategoryScythe extends EnchantmentCategoryMixin{
    @Override
    public boolean canEnchant(Item item) {
        return item instanceof MalumScytheItem;
    }
}
