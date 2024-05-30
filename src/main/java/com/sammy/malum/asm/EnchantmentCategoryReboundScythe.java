package com.sammy.malum.asm;

import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;

public class EnchantmentCategoryReboundScythe extends EnchantmentCategoryMixin {
    @Override
    public boolean canEnchant(Item item) {
        return item.getDefaultInstance().is(ItemTagRegistry.SCYTHE) || (CommonConfig.ULTIMATE_REBOUND.getConfigValue() && item instanceof TieredItem);
    }
}
