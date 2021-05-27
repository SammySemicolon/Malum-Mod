package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.*;
import com.sammy.malum.common.rites.RiteOfCelerity;
import com.sammy.malum.common.rites.RiteOfDeath;
import com.sammy.malum.common.rites.RiteOfGrowth;
import com.sammy.malum.common.rites.RiteOfWarding;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import static com.sammy.malum.common.book.categories.DiscoveryCategory.*;
import static com.sammy.malum.common.book.categories.SpiritTinkeringCategory.spirit_resonators;
import static com.sammy.malum.core.init.items.MalumItems.*;

public class DarkArtsCategory extends BookCategory
{
    public static BookEntry totem_magic;
    public static BookEntry rite_of_growth;
    public static BookEntry rite_of_death;
    public static BookEntry rite_of_warding;
    public static BookEntry rite_of_celerity;

    public DarkArtsCategory()
    {
        super(TOTEM_BASE.get().getDefaultInstance(), "dark_arts");
        Item EMPTY = Items.BARRIER;

        totem_magic = new BookEntry(TOTEM_BASE.get(), "totem_magic")
                .addPage(new HeadlineTextPage("totem_magic"))
                .addPage(new TextPage("totem_magic_2"))
                .addPage(new TextPage("totem_magic_3"))
                .addPage(new SpiritInfusionPage(TOTEM_BASE.get()))
                .addLink(spiritInfusion).addLink(runewoodTrees).addLink(spirit_resonators).addLink(arcaneSpirit).addLink(earthenSpirit);

        rite_of_growth = new BookEntry(HOLY_SPIRIT.get(), "rite_of_growth")
                .addPage(new HeadlineTextPage("rite_of_growth"))
                .addPage(new RitePage(new RiteOfGrowth()))
                .addLink(totem_magic).addLink(holySpirit).addLink(arcaneSpirit);

        rite_of_death = new BookEntry(WICKED_SPIRIT.get(), "rite_of_death")
                .addPage(new HeadlineTextPage("rite_of_death"))
                .addPage(new RitePage(new RiteOfDeath()))
                .addLink(totem_magic).addLink(wickedSpirit).addLink(arcaneSpirit);

        rite_of_warding = new BookEntry(EARTHEN_SPIRIT.get(), "rite_of_warding")
                .addPage(new HeadlineTextPage("rite_of_warding"))
                .addPage(new RitePage(new RiteOfWarding()))
                .addLink(totem_magic).addLink(earthenSpirit).addLink(arcaneSpirit);

        rite_of_celerity = new BookEntry(AERIAL_SPIRIT.get(), "rite_of_celerity")
                .addPage(new HeadlineTextPage("rite_of_celerity"))
                .addPage(new RitePage(new RiteOfCelerity()))
                .addLink(totem_magic).addLink(aerialSpirit).addLink(arcaneSpirit);

        addEntries(totem_magic, rite_of_growth, rite_of_death, rite_of_warding, rite_of_celerity);
    }
}
