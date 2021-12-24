package com.sammy.malum.core.helper;

import net.minecraft.util.FastColor;

import java.awt.*;

public class ColorHelper {
    public static Color getColor(int decimal)
    {
        int red = FastColor.ARGB32.red(decimal);
        int green = FastColor.ARGB32.green(decimal);
        int blue = FastColor.ARGB32.blue(decimal);
        return new Color(red, green, blue);
    }

    public static int getColor(Color color)
    {
        return FastColor.ARGB32.color(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
    }
}
