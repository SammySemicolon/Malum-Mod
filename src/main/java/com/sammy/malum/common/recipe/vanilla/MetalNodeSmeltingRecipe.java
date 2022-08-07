package com.sammy.malum.common.recipe.vanilla;

import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class MetalNodeSmeltingRecipe extends SmeltingRecipe implements INodeSmeltingRecipe {

    public static final String NAME = "node_smelting";
    public final IngredientWithCount output;

    public MetalNodeSmeltingRecipe(ResourceLocation pId, String pGroup, Ingredient pIngredient, IngredientWithCount output, float pExperience, int pCookingTime) {
        super(pId, pGroup, pIngredient, ItemStack.EMPTY, pExperience, pCookingTime);
        this.output = output;
    }

    @Override
    public boolean isSpecial()
    {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.METAL_NODE_SMELTING_SERIALIZER.get();
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
