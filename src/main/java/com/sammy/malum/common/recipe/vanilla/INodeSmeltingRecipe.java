package com.sammy.malum.common.recipe.vanilla;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface INodeSmeltingRecipe {
    ItemStack getOutput();
    Ingredient getIngredient();
}
