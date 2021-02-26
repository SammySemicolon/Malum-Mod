package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.TextPage;
import com.sammy.malum.core.init.MalumItems;

public class TotemMagicCategory extends BookCategory
{
    public TotemMagicCategory()
    {
        super(MalumItems.MALUM_BOOK.get().getDefaultInstance(), "discovery");
    
        BookEntry flora = new BookEntry(MalumItems.RUNEWOOD_SAPLING.get(), "natural_flora")
                .addPage(new TextPage("yus"));
        addEntries(flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora,flora);
    }
}
