package com.sammy.malum.common.recipe.vanilla;

import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

public class MetalNodeBlastingRecipe extends BlastingRecipe implements INodeSmeltingRecipe {

    public static final String NAME = "node_blasting";
    public final IngredientWithCount output;
    public ItemStack cachedOutput;

    public MetalNodeBlastingRecipe(ResourceLocation pId, String pGroup, Ingredient pIngredient, IngredientWithCount output, float pExperience, int pCookingTime) {
        super(pId, pGroup, CookingBookCategory.MISC, pIngredient, ItemStack.EMPTY, pExperience, pCookingTime);
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
    public IngredientWithCount getOutput() {
        return output;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        if (cachedOutput == null) {
            cachedOutput = output.getStack();
        }
        return cachedOutput;
    }
}