package com.kittykitcatcat.malum.recipes;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.SpiritData;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.recipes.spiritinfusionresults.CarryOverNBTResult;
import com.kittykitcatcat.malum.recipes.spiritinfusionresults.ISpiritInfusionResult;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.item.Items.AIR;

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

    public SpiritInfusionRecipe(Item catalyst, Item input1, Item input2, Item input3, Item input4, Item input5, Item input6, Item input7, Item input8, ItemStack outputStack, ISpiritInfusionResult infusionResult, int infusionTime, SpiritData data)
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
                ModItems.spirited_steel_ingot,
                ModItems.runic_ash,
                ModItems.transmissive_ingot,
                ModItems.runic_ash,
                AIR,
                ModItems.transmissive_ingot,
                ModItems.runic_ash,
                ModItems.transmissive_ingot,
                AIR,
                MalumHelper.itemStack(ModItems.royal_steel_ingot, 6),
                160,
                new SpiritData("minecraft:zombie", 0.5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                ModItems.stygian_pearl,
                ModItems.spirit_stone,
                ModItems.spirited_steel_ingot,
                ModItems.spirit_stone,
                ModItems.spirited_steel_ingot,
                ModItems.spirit_stone,
                ModItems.spirited_steel_ingot,
                ModItems.spirit_stone,
                ModItems.spirited_steel_ingot,
                new ItemStack(ModItems.arcane_apparatus),
                320,
                new SpiritData("minecraft:guardian", 1.5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                ModItems.cursed_nebulous,
                ModItems.enchanted_quartz,
                ModItems.arcane_apparatus,
                ModItems.vacant_gemstone,
                ModItems.arcane_apparatus,
                ModItems.enchanted_quartz,
                ModItems.arcane_apparatus,
                ModItems.vacant_gemstone,
                ModItems.arcane_apparatus,
                new ItemStack(ModItems.stellar_apparatus),
                320,
                new SpiritData("minecraft:wither", 0.25f)
        ));

        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                ModItems.vacant_gemstone,
                AIR,
                ModItems.vacant_gemstone,
                AIR,
                ModItems.spirited_steel_ingot,
                ModItems.spirit_stone,
                ModItems.spirited_steel_ingot,
                ModItems.spirit_stone,
                ModItems.spirited_steel_ingot,
                new ItemStack(ModItems.block_transmutation_tool),
                640,
                new SpiritData("minecraft:guardian", 3f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                Items.ENDER_CHEST,
                AIR,
                ModItems.stygian_pearl,
                AIR,
                ModItems.spirited_steel_ingot,
                AIR,
                ModItems.spirited_steel_ingot,
                AIR,
                ModItems.spirited_steel_ingot,
                new ItemStack(ModItems.ender_artifact),
                640,
                new SpiritData("minecraft:enderman", 3f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                Items.LEATHER,
                AIR,
                Items.SPIDER_EYE,
                AIR,
                Items.GUNPOWDER,
                AIR,
                Items.ROTTEN_FLESH,
                AIR,
                Items.BONE,
                MalumHelper.itemStack(Items.LEATHER, 9),
                80,
                new SpiritData("minecraft:cow", 0.5f)
        ));
    }
}