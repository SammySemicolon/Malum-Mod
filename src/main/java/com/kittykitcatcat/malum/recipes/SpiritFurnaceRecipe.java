package com.kittykitcatcat.malum.recipes;

import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class SpiritFurnaceRecipe
{

    private Item inputItem;
    private Item outputItem;
    private Item sideItem;
    private int outputCount;
    private int burnTime;

    public SpiritFurnaceRecipe(Item inputItem, Item outputItem, int outputCount, int burnTime)
    {
        this.inputItem = inputItem;
        this.outputItem = outputItem;
        this.outputCount = outputCount;
        this.burnTime = burnTime;
    }

    public SpiritFurnaceRecipe(Item inputItem, Item outputItem, Item sideItem, int outputCount, int burnTime)
    {
        this.inputItem = inputItem;
        this.outputItem = outputItem;
        this.sideItem = sideItem;
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

    public Item getSideItem()
    {
        return sideItem;
    }

    public int getBurnTime()
    {
        return burnTime;
    }

    public static void initRecipes()
    {
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.QUARTZ, ModItems.enchanted_quartz, ModItems.runic_ash, 1, 3200));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.DIAMOND, ModItems.vacant_gemstone, ModItems.runic_ash, 1, 1600));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.IRON_INGOT, ModItems.unrefined_spirited_steel, ModItems.runic_ash, 1, 800));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.GOLD_INGOT, ModItems.transmissive_ingot, ModItems.runic_ash, 1, 400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.STONE, ModItems.spirit_stone, 1, 100));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.OBSIDIAN, ModItems.dark_spirit_stone, 1, 6400));
    }
}