package com.sammy.malum.core.init;

import com.sammy.malum.core.init.worldgen.MalumFeatures;
import com.sammy.malum.core.systems.recipes.ArcaneCraftingRecipe;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StartupEvents
{
    @SubscribeEvent
    public static void registerRecipes(FMLCommonSetupEvent event)
    {
        ArcaneCraftingRecipe.initRecipes();
    }
    @SubscribeEvent
    public static void registerFeatures(FMLCommonSetupEvent event)
    {
        event.enqueueWork(MalumFeatures::new);
    }
}