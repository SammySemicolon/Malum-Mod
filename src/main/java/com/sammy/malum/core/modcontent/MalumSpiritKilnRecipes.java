package com.sammy.malum.core.modcontent;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;

public class MalumSpiritKilnRecipes
{
    public static final ArrayList<MalumSpiritKilnRecipe> INFUSING = new ArrayList<>();
    
    public static void init()
    {
        new MalumSpiritKilnRecipe(Items.SOUL_SAND, 1, MalumItems.SOUL_SHARD.get(), 1, 2, MalumItems.BLAZE_QUARTZ.get(), Items.QUARTZ);
        new MalumSpiritKilnRecipe(Items.GOLD_INGOT, 1, MalumItems.HALLOWED_GOLD_INGOT.get(), 1, 4, MalumItems.AVARICIOUS_SPIRIT_SPLINTER.get(), MalumItems.FUSIBLE_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(Items.IRON_INGOT, 1, MalumItems.SPIRITED_METAL_INGOT.get(), 1, 4, MalumItems.SINISTER_SPIRIT_SPLINTER.get(), MalumItems.FUSIBLE_SPIRIT_SPLINTER.get());
    }
    
    public static MalumSpiritKilnRecipe getRecipe(ItemStack stack)
    {
        for (MalumSpiritKilnRecipe recipe : INFUSING)
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
    
    public static MalumSpiritKilnRecipe getPreciseRecipe(ItemStack stack, ArrayList<Item> extraItems)
    {
        for (MalumSpiritKilnRecipe recipe : INFUSING)
        {
            if (recipe.hasAlternatives)
            {
                if (recipe.inputItem.equals(stack.getItem()))
                {
                    if (stack.getCount() >= recipe.inputItemCount)
                    {
                        if (recipe.extraItems.containsAll(extraItems))
                        {
                            return recipe;
                        }
                    }
                }
            }
        }
        return null;
    }
    public static class MalumSpiritKilnRecipe
    {
    
        public final Item inputItem;
        public final int inputItemCount;
        public final Item outputItem;
        public final int outputItemCount;
        public final int recipeTime;
        public boolean hasAlternatives;
        public ArrayList<Item> extraItems;
    
        public boolean isAdvanced()
        {
            return extraItems != null;
        }
        
        public MalumSpiritKilnRecipe(Item inputItem, int inputItemCount, Item outputItem, int outputItemCount, int recipeTime)
        {
            this.inputItem = inputItem;
            this.inputItemCount = inputItemCount;
            this.outputItem = outputItem;
            this.outputItemCount = outputItemCount;
            this.recipeTime = recipeTime * MalumMod.globalSpeedMultiplier;
            this.hasAlternatives = false;
            INFUSING.add(this);
        }
    
        public MalumSpiritKilnRecipe(Item inputItem, int inputItemCount, Item outputItem, int outputItemCount, int recipeTime, Item... items)
        {
            this.inputItem = inputItem;
            this.inputItemCount = inputItemCount;
            this.outputItem = outputItem;
            this.outputItemCount = outputItemCount;
            this.recipeTime = recipeTime * MalumMod.globalSpeedMultiplier;
            this.extraItems = new ArrayList<>();
            for (Item item : items)
            {
                if (extraItems.contains(item))
                {
                    throw new ArrayStoreException("Yo there shouldn't be any duplicate extra items in a tainted furnace recipe, this one is 100% on you. Duplicate item: " + item.getRegistryName().getPath());
                }
                extraItems.add(item);
            }
            for (MalumSpiritKilnRecipe recipe : INFUSING)
            {
                if (recipe.inputItem.equals(this.inputItem))
                {
                    recipe.hasAlternatives = true;
                    this.hasAlternatives = true;
                }
            }
            INFUSING.add(this);
        }
    }
}