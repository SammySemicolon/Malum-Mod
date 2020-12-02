package com.sammy.malum.client;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.core.systems.essences.EssenceHelper;
import com.sammy.malum.core.systems.essences.IEssenceHolder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientHelper
{
    public static void makeTooltip(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn, ArrayList<ITextComponent> components)
    {
        tooltip.add(combinedComponent(simpleTranslatableComponent("malum.tooltip.hold"), importantTranslatableComponent("malum.tooltip.sneak", Screen.hasShiftDown())));
        if (Screen.hasShiftDown())
        {
            tooltip.addAll(components);
        }
    }
    
    public static ArrayList<ITextComponent> stackSpiritsTooltip(ItemStack stack, ArrayList<Pair<String, Integer>> spirits)
    {
        ArrayList<ITextComponent> tooltip = new ArrayList<>();
        if (EssenceHelper.validate(stack))
        {
            IEssenceHolder essenceHolder = (IEssenceHolder) stack.getItem();
            for (int i = 0; i < spirits.size(); i++)
            {
                String spirit = spirits.get(i).getFirst();
                int count = spirits.get(i).getSecond();
                boolean lit = !spirit.equals("empty");
                tooltip.add(combinedComponent(simpleTranslatableComponent("malum.tooltip.slot"), simpleComponent(" " + (i+1)  + ": "),importantComponent(count + "/" + essenceHolder.getMaxEssence(), lit), importantComponent(spirit, lit)));
            }
        }
        return tooltip;
    }
    
    public static IFormattableTextComponent simpleComponent(String message)
    {
        return new StringTextComponent(message).mergeStyle(TextFormatting.WHITE);
    }
    
    public static IFormattableTextComponent simpleTranslatableComponent(String message)
    {
        return new TranslationTextComponent(message).mergeStyle(TextFormatting.WHITE);
    }
    
    public static IFormattableTextComponent importantComponent(String message, boolean litUp)
    {
        TextFormatting textFormatting = litUp ? TextFormatting.LIGHT_PURPLE : TextFormatting.DARK_PURPLE;
            return combinedComponent(new StringTextComponent("[").mergeStyle(TextFormatting.WHITE),
                    new StringTextComponent(message).mergeStyle(textFormatting),
                    new StringTextComponent("] ").mergeStyle(TextFormatting.WHITE)).mergeStyle(TextFormatting.BOLD);
    }
    
    public static IFormattableTextComponent importantTranslatableComponent(String message, boolean litUp)
    {
        TextFormatting textFormatting = litUp ? TextFormatting.LIGHT_PURPLE : TextFormatting.DARK_PURPLE;
        return combinedComponent(new StringTextComponent("[").mergeStyle(TextFormatting.WHITE),
                new TranslationTextComponent(message).mergeStyle(textFormatting),
                new StringTextComponent("] ").mergeStyle(TextFormatting.WHITE)).mergeStyle(TextFormatting.BOLD);
    }
    
    public static IFormattableTextComponent combinedComponent(IFormattableTextComponent... components)
    {
        IFormattableTextComponent finalComponent = components[0];
        for(IFormattableTextComponent component : components)
        {
            if (!component.equals(finalComponent))
            {
                finalComponent.append(component);
            }
        }
        return finalComponent;
    }
    
}