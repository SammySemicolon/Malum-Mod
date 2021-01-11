package com.sammy.malum.common.book.pages;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BookPage
{
    public final ItemStack iconStack;
    public final String translationKey;
    public final String detailedTranslationKey;
    
    public BookPage(Item item, String translationKey) {
        this.iconStack = item.getDefaultInstance();
        this.translationKey = "malum.gui.book.page." + translationKey;
        this.detailedTranslationKey = "malum.gui.book.page." + translationKey + ".detailed";
    }
}
