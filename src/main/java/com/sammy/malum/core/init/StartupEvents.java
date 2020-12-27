package com.sammy.malum.core.init;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.spirits.MalumSpiritTypes;
import com.sammy.malum.core.init.worldgen.MalumFeatures;
import com.sammy.malum.core.recipes.ArcaneCraftingRecipe;
import com.sammy.malum.core.recipes.TaintTransfusion;
import com.sammy.malum.core.recipes.TaintedFurnaceRecipe;
import com.sammy.malum.core.systems.spirits.item.SpiritSplinterItem;
import com.sammy.malum.core.systems.spirits.types.MalumSpiritType;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.SlotTypeMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.core.init.MalumItems.EMPTY_SPLINTER;
import static com.sammy.malum.core.init.MalumItems.ITEMS;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StartupEvents
{
    @SubscribeEvent
    public static void registerRecipes(FMLCommonSetupEvent event)
    {
        ArcaneCraftingRecipe.init();
        TaintTransfusion.init();
        TaintedFurnaceRecipe.init();
    }
    
    @SubscribeEvent
    public static void bindSpiritsToSplinters(FMLCommonSetupEvent event)
    {
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        ArrayList<RegistryObject<Item>> registryObjects = (ArrayList<RegistryObject<Item>>) MalumHelper.takeAll(items, t -> t.get() instanceof SpiritSplinterItem && !t.equals(EMPTY_SPLINTER));
        ArrayList<SpiritSplinterItem> splinters = new ArrayList<>();
        registryObjects.forEach(t -> splinters.add((SpiritSplinterItem) t.get()));
        for (MalumSpiritType spiritType : MalumSpiritTypes.SPIRITS)
        {
            for (SpiritSplinterItem item : splinters)
            {
                if (spiritType.splinter.equals(item.getRegistryName().getPath()))
                {
                    item.type = spiritType;
                    spiritType.splinterItem = item;
                    break;
                }
            }
        }
        event.enqueueWork(MalumFeatures::new);
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