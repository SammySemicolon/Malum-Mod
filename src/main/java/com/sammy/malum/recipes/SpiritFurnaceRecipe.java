package com.sammy.malum.recipes;

import com.sammy.malum.init.ModItems;
import com.sammy.malum.init.ModRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class SpiritFurnaceRecipe
{

    private final Item inputItem;
    private final Item outputItem;
    private Item sideItem;
    private int sideItemChance;
    private final int burnTime;

    public SpiritFurnaceRecipe(Item inputItem, Item outputItem, int burnTime)
    {
        this.inputItem = inputItem;
        this.outputItem = outputItem;
        this.burnTime = burnTime;
    }

    public SpiritFurnaceRecipe(Item inputItem, Item outputItem, Item sideItem, int sideItemChance, int burnTime)
    {
        this.inputItem = inputItem;
        this.outputItem = outputItem;
        this.sideItem = sideItem;
        this.sideItemChance = sideItemChance;
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

    public Item getSideItem()
    {
        return sideItem;
    }

    public int getSideItemChance()
    {
        return sideItemChance;
    }

    public int getBurnTime()
    {
        return burnTime;
    }

    public static void initRecipes()
    {
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.GOLD_INGOT, ModItems.transmissive_ingot, ModItems.archaic_crystal_ore, 6, 400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.IRON_INGOT, ModItems.spirited_steel_ingot, ModItems.archaic_crystal_ore, 6, 400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.DIAMOND, ModItems.vacant_gemstone, ModItems.archaic_crystal_ore, 4, 1600));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.QUARTZ, ModItems.enchanted_quartz, ModItems.archaic_crystal_ore, 4, 1600));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.LEATHER, ModItems.evil_leather, 400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.STRING, ModItems.spirit_silk, 400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.STONE, ModItems.spirit_stone, ModItems.runic_ash, 2, 200));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.OBSIDIAN, ModItems.dark_spirit_stone, ModItems.runic_ash, 2, 200));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.ENDER_PEARL, ModItems.stygian_pearl, 6400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.NETHER_STAR, ModItems.cursed_nebulous, 64000));
    }
}