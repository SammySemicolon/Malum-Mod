package com.sammy.malum.common.recipe.vanilla;

import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

public class MetalNodeBlastingRecipe extends BlastingRecipe implements INodeSmeltingRecipe {

    public static final String NAME = "node_blasting";
    public final IngredientWithCount output;

    public MetalNodeBlastingRecipe(ResourceLocation pId, String pGroup, Ingredient pIngredient, IngredientWithCount output, float pExperience, int pCookingTime) {
        super(pId, pGroup, pIngredient, ItemStack.EMPTY, pExperience, pCookingTime);
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
    public ItemStack getResultItem() {
        return output.getStack();
    }

    @Override
    public ItemStack assemble(Container pInv) {
        return output.getStack();
    }

    @Override
    public IngredientWithCount getOutput() {
        return output;
    }
}