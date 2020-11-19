package com.sammy.malum.client;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.essences.MalumEssenceTypes;
import com.sammy.malum.core.systems.essences.SimpleEssenceType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import java.util.ArrayList;
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
    public static ArrayList<ITextComponent> itemEssences(ItemStack stack)
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        CompoundNBT compoundNBT = stack.getOrCreateTag();
        for(SimpleEssenceType essenceType : MalumEssenceTypes.ESSENCES)
        {
            if (compoundNBT.contains(essenceType.identifier))
            {
                int count = compoundNBT.getInt(essenceType.identifier);
                components.add(
                        combinedComponent(
                                simpleTranslatableComponent("malum.tooltip.contains"),
                                importantComponent("" + count, true),
                                importantComponent(MalumHelper.toTitleCase(essenceType.identifier.replace(MalumMod.MODID + ":", ""), "_"), true)
                        )
                );
            }
        }
        return components;
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