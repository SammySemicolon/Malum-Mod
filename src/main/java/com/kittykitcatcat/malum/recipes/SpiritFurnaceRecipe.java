package com.kittykitcatcat.malum.recipes;

import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SpiritFurnaceRecipe
{

    private ItemStack inputItem;
    private ItemStack outputItem;
    private ItemStack sideItem;
    private int burnTime;

    public SpiritFurnaceRecipe(ItemStack inputItem, ItemStack outputItem, int burnTime)
    {
        this.inputItem = inputItem;
        this.outputItem = outputItem;
        this.burnTime = burnTime;
    }

    public SpiritFurnaceRecipe(ItemStack inputItem, ItemStack outputItem, ItemStack sideItem, int burnTime)
    {
        this.inputItem = inputItem;
        this.outputItem = outputItem;
        this.sideItem = sideItem;
        this.burnTime = burnTime;
    }

    public ItemStack getInputItem()
    {
        return inputItem;
    }

    public ItemStack getOutputItem()
    {
        return outputItem;
    }

    public ItemStack getSideItem()
    {
        return sideItem;
    }

    public int getBurnTime()
    {
        return burnTime;
    }

    public static void initRecipes()
    {
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(new ItemStack(Items.GOLD_INGOT), new ItemStack(ModItems.transmissive_ingot), new ItemStack(ModItems.runic_ash),  400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(ModItems.spirited_steel_ingot), new ItemStack(ModItems.runic_ash), 800));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(new ItemStack(Items.DIAMOND), new ItemStack(ModItems.vacant_gemstone), new ItemStack(ModItems.runic_ash), 1600));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(new ItemStack(Items.QUARTZ), new ItemStack(ModItems.enchanted_quartz), new ItemStack(ModItems.runic_ash), 3200));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(new ItemStack(Items.LEATHER), new ItemStack(ModItems.evil_leather), 400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(new ItemStack(Items.STRING), new ItemStack(ModItems.spirit_silk), 400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(new ItemStack(Items.STONE), new ItemStack(ModItems.spirit_stone), 100));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(new ItemStack(Items.OBSIDIAN), new ItemStack(ModItems.dark_spirit_stone),  6400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(new ItemStack(Items.ENDER_PEARL), new ItemStack(ModItems.stygian_pearl),  6400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(new ItemStack(Items.NETHER_STAR), new ItemStack(ModItems.cursed_nebulous),  64000));
    }
}