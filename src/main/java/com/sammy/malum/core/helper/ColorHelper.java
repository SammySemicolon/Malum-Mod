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

    public static Color darker(Color color, int times) {
        float FACTOR = (float) Math.pow(0.7f, times);
        return new Color(Math.max((int) (color.getRed() * FACTOR), 0),
                Math.max((int) (color.getGreen() * FACTOR), 0),
                Math.max((int) (color.getBlue() * FACTOR), 0),
                color.getAlpha());
    }

    public static Color brighter(Color color, int times) {
        float FACTOR = (float) Math.pow(0.7f, times);
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = color.getAlpha();

        int i = (int) (1.0 / (1.0 - FACTOR));
        if (r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }
        if (r > 0 && r < i) r = i;
        if (g > 0 && g < i) g = i;
        if (b > 0 && b < i) b = i;

        return new Color(Math.min((int) (r / FACTOR), 255),
                Math.min((int) (g / FACTOR), 255),
                Math.min((int) (b / FACTOR), 255),
                alpha);
    }
}
