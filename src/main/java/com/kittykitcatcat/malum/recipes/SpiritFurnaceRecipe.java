package com.kittykitcatcat.malum.recipes;

import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class SpiritFurnaceRecipe
{

    private Item inputItem;
    private Item outputItem;
    private int outputCount;
    private int burnTime;
    public SpiritFurnaceRecipe(Item inputItem, Item outputItem, int outputCount, int burnTime)
    {
        this.inputItem = inputItem;
        this.outputItem = outputItem;
        this.outputCount = outputCount;
        this.burnTime = burnTime;
    }

    public Item getInputItem()
    {
        return inputItem;
    }

    public Item getOutputItem()
    {
        return outputItem;
    }
    public int getOutputCount()
    {
        return outputCount;
    }
    public int getBurnTime()
    {
        return burnTime;
    }
    public static void initRecipes()
    {
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.DIAMOND, ModItems.vacant_gemstone, 1, 20));
    }
}
