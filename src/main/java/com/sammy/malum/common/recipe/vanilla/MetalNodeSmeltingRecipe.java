package com.sammy.malum.common.recipe.vanilla;

import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class MetalNodeSmeltingRecipe extends SmeltingRecipe implements INodeSmeltingRecipe {

    public static final String NAME = "node_smelting";
    public final ItemStack output;
    public ItemStack cachedOutput;

    public MetalNodeSmeltingRecipe(String pGroup, Ingredient pIngredient, ItemStack output, float pExperience, int pCookingTime) {
        super(pGroup, CookingBookCategory.MISC, pIngredient, ItemStack.EMPTY, pExperience, pCookingTime);
        this.output = output;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.METAL_NODE_SMELTING_SERIALIZER.get();
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        if (cachedOutput == null) {
            cachedOutput = output.copy();
        }
        return cachedOutput;
    }
}
