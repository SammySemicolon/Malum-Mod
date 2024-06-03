package com.sammy.malum.asm;

import com.sammy.malum.mixin.EnchantmentCategoryMixin;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.world.item.Item;

public class EnchantmentCategoryScythe extends EnchantmentCategoryMixin {
    @Override
    public boolean canEnchant(Item item) {
        return item.getDefaultInstance().is(ItemTagRegistry.SCYTHE);
    }
}
