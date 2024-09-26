package com.sammy.malum.core.systems.recipe;

import com.sammy.malum.common.recipe.spirit.transmutation.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class LodestoneRecipeType<T extends Recipe<?>> implements RecipeType<T> {

    public final ResourceLocation id;

    public LodestoneRecipeType(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public static <T extends RecipeInput, K extends Recipe<T>> K getRecipe(Level level, RecipeType<K> recipeType, T recipeInput) {
        return findRecipe(level, recipeType, recipe -> recipe.matches(recipeInput, level));
    }

    public static <T extends RecipeInput, K extends Recipe<T>> K findRecipe(Level level, RecipeType<K> recipeType, Predicate<Recipe<T>> predicate) {
        List<RecipeHolder<K>> recipes = getRecipeHolders(level, recipeType);
        for (RecipeHolder<K> recipe : recipes) {
            final K value = recipe.value();
            if (predicate.test(value)) {
                return value;
            }
        }
        return null;
    }

    public static <T extends RecipeInput, K extends Recipe<T>> List<K> getRecipes(Level level, RecipeType<K> recipeType) {
        return getRecipeHolders(level, recipeType).stream().map(RecipeHolder::value).collect(Collectors.toList());
    }

    public static <T extends RecipeInput, K extends Recipe<T>> List<RecipeHolder<K>> getRecipeHolders(Level level, RecipeType<K> recipeType) {
        return level.getRecipeManager().getAllRecipesFor(recipeType);
    }
}
