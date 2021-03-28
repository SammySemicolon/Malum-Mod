package com.sammy.malum;

import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class MalumConstants
{
    
    public static final Color[] SPIRIT_COLORS = new Color[]{
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
        return SPIRIT_COLORS[MalumMod.RANDOM.nextInt(SPIRIT_COLORS.length-1)];
    }
    public static Color brightest()
    {
        return SPIRIT_COLORS[0];
    }
    public static Color bright()
    {
        return SPIRIT_COLORS[MathHelper.nextInt(MalumMod.RANDOM, 0,2)];
    }
    public static Color faded()
    {
        return SPIRIT_COLORS[MathHelper.nextInt(MalumMod.RANDOM, 3,6)];
    }
    public static Color dark()
    {
        return SPIRIT_COLORS[MathHelper.nextInt(MalumMod.RANDOM, 7,9)];
    }
    public static Color darkest()
    {
        return SPIRIT_COLORS[9];
    }
    
    public static final Color ORANGE = new Color(170, 76, 16);
    public static final Color MAGENTA = new Color(224, 51, 224);
    public static final Color LIGHT_BLUE = new Color(101, 219, 219);
    public static final Color YELLOW = new Color(236, 167, 49);
    public static final Color LIME = new Color(155, 187, 41);
    public static final Color PINK = SPIRIT_COLORS[4];
    public static final Color CYAN = new Color(38, 194, 194);
    public static final Color PURPLE = new Color(76, 18, 160);
    public static final Color BLUE = new Color(13, 54, 205);
    public static final Color BROWN = new Color(102, 31, 13);
    public static final Color GREEN = new Color(23, 134, 13);
    public static final Color RED = new Color(180, 49, 49);
}
