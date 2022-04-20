package com.sammy.malum.common.recipe.vanilla;

import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class MetalNodeBlastingRecipe extends BlastingRecipe {

    public static final String NAME = "node_blasting";

    public MetalNodeBlastingRecipe(ResourceLocation pId, String pGroup, Ingredient pIngredient, ItemStack pResult, float pExperience, int pCookingTime) {
        super(pId, pGroup, pIngredient, pResult, pExperience, pCookingTime);
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.METAL_NODE_BLASTING_SERIALIZER.get();
    }
}