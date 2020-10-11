package com.sammy.malum.init;

import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import static com.sammy.malum.ClientHandler.*;
import static com.sammy.malum.init.ModItems.*;

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
                makeTranslationComponent("malum.tooltip.funk_engine.desc"),
                makeTranslationComponent("malum.tooltip.funk_engine.effect.a")
                        .append(makeImportantComponent("malum.tooltip.funk_engine.effect.b", true)));
    
        addTooltip(spiritwood_stave, staveComponent());
        addTooltip(resonant_stave, staveComponent());
        addTooltip(fiery_stave, staveComponent());
        addTooltip(bone_stave, staveComponent());
        
        addTooltip(creative_spiritwood_stave, makeTranslationComponent("malum.tooltip.creative_stave.desc")
                .append(makeImportantComponent("malum.tooltip.basic_stave.effect.b", true)));
    
        addTooltip(miracle_pearl, extraSpirit(1), extraIntegrity(50), chanceIntegrity(25));
        addTooltip(spiritwood_bark_necklace, extraSpirit(1));
    
        addTooltip(ethereal_bulwark, makeTranslationComponent("malum.tooltip.ethereal_bulwark.effect"));
        addTooltip(vacant_aegis, makeTranslationComponent("malum.tooltip.vacant_aegis.effect"));
        addTooltip(vacant_rapier, makeTranslationComponent("malum.tooltip.vacant_rapier.effect"));
    }
    public static void addTooltip(Item item, ITextComponent... components)
    {
        ArrayList<ITextComponent> componentArrayList = Arrays.stream(components).distinct().collect(Collectors.toCollection(ArrayList::new));
        tooltips.put(item, componentArrayList);
    }
    public static ITextComponent staveComponent()
    {
        return makeTranslationComponent("malum.tooltip.basic_stave.effect.a").append(makeImportantComponent("malum.tooltip.basic_stave.effect.b", true));
    }
    public static ITextComponent extraSpirit(int amount)
    {
        return makeTranslationComponent("malum.tooltip.extra_spirit.effect.a")
                .append(new StringTextComponent(String.valueOf(amount)))
                .append(makeTranslationComponent("malum.tooltip.extra_spirit.effect.b"));
    }
    public static ITextComponent extraIntegrity(int amount)
    {
        return makeTranslationComponent("malum.tooltip.extra_integrity.effect")
                .append(new StringTextComponent(amount + "%"));
    }
    public static ITextComponent chanceIntegrity(int amount)
    {
        return makeTranslationComponent("malum.tooltip.chance_integrity.effect")
                .append(new StringTextComponent(amount + "%"));
        
    }
}
