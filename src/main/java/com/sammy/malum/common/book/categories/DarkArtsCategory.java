package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.pages.BookPage;
import com.sammy.malum.core.init.MalumItems;

public class DarkArtsCategory extends BookCategory
{
    public DarkArtsCategory()
    {
        super(MalumItems.ETHER.get().getDefaultInstance(), "dark_arts",
                new BookPage(MalumItems.ETHER.get(), "ether"));
    }
}
