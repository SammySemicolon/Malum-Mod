package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.recipes.ItemIngredient;
import com.sammy.malum.core.systems.recipes.SimpleItemIngredient;
import com.sammy.malum.core.systems.recipes.SpiritIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;

public class MalumWellOfSufferingRecipes
{

    public static final ArrayList<WellOfSufferingRecipe> RECIPES = new ArrayList<>();

    public static void init()
    {

    }

    public static WellOfSufferingRecipe getRecipe(ArrayList<ItemStack> stacks)
    {
        for (WellOfSufferingRecipe recipe : RECIPES)
        {
            if (recipe.matches(stacks))
            {
                return recipe;
            }
        }
        return null;
    }


    public static class WellOfSufferingRecipe
    {
        public ArrayList<ItemIngredient> itemIngredients;
        public ItemIngredient outputIngredient;

        public WellOfSufferingRecipe(ItemIngredient outputIngredient)
        {
            this.outputIngredient = outputIngredient;
            this.itemIngredients = new ArrayList<>();
            RECIPES.add(this);
        }

        public WellOfSufferingRecipe addExtraItem(SimpleItemIngredient ingredient)
        {
            itemIngredients.add(ingredient);
            return this;
        }

        public ArrayList<ItemStack> sortedStacks(ArrayList<ItemStack> stacks)
        {
            ArrayList<ItemStack> sortedStacks = new ArrayList<>();
            for (ItemIngredient ingredient : itemIngredients)
            {
                for (ItemStack stack : stacks)
                {
                    if (ingredient.matches(stack))
                    {
                        sortedStacks.add(stack);
                        break;
                    }
                }
            }
            return sortedStacks;
        }

        public boolean matches(ArrayList<ItemStack> stacks)
        {
            ArrayList<ItemStack> sortedStacks = sortedStacks(stacks);
            if (sortedStacks.size() < itemIngredients.size())
            {
                return false;
            }
            for (int i = 0; i < itemIngredients.size(); i++)
            {
                ItemIngredient ingredient = itemIngredients.get(i);
                ItemStack stack = sortedStacks.get(i);
                if (!ingredient.matches(stack))
                {
                    return false;
                }
            }
            return true;
        }
    }
}