package com.sammy.malum.recipes;

import com.sammy.malum.init.ModItems;
import com.sammy.malum.init.ModRecipes;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tileentity.FurnaceTileEntity;

public class CrystallineAcceleratorRecipe
{
    
    private final Item inputItem;
    private final int inputCount;
    private final int recipeTime;
    private final Item outputItem;
    private final int outputCount;
    private final boolean outputSpirit;
    
    public CrystallineAcceleratorRecipe(Item inputItem, int inputCount, int recipeTime, Item outputItem, int outputCount, boolean outputSpirit)
    {
        this.inputItem = inputItem;
        this.inputCount = inputCount;
        this.recipeTime = recipeTime;
        this.outputItem = outputItem;
        this.outputCount = outputCount;
        this.outputSpirit = outputSpirit;
    }
    
    public static void initData()
    {
        ModRecipes.addCrystallineAcceleratorRecipe(new CrystallineAcceleratorRecipe(Items.LAPIS_ORE,1, 200, Items.LAPIS_BLOCK, 1,false));
        ModRecipes.addCrystallineAcceleratorRecipe(new CrystallineAcceleratorRecipe(Items.EMERALD_ORE,1, 200, Items.EMERALD_BLOCK, 1,true));
    }
    
    public Item getInputItem()
    {
        return inputItem;
    }
    
    public int getInputCount()
    {
        return inputCount;
    }
    
    public int getRecipeTime()
    {
        return recipeTime;
    }
    
    public Item getOutputItem()
    {
        return outputItem;
    }
    
    public int getOutputCount()
    {
        return outputCount;
    }
    
    public boolean getOutputSpirit()
    {
        return outputSpirit;
    }
}