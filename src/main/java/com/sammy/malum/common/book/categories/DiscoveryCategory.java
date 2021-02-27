package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.CraftingPage;
import com.sammy.malum.common.book.pages.HeadlineTextPage;
import com.sammy.malum.common.book.pages.SmeltingPage;
import com.sammy.malum.common.book.pages.TextPage;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DiscoveryCategory extends BookCategory
{
    public DiscoveryCategory()
    {
        super(MalumItems.MALUM_BOOK.get().getDefaultInstance(), "discovery");
        Item EMPTY = Items.BARRIER;
        BookEntry arcaneBasics = new BookEntry(Items.SOUL_SAND, "arcane_basics")
                .addPage(new HeadlineTextPage("arcane_basics"))
                .addPage(new HeadlineTextPage("soul_sand"))
                .addPage(new HeadlineTextPage("redstone_dust"));
    
        BookEntry runewoodTrees = new BookEntry(MalumItems.RUNEWOOD_SAPLING.get(), "runewood_trees")
                .addPage(new HeadlineTextPage("runewood_trees"))
                .addPage(new HeadlineTextPage("sun_kissed_leaves"))
                .addPage(new HeadlineTextPage("sun_kissed_grass"))
                .addPage(new CraftingPage(Items.PURPLE_DYE, MalumItems.LAVENDER.get()));
        
        BookEntry solarSap = new BookEntry(MalumItems.SOLAR_SAP_BOTTLE.get(), "solar_sap")
                .addPage(new HeadlineTextPage("solar_sap"))
                .addPage(new HeadlineTextPage("solar_sap_again"))
                .addPage(new HeadlineTextPage("solar_syrup"))
                .addPage(new SmeltingPage(MalumItems.SOLAR_SAP_BOTTLE.get(), MalumItems.SOLAR_SYRUP_BOTTLE.get()))
                .addPage(new HeadlineTextPage("solar_sapball"))
                .addPage(new CraftingPage(MalumItems.SOLAR_SAPBALL.get(), MalumItems.SOLAR_SAP_BOTTLE.get(), Items.SLIME_BALL))
                .addLink(runewoodTrees);
    
        BookEntry unholyBlend = new BookEntry(MalumItems.UNHOLY_BLEND.get(), "unholy_blend")
                .addPage(new HeadlineTextPage("unholy_blend"))
                .addPage(new TextPage("unholy_blend_again"))
                .addPage(new CraftingPage(MalumItems.UNHOLY_BLEND.get(), Items.REDSTONE, Items.ROTTEN_FLESH, Items.SOUL_SAND))
                .addPage(new SmeltingPage(MalumItems.UNHOLY_BLEND.get(), MalumItems.ARCANE_GRIT.get()))
                .addLink(arcaneBasics);
    
        BookEntry soulGem = new BookEntry(MalumItems.SOUL_GEM.get(), "soul_gem")
                .addPage(new HeadlineTextPage("soul_gem"))
                .addPage(new CraftingPage(MalumItems.SOUL_GEM.get(), EMPTY, MalumItems.ARCANE_GRIT.get(), EMPTY, MalumItems.ARCANE_GRIT.get(), Items.DIAMOND, MalumItems.ARCANE_GRIT.get(), EMPTY, MalumItems.ARCANE_GRIT.get(), EMPTY))
                .addLink(unholyBlend);
        addEntries(arcaneBasics, runewoodTrees, solarSap, unholyBlend, soulGem);
    }
}
