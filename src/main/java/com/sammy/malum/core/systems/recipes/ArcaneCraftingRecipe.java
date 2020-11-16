package com.sammy.malum.core.systems.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;

public class ArcaneCraftingRecipe
{
    public static final ArrayList<ArcaneCraftingRecipe> recipes = new ArrayList<>();
    public Item[] inputItems;
    public int[] inputItemCounts;
    public Item outputItem;
    public int outputItemCount;
    
    public ArcaneCraftingRecipe(Item[] inputItems, int[] inputItemCounts, Item outputItem, int outputItemCount)
    {
        this.inputItems = inputItems;
        this.inputItemCounts = inputItemCounts;
        this.outputItem = outputItem;
        this.outputItemCount = outputItemCount;
    }
    public static void initRecipes()
    {
        recipes.add(new ArcaneCraftingRecipe(
                new Item[]{Items.NETHERITE_SCRAP, Items.ACACIA_BOAT, Items.SLIME_BALL, Items.ACACIA_DOOR,Items.NETHERITE_SCRAP},
                new int[]{2,3,4,6,1},
                Items.SLIME_BALL,
                2
        ));
    }
}