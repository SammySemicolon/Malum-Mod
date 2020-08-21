package com.kittykitcatcat.malum.init;

import com.kittykitcatcat.malum.MalumHelper;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import static com.kittykitcatcat.malum.init.ModItems.*;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTooltips
{
    public static HashMap<Item, ArrayList<ITextComponent>> tooltips = new HashMap<>();
    

    @SubscribeEvent
    public static void setupTooltips(FMLClientSetupEvent event)
    {
        tooltips = new HashMap<>();
        addTooltip(funk_engine, new TranslationTextComponent("malum.tooltip.funkengine.desc"),
                new TranslationTextComponent("malum.tooltip.funkengine.effect.a")
                        .appendSibling(MalumHelper.makeImportantComponent("malum.tooltip.funkengine.effect.b", true)));
    
        addTooltip(spiritwood_stave, new TranslationTextComponent("malum.tooltip.basicstave.desc")
                .appendSibling(MalumHelper.makeImportantComponent("malum.tooltip.basicstave.effect", true)));
        addTooltip(resonant_stave, new TranslationTextComponent("malum.tooltip.basicstave.desc")
                .appendSibling(MalumHelper.makeImportantComponent("malum.tooltip.basicstave.effect", true)));
        addTooltip(fiery_stave, new TranslationTextComponent("malum.tooltip.basicstave.desc")
                .appendSibling(MalumHelper.makeImportantComponent("malum.tooltip.basicstave.effect", true)));
        addTooltip(bone_stave, new TranslationTextComponent("malum.tooltip.basicstave.desc")
                .appendSibling(MalumHelper.makeImportantComponent("malum.tooltip.basicstave.effect", true)));
        
        addTooltip(creative_spiritwood_stave, new TranslationTextComponent("malum.tooltip.creativestave.desc")
                .appendSibling(MalumHelper.makeImportantComponent("malum.tooltip.basicstave.effect", true)));
    }
    public static void addTooltip(Item item, ITextComponent... components)
    {
        ArrayList<ITextComponent> componentArrayList = Arrays.stream(components).distinct().collect(Collectors.toCollection(ArrayList::new));
        tooltips.put(item, componentArrayList);
    }
}
