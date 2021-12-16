package com.sammy.malum.client;

import net.minecraft.world.level.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.world.item.Item;
import net.minecraft.util.ColorHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.RegistryObject;

import java.awt.Color;

@OnlyIn(Dist.CLIENT)
public class ClientHelper
{
    public static void spawnBlockParticles(BlockPos pos, BlockState state)
    {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.particleEngine.destroy(pos, state);
    }
    public static void registerItemColor(ItemColors itemColors, RegistryObject<Item> item, Color color)
    {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        itemColors.register((stack, i) -> r << 16 | g << 8 | b, item.get());
    }
    public static Color getColor(int decimal)
    {
        int red = ColorHelper.PackedColor.red(decimal);
        int green = ColorHelper.PackedColor.green(decimal);
        int blue = ColorHelper.PackedColor.blue(decimal);
        return new Color(red, green, blue);
    }
    public static IFormattableTextComponent simpleTranslatableComponent(String message)
    {
        return new TranslatableComponent(message);
    }

    public static void setCutout(RegistryObject<Block> b)
    {
        RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout());
    }
}