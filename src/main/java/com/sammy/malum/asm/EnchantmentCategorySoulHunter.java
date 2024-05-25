package com.sammy.malum.asm;

import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.world.item.Item;

public class EnchantmentCategorySoulHunter extends EnchantmentCategoryMixin{
    @Override
    public boolean canEnchant(Item item) {
        return item.getDefaultInstance().is(ItemTagRegistry.SOUL_HUNTER_WEAPON);
    }
}
