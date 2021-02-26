package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.entries.BookEntryGrouping;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class BookCategory
{
    public final ItemStack iconStack;
    public final String translationKey;
    public ArrayList<BookEntryGrouping> groupings;
    public BookCategory(ItemStack iconStack, String translationKey)
    {
        this.iconStack = iconStack;
        this.translationKey = "malum.gui.book.chapter." + translationKey;
    }
    public void addEntries(BookEntry... pages)
    {
        ArrayList<BookEntryGrouping> groupings = new ArrayList<>();
        int currentPage = 0;
        for (int i =0; i < Math.ceil(pages.length / 10f); i++)
        {
            BookEntryGrouping grouping = new BookEntryGrouping(i==0);
            int entries = 10;
            if (i == 0)
            {
                entries--;
            }
            for (int j = 0; j < entries; j++)
            {
                if (currentPage < pages.length)
                {
                    grouping.entries.add(pages[currentPage]);
                    currentPage++;
                }
            }
            if (grouping.entries.isEmpty())
            {
                continue;
            }
            groupings.add(grouping);
        }
        this.groupings = groupings;
    }
}
