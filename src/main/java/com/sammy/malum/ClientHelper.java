package com.sammy.malum;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;

import java.awt.Color;

@OnlyIn(Dist.CLIENT)
public class ClientHelper
{
    public static void spawnBlockParticles(BlockPos pos, BlockState state)
    {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.particles.addBlockDestroyEffects(pos, state);
    }
    public static void registerItemColorTint(ItemColors itemColors, RegistryObject<Item> item, Color color)
    {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
    
        itemColors.register((stack, c) -> {
            if (c == 0)
            {
                return r << 16 | g << 8 | b;
            }
            else
            {
                return 0xFFFFFF;
            }
        }, item.get());
    }
    public static void registerItemColor(ItemColors itemColors, RegistryObject<Item> item, Color color)
    {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        itemColors.register((stack, i) -> r << 16 | g << 8 | b, item.get());
    }
    
    public static IFormattableTextComponent simpleComponent(String message)
    {
        return new StringTextComponent(message).mergeStyle(TextFormatting.WHITE);
    }
    
    public static IFormattableTextComponent simpleTranslatableComponent(String message)
    {
        return new TranslationTextComponent(message).mergeStyle(TextFormatting.WHITE);
    }
    
    public static IFormattableTextComponent importantComponent(String message)
    {
        return new StringTextComponent(message).mergeStyle(TextFormatting.DARK_PURPLE, TextFormatting.ITALIC);
    }
    
    public static IFormattableTextComponent importantTranslatableComponent(String message)
    {
        return new TranslationTextComponent(message).mergeStyle(TextFormatting.DARK_PURPLE, TextFormatting.ITALIC);
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