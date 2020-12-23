package com.sammy.malum.core.recipes;

import com.sammy.malum.MalumHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ArcaneCraftingRecipe
{
    public static final ArrayList<ArcaneCraftingRecipe> recipes = new ArrayList<>();
    public ArrayList<Item> inputItems;
    public ArrayList<String> spirits;
    public Item outputItem;
    public int outputItemCount;
    
    public ArcaneCraftingRecipe(ArrayList<Item> inputItems, ArrayList<String> spirits, Item outputItem, int outputItemCount)
    {
        this.inputItems = inputItems;
        this.spirits = spirits;
        this.outputItem = outputItem;
        this.outputItemCount = outputItemCount;
        recipes.add(this);
    }
    public static void init()
    {
//        new ArcaneCraftingRecipe(toArrayList(Items.IRON_INGOT, Items.IRON_INGOT, DARK_FLARES.get()), toArrayList(SINISTER_ESSENCE.identifier, SINISTER_ESSENCE.identifier),RUIN_PLATING.get(), 2);
//        new ArcaneCraftingRecipe(toArrayList(Items.GUNPOWDER, Items.GLOWSTONE_DUST, Items.GLOWSTONE_DUST), toArrayList(ARCANE_ESSENCE.identifier,ARCANE_ESSENCE.identifier, UNDEAD_ESSENCE.identifier),ECTOPLASM.get(), 4);
//
//        new ArcaneCraftingRecipe(toArrayList(Items.BLAZE_ROD), toArrayList(NETHERBORNE_ESSENCE.identifier),Items.BLAZE_POWDER, 3);
    }
    public static ArcaneCraftingRecipe getRecipe(ArrayList<ItemStack> stacks)
    {
        for (ArcaneCraftingRecipe recipe : recipes)
        {
            ArcaneCraftingRecipe rightRecipeMaybe = test(MalumHelper.nonEmptyStackList(stacks), recipe);
            if (rightRecipeMaybe != null)
            {
                return rightRecipeMaybe;
            }
        }
        return null;
    }
    public static ArcaneCraftingRecipe test(ArrayList<ItemStack> stacks, ArcaneCraftingRecipe recipe)
    {
        if (stacks.isEmpty())
        {
            return null;
        }
        if (stacks.size() != recipe.inputItems.size())
        {
            return null;
        }
        for (ItemStack stack : stacks)
        {
            if (!recipe.inputItems.contains(stack.getItem()))
            {
                return null;
            }
        }
        return recipe;
    }
}