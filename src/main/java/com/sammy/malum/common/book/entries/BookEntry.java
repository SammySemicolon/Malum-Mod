package com.sammy.malum.common.book.entries;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.pages.BookPage;
import com.sammy.malum.common.book.pages.TextPage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class BookEntry
{
    public final ItemStack iconStack;
    public final String translationKey;
    
    public ArrayList<BookPage> pages = new ArrayList<>();
    public ArrayList<BookEntry> links = new ArrayList<>();
    
    public BookEntry(Item item, String translationKey) {
        this.iconStack = item.getDefaultInstance();
        this.translationKey = "malum.gui.book.entry." + translationKey;
    }
    public BookEntry addPage(BookPage page)
    {
        if (pages.isEmpty() && page instanceof TextPage)
        {
            ((TextPage) page).hasHeadline = true;
        }
        pages.add(page);
        return this;
    }
    public BookEntry addLink(BookEntry entry)
    {
        links.add(entry);
        return this;
    }
}
