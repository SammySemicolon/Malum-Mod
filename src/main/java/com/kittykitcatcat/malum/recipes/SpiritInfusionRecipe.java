package com.kittykitcatcat.malum.recipes;

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
    String soul;

    ItemStack outputStack;
    ISpiritInfusionResult infusionResult;
    public SpiritInfusionRecipe(Item catalyst, RitualAnchorInput input1, RitualAnchorInput input2, RitualAnchorInput input3, RitualAnchorInput input4,ItemStack outputStack, int infusionTime, String soul)
    {
        this.catalyst = catalyst;
        items.add(input1);
        items.add(input2);
        items.add(input3);
        items.add(input4);
        this.infusionTime = infusionTime;
        this.outputStack = outputStack;
        this.soul = soul;
    }
    public SpiritInfusionRecipe(Item catalyst, RitualAnchorInput input1, RitualAnchorInput input2, RitualAnchorInput input3, RitualAnchorInput input4,ItemStack outputStack,ISpiritInfusionResult infusionResult, int infusionTime, String soul)
    {
        this.catalyst = catalyst;
        items.add(input1);
        items.add(input2);
        items.add(input3);
        items.add(input4);
        this.infusionTime = infusionTime;
        this.outputStack = outputStack;
        this.soul = soul;
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

    public List<RitualAnchorInput> getInputs()
    {
        return items;
    }

    public String getSoul()
    {
        return soul;
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
                        1000,
                        "bruh"));
    }
}