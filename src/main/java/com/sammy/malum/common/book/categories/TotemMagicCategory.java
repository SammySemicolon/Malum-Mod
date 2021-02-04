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
                new BookPage(MalumItems.TOTEM_CORE.get(), "totem basics"),
                new BookPage(MalumItems.IMPERVIOUS_ROCK.get(), "magic resistance"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_sacrifice"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_imbuement"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_life"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_death"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_water"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_fortitude"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_air"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_agility"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_levitation"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_growth"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_warding"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_destruction"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_collection"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_drought"),
                new BookPage(MalumItems.TOTEM_CORE.get(), "rite_of_rain")
                );
    }
}
