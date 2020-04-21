package com.kittykitcatcat.malum.recipes;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.SpiritData;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.recipes.spiritinfusionresults.CarryOverNBTResult;
import com.kittykitcatcat.malum.recipes.spiritinfusionresults.ISpiritInfusionResult;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.IntNBT;

import java.util.ArrayList;
import java.util.List;

import static com.kittykitcatcat.malum.MalumHelper.*;
import static com.kittykitcatcat.malum.init.ModItems.*;
import static net.minecraft.item.Items.*;
import static net.minecraft.item.Items.AIR;

public class SpiritInfusionRecipe
{
    Item catalyst;
    List<Item> items = new ArrayList<>();
    int infusionTime;
    SpiritData data;

    ItemStack outputStack;
    ISpiritInfusionResult infusionResult;

    public SpiritInfusionRecipe(Item input0, Item input1, Item input2, Item input3, Item catalyst,  Item input4, Item input5, Item input6, Item input7, ItemStack outputStack, int infusionTime, SpiritData data)
    {
        items.add(input1);
        items.add(input2);
        items.add(input4);
        items.add(input7);
        items.add(input6);
        items.add(input5);
        items.add(input3);
        items.add(input0);
        this.catalyst = catalyst;
        this.infusionTime = infusionTime;
        this.outputStack = outputStack;
        this.data = data;
    }

    public SpiritInfusionRecipe(Item input0, Item input1, Item input2, Item input3,Item catalyst,  Item input4, Item input5, Item input6, Item input7, ItemStack outputStack, ISpiritInfusionResult infusionResult, int infusionTime, SpiritData data)
    {
        items.add(input1);
        items.add(input2);
        items.add(input4);
        items.add(input7);
        items.add(input6);
        items.add(input5);
        items.add(input3);
        items.add(input0);
        this.catalyst = catalyst;

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
                runic_ash, transmissive_ingot, runic_ash,
                AIR, spirited_steel_ingot, AIR,
                runic_ash, transmissive_ingot, runic_ash,
                stackWithCount(royal_steel_ingot, 6),
                160,
                new SpiritData("minecraft:zombie", 0.5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                AIR, runic_ash, AIR,
                REDSTONE, BLAZE_POWDER, GUNPOWDER,
                AIR, GLOWSTONE_DUST, AIR,
                MalumHelper.stackWithCount(exothermic_ash, 4),
                320,
                new SpiritData("minecraft:blaze", 1.5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                spirited_steel_ingot, spirited_steel_ingot, spirited_steel_ingot,
                MAGMA_CREAM, exothermic_ash, MAGMA_CREAM,
                spirited_steel_ingot, spirited_steel_ingot, spirited_steel_ingot,
                MalumHelper.stackWithCount(exothermic_cell, 2),
                640,
                new SpiritData("minecraft:magma_cube", 4f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                spirited_steel_ingot, spirit_stone, spirited_steel_ingot,
                spirit_stone, stygian_pearl, spirit_stone,
                spirited_steel_ingot, spirit_stone, spirited_steel_ingot,
                MalumHelper.stackWithCount(arcane_apparatus, 2),
                320,
                new SpiritData("minecraft:guardian", 1.5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                arcane_apparatus, enchanted_quartz, arcane_apparatus,
                vacant_gemstone, cursed_nebulous, enchanted_quartz,
                arcane_apparatus, vacant_gemstone, arcane_apparatus,
                new ItemStack(stellar_apparatus),
                640,
                new SpiritData("minecraft:wither", 0.5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                SHULKER_SHELL, evil_leather, vacant_gemstone,
                evil_leather, SHULKER_SHELL, evil_leather,
                vacant_gemstone, evil_leather, AIR,
                new ItemStack(shulker_on_heal_curio),
                new CarryOverNBTResult(),
                640,
                new SpiritData("minecraft:shulker", 5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                PHANTOM_MEMBRANE, arcane_apparatus, PHANTOM_MEMBRANE,
                FEATHER, PHANTOM_MEMBRANE, FEATHER,
                FEATHER, AIR, FEATHER,
                new ItemStack(phantom_wings_curio),
                new CarryOverNBTResult(),
                640,
                new SpiritData("minecraft:phantom", 2.5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                CROSSBOW, evil_leather, spirit_silk,
                evil_leather, ARROW, evil_leather,
                spirit_silk, evil_leather, AIR,
                new ItemStack(crossbow_reload_curio),
                new CarryOverNBTResult(),
                640,
                new SpiritData("minecraft:pillager", 2.5f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                AIR, IRON_INGOT, IRON_BLOCK,
                AIR, IRON_BLOCK, IRON_INGOT,
                STICK, AIR, AIR,
                new ItemStack(ultimate_weapon),
                160,
                new SpiritData("minecraft:iron_golem", 1f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                AIR, vacant_gemstone, AIR,
                spirited_steel_ingot, vacant_gemstone, spirited_steel_ingot,
                spirit_stone, spirited_steel_ingot, spirit_stone,
                new ItemStack(block_transmutation_tool),
                640,
                new SpiritData("minecraft:guardian", 3f)
        ));
        ModRecipes.addSpiritInfusionRecipe(new SpiritInfusionRecipe(
                AIR, stygian_pearl, AIR,
                spirited_steel_ingot, ENDER_CHEST, spirited_steel_ingot,
                AIR, spirited_steel_ingot, AIR,
                new ItemStack(ender_artifact),
                640,
                new SpiritData("minecraft:enderman", 3f)
        ));
    }
}