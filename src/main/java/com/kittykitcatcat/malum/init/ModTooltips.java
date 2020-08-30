package com.kittykitcatcat.malum.init;

import com.kittykitcatcat.malum.ClientHandler;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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
        addTooltip(funk_engine,
                new TranslationTextComponent("malum.tooltip.funk_engine.desc"),
                new TranslationTextComponent("malum.tooltip.funk_engine.effect.a")
                        .appendSibling(ClientHandler.makeImportantComponent("malum.tooltip.funk_engine.effect.b", true)));
    
        addTooltip(spiritwood_stave, staveComponent());
        addTooltip(resonant_stave, staveComponent());
        addTooltip(fiery_stave, staveComponent());
        addTooltip(bone_stave, staveComponent());
        
        addTooltip(creative_spiritwood_stave, new TranslationTextComponent("malum.tooltip.creative_stave.desc")
                .appendSibling(ClientHandler.makeImportantComponent("malum.tooltip.basic_stave.effect.b", true)));
    
        addTooltip(miracle_pearl, extraSpirit(1), extraIntegrity("50%"), chanceIntegrity(25));
        addTooltip(spiritwood_bark_necklace, extraSpirit(1));
    
        addTooltip(ethereal_bulwark, new TranslationTextComponent("malum.tooltip.ethereal_bulwark.effect"));
        addTooltip(vacant_aegis, new TranslationTextComponent("malum.tooltip.vacant_aegis.effect"));
        addTooltip(vacant_rapier, new TranslationTextComponent("malum.tooltip.vacant_rapier.effect"));
    }
    public static void addTooltip(Item item, ITextComponent... components)
    {
        ArrayList<ITextComponent> componentArrayList = Arrays.stream(components).distinct().collect(Collectors.toCollection(ArrayList::new));
        tooltips.put(item, componentArrayList);
    }
    public static ITextComponent staveComponent()
    {
        return new TranslationTextComponent("malum.tooltip.basic_stave.effect.a").appendSibling(ClientHandler.makeImportantComponent("malum.tooltip.basic_stave.effect.b", true));
    }
    public static ITextComponent extraSpirit(int amount)
    {
        return new TranslationTextComponent("malum.tooltip.extra_spirit.effect.a")
                .appendSibling(new StringTextComponent(String.valueOf(amount)))
                .appendSibling(new TranslationTextComponent("malum.tooltip.extra_spirit.effect.b"));
    }
    public static ITextComponent extraIntegrity(String amount)
    {
        return new TranslationTextComponent("malum.tooltip.extra_integrity.effect")
                .appendSibling(new StringTextComponent(String.valueOf(amount)));
    }
    public static ITextComponent extraIntegrity(int amount)
    {
        return new TranslationTextComponent("malum.tooltip.extra_integrity.effect")
                .appendSibling(new StringTextComponent(String.valueOf(amount)));
    }
    public static ITextComponent chanceIntegrity(int amount)
    {
        return new TranslationTextComponent("malum.tooltip.chance_integrity.effect")
                .appendSibling(new StringTextComponent(amount + "%"));
        
    }
}
