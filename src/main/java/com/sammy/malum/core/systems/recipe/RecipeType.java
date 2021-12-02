package com.sammy.malum.core.systems.recipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;

public class RecipeType<T extends IRecipe<?>> implements IRecipeType<T>
{
    @Override
    public String toString()
    {
        return Registry.RECIPE_TYPE.getKey(this).toString();
    }
}
