package com.sammy.malum;

import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class MalumConstants
{
    public static Color[] spiritColors = new Color[]{
            new Color(255, 210, 243),
            new Color(255, 183, 236),
            new Color(240, 131, 232),
            new Color(236, 110, 226),
            new Color(203, 81, 204),
            new Color(182, 61, 183),
            new Color(163, 44, 191),
            new Color(129, 41, 191),
            new Color(112, 30, 169),
            new Color(97, 22, 140)
    };
    public static Color random()
    {
        return spiritColors[MalumMod.RANDOM.nextInt(spiritColors.length-1)];
    }
    public static Color brightest()
    {
        return spiritColors[0];
    }
    public static Color bright()
    {
        return spiritColors[MathHelper.nextInt(MalumMod.RANDOM, 0,2)];
    }
    public static Color faded()
    {
        return spiritColors[MathHelper.nextInt(MalumMod.RANDOM, 3,6)];
    }
    public static Color dark()
    {
        return spiritColors[MathHelper.nextInt(MalumMod.RANDOM, 7,9)];
    }
    public static Color darkest()
    {
        return spiritColors[9];
    }
}
