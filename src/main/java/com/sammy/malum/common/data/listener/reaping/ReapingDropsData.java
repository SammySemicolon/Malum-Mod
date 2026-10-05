package com.sammy.malum.common.data.listener.reaping;

import net.minecraft.world.item.crafting.Ingredient;

public record ReapingDropsData(Ingredient drop, float chance, int min, int max) {
}
