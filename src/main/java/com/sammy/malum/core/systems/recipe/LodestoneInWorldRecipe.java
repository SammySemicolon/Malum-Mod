package com.sammy.malum.core.systems.recipe;

import net.minecraft.core.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

//TODO: move to lodestone

/**
 * A Recipe Class designed for In-World Crafting without the presence of an interface.
 * Overrides most methods that are only needed when considering an interface.
 */
public abstract class LodestoneInWorldRecipe<T extends RecipeInput> implements Recipe<T> {

    private final RecipeSerializer<?> recipeSerializer;
    private final RecipeType<?> recipeType;
    public final ItemStack output;

    public LodestoneInWorldRecipe(RecipeSerializer<?> recipeSerializer, RecipeType<?> recipeType, ItemStack output) {
        this.recipeSerializer = recipeSerializer;
        this.recipeType = recipeType;
        this.output = output;
    }

    public LodestoneInWorldRecipe(RecipeSerializer<?> recipeSerializer, RecipeType<?> recipeType) {
        this(recipeSerializer, recipeType, ItemStack.EMPTY);
    }

    @Override
    public ItemStack assemble(T input, HolderLookup.Provider registries) {
        return getResultItem(registries).copy();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return recipeSerializer;
    }

    @Override
    public RecipeType<?> getType() {
        return recipeType;
    }
}
