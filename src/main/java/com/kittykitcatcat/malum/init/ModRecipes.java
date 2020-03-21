package com.kittykitcatcat.malum.init;


import com.kittykitcatcat.malum.recipes.BlockTransmutationRecipe;
import net.minecraft.block.BlockState;
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
        BlockTransmutationRecipe.initRecipes();
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
}
