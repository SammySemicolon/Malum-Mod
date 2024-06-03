package com.sammy.malum.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("ALL") //Don't add this in mixins.json
@Mixin(EnchantmentCategory.class)
public abstract class EnchantmentCategoryMixin {
    @Shadow
    public abstract boolean canEnchant(Item item);
}