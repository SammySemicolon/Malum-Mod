package com.sammy.malum.common.recipe.vanilla;

import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import com.sammy.ortus.systems.recipe.IngredientWithCount;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.Optional;

public class MetalNodeBlastingRecipe extends BlastingRecipe {

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
}