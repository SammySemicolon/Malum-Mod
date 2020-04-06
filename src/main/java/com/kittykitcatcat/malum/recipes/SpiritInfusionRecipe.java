package com.kittykitcatcat.malum.recipes;

import com.kittykitcatcat.malum.SpiritData;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.recipes.spiritinfusionresults.CarryOverNBTResult;
import com.kittykitcatcat.malum.recipes.spiritinfusionresults.ISpiritInfusionResult;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class SpiritInfusionRecipe
{
    Item catalyst;
    List<Item> items = new ArrayList<>();
    int infusionTime;
    SpiritData data;

    ItemStack outputStack;
    ISpiritInfusionResult infusionResult;

    public SpiritInfusionRecipe(Item catalyst, Item input1, Item input2, Item input3, Item input4, Item input5, Item input6, Item input7, Item input8, ItemStack outputStack, int infusionTime, SpiritData data)
    {
        this.catalyst = catalyst;
        items.add(input1);
        items.add(input2);
        items.add(input3);
        items.add(input4);
        items.add(input5);
        items.add(input6);
        items.add(input7);
        items.add(input8);
        this.infusionTime = infusionTime;
        this.outputStack = outputStack;
        this.data = data;
    }

    public SpiritInfusionRecipe(Item catalyst, Item input1, Item input2, Item input3, Item input4, Item input5, Item input6, Item input7, Item input8,  ItemStack outputStack, ISpiritInfusionResult infusionResult, int infusionTime, SpiritData data)
    {
        this.catalyst = catalyst;
        items.add(input1);
        items.add(input2);
        items.add(input3);
        items.add(input4);
        items.add(input5);
        items.add(input6);
        items.add(input7);
        items.add(input8);
        this.infusionTime = infusionTime;
        this.outputStack = outputStack;
        this.data = data;
        this.infusionResult = infusionResult;
    }

    public ISpiritInfusionResult getInfusionResult()
    {
        return infusionResult;
    }

    public int getInfusionTime()
    {
        return infusionTime;
    }

    public Item getCatalyst()
    {
        return catalyst;
    }

    public ItemStack getOutputStack()
    {
        return outputStack;
    }
    public List<Item> getItems()
    {
        return items;
    }

    public SpiritData getData()
    {
        return data;
    }

    public static void initRecipes()
    {
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                Items.APPLE,
                Items.DIAMOND,
                Items.DIAMOND,
                Items.DIAMOND,
                Items.DIAMOND,
                Items.DIAMOND,
                Items.DIAMOND,
                Items.DIAMOND,
                Items.DIAMOND,
                new ItemStack(Items.NETHER_STAR),
                new CarryOverNBTResult(),
                40,
                new SpiritData("minecraft:zombie", 2)
        ));
    }
}