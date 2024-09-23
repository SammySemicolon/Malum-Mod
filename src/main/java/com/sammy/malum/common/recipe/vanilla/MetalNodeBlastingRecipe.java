package com.sammy.malum.common.recipe.vanilla;

import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import net.minecraft.core.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class MetalNodeBlastingRecipe extends BlastingRecipe implements INodeSmeltingRecipe {

    public static final String NAME = "node_blasting";
    public final ItemStack output;
    public ItemStack cachedOutput;

    public MetalNodeBlastingRecipe(String pGroup, Ingredient pIngredient, ItemStack output, float pExperience, int pCookingTime) {
        super(pGroup, CookingBookCategory.MISC, pIngredient, ItemStack.EMPTY, pExperience, pCookingTime);
        this.output = output;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.METAL_NODE_BLASTING_SERIALIZER.get();
    }

    @Override
    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        if (cachedOutput == null) {
            cachedOutput = output.copy();
        }
        return cachedOutput;
    }
}