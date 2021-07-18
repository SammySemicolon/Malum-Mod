package com.sammy.malum.core.modcontent;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.recipes.ItemIngredient;
import com.sammy.malum.core.systems.recipes.SimpleItemIngredient;
import com.sammy.malum.core.systems.recipes.SpiritIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;

public class MalumArcaneAssemblyRecipes
{
    public static final ArrayList<MalumArcaneAssemblerRecipe> RECIPES = new ArrayList<>();

    public static void init()
    {
        new MalumArcaneAssemblerRecipe(
                new SimpleItemIngredient(Tags.Items.GEMS_LAPIS),
                new ItemIngredient(MalumItems.CONFINED_BRILLIANCE.get(), 4));

        new MalumArcaneAssemblerRecipe(
                new SimpleItemIngredient(Items.GLASS_BOTTLE),
                new ItemIngredient(Items.EXPERIENCE_BOTTLE, 1),
                new SimpleItemIngredient(MalumItems.CONFINED_BRILLIANCE.get()));

        new MalumArcaneAssemblerRecipe(
                new SimpleItemIngredient(Tags.Items.CROPS_WHEAT),
                new ItemIngredient(MalumItems.CURSED_STRAND.get(), 1));
    }

    public static MalumArcaneAssemblerRecipe getRecipe(ItemStack stack)
    {
        for (MalumArcaneAssemblerRecipe recipe : RECIPES)
        {
            if (recipe.primeIngredient.matches(stack))
            {
                return recipe;
            }
        }
        return null;
    }

    public static class MalumArcaneAssemblerRecipe
    {
        public ItemIngredient primeIngredient;
        public ArrayList<SimpleItemIngredient> extraIngredients;
        public ItemIngredient outputIngredient;

        public MalumArcaneAssemblerRecipe(ItemIngredient primeIngredient, ItemIngredient outputIngredient, SimpleItemIngredient... extraIngredients)
        {
            this.primeIngredient = primeIngredient;
            this.outputIngredient = outputIngredient;
            this.extraIngredients = MalumHelper.toArrayList(extraIngredients);
            RECIPES.add(this);
        }

        public ArrayList<ItemStack> sortedStacks(ArrayList<ItemStack> stacks)
        {
            ArrayList<ItemStack> sortedStacks = new ArrayList<>();
            for (SimpleItemIngredient ingredient : extraIngredients)
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

        public boolean matches(ItemStack inputStack, ArrayList<ItemStack> stacks)
        {
            if (inputStack.getCount() < primeIngredient.count)
            {
                return false;
            }
            ArrayList<ItemStack> sortedStacks = sortedStacks(stacks);
            if (sortedStacks.size() < extraIngredients.size())
            {
                return false;
            }
            for (int i = 0; i < extraIngredients.size(); i++)
            {
                SimpleItemIngredient ingredient = extraIngredients.get(i);
                ItemStack stack = sortedStacks.get(i);
                if (!ingredient.matches(stack))
                {
                    return false;
                }
            }
            return true;
        }

        public boolean isItemAllowed(ItemStack stack)
        {
            if (extraIngredients.size() == 0)
            {
                return false;
            }
            for (SimpleItemIngredient ingredient : extraIngredients)
            {
                if (ingredient.matches(stack))
                {
                    return true;
                }
            }
            return false;
        }
    }
}