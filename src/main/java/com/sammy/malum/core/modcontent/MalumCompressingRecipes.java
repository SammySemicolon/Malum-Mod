package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.recipes.ItemIngredient;
import com.sammy.malum.core.systems.recipes.SimpleItemIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;

public class MalumCompressingRecipes
{

    public static final ArrayList<ArcaneCompressorRecipe> RECIPES = new ArrayList<>();

    public static void init()
    {
        new ArcaneCompressorRecipe(new ItemIngredient(MalumItems.CONFINED_BRILLIANCE.get(), 8))
                .addExtraItem(new ItemIngredient(MalumItems.ARCANE_SPIRIT.get(), 2))
                .addExtraItem(new ItemIngredient(Tags.Items.GEMS_LAPIS, 8));
    }

    public static ArcaneCompressorRecipe getRecipe(ArrayList<ItemStack> stacks)
    {
        for (ArcaneCompressorRecipe recipe : RECIPES)
        {
            if (recipe.matches(stacks))
            {
                return recipe;
            }
        }
        return null;
    }


    public static class ArcaneCompressorRecipe
    {
        public ArrayList<ItemIngredient> itemIngredients;
        public ItemIngredient outputIngredient;

        public ArcaneCompressorRecipe(ItemIngredient outputIngredient)
        {
            this.outputIngredient = outputIngredient;
            this.itemIngredients = new ArrayList<>();
            RECIPES.add(this);
        }

        public ArcaneCompressorRecipe addExtraItem(ItemIngredient ingredient)
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