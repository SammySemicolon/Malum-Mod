package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.pages.BookPage;
import com.sammy.malum.core.init.MalumItems;

public class TotemMagicCategory extends BookCategory
{
    public TotemMagicCategory()
    {
        super(MalumItems.RUNEWOOD.get().getDefaultInstance(), "totem_magic",
                new BookPage(MalumItems.RUNEWOOD_SAPLING.get(), "natural_flora"),
                new BookPage(MalumItems.RUNEWOOD_LOG.get(), "magic_wood"),
                new BookPage(MalumItems.SOLAR_SAP_BOTTLE.get(), "tree sap"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "totem basics",
                        new BookPage(MalumItems.LIFE_SPIRIT_SPLINTER.get(), "rite_of_life"),
                        new BookPage(MalumItems.DEATH_SPIRIT_SPLINTER.get(), "rite_of_death"))
                );
    }
}
