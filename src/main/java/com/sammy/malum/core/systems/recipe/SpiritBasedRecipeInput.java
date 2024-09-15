package com.sammy.malum.core.systems.recipe;

import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

public class SpiritBasedRecipeInput implements RecipeInput {

    public final List<ItemStack> items;
    public final List<ItemStack> spirits;

    public SpiritBasedRecipeInput(ItemStack stack, List<ItemStack> spirits) {
        this(List.of(stack), spirits);
    }

    public SpiritBasedRecipeInput(List<ItemStack> items, List<ItemStack> spirits) {
        this.items = items;
        this.spirits = spirits;
    }

    @Override
    public ItemStack getItem(int index) {
        return index < items.size() ? items.get(index) : spirits.get(index-items.size());
    }

    @Override
    public int size() {
        return items.size() + spirits.size();
    }

    public boolean test(Ingredient mainIngredient, List<Ingredient> extraIngredients, List<SpiritIngredient> spirits) {
        final List<Ingredient> ingredients = new ArrayList<>(List.of(mainIngredient));
        ingredients.addAll(extraIngredients);
        return testItems(ingredients) && testSpirits(spirits);
    }
    public boolean test(Ingredient ingredient, List<SpiritIngredient> spirits) {
        return testItems(List.of(ingredient)) && testSpirits(spirits);
    }
    public boolean test(List<Ingredient> ingredients, List<SpiritIngredient> spirits) {
        return testItems(ingredients) && testSpirits(spirits);
    }

    public boolean testItems(List<Ingredient> ingredients) {
        if (ingredients.isEmpty()) {
            return true;
        }
        if (items.size() != ingredients.size()) {
            return false;
        }

        for (int i = 0; i < this.spirits.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            ItemStack stack = items.get(i);
            if (!ingredient.test(stack)) {
                return false;
            }
        }
        return true;
    }
    public boolean testSpirits(List<SpiritIngredient> recipeSpirits) {
        if (recipeSpirits.isEmpty()) {
            return true;
        }
        if (this.spirits.size() != recipeSpirits.size()) {
            return false;
        }
        List<ItemStack> sortedStacks = sortSpirits(recipeSpirits);
        if (sortedStacks.size() < this.spirits.size()) {
            return false;
        }
        for (int i = 0; i < this.spirits.size(); i++) {
            SpiritIngredient ingredient = recipeSpirits.get(i);
            ItemStack stack = sortedStacks.get(i);
            if (!ingredient.test(stack)) {
                return false;
            }
        }
        return true;
    }

    public List<ItemStack> sortSpirits(List<SpiritIngredient> recipeSpirits) {
        List<ItemStack> sortedStacks = new ArrayList<>();
        for (SpiritIngredient ingredient : recipeSpirits) {
            for (ItemStack stack : spirits) {
                if (ingredient.test(stack)) {
                    sortedStacks.add(stack);
                    break;
                }
            }
        }
        return sortedStacks;
    }
}