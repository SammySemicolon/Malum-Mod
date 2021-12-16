package com.sammy.malum.client;

import net.minecraft.client.color.item.ItemColors;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

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
    public static Color getColor(int decimal)
    {
        int red = FastColor.ARGB32.red(decimal);
        int green = FastColor.ARGB32.green(decimal);
        int blue = FastColor.ARGB32.blue(decimal);
        return new Color(red, green, blue);
    }
    public static TranslatableComponent simpleTranslatableComponent(String message)
    {
        return new TranslatableComponent(message);
    }

}