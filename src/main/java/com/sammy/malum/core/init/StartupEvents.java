package com.sammy.malum.core.init;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.totems.TotemPoleBlock;
import com.sammy.malum.core.modcontent.*;
import com.sammy.malum.core.init.worldgen.MalumFeatures;
import com.sammy.malum.core.systems.spirits.item.SpiritSplinterItem;
import com.sammy.malum.core.systems.spirits.types.MalumSpiritType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
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

import static com.sammy.malum.core.init.MalumItems.ITEMS;
import static com.sammy.malum.core.init.blocks.MalumBlocks.BLOCKS;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StartupEvents
{
    @SubscribeEvent
    public static void registerRecipes(FMLCommonSetupEvent event)
    {
        MalumSpiritKilnRecipes.init();
        MalumSpiritKilnFuels.init();
        MalumRunes.init();
        MalumChiseling.init();
        MalumRites.init();
        MalumSpiritTypes.init();
        bindRunesToPoles(event);
    }
    public static void bindRunesToPoles(FMLCommonSetupEvent event)
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        ArrayList<RegistryObject<Block>> registryObjects = (ArrayList<RegistryObject<Block>>) MalumHelper.takeAll(blocks, t -> t.get() instanceof TotemPoleBlock);
        ArrayList<TotemPoleBlock> poles = new ArrayList<>();
        registryObjects.forEach(t -> poles.add((TotemPoleBlock) t.get()));
        for (MalumRunes.MalumRune rune : MalumRunes.RUNES)
        {
            for (TotemPoleBlock block : poles)
            {
                String name = Registry.BLOCK.getKey(block).getPath().substring("totem_pole_".length());
                if (name.equals(rune.id))
                {
                    block.rune = rune;
                }
            }
        }
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