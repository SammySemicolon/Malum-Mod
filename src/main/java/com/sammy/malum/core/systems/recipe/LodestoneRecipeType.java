package com.sammy.malum.core.systems.recipe;

import net.minecraft.resources.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class LodestoneRecipeType<T extends RecipeInput, K extends Recipe<T>> implements RecipeType<K> {

    public final ResourceLocation id;

    public LodestoneRecipeType(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public K getRecipe(Level level, T input) {
        return getRecipe(level, recipe -> recipe.matches(input level));
    }
    
    public K getRecipe(Level level, Predicate<K> predicate) {
        List<RecipeHolder<K>> recipes = getRecipeHolders(level);
        for (RecipeHolder<K> recipe : recipes) {
            final K value = recipe.value();
            if (predicate.test(value)) {
                return value;
            }
        }
        return null;
    }

    public List<K> getRecipes(Level level) {
        return getRecipeHolders(level).stream().map(RecipeHolder::value).collect(Collectors.toList());
    }

    public List<RecipeHolder<K>> getRecipeHolders(Level level) {
        return level.getRecipeManager().getAllRecipesFor(this);
    }

}
