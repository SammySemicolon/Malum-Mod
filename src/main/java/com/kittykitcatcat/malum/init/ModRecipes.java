package com.kittykitcatcat.malum.init;


import com.kittykitcatcat.malum.recipes.SpiritFurnaceFuelData;
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
        SpiritFurnaceFuelData.initData();
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
    
    
    
    public static List<SpiritFurnaceFuelData> spiritFurnaceFuelData = new ArrayList<>();
    
    public static void addSpiritFurnaceFuelData(SpiritFurnaceFuelData data)
    {
        if (!spiritFurnaceFuelData.contains(data))
        {
            spiritFurnaceFuelData.add(data);
        }
    }
    
    public static SpiritFurnaceFuelData getSpiritFurnaceFuelData(ItemStack item)
    {
        if (!item.isEmpty())
        {
            for (SpiritFurnaceFuelData data : spiritFurnaceFuelData)
            {
                if (data.getFuelItem().equals(item.getItem()))
                {
                    return data;
                }
            }
        }
        return null;
    }
}
