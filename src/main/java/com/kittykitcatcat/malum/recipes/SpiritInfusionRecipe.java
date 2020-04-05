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
    List<RitualAnchorInput> items = new ArrayList<>();
    int infusionTime;
    SpiritData data;

    ItemStack outputStack;
    ISpiritInfusionResult infusionResult;

    public SpiritInfusionRecipe(Item catalyst, RitualAnchorInput input1, RitualAnchorInput input2, RitualAnchorInput input3, RitualAnchorInput input4, ItemStack outputStack, int infusionTime, SpiritData data)
    {
        this.catalyst = catalyst;
        items.add(input1);
        items.add(input2);
        items.add(input3);
        items.add(input4);
        this.infusionTime = infusionTime;
        this.outputStack = outputStack;
        this.data = data;
    }

    public SpiritInfusionRecipe(Item catalyst, RitualAnchorInput input1, RitualAnchorInput input2, RitualAnchorInput input3, RitualAnchorInput input4, ItemStack outputStack, ISpiritInfusionResult infusionResult, int infusionTime, SpiritData data)
    {
        this.catalyst = catalyst;
        items.add(input1);
        items.add(input2);
        items.add(input3);
        items.add(input4);
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
    public List<RitualAnchorInput> getInputs()
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
                Items.DIAMOND,
                new RitualAnchorInput(Items.ACACIA_LOG, Items.ACACIA_LOG, Items.ACACIA_LOG, Items.ACACIA_LOG),
                new RitualAnchorInput(Items.ACACIA_PLANKS, Items.ACACIA_PLANKS, Items.ACACIA_PLANKS, Items.ACACIA_PLANKS),
                new RitualAnchorInput(Items.ACACIA_FENCE, Items.ACACIA_FENCE, Items.ACACIA_FENCE, Items.ACACIA_FENCE),
                new RitualAnchorInput(Items.ACACIA_SAPLING, Items.ACACIA_SAPLING, Items.ACACIA_SAPLING, Items.ACACIA_SAPLING),
                new ItemStack(Items.NETHER_STAR),
                new CarryOverNBTResult(),
                40,
                new SpiritData("minecraft:zombie", 2)
        ));
    }
}