package com.sammy.malum.common.book.categories;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.pages.BookPage;
import com.sammy.malum.common.book.pages.BookPageGrouping;
import net.minecraft.item.ItemStack;

import java.awt.print.Book;
import java.util.ArrayList;

public class BookCategory
{
    public final ItemStack iconStack;
    public final String translationKey;
    public final ArrayList<BookPageGrouping> groupings;
    public BookCategory(ItemStack iconStack, String translationKey, BookPage... pages)
    {
        this.iconStack = iconStack;
        this.translationKey = "malum.gui.book.chapter." + translationKey;
        
        ArrayList<BookPageGrouping> groupings = new ArrayList<>();
        for (int i =0; i < Math.ceil(pages.length / 10f); i++)
        {
            BookPageGrouping grouping = new BookPageGrouping();
            for (int j = 0; j < 10; j++)
            {
                int pageIndex = i*10+j;
                if (pageIndex < pages.length)
                {
                    grouping.pages.add(pages[pageIndex]);
                }
            }
            groupings.add(grouping);
        }
        this.groupings = groupings;
    }
}
