package com.sammy.malum.asm;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EnchantmentCategory.class)
abstract class EnchantmentCategoryMixin {
    @Shadow
    public abstract boolean canEnchant(Item item);
}