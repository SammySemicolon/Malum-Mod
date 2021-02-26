package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.TextPage;
import com.sammy.malum.core.init.MalumItems;

public class DarkArtsCategory extends BookCategory
{
    public DarkArtsCategory()
    {
        super(MalumItems.POPPET.get().getDefaultInstance(), "dark_arts");
    
        BookEntry voodooDolls = new BookEntry(MalumItems.POPPET.get(), "voodoo_dolls")
                .addPage(new TextPage("yeah"));
        BookEntry ether = new BookEntry(MalumItems.PINK_ETHER.get(), "ether")
                .addPage(new TextPage("ummmmmmm"))
                .addPage(new TextPage("well_you_see"))
                .addPage(new TextPage("uhhhh"))
                .addLink(voodooDolls);
        addEntries(ether, voodooDolls);
    }
}
