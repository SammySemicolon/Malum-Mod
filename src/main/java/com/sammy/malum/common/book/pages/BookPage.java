package com.sammy.malum.common.book.pages;

import com.sammy.malum.MalumHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class BookPage
{
    public final ItemStack iconStack;
    public final String translationKey;
    public final String detailedTranslationKey;
    public BookPage sourcePage;
    public ArrayList<BookPage> linkedPages = new ArrayList<>();
    
    public BookPage(Item item, String translationKey) {
        this.iconStack = item.getDefaultInstance();
        this.translationKey = "malum.gui.book.page." + translationKey;
        this.detailedTranslationKey = "malum.gui.book.page." + translationKey + ".detailed";
    }
    public BookPage(Item item, String translationKey, BookPage... linkedPages) {
        this(item, translationKey);
        this.linkedPages = MalumHelper.toArrayList(linkedPages);
        this.linkedPages.forEach(p -> p.sourcePage = this);
    }
}
