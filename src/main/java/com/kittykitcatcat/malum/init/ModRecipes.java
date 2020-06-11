package com.kittykitcatcat.malum.init;


import com.kittykitcatcat.malum.recipes.SpiritFurnaceRecipe;
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
        if (!item.isEmpty())
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
}
