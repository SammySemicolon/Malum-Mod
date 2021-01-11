package com.sammy.malum.common.book.categories;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.book.pages.BookPage;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class BookCategory
{
    public final ItemStack iconStack;
    public final String translationKey;
    public final ArrayList<BookPage> pages;
    public BookCategory(ItemStack iconStack, String translationKey, BookPage... pages)
    {
        this.iconStack = iconStack;
        this.translationKey = "malum.gui.book.chapter." + translationKey;
        this.pages = MalumHelper.toArrayList(pages);
    }
    public void draw()
    {
    
    }
}
