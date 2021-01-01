package com.sammy.malum.client;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.spirits.item.ISpiritHolderItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ClientHelper
{
    public static void registerItemColor(ItemColors itemColors, RegistryObject<Item> item, Color color)
    {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        itemColors.register((stack, i) -> r << 16 | g << 8 | b, item.get());
    }
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
        if (SpiritHelper.validate(stack))
        {
            ISpiritHolderItem essenceHolder = (ISpiritHolderItem) stack.getItem();
            for (int i = 0; i < spirits.size(); i++)
            {
                String spirit = spirits.get(i).getFirst();
                int count = spirits.get(i).getSecond();
                boolean lit = !spirit.equals("empty");
                tooltip.add(combinedComponent(simpleTranslatableComponent("malum.tooltip.slot"), simpleComponent(" " + (i + 1) + ": "), importantComponent(count + "/" + essenceHolder.getMaxSpirits(), lit), importantComponent(spirit, lit)));
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
        return combinedComponent(new StringTextComponent("[").mergeStyle(TextFormatting.WHITE), new StringTextComponent(message).mergeStyle(textFormatting), new StringTextComponent("] ").mergeStyle(TextFormatting.WHITE)).mergeStyle(TextFormatting.BOLD);
    }
    
    public static IFormattableTextComponent importantTranslatableComponent(String message, boolean litUp)
    {
        TextFormatting textFormatting = litUp ? TextFormatting.LIGHT_PURPLE : TextFormatting.DARK_PURPLE;
        return combinedComponent(new StringTextComponent("[").mergeStyle(TextFormatting.WHITE), new TranslationTextComponent(message).mergeStyle(textFormatting), new StringTextComponent("] ").mergeStyle(TextFormatting.WHITE)).mergeStyle(TextFormatting.BOLD);
    }
    
    public static IFormattableTextComponent combinedComponent(IFormattableTextComponent... components)
    {
        IFormattableTextComponent finalComponent = components[0];
        for (IFormattableTextComponent component : components)
        {
            if (!component.equals(finalComponent))
            {
                finalComponent.append(component);
            }
        }
        return finalComponent;
    }
}