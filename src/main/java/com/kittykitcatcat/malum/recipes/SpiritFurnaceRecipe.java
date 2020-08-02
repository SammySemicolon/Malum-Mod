package com.kittykitcatcat.malum.recipes;

import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.GOLD_INGOT, ModItems.transmissive_ingot, ModItems.runic_ash, 6, 400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.IRON_INGOT, ModItems.spirited_steel_ingot, ModItems.runic_ash, 6, 800));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.DIAMOND, ModItems.vacant_gemstone, ModItems.runic_ash, 4, 1600));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.QUARTZ, ModItems.enchanted_quartz, ModItems.runic_ash, 4, 3200));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.LEATHER, ModItems.evil_leather, 400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.STRING, ModItems.spirit_silk, 400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.STONE, ModItems.spirit_stone, ModItems.archaic_sulphur, 2, 200));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.OBSIDIAN, ModItems.dark_spirit_stone, ModItems.archaic_sulphur, 2, 6400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.ENDER_PEARL, ModItems.stygian_pearl, 6400));
        ModRecipes.addSpiritFurnaceRecipe(new SpiritFurnaceRecipe(Items.NETHER_STAR, ModItems.cursed_nebulous, 64000));
    }
}