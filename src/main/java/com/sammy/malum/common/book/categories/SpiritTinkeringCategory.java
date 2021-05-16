package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.CraftingPage;
import com.sammy.malum.common.book.pages.HeadlineTextPage;
import com.sammy.malum.common.book.pages.ItemListPage;
import com.sammy.malum.common.book.pages.SpiritInfusionPage;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import static com.sammy.malum.core.init.items.MalumItems.*;

public class SpiritTinkeringCategory extends BookCategory
{
    public static BookEntry soul_stained_steel;
    public static BookEntry hallowed_gold;
    public static BookEntry baubles;
    public static BookEntry radiant_soulstone;
    public static BookEntry soul_stained_stronghold;
    public static BookEntry tyrving;

    public SpiritTinkeringCategory()
    {
        super(SOUL_STAINED_STEEL_INGOT.get().getDefaultInstance(), "spirit_tinkering");
        Item EMPTY = Items.BARRIER;

        soul_stained_steel = new BookEntry(SOUL_STAINED_STEEL_INGOT.get(), "soul_stained_steel")
                .addPage(new HeadlineTextPage("soul_stained_steel"))
                .addPage(new SpiritInfusionPage(SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(CraftingPage.blockCraftingPage(SOUL_STAINED_STEEL_BLOCK.get(), SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(CraftingPage.nuggetCraftingPage(SOUL_STAINED_STEEL_NUGGET.get(), SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new HeadlineTextPage("soul_stained_steel_gear"))
                .addPage(new ItemListPage(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_HOE.get())
                        .addList(SOUL_STAINED_STEEL_HELMET.get(), SOUL_STAINED_STEEL_CHESTPLATE.get(), SOUL_STAINED_STEEL_LEGGINGS.get(), SOUL_STAINED_STEEL_BOOTS.get()))
                .addLink(DiscoveryCategory.wickedSpirit).addLink(DiscoveryCategory.arcaneSpirit);

        hallowed_gold = new BookEntry(HALLOWED_GOLD_INGOT.get(), "hallowed_gold")
                .addPage(new HeadlineTextPage("hallowed_gold"))
                .addPage(new SpiritInfusionPage(HALLOWED_GOLD_INGOT.get()))
                .addPage(CraftingPage.blockCraftingPage(HALLOWED_GOLD_BLOCK.get(), HALLOWED_GOLD_INGOT.get()))
                .addPage(CraftingPage.nuggetCraftingPage(HALLOWED_GOLD_NUGGET.get(), HALLOWED_GOLD_INGOT.get()))
                .addPage(new HeadlineTextPage("spirit_jar"))
                .addPage(new CraftingPage(SPIRIT_JAR.get(), Items.GLASS_PANE, HALLOWED_GOLD_INGOT.get(), Items.GLASS_PANE, Items.GLASS_PANE, EMPTY, Items.GLASS_PANE, Items.GLASS_PANE, Items.GLASS_PANE, Items.GLASS_PANE))
                .addLink(DiscoveryCategory.holySpirit).addLink(DiscoveryCategory.arcaneSpirit);

        addEntries(soul_stained_steel, hallowed_gold);
    }
}
