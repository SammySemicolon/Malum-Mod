package com.sammy.malum.core.init;

import com.sammy.malum.core.init.worldgen.MalumFeatures;
import com.sammy.malum.core.systems.recipes.ArcaneCraftingRecipe;
import com.sammy.malum.core.systems.recipes.TaintConversion;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StartupEvents
{
    @SubscribeEvent
    public static void registerRecipes(FMLCommonSetupEvent event)
    {
        ArcaneCraftingRecipe.init();
        TaintConversion.init();
    }
    
    @SubscribeEvent
    public static void registerFeatures(FMLCommonSetupEvent event)
    {
        event.enqueueWork(MalumFeatures::new);
    }
    
    @SubscribeEvent
    public static void registerCurios(InterModEnqueueEvent event)
    {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("spirit_trinket").size(2).cosmetic().build());
    }
}