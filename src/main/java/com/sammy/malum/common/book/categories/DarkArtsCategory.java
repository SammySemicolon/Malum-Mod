package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.*;
import com.sammy.malum.common.rites.*;
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
    public static BookEntry totem_crafting;
    public static BookEntry rite_of_assembly;
    public static BookEntry rite_of_imbuing;

    public DarkArtsCategory()
    {
        super(TOTEM_BASE.get().getDefaultInstance(), "dark_arts");
        Item EMPTY = Items.BARRIER;

        totem_magic = new BookEntry(TOTEM_BASE.get(), "totem_magic")
                .addPage(new HeadlineTextPage("totem_magic"))
                .addPage(new TextPage("totem_magic_2"))
                .addPage(new TextPage("totem_magic_3"))
                .addPage(new SpiritInfusionPage(TOTEM_BASE.get()))
                .addLink(()->spirit_infusion).addLink(()->runewood_trees).addLink(()->spirit_resonators).addLink(()->arcaneSpirit).addLink(()->earthenSpirit);

        rite_of_growth = new BookEntry(SACRED_SPIRIT.get(), "rite_of_growth")
                .addPage(new HeadlineTextPage("rite_of_growth"))
                .addPage(new RitePage(new RiteOfGrowth()))
                .addLink(()->totem_magic).addLink(()->sacredSpirit).addLink(()->arcaneSpirit);

        rite_of_death = new BookEntry(WICKED_SPIRIT.get(), "rite_of_death")
                .addPage(new HeadlineTextPage("rite_of_death"))
                .addPage(new RitePage(new RiteOfDeath()))
                .addLink(()->totem_magic).addLink(()->wickedSpirit).addLink(()->arcaneSpirit);

        rite_of_warding = new BookEntry(EARTHEN_SPIRIT.get(), "rite_of_warding")
                .addPage(new HeadlineTextPage("rite_of_warding"))
                .addPage(new RitePage(new RiteOfWarding()))
                .addLink(()->totem_magic).addLink(()->earthenSpirit).addLink(()->arcaneSpirit);

        rite_of_celerity = new BookEntry(AERIAL_SPIRIT.get(), "rite_of_celerity")
                .addPage(new HeadlineTextPage("rite_of_celerity"))
                .addPage(new RitePage(new RiteOfCelerity()))
                .addLink(()->totem_magic).addLink(()->aerialSpirit).addLink(()->arcaneSpirit);

        totem_crafting = new BookEntry(TOTEM_BASE.get(), "totem_crafting")
                .addPage(new HeadlineTextPage("totem_crafting"))
                .addPage(new SpiritInfusionPage(TOTEM_BASE.get()))
                .addLink(()->totem_magic).addLink(()->rite_of_assembly).addLink(()->rite_of_imbuing);

        rite_of_assembly = new BookEntry(AQUATIC_SPIRIT.get(), "rite_of_assembly")
                .addPage(new HeadlineTextPage("rite_of_assembly"))
                .addPage(new RitePage(new RiteOfAssembly()))
                .addLink(()->totem_magic).addLink(()->totem_crafting).addLink(()->eldritchSpirit).addLink(()->arcaneSpirit).addLink(()->aquaticSpirit);

        rite_of_imbuing = new BookEntry(INFERNAL_SPIRIT.get(), "rite_of_imbuing")
                .addPage(new HeadlineTextPage("rite_of_imbuing"))
                .addPage(new RitePage(new RiteOfImbuing()))
                .addLink(()->totem_magic).addLink(()->totem_crafting).addLink(()->eldritchSpirit).addLink(()->arcaneSpirit).addLink(()->infernalSpirit);

        addEntries(totem_magic, rite_of_growth, rite_of_death, rite_of_warding, rite_of_celerity, totem_crafting, rite_of_assembly, rite_of_imbuing);
    }
}
