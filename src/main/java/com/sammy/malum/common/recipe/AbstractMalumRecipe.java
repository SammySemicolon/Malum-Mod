package com.sammy.malum.common.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.systems.recipe.ILodestoneRecipe;

import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractMalumRecipe extends ILodestoneRecipe {
    private final ResourceLocation id;
    private final RecipeSerializer<?> recipeSerializer;
    private final RecipeType<?> recipeType;

    protected AbstractMalumRecipe(ResourceLocation id, RecipeSerializer<?> recipeSerializer, RecipeType<?> recipeType) {
        this.id = id;
        this.recipeSerializer = recipeSerializer;
        this.recipeType = recipeType;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return recipeSerializer;
    }

    @Override
    public RecipeType<?> getType() {
        return recipeType;
    }

    public static <T extends AbstractMalumRecipe> T getRecipe(Level level, RecipeType<T> recipeType, Predicate<T> predicate) {
        List<T> recipes = getRecipes(level, recipeType);
        for (T recipe : recipes) {
            if (predicate.test(recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static <T extends AbstractMalumRecipe> List<T> getRecipes(Level level, RecipeType<T> recipeType) {
        return level.getRecipeManager().getAllRecipesFor(recipeType);
    }
}
