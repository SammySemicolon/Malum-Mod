package com.kittykitcatcat.malum.init;


import com.kittykitcatcat.malum.recipes.BlockCorruptionRecipe;
import com.kittykitcatcat.malum.recipes.BlockTransmutationRecipe;
import com.kittykitcatcat.malum.recipes.SpiritFurnaceRecipe;
import com.kittykitcatcat.malum.recipes.SpiritInfusionRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRecipes
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerRecipes(FMLCommonSetupEvent event)
    {
        SpiritFurnaceRecipe.initRecipes();
        BlockTransmutationRecipe.initRecipes();
        SpiritInfusionRecipe.initRecipes();
        BlockCorruptionRecipe.initRecipes();
    }

    public static List<SpiritInfusionRecipe> spiritInfusionRecipes = new ArrayList<>();

    public static void addSpiritInfusionRecipe(SpiritInfusionRecipe recipe)
    {
        if (!spiritInfusionRecipes.contains(recipe))
        {
            spiritInfusionRecipes.add(recipe);
        }
    }

    public static SpiritInfusionRecipe getSpiritInfusionRecipe(List<Item> items, Item catalyst)
    {
        if (catalyst != null)
        {
            for (SpiritInfusionRecipe recipe : spiritInfusionRecipes)
            {
                if (catalyst.equals(recipe.getCatalyst()))
                {
                    if (items.equals(recipe.getItems()))
                    {
                        return recipe;
                    }
                }
            }
        }
        return null;
    }

    public static SpiritInfusionRecipe getSpiritInfusionRecipe(Item catalyst)
    {
        if (catalyst != null)
        {
            for (SpiritInfusionRecipe recipe : spiritInfusionRecipes)
            {
                if (catalyst.equals(recipe.getCatalyst()))
                {
                    return recipe;
                }
            }
        }
        return null;
    }
    public static List<BlockTransmutationRecipe> blockTransmutationRecipes = new ArrayList<>();

    public static void addBlockTransmutationRecipe(BlockTransmutationRecipe recipe)
    {
        if (!blockTransmutationRecipes.contains(recipe))
        {
            blockTransmutationRecipes.add(recipe);
        }
    }

    public static BlockTransmutationRecipe getBlockTransmutationRecipe(BlockState sourceBlock)
    {
        if (sourceBlock != null)
        {
            for (BlockTransmutationRecipe recipe : blockTransmutationRecipes)
            {
                if (recipe.getBlock() == sourceBlock.getBlock())
                {
                    return recipe;
                }
            }
        }
        return null;
    }


    public static List<SpiritFurnaceRecipe> spiritFurnaceRecipes = new ArrayList<>();

    public static void addSpiritFurnaceRecipe(SpiritFurnaceRecipe recipe)
    {
        if (!spiritFurnaceRecipes.contains(recipe))
        {
            spiritFurnaceRecipes.add(recipe);
        }
    }

    public static SpiritFurnaceRecipe getSpiritFurnaceRecipe(ItemStack item)
    {
        if (item.isEmpty())
        {
            for (SpiritFurnaceRecipe recipe : spiritFurnaceRecipes)
            {
                if (recipe.getInputItem().getItem().equals(item.getItem()))
                {
                    return recipe;
                }
            }
        }
        return null;
    }

    public static List<BlockCorruptionRecipe> blockCorruptionRecipes = new ArrayList<>();

    public static void addBlockCorruptionRecipe(BlockCorruptionRecipe recipe)
    {
        if (!blockCorruptionRecipes.contains(recipe))
        {
            blockCorruptionRecipes.add(recipe);
        }
    }

    public static BlockCorruptionRecipe getBlockCorruptionRecipe(BlockState sourceBlock)
    {
        if (sourceBlock != null)
        {
            for (BlockCorruptionRecipe recipe : blockCorruptionRecipes)
            {
                if (recipe.getBlock() == sourceBlock.getBlock())
                {
                    return recipe;
                }
            }
        }
        return null;
    }
}
