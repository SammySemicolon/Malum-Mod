package com.sammy.malum.common.cooler_book;

import com.sammy.malum.MalumHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class CoolerBookEntry
{
    public enum Arrow
    {
        UP,DOWN,LEFT,RIGHT,UP_LEFT, UP_RIGHT,DOWN_LEFT,DOWN_RIGHT,LEFT_UP,LEFT_DOWN,RIGHT_UP,RIGHT_DOWN;
    }
    public final ItemStack iconStack;
    public final String identifier;
    public final int xOffset;
    public final int yOffset;
    public ArrayList<Arrow> arrows = new ArrayList<>();
    public CoolerBookEntry(String identifier, Item item, int xOffset, int yOffset)
    {
        this.iconStack = item.getDefaultInstance();
        this.identifier = identifier;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    public CoolerBookEntry addArrows(Arrow... arrows)
    {
        this.arrows = MalumHelper.toArrayList(arrows);
        return this;
    }
    public String translationKey()
    {
        return "malum.gui.book.entry." + identifier;
    }
}