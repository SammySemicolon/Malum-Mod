package com.sammy.malum.core.init;

import com.sammy.malum.common.book.MalumBookCategories;
import com.sammy.malum.core.modcontent.*;
import com.sammy.malum.core.init.worldgen.MalumFeatures;
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
    public static void registerModContents(FMLCommonSetupEvent event)
    {
        MalumSpiritTypes.init();
        MalumSpiritKilnRecipes.init();
        MalumSpiritKilnFuels.init();
        MalumRites.init();
        MalumTransfusions.init();
        MalumBookCategories.init();
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