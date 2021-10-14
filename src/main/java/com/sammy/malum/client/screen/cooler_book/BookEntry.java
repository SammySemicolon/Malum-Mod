package com.sammy.malum.client.screen.cooler_book;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.cooler_book.BookEntry.EntryLine.LineEnum;
import com.sammy.malum.client.screen.cooler_book.pages.BookPage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;

import static com.sammy.malum.client.screen.cooler_book.BookEntry.EntryLine.LineEnum.*;

public class BookEntry
{
    public final ItemStack iconStack;
    public final String identifier;
    public final int xOffset;
    public final int yOffset;
    public ArrayList<EntryLine> arrows = new ArrayList<>();
    public ArrayList<BookPage> pages = new ArrayList<>();
    public BookEntry(String identifier, Item item, int xOffset, int yOffset)
    {
        this.iconStack = item.getDefaultInstance();
        this.identifier = identifier;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    public String translationKey()
    {
        return "malum.gui.book.entry." + identifier;
    }
    public String descriptionTranslationKey()
    {
        return "malum.gui.book.entry." + identifier + ".description";
    }
    public BookEntry addPage(BookPage page)
    {
        pages.add(page);
        return this;
    }
    public BookEntry addModCompatPage(BookPage page, String modId)
    {
        if (ModList.get().isLoaded(modId))
        {
            pages.add(page);
        }
        return this;
    }
    public static class EntryLine
    {
        public enum LineEnum
        {
            HORIZONTAL, VERTICAL,

            BEND_UP_LEFT, BEND_UP_RIGHT,
            BEND_DOWN_LEFT, BEND_DOWN_RIGHT,

            TWO_WAY_BEND_UP, TWO_WAY_BEND_LEFT,
            TWO_WAY_BEND_RIGHT, TWO_WAY_BEND_DOWN,

            CONNECTION_UP, CONNECTION_DOWN,
            CONNECTION_LEFT, CONNECTION_RIGHT
        }
        public final int xOffset;
        public final int yOffset;
        public ArrayList<LineEnum> lines;

        public EntryLine(int xOffset, int yOffset, LineEnum... arrows)
        {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.lines = MalumHelper.toArrayList(arrows);
        }
    }
    public BookEntry addLine(int xOffset, int yOffset, LineEnum line)
    {
        arrows.add(new EntryLine(xOffset,yOffset, line));
        return this;
    }
    public BookEntry addLine(LineEnum line)
    {
        addLine(0,0,line);
        return this;
    }
    public BookEntry vertical(int xOffset, int yOffset)
    {
        addLine(xOffset,yOffset,VERTICAL);
        return this;
    }
    public BookEntry horizontal(int xOffset, int yOffset)
    {
        addLine(xOffset,yOffset,HORIZONTAL);
        return this;
    }
    public BookEntry up(int count)
    {
        addLine(CONNECTION_UP);
        for (int i =0; i < count; i++)
        {
            vertical(0, i+1);
        }
        addLine(0, count+1, CONNECTION_DOWN);
        return this;
    }
    public BookEntry down(int count)
    {
        addLine(CONNECTION_DOWN);
        for (int i =0; i < count; i++)
        {
            vertical(0, -i-1);
        }
        addLine(0, -count-1, CONNECTION_UP);
        return this;
    }
    public BookEntry left(int count)
    {
        addLine(CONNECTION_LEFT);
        for (int i =0; i < count; i++)
        {
            horizontal(-i-1, 0);
        }
        addLine(-count-1, 0, CONNECTION_RIGHT);
        return this;
    }
    public BookEntry right(int count)
    {
        addLine(CONNECTION_RIGHT);
        for (int i =0; i < count; i++)
        {
            horizontal(i+1, 0);
        }
        addLine(count+1, 0, CONNECTION_LEFT);
        return this;
    }
    public BookEntry upLeft(int xCount, int yCount)
    {
        addLine(CONNECTION_UP);
        for (int i =1; i < yCount; i++)
        {
            vertical(0, i);
        }
        addLine(0, yCount, BEND_DOWN_LEFT);
        for (int i =1; i < xCount; i++)
        {
            horizontal(-i, yCount);
        }
        addLine(-xCount, yCount, CONNECTION_RIGHT);
        return this;
    }

    public BookEntry upRight(int xCount, int yCount)
    {
        addLine(CONNECTION_UP);
        for (int i =1; i < yCount; i++)
        {
            vertical(0, i);
        }
        addLine(0, yCount, BEND_DOWN_RIGHT);
        for (int i =1; i < xCount; i++)
        {
            horizontal(i, yCount);
        }
        addLine(xCount, yCount, CONNECTION_LEFT);
        return this;
    }
    public BookEntry downLeft(int xCount, int yCount)
    {
        addLine(CONNECTION_DOWN);
        for (int i =1; i < yCount; i++)
        {
            vertical(0, -i);
        }
        addLine(0, -yCount, BEND_UP_LEFT);
        for (int i =1; i < xCount; i++)
        {
            horizontal(-i, -yCount);
        }
        addLine(-xCount, -yCount, CONNECTION_RIGHT);
        return this;
    }
    public BookEntry downRight(int xCount, int yCount)
    {
        addLine(CONNECTION_DOWN);
        for (int i =1; i < yCount; i++)
        {
            vertical(0, -i);
        }
        addLine(0, -yCount, BEND_UP_RIGHT);
        for (int i =1; i < xCount; i++)
        {
            horizontal(i, -yCount);
        }
        addLine(xCount, -yCount, CONNECTION_LEFT);
        return this;
    }
    public BookEntry leftUp(int xCount, int yCount)
    {
        addLine(CONNECTION_LEFT);
        for (int i =1; i < xCount; i++)
        {
            horizontal(-i, 0);
        }
        addLine(-xCount, 0, BEND_UP_RIGHT);
        for (int i =1; i < yCount; i++)
        {
            vertical(-xCount, i);
        }
        addLine(-xCount, yCount, CONNECTION_DOWN);
        return this;
    }
    public BookEntry leftDown(int xCount, int yCount)
    {
        addLine(CONNECTION_LEFT);
        for (int i =1; i < xCount; i++)
        {
            horizontal(-i, 0);
        }
        addLine(-xCount, 0, BEND_DOWN_RIGHT);
        for (int i =1; i < yCount; i++)
        {
            vertical(-xCount, -i);
        }
        addLine(-xCount, -yCount, CONNECTION_UP);
        return this;
    }
    public BookEntry rightUp(int xCount, int yCount)
    {
        addLine(CONNECTION_RIGHT);
        for (int i =1; i < xCount; i++)
        {
            horizontal(i, 0);
        }
        addLine(xCount, 0, BEND_UP_LEFT);
        for (int i =1; i < yCount; i++)
        {
            vertical(xCount, i);
        }
        addLine(xCount, yCount, CONNECTION_DOWN);
        return this;
    }
    public BookEntry rightDown(int xCount, int yCount)
    {
        addLine(CONNECTION_RIGHT);
        for (int i =1; i < xCount; i++)
        {
            horizontal(i, 0);
        }
        addLine(xCount, 0, BEND_DOWN_LEFT);
        for (int i =1; i < yCount; i++)
        {
            vertical(xCount, -i);
        }
        addLine(xCount, -yCount, CONNECTION_UP);
        return this;
    }
}