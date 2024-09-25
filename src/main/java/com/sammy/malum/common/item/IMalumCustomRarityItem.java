package com.sammy.malum.common.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public interface IMalumCustomRarityItem {
    Rarity getRarity(ItemStack stack);
}
