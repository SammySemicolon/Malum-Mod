package com.sammy.malum.core.recipes;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;

import static com.sammy.malum.core.recipes.TaintTransfusion.transfusions;

public class TaintedFurnaceRecipe
{
    public static final ArrayList<TaintedFurnaceRecipe> recipes = new ArrayList<>();
    public static int globalSpeedMultiplier = 40;
    public final Item inputItem;
    public final int inputItemCount;
    public final Item outputItem;
    public final int outputItemCount;
    public final int recipeTime;
    public ArrayList<Item> extraItems;
    public TaintedFurnaceRecipe(Item inputItem, int inputItemCount, Item outputItem, int outputItemCount, int recipeTime) {
        this.inputItem = inputItem;
        this.inputItemCount = inputItemCount;
        this.outputItem = outputItem;
        this.outputItemCount = outputItemCount;
        this.recipeTime = recipeTime * globalSpeedMultiplier;
        recipes.add(this);
    }
    public TaintedFurnaceRecipe(Item inputItem, int inputItemCount, Item outputItem, int outputItemCount, int recipeTime, Item... items) {
        this.inputItem = inputItem;
        this.inputItemCount = inputItemCount;
        this.outputItem = outputItem;
        this.outputItemCount = outputItemCount;
        this.recipeTime = recipeTime * globalSpeedMultiplier;
        this.extraItems = new ArrayList<>();
        for (Item item : items)
        {
            if (extraItems.contains(item))
            {
                throw new ArrayStoreException("Yo there shouldn't be any duplicate extra items in a tainted furnace recipe, this one is 100% on you. Duplicate item: " + item.getRegistryName().getPath());
            }
            extraItems.add(item);
        }
        recipes.add(this);
    }
    public boolean isAdvanced()
    {
        return extraItems != null;
    }
    public static void init()
    {
        new TaintedFurnaceRecipe(Items.GLOWSTONE_DUST, 2, MalumItems.DARK_FLARES.get(), 1, 4, Items.COAL, Items.GUNPOWDER);
        new TaintedFurnaceRecipe(Items.GOLD_INGOT, 1, MalumItems.TRANSMISSIVE_METAL_INGOT.get(), 1, 4, MalumItems.SULPHUR.get(), Items.GOLD_NUGGET);
        new TaintedFurnaceRecipe(Items.IRON_INGOT, 2, MalumItems.RUIN_PLATING.get(), 4, 4, MalumItems.DARK_FLARES.get(), Items.IRON_NUGGET);
        new TaintedFurnaceRecipe(Items.FLINT, 2, Items.GUNPOWDER, 1,4);
        for (TaintTransfusion transfusion : transfusions)
        {
            new TaintedFurnaceRecipe(transfusion.inputBlock.asItem(),1,transfusion.outputBlock.asItem(),1,1);
        }
    }
    public static TaintedFurnaceRecipe getRecipe(ItemStack stack)
    {
        for (TaintedFurnaceRecipe recipe : recipes)
        {
            if (recipe.inputItem.equals(stack.getItem()))
            {
                if (stack.getCount() >= recipe.inputItemCount)
                {
                    return recipe;
                }
            }
        }
        return null;
    }
}
