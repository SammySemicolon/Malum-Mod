package com.sammy.malum.client.screen.old_book.categories;

import com.sammy.malum.client.screen.old_book.entries.BookEntry;
import com.sammy.malum.client.screen.old_book.entries.BookEntryGrouping;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class BookCategory
{
    public static float PAGES_PER_GROUPING = 12;

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
        for (int i =0; i <= Math.ceil(pages.length / PAGES_PER_GROUPING); i++)
        {
            BookEntryGrouping grouping = new BookEntryGrouping(i==0);
            int entries = (int) PAGES_PER_GROUPING;
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
