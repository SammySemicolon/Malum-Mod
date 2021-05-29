package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static com.sammy.malum.core.init.items.MalumItems.*;

public class DiscoveryCategory extends BookCategory
{
    public static BookEntry sacredSpirit;
    public static BookEntry wickedSpirit;
    public static BookEntry arcaneSpirit;
    public static BookEntry eldritchSpirit;
    public static BookEntry earthenSpirit;
    public static BookEntry infernalSpirit;
    public static BookEntry aerialSpirit;
    public static BookEntry aquaticSpirit;

    public static BookEntry basicsOfMagic;
    public static BookEntry runewoodTrees;
    public static BookEntry solarSap;
    public static BookEntry blazingQuartz;
    public static BookEntry soulstone;
    public static BookEntry spiritHarvesting;
    public static BookEntry spiritInfusion;
    public static BookEntry taintedRock;
    public static BookEntry twistedRock;
    public static BookEntry ether;
    public static BookEntry spiritArchitecture;

    public DiscoveryCategory()
    {
        super(TAINTED_ROCK.get().getDefaultInstance(), "discovery");
        Item EMPTY = Items.BARRIER;

        sacredSpirit = new BookEntry(SACRED_SPIRIT.get(), "sacred_spirit")
                .addPage(new HeadlineTextPage("sacred_spirit"));
        wickedSpirit = new BookEntry(WICKED_SPIRIT.get(), "wicked_spirit")
                .addPage(new HeadlineTextPage("wicked_spirit"));
        arcaneSpirit = new BookEntry(ARCANE_SPIRIT.get(), "arcane_spirit")
                .addPage(new HeadlineTextPage("arcane_spirit"));
        eldritchSpirit = new BookEntry(ELDRITCH_SPIRIT.get(), "eldritch_spirit")
                .addPage(new HeadlineTextPage("eldritch_spirit"));
        earthenSpirit = new BookEntry(EARTHEN_SPIRIT.get(), "earthen_spirit")
                .addPage(new HeadlineTextPage("earthen_spirit"));
        infernalSpirit = new BookEntry(INFERNAL_SPIRIT.get(), "infernal_spirit")
                .addPage(new HeadlineTextPage("infernal_spirit"));
        aerialSpirit = new BookEntry(AERIAL_SPIRIT.get(), "aerial_spirit")
                .addPage(new HeadlineTextPage("aerial_spirit"));
        aquaticSpirit = new BookEntry(AQUATIC_SPIRIT.get(), "aquatic_spirit")
                .addPage(new HeadlineTextPage("aquatic_spirit"));

        basicsOfMagic = new BookEntry(Items.SOUL_SAND, "basics_of_magic")
                .addPage(new HeadlineTextPage("basics_of_magic"))
                .addPage(new TextPage("basics_of_magic_2"));

        runewoodTrees = new BookEntry(RUNEWOOD_SAPLING.get(), "runewood")
                .addPage(new HeadlineTextPage("runewood_trees"))
                .addPage(new ItemListPage(RUNEWOOD_PLANKS.get(), VERTICAL_RUNEWOOD_PLANKS.get(), BOLTED_RUNEWOOD_PLANKS.get(), RUNEWOOD_PANEL.get(), RUNEWOOD_TILES.get())
                        .addList(RUNEWOOD_PLANKS_SLAB.get(), VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), BOLTED_RUNEWOOD_PLANKS_SLAB.get(), RUNEWOOD_PANEL_SLAB.get(), RUNEWOOD_TILES_SLAB.get())
                        .addList(RUNEWOOD_PLANKS_STAIRS.get(), VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), BOLTED_RUNEWOOD_PLANKS_STAIRS.get(), RUNEWOOD_PANEL_STAIRS.get(), RUNEWOOD_TILES_STAIRS.get())
                        .addList(CUT_RUNEWOOD_PLANKS.get(), RUNEWOOD_BEAM.get(), BOLTED_RUNEWOOD_BEAM.get()))

                .addPage(CraftingPage.itemStandPage(RUNEWOOD_ITEM_STAND.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS_SLAB.get()))
                .addPage(CraftingPage.itemPedestalPage(RUNEWOOD_ITEM_PEDESTAL.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS_SLAB.get()))
                .addPage(new SmeltingPage(RUNEWOOD_LOG.get(), ARCANE_CHARCOAL.get()));

        solarSap = new BookEntry(SOLAR_SAP_BOTTLE.get(), "solar_sap")
                .addPage(new HeadlineTextPage("solar_sap"))
                .addPage(new SmeltingPage(SOLAR_SAP_BOTTLE.get(), SOLAR_SYRUP_BOTTLE.get()))
                .addPage(new CraftingPage(new ItemStack(SOLAR_SAPBALL.get(), 3), SOLAR_SAP_BOTTLE.get(), Items.SLIME_BALL))
                .addLink(runewoodTrees);

        blazingQuartz = new BookEntry(BLAZING_QUARTZ.get(), "blazing_quartz")
                .addPage(new HeadlineTextPage("blazing_quartz"))
                .addPage(new CraftingPage(new ItemStack(BLAZING_QUARTZ_BLOCK.get()), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get()));

        soulstone = new BookEntry(SOULSTONE.get(), "soulstone")
                .addPage(new HeadlineTextPage("soulstone"))
                .addPage(new SmeltingPage(SOULSTONE_ORE.get(), SOULSTONE.get()))
                .addPage(new CraftingPage(new ItemStack(SOULSTONE_BLOCK.get()), SOULSTONE.get(), SOULSTONE.get(), SOULSTONE.get(), SOULSTONE.get(), SOULSTONE.get(), SOULSTONE.get(), SOULSTONE.get(), SOULSTONE.get(), SOULSTONE.get()));

        spiritHarvesting = new BookEntry(CRUDE_SCYTHE.get(), "spirit_harvesting")
                .addPage(new HeadlineTextPage("spirit_harvesting"))
                .addPage(new TextPage("spirit_harvesting_2"))
                .addPage(new CraftingPage(CRUDE_SCYTHE.get(), Items.IRON_INGOT, Items.IRON_INGOT, SOULSTONE.get(), EMPTY, Items.STICK, Items.IRON_INGOT, Items.STICK))
                .addPage(new HeadlineTextPage("scythe_enchanting"))
                .addPage(new HeadlineTextPage("haunting"))
                .addPage(new HeadlineTextPage("rebound"))
                .addPage(new HeadlineTextPage("spirit_plunder"))
                .addLink(basicsOfMagic).addLink(runewoodTrees).addLink(soulstone);

        spiritInfusion = new BookEntry(SPIRIT_ALTAR.get(), "spirit_infusion")
                .addPage(new HeadlineTextPage("spirit_infusion"))
                .addPage(new TextPage("spirit_infusion_2"))
                .addPage(new CraftingPage(SPIRIT_ALTAR.get(), EMPTY, SOULSTONE.get(), EMPTY, Items.GOLD_INGOT, RUNEWOOD_PLANKS.get(), Items.GOLD_INGOT, RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS.get()))
                .addPage(new HeadlineTextPage("hex_ash"))
                .addPage(new SpiritInfusionPage(HEX_ASH.get()))
                .addLink(basicsOfMagic).addLink(soulstone).addLink(spiritHarvesting).addLink(arcaneSpirit);

        taintedRock = new BookEntry(TAINTED_ROCK.get(), "tainted_rock")
                .addPage(new HeadlineTextPage("tainted_rock"))
                .addPage(new SpiritInfusionPage(TAINTED_ROCK.get()))
                .addPage(new HeadlineTextPage("tainted_rock_architecture"))
                .addPage(new ItemListPage(TAINTED_ROCK.get(), SMOOTH_TAINTED_ROCK.get(), POLISHED_TAINTED_ROCK.get(), TAINTED_ROCK_BRICKS.get(), TAINTED_ROCK_TILES.get())
                        .addList(TAINTED_ROCK_SLAB.get(), SMOOTH_TAINTED_ROCK_SLAB.get(), POLISHED_TAINTED_ROCK_SLAB.get(), TAINTED_ROCK_BRICKS_SLAB.get(), TAINTED_ROCK_TILES_SLAB.get())
                        .addList(TAINTED_ROCK_STAIRS.get(), SMOOTH_TAINTED_ROCK_STAIRS.get(), POLISHED_TAINTED_ROCK_STAIRS.get(), TAINTED_ROCK_BRICKS_STAIRS.get(), TAINTED_ROCK_TILES_STAIRS.get())
                        .addList(TAINTED_ROCK_PILLAR.get(), TAINTED_ROCK_PILLAR_CAP.get(), TAINTED_ROCK_COLUMN.get(), TAINTED_ROCK_COLUMN_CAP.get())
                        .addList(CUT_TAINTED_ROCK.get(), CHISELED_TAINTED_ROCK.get()))
                .addPage(CraftingPage.itemStandPage(TWISTED_ROCK_ITEM_STAND.get(), TAINTED_ROCK.get(), TAINTED_ROCK_SLAB.get()))
                .addPage(CraftingPage.itemPedestalPage(TAINTED_ROCK_ITEM_PEDESTAL.get(), TAINTED_ROCK.get(), TAINTED_ROCK_SLAB.get()))
                .addLink(spiritInfusion).addLink(sacredSpirit).addLink(arcaneSpirit);

        twistedRock = new BookEntry(TWISTED_ROCK.get(), "twisted_rock")
                .addPage(new HeadlineTextPage("twisted_rock"))
                .addPage(new SpiritInfusionPage(TWISTED_ROCK.get()))
                .addPage(new HeadlineTextPage("twisted_rock_architecture"))
                .addPage(new ItemListPage(TWISTED_ROCK.get(), SMOOTH_TWISTED_ROCK.get(), POLISHED_TWISTED_ROCK.get(), TWISTED_ROCK_BRICKS.get(), TWISTED_ROCK_TILES.get())
                        .addList(TWISTED_ROCK_SLAB.get(), SMOOTH_TWISTED_ROCK_SLAB.get(), POLISHED_TWISTED_ROCK_SLAB.get(), TWISTED_ROCK_BRICKS_SLAB.get(), TWISTED_ROCK_TILES_SLAB.get())
                        .addList(TWISTED_ROCK_STAIRS.get(), SMOOTH_TWISTED_ROCK_STAIRS.get(), POLISHED_TWISTED_ROCK_STAIRS.get(), TWISTED_ROCK_BRICKS_STAIRS.get(), TWISTED_ROCK_TILES_STAIRS.get())
                        .addList(TWISTED_ROCK_PILLAR.get(), TWISTED_ROCK_PILLAR_CAP.get(), TWISTED_ROCK_COLUMN.get(), TWISTED_ROCK_COLUMN_CAP.get())
                        .addList(CUT_TWISTED_ROCK.get(), CHISELED_TWISTED_ROCK.get()))
                .addPage(CraftingPage.itemStandPage(TWISTED_ROCK_ITEM_STAND.get(), TWISTED_ROCK.get(), TWISTED_ROCK_SLAB.get()))
                .addPage(CraftingPage.itemPedestalPage(TWISTED_ROCK_ITEM_PEDESTAL.get(), TWISTED_ROCK.get(), TWISTED_ROCK_SLAB.get()))
                .addLink(spiritInfusion).addLink(wickedSpirit).addLink(arcaneSpirit);

        ether = new BookEntry(YELLOW_ETHER.get(), "ether")
                .addPage(new HeadlineTextPage("ether"))
                .addPage(new SpiritInfusionPage(YELLOW_ETHER.get()))
                .addPage(new CraftingPage(new ItemStack(YELLOW_ETHER_TORCH.get()), EMPTY, EMPTY, EMPTY, EMPTY, YELLOW_ETHER.get(), EMPTY, EMPTY, Items.STICK, EMPTY))
                .addPage(new CraftingPage(new ItemStack(YELLOW_ETHER_BRAZIER.get()), EMPTY, EMPTY, EMPTY, TAINTED_ROCK.get(), YELLOW_ETHER.get(), TAINTED_ROCK.get(), Items.STICK, TAINTED_ROCK.get(), Items.STICK))
                .addLink(spiritInfusion).addLink(arcaneSpirit).addLink(infernalSpirit);

        spiritArchitecture = new BookEntry(ERODED_ROCK.get(), "spirit_architecture")
                .addPage(new HeadlineTextPage("spirit_architecture"))
                .addPage(new SpiritInfusionPage(CLEANSED_ROCK.get()))
                .addPage(new ItemListPage(CLEANSED_ROCK.get(), SMOOTH_CLEANSED_ROCK.get(), POLISHED_CLEANSED_ROCK.get(), CLEANSED_ROCK_BRICKS.get(), CLEANSED_ROCK_TILES.get())
                        .addList(CLEANSED_ROCK_SLAB.get(), SMOOTH_CLEANSED_ROCK_SLAB.get(), POLISHED_CLEANSED_ROCK_SLAB.get(), CLEANSED_ROCK_BRICKS_SLAB.get(), CLEANSED_ROCK_TILES_SLAB.get())
                        .addList(CLEANSED_ROCK_STAIRS.get(), SMOOTH_CLEANSED_ROCK_STAIRS.get(), POLISHED_CLEANSED_ROCK_STAIRS.get(), CLEANSED_ROCK_BRICKS_STAIRS.get(), CLEANSED_ROCK_TILES_STAIRS.get())
                        .addList(CLEANSED_ROCK_PILLAR.get(), CLEANSED_ROCK_PILLAR_CAP.get(), CLEANSED_ROCK_COLUMN.get(), CLEANSED_ROCK_COLUMN_CAP.get())
                        .addList(CUT_CLEANSED_ROCK.get(), CHISELED_CLEANSED_ROCK.get()))
                .addPage(CraftingPage.itemStandPage(CLEANSED_ROCK_ITEM_STAND.get(), CLEANSED_ROCK.get(), CLEANSED_ROCK_SLAB.get()))
                .addPage(CraftingPage.itemPedestalPage(CLEANSED_ROCK_ITEM_PEDESTAL.get(), CLEANSED_ROCK.get(), CLEANSED_ROCK_SLAB.get()))
                .addPage(new SpiritInfusionPage(PURIFIED_ROCK.get()))
                .addPage(new ItemListPage(PURIFIED_ROCK.get(), SMOOTH_PURIFIED_ROCK.get(), POLISHED_PURIFIED_ROCK.get(), PURIFIED_ROCK_BRICKS.get(), PURIFIED_ROCK_TILES.get())
                        .addList(PURIFIED_ROCK_SLAB.get(), SMOOTH_PURIFIED_ROCK_SLAB.get(), POLISHED_PURIFIED_ROCK_SLAB.get(), PURIFIED_ROCK_BRICKS_SLAB.get(), PURIFIED_ROCK_TILES_SLAB.get())
                        .addList(PURIFIED_ROCK_STAIRS.get(), SMOOTH_PURIFIED_ROCK_STAIRS.get(), POLISHED_PURIFIED_ROCK_STAIRS.get(), PURIFIED_ROCK_BRICKS_STAIRS.get(), PURIFIED_ROCK_TILES_STAIRS.get())
                        .addList(PURIFIED_ROCK_PILLAR.get(), PURIFIED_ROCK_PILLAR_CAP.get(), PURIFIED_ROCK_COLUMN.get(), PURIFIED_ROCK_COLUMN_CAP.get())
                        .addList(CUT_PURIFIED_ROCK.get(), CHISELED_PURIFIED_ROCK.get()))
                .addPage(CraftingPage.itemStandPage(PURIFIED_ROCK_ITEM_STAND.get(), PURIFIED_ROCK.get(), PURIFIED_ROCK_SLAB.get()))
                .addPage(CraftingPage.itemPedestalPage(PURIFIED_ROCK_ITEM_PEDESTAL.get(), PURIFIED_ROCK.get(), PURIFIED_ROCK_SLAB.get()))
                .addPage(new SpiritInfusionPage(ERODED_ROCK.get()))
                .addPage(new ItemListPage(ERODED_ROCK.get(), SMOOTH_ERODED_ROCK.get(), POLISHED_ERODED_ROCK.get(), ERODED_ROCK_BRICKS.get(), ERODED_ROCK_TILES.get())
                        .addList(ERODED_ROCK_SLAB.get(), SMOOTH_ERODED_ROCK_SLAB.get(), POLISHED_ERODED_ROCK_SLAB.get(), ERODED_ROCK_BRICKS_SLAB.get(), ERODED_ROCK_TILES_SLAB.get())
                        .addList(ERODED_ROCK_STAIRS.get(), SMOOTH_ERODED_ROCK_STAIRS.get(), POLISHED_ERODED_ROCK_STAIRS.get(), ERODED_ROCK_BRICKS_STAIRS.get(), ERODED_ROCK_TILES_STAIRS.get())
                        .addList(ERODED_ROCK_PILLAR.get(), ERODED_ROCK_PILLAR_CAP.get(), ERODED_ROCK_COLUMN.get(), ERODED_ROCK_COLUMN_CAP.get())
                        .addList(CUT_ERODED_ROCK.get(), CHISELED_ERODED_ROCK.get()))
                .addPage(CraftingPage.itemStandPage(ERODED_ROCK_ITEM_STAND.get(), ERODED_ROCK.get(), ERODED_ROCK_SLAB.get()))
                .addPage(CraftingPage.itemPedestalPage(ERODED_ROCK_ITEM_PEDESTAL.get(), ERODED_ROCK.get(), ERODED_ROCK_SLAB.get()))
                .addLink(spiritInfusion).addLink(arcaneSpirit).addLink(earthenSpirit);

        addEntries(basicsOfMagic, runewoodTrees, solarSap, blazingQuartz, soulstone, spiritHarvesting, spiritInfusion, taintedRock, twistedRock, ether, spiritArchitecture, sacredSpirit, wickedSpirit, arcaneSpirit, eldritchSpirit, earthenSpirit, infernalSpirit, aerialSpirit, aquaticSpirit);
    }
}