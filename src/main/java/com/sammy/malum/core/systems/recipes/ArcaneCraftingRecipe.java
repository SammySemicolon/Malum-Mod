package com.sammy.malum.core.systems.recipes;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.sammy.malum.core.init.MalumItems.*;

public class ArcaneCraftingRecipe
{
    public static final ArrayList<ArcaneCraftingRecipe> recipes = new ArrayList<>();
    public ArrayList<ItemStack> itemStacks;
    public Item outputItem;
    public int outputItemCount;
    public int inputItemCount;
    
    public ArcaneCraftingRecipe(ArrayList<Item> inputItems, ArrayList<Integer> inputItemCounts, Item outputItem, int outputItemCount)
    {
        this.inputItemCount = inputItems.size();
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < inputItemCount; i++)
        {
            stacks.add(new ItemStack(inputItems.get(i), inputItemCounts.get(i)));
        }
        this.itemStacks = stacks;
        this.outputItem = outputItem;
        this.outputItemCount = outputItemCount;
    }
    public static void init()
    {
        recipes.add(new ArcaneCraftingRecipe(
                MalumHelper.toArrayList(ILLUSTRIOUS_FABRIC.get(), SMOOTH_TAINTED_ROCK.get()),
                MalumHelper.toArrayList(2,1),
                MalumItems.ZOOM_ROCK.get(),
                1
        ));
        recipes.add(new ArcaneCraftingRecipe(
                MalumHelper.toArrayList(ILLUSTRIOUS_FABRIC.get(), SMOOTH_DARKENED_TAINTED_ROCK.get()),
                MalumHelper.toArrayList(2,1),
                DARKENED_ZOOM_ROCK.get(),
                1
        ));
    }
    public static ArcaneCraftingRecipe getRecipe(ArrayList<ItemStack> stacks)
    {
        for (ArcaneCraftingRecipe recipe : recipes)
        {
            ArcaneCraftingRecipe rightRecipeMaybe = test(stacks, recipe);
            if (rightRecipeMaybe != null)
            {
                return rightRecipeMaybe;
            }
        }
        return null;
    }
    public static ArcaneCraftingRecipe test(ArrayList<ItemStack> stacks, ArcaneCraftingRecipe recipe)
    {
        ArrayList<ItemStack> desiredStacks = MalumHelper.nonEmptyStackList(stacks);
        ArrayList<Item> recipeItems = new ArrayList<>();
        recipe.itemStacks.forEach(i -> recipeItems.add(i.getItem()));
        if (desiredStacks.isEmpty())
        {
            return null;
        }
        if (desiredStacks.size() != recipe.itemStacks.size())
        {
            return null;
        }
        for (int i = 0; i < desiredStacks.size(); i++)
        {
            if (recipeItems.contains(desiredStacks.get(i).getItem()))
            {
                int finalI = i;
                ItemStack stack = desiredStacks.stream().filter(s -> s.getItem().equals(desiredStacks.get(finalI).getItem())).findFirst().get();
                if (stack.getCount() < desiredStacks.get(i).getCount())
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
        return recipe;
    }
}