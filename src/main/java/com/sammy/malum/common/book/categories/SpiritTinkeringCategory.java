package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import static com.sammy.malum.common.book.categories.DiscoveryCategory.*;
import static com.sammy.malum.core.init.items.MalumItems.*;

public class SpiritTinkeringCategory extends BookCategory
{
    public static BookEntry soul_stained_steel;
    public static BookEntry hallowed_gold;
    public static BookEntry curios;
    public static BookEntry spirit_resonators;
    public static BookEntry arcane_spoil_ring;
    public static BookEntry arcane_reach_ring;
    public static BookEntry radiant_soulstone;
    public static BookEntry stronghold_armor;
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
                .addLink(spirit_infusion).addLink(wickedSpirit).addLink(arcaneSpirit);

        hallowed_gold = new BookEntry(HALLOWED_GOLD_INGOT.get(), "hallowed_gold")
                .addPage(new HeadlineTextPage("hallowed_gold"))
                .addPage(new SpiritInfusionPage(HALLOWED_GOLD_INGOT.get()))
                .addPage(CraftingPage.blockCraftingPage(HALLOWED_GOLD_BLOCK.get(), HALLOWED_GOLD_INGOT.get()))
                .addPage(CraftingPage.nuggetCraftingPage(HALLOWED_GOLD_NUGGET.get(), HALLOWED_GOLD_INGOT.get()))
                .addPage(new HeadlineTextPage("spirit_jar"))
                .addPage(new CraftingPage(SPIRIT_JAR.get(), Items.GLASS_PANE, HALLOWED_GOLD_INGOT.get(), Items.GLASS_PANE, Items.GLASS_PANE, EMPTY, Items.GLASS_PANE, Items.GLASS_PANE, Items.GLASS_PANE, Items.GLASS_PANE))
                .addLink(spirit_infusion).addLink(sacredSpirit).addLink(arcaneSpirit);

        curios = new BookEntry(GILDED_RING.get(), "curios")
                .addPage(new HeadlineTextPage("curios"))
                .addPage(new TextPage("curios_2"))
                .addPage(new CraftingPage(ORNATE_NECKLACE.get(), EMPTY, Items.STRING, EMPTY, Items.STRING, EMPTY, Items.STRING, EMPTY, SOUL_STAINED_STEEL_INGOT.get(), EMPTY))
                .addPage(new CraftingPage(ORNATE_RING.get(), EMPTY, Items.LEATHER, SOUL_STAINED_STEEL_INGOT.get(), Items.LEATHER, EMPTY, Items.LEATHER, EMPTY, Items.LEATHER, EMPTY))
                .addPage(new CraftingPage(GILDED_BELT.get(), Items.LEATHER, Items.LEATHER, Items.LEATHER, HALLOWED_GOLD_INGOT.get(), SOULSTONE.get(), HALLOWED_GOLD_INGOT.get(), EMPTY, HALLOWED_GOLD_INGOT.get(), EMPTY))
                .addPage(new CraftingPage(GILDED_RING.get(), EMPTY, Items.LEATHER, HALLOWED_GOLD_INGOT.get(), Items.LEATHER, EMPTY, Items.LEATHER, EMPTY, Items.LEATHER, EMPTY))
                .addLink(soul_stained_steel).addLink(hallowed_gold);

        spirit_resonators = new BookEntry(HALLOWED_SPIRIT_RESONATOR.get(), "spirit_resonators")
                .addPage(new HeadlineTextPage("spirit_resonators"))
                .addPage(new TextPage("spirit_resonators_2"))
                .addPage(new CraftingPage(STAINED_SPIRIT_RESONATOR.get(), EMPTY, RUNEWOOD_PLANKS.get(), EMPTY, SOUL_STAINED_STEEL_INGOT.get(), Items.QUARTZ, SOUL_STAINED_STEEL_INGOT.get(), EMPTY, RUNEWOOD_PLANKS.get(), EMPTY))
                .addPage(new CraftingPage(HALLOWED_SPIRIT_RESONATOR.get(), EMPTY, RUNEWOOD_PLANKS.get(), EMPTY, HALLOWED_GOLD_INGOT.get(), Items.QUARTZ, HALLOWED_GOLD_INGOT.get(), EMPTY, RUNEWOOD_PLANKS.get(), EMPTY))
                .addLink(spirit_infusion).addLink(runewood_trees).addLink(soul_stained_steel).addLink(hallowed_gold);

        arcane_spoil_ring = new BookEntry(RING_OF_ARCANE_SPOIL.get(), "arcane_spoil_ring")
                .addPage(new HeadlineTextPage("arcane_spoil_ring"))
                .addPage(new SpiritInfusionPage(RING_OF_ARCANE_SPOIL.get()))
                .addLink(spirit_infusion).addLink(soulstone).addLink(sacredSpirit).addLink(wickedSpirit).addLink(curios);

        arcane_reach_ring = new BookEntry(RING_OF_ARCANE_REACH.get(), "arcane_reach_ring")
                .addPage(new HeadlineTextPage("arcane_reach_ring"))
                .addPage(new SpiritInfusionPage(RING_OF_ARCANE_REACH.get()))
                .addLink(spirit_infusion).addLink(soulstone).addLink(sacredSpirit).addLink(wickedSpirit).addLink(curios);

        radiant_soulstone = new BookEntry(RADIANT_SOULSTONE.get(), "radiant_soulstone")
                .addPage(new HeadlineTextPage("radiant_soulstone"))
                .addPage(new SpiritInfusionPage(RADIANT_SOULSTONE.get()))
                .addLink(spirit_infusion).addLink(soulstone).addLink(sacredSpirit).addLink(wickedSpirit).addLink(arcaneSpirit);

        tyrving = new BookEntry(TYRVING.get(), "tyrving")
                .addPage(new HeadlineTextPage("tyrving"))
                .addPage(new SpiritInfusionPage(TYRVING.get()))
                .addLink(spirit_infusion).addLink(wickedSpirit).addLink(eldritchSpirit).addLink(soul_stained_steel).addLink(radiant_soulstone);

        stronghold_armor = new BookEntry(SOUL_STAINED_STRONGHOLD_CHESTPLATE.get(), "stronghold_armor")
                .addPage(new HeadlineTextPage("stronghold_armor"))
                .addPage(new SpiritInfusionPage(SOUL_STAINED_STRONGHOLD_HELMET.get()))
                .addPage(new SpiritInfusionPage(SOUL_STAINED_STRONGHOLD_CHESTPLATE.get()))
                .addPage(new SpiritInfusionPage(SOUL_STAINED_STRONGHOLD_LEGGINGS.get()))
                .addPage(new SpiritInfusionPage(SOUL_STAINED_STRONGHOLD_BOOTS.get()))
                .addLink(spirit_infusion).addLink(eldritchSpirit).addLink(soul_stained_steel).addLink(radiant_soulstone);

        addEntries(soul_stained_steel, hallowed_gold, curios, spirit_resonators, arcane_spoil_ring, arcane_reach_ring, radiant_soulstone, tyrving, stronghold_armor);
    }
}
