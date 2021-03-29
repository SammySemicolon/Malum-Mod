package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.*;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static com.sammy.malum.core.init.MalumItems.*;

public class DiscoveryCategory extends BookCategory
{
    public static BookEntry lifeSpirit;
    public static BookEntry deathSpirit;
    public static BookEntry magicSpirit;
    public static BookEntry earthSpirit;
    public static BookEntry fireSpirit;
    public static BookEntry airSpirit;
    public static BookEntry waterSpirit;
    public static BookEntry eldritchSpirit;

    public static BookEntry basicsOfMagic;
    public static BookEntry runewoodTrees;
    public static BookEntry arcaneCharcoal;
    public static BookEntry solarSap;
    public static BookEntry blazingQuartz;
    public static BookEntry grimslate;
    public static BookEntry spiritHarvesting;
    public static BookEntry spiritInfusion;
    public static BookEntry taintedRock;
    public static BookEntry twistedRock;

    public DiscoveryCategory()
    {
        super(MalumItems.TAINTED_ROCK.get().getDefaultInstance(), "discovery");
        Item EMPTY = Items.BARRIER;

        lifeSpirit = new BookEntry(MalumItems.LIFE_SPIRIT_SPLINTER.get(), "life_spirit")
                .addPage(new HeadlineTextPage("life_spirit"));
        deathSpirit = new BookEntry(MalumItems.DEATH_SPIRIT_SPLINTER.get(), "death_spirit")
                .addPage(new HeadlineTextPage("death_spirit"));
        magicSpirit = new BookEntry(MalumItems.MAGIC_SPIRIT_SPLINTER.get(), "magic_spirit")
                .addPage(new HeadlineTextPage("magic_spirit"));
        earthSpirit = new BookEntry(MalumItems.EARTH_SPIRIT_SPLINTER.get(), "earth_spirit")
                .addPage(new HeadlineTextPage("earth_spirit"));
        fireSpirit = new BookEntry(MalumItems.FIRE_SPIRIT_SPLINTER.get(), "fire_spirit")
                .addPage(new HeadlineTextPage("fire_spirit"));
        airSpirit = new BookEntry(MalumItems.AIR_SPIRIT_SPLINTER.get(), "air_spirit")
                .addPage(new HeadlineTextPage("air_spirit"));
        waterSpirit = new BookEntry(MalumItems.WATER_SPIRIT_SPLINTER.get(), "water_spirit")
                .addPage(new HeadlineTextPage("water_spirit"));
        eldritchSpirit = new BookEntry(MalumItems.ELDRITCH_SPIRIT_SPLINTER.get(), "eldritch_spirit")
                .addPage(new HeadlineTextPage("eldritch_spirit"));

        basicsOfMagic = new BookEntry(Items.SOUL_SAND, "basics_of_magic")
                .addPage(new HeadlineTextPage("basics_of_magic"))
                .addPage(new TextPage("basics_of_magic_2"));

        runewoodTrees = new BookEntry(MalumItems.RUNEWOOD_SAPLING.get(), "runewood")
                .addPage(new HeadlineTextPage("runewood_trees"))
                .addPage(new TextPage("runewood_trees_2"))
                .addPage(new HeadlineTextPage("runewood_architecture"))
                .addPage(new ItemListPage(MalumItems.RUNEWOOD_PLANKS.get(), MalumItems.VERTICAL_RUNEWOOD_PLANKS.get(), MalumItems.BOLTED_RUNEWOOD_PLANKS.get(), MalumItems.RUNEWOOD_PANEL.get(), MalumItems.RUNEWOOD_TILES.get())
                                 .addList(MalumItems.RUNEWOOD_PLANKS_SLAB.get(), MalumItems.VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), MalumItems.BOLTED_RUNEWOOD_PLANKS_SLAB.get(), MalumItems.RUNEWOOD_PANEL_SLAB.get(), MalumItems.RUNEWOOD_TILES_SLAB.get())
                                 .addList(MalumItems.RUNEWOOD_PLANKS_STAIRS.get(), MalumItems.VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), MalumItems.BOLTED_RUNEWOOD_PLANKS_STAIRS.get(), MalumItems.RUNEWOOD_PANEL_STAIRS.get(), MalumItems.RUNEWOOD_TILES_STAIRS.get())
                                 .addList(MalumItems.CUT_RUNEWOOD_PLANKS.get(), MalumItems.RUNEWOOD_BEAM.get(), MalumItems.BOLTED_RUNEWOOD_BEAM.get()));

        arcaneCharcoal = new BookEntry(MalumItems.ARCANE_CHARCOAL.get(), "arcane_charcoal")
                .addPage(new HeadlineTextPage("arcane_charcoal"))
                .addPage(new SmeltingPage(MalumItems.RUNEWOOD_LOG.get(), MalumItems.ARCANE_CHARCOAL.get()))
                .addLink(runewoodTrees);

        solarSap = new BookEntry(MalumItems.SOLAR_SAP_BOTTLE.get(), "solar_sap")
                .addPage(new HeadlineTextPage("solar_sap"))
                .addPage(new TextPage("solar_sap_2"))
                .addPage(new SmeltingPage(MalumItems.SOLAR_SAP_BOTTLE.get(), MalumItems.SOLAR_SYRUP_BOTTLE.get()))
                .addPage(new CraftingPage(new ItemStack(SOLAR_SAPBALL.get(),3), MalumItems.SOLAR_SAP_BOTTLE.get(), Items.SLIME_BALL))
                .addLink(runewoodTrees);

        blazingQuartz = new BookEntry(BLAZING_QUARTZ.get(), "blazing_quartz")
                .addPage(new HeadlineTextPage("blazing_quartz"))
                .addPage(new CraftingPage(new ItemStack(BLAZING_QUARTZ_BLOCK.get()), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get(), BLAZING_QUARTZ.get()));

        grimslate = new BookEntry(MalumItems.GRIMSLATE_PLATING.get(), "grimslate")
                .addPage(new HeadlineTextPage("grimslate"))
                .addPage(new SmeltingPage(MalumItems.GRIMSLATE_ORE.get(), MalumItems.GRIMSLATE_PLATING.get()))
                .addPage(new CraftingPage(new ItemStack(GRIMSLATE_BLOCK.get()), GRIMSLATE_PLATING.get(), GRIMSLATE_PLATING.get(), GRIMSLATE_PLATING.get(), GRIMSLATE_PLATING.get(), GRIMSLATE_PLATING.get(), GRIMSLATE_PLATING.get(), GRIMSLATE_PLATING.get(), GRIMSLATE_PLATING.get(), GRIMSLATE_PLATING.get()));

        spiritHarvesting = new BookEntry(CRUDE_SCYTHE.get(), "spirit_harvesting")
                .addPage(new HeadlineTextPage("spirit_harvesting"))
                .addPage(new TextPage("spirit_harvesting_2"))
                .addPage(new TextPage("spirit_harvesting_3"))
                .addPage(new CraftingPage(MalumItems.CRUDE_SCYTHE.get(), Items.IRON_INGOT, Items.IRON_INGOT, GRIMSLATE_PLATING.get(), EMPTY, Items.STICK, Items.IRON_INGOT, Items.STICK))
                .addLink(basicsOfMagic).addLink(grimslate);

        spiritInfusion = new BookEntry(SPIRIT_ALTAR.get(), "spirit_infusion")
                .addPage(new HeadlineTextPage("spirit_infusion"))
                .addPage(new TextPage("spirit_infusion_2"))
                .addPage(new CraftingPage(SPIRIT_ALTAR.get(), EMPTY, GRIMSLATE_PLATING.get(), EMPTY, Items.GOLD_INGOT, RUNEWOOD_PLANKS.get(), Items.GOLD_INGOT, RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS.get(), RUNEWOOD_PLANKS.get()))
                .addPage(new SpiritInfusionPage(new ItemStack(BLAZING_QUARTZ.get(), 4), new ItemStack(Items.BLAZE_POWDER, 2), new ItemStack(FIRE_SPIRIT_SPLINTER.get(), 4)))
                .addLink(basicsOfMagic).addLink(grimslate).addLink(spiritHarvesting);

        taintedRock = new BookEntry(MalumItems.TAINTED_ROCK.get(), "tainted_rock")
                .addPage(new HeadlineTextPage("tainted_rock"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.COBBLESTONE, 16), new ItemStack(MalumItems.TAINTED_ROCK.get(),16), new ItemStack(MalumItems.LIFE_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new HeadlineTextPage("tainted_rock_architecture"))
                .addPage(new ItemListPage(MalumItems.TAINTED_ROCK.get(), MalumItems.SMOOTH_TAINTED_ROCK.get(), MalumItems.POLISHED_TAINTED_ROCK.get(), MalumItems.TAINTED_ROCK_BRICKS.get(), MalumItems.TAINTED_ROCK_TILES.get())
                        .addList(MalumItems.TAINTED_ROCK_SLAB.get(), MalumItems.SMOOTH_TAINTED_ROCK_SLAB.get(), MalumItems.POLISHED_TAINTED_ROCK_SLAB.get(), MalumItems.TAINTED_ROCK_BRICKS_SLAB.get(), MalumItems.TAINTED_ROCK_TILES_SLAB.get())
                        .addList(MalumItems.TAINTED_ROCK_STAIRS.get(), MalumItems.SMOOTH_TAINTED_ROCK_STAIRS.get(), MalumItems.POLISHED_TAINTED_ROCK_STAIRS.get(), MalumItems.TAINTED_ROCK_BRICKS_STAIRS.get(), MalumItems.TAINTED_ROCK_TILES_STAIRS.get())
                        .addList(MalumItems.TAINTED_ROCK_PILLAR.get(), MalumItems.TAINTED_ROCK_PILLAR_CAP.get(), MalumItems.TAINTED_ROCK_COLUMN.get(), MalumItems.TAINTED_ROCK_COLUMN_CAP.get())
                        .addList(MalumItems.CUT_TAINTED_ROCK.get(), MalumItems.CHISELED_TAINTED_ROCK.get()));

        twistedRock = new BookEntry(MalumItems.TWISTED_ROCK.get(), "twisted_rock")
                .addPage(new HeadlineTextPage("twisted_rock"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.COBBLESTONE, 16), new ItemStack(MalumItems.TWISTED_ROCK.get(),16), new ItemStack(MalumItems.DEATH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new HeadlineTextPage("twisted_rock_architecture"))
                .addPage(new ItemListPage(MalumItems.TWISTED_ROCK.get(), MalumItems.SMOOTH_TWISTED_ROCK.get(), MalumItems.POLISHED_TWISTED_ROCK.get(), MalumItems.TWISTED_ROCK_BRICKS.get(), MalumItems.TWISTED_ROCK_TILES.get())
                        .addList(MalumItems.TWISTED_ROCK_SLAB.get(), MalumItems.SMOOTH_TWISTED_ROCK_SLAB.get(), MalumItems.POLISHED_TWISTED_ROCK_SLAB.get(), MalumItems.TWISTED_ROCK_BRICKS_SLAB.get(), MalumItems.TWISTED_ROCK_TILES_SLAB.get())
                        .addList(MalumItems.TWISTED_ROCK_STAIRS.get(), MalumItems.SMOOTH_TWISTED_ROCK_STAIRS.get(), MalumItems.POLISHED_TWISTED_ROCK_STAIRS.get(), MalumItems.TWISTED_ROCK_BRICKS_STAIRS.get(), MalumItems.TWISTED_ROCK_TILES_STAIRS.get())
                        .addList(MalumItems.TWISTED_ROCK_PILLAR.get(), MalumItems.TWISTED_ROCK_PILLAR_CAP.get(), MalumItems.TWISTED_ROCK_COLUMN.get(), MalumItems.TWISTED_ROCK_COLUMN_CAP.get())
                        .addList(MalumItems.CUT_TWISTED_ROCK.get(), MalumItems.CHISELED_TWISTED_ROCK.get()));

        /*
        arcaneFuels = new BookEntry(MalumItems.ARCANE_CHARCOAL.get(), "arcane_fuels")
                .addPage(new HeadlineTextPage("arcane_charcoal"))
                .addPage(new SmeltingPage(MalumItems.RUNEWOOD_LOG.get(), MalumItems.ARCANE_CHARCOAL.get()))
                .addPage(new HeadlineTextPage("blaze_quartz"))
                .addLink(runewood);

        solarSap = new BookEntry(MalumItems.SOLAR_SAP_BOTTLE.get(), "solar_sap")
                .addPage(new HeadlineTextPage("solar_sap"))
                .addPage(new TextPage("solar_sap_again"))
                .addPage(new HeadlineTextPage("solar_syrup"))
                .addPage(new SmeltingPage(MalumItems.SOLAR_SAP_BOTTLE.get(), MalumItems.SOLAR_SYRUP_BOTTLE.get()))
                .addPage(new HeadlineTextPage("solar_sapball"))
                .addPage(new CraftingPage(new ItemStack(MalumItems.SOLAR_SAPBALL.get(),3), MalumItems.SOLAR_SAP_BOTTLE.get(), Items.SLIME_BALL))
                .addLink(runewood);

        unholyBlend = new BookEntry(MalumItems.UNHOLY_BLEND.get(), "unholy_blend")
                .addPage(new HeadlineTextPage("unholy_blend"))
                .addPage(new TextPage("unholy_blend_again"))
                .addPage(new CraftingPage(MalumItems.UNHOLY_BLEND.get(), Items.REDSTONE, Items.ROTTEN_FLESH, Items.SOUL_SAND))
                .addPage(new SmeltingPage(MalumItems.UNHOLY_BLEND.get(), MalumItems.ARCANE_GRIT.get()))
                .addLink(magicalBasics);

        soulGem = new BookEntry(MalumItems.SOUL_GEM.get(), "soul_gem")
                .addPage(new HeadlineTextPage("soul_gem"))
                .addPage(new CraftingPage(MalumItems.SOUL_GEM.get(), EMPTY, MalumItems.ARCANE_GRIT.get(), EMPTY, MalumItems.ARCANE_GRIT.get(), Items.DIAMOND, MalumItems.ARCANE_GRIT.get(), EMPTY, MalumItems.ARCANE_GRIT.get(), EMPTY))
                .addLink(unholyBlend);

        spiritBasics = new BookEntry(MalumItems.CRUDE_SCYTHE.get(), "spirit_basics")
                .addPage(new HeadlineTextPage("spirit_basics"))
                .addPage(new TextPage("spirit_basics_part_two"))
                .addPage(new TextPage("spirit_basics_part_three"))
                .addPage(new HeadlineTextPage("spirit_harvesting"))
                .addPage(new TextPage("spirit_harvesting_part_two"))
                .addPage(new TextPage("spirit_harvesting_part_three"))
                .addPage(new CraftingPage(MalumItems.CRUDE_SCYTHE.get(), Items.IRON_INGOT, Items.IRON_INGOT, MalumItems.SOUL_GEM.get(), EMPTY, Items.STICK, Items.IRON_INGOT, Items.STICK));

        spiritAltar = new BookEntry(MalumItems.SPIRIT_ALTAR.get(), "spirit_altar")
                .addPage(new HeadlineTextPage("spirit_altar"))
                .addPage(new CraftingPage(MalumItems.SPIRIT_ALTAR.get(), EMPTY, MalumItems.SOUL_GEM.get(), EMPTY, Items.GOLD_INGOT, MalumItems.RUNEWOOD_PLANKS.get(), Items.GOLD_INGOT, MalumItems.RUNEWOOD_PLANKS.get(), MalumItems.RUNEWOOD_PLANKS.get(), MalumItems.RUNEWOOD_PLANKS.get()));

        taintedRock = new BookEntry(MalumItems.TAINTED_ROCK.get(), "tainted_rock")
                .addPage(new HeadlineTextPage("tainted_rock"))
                .addPage(new TextPage("tainted_rock_again"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.COBBLESTONE, 16), new ItemStack(MalumItems.TAINTED_ROCK.get(),16), new ItemStack(MalumItems.LIFE_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new HeadlineTextPage("tainted_rock_architecture"))
                .addPage(new ItemListPage(MalumItems.TAINTED_ROCK.get(), MalumItems.SMOOTH_TAINTED_ROCK.get(), MalumItems.POLISHED_TAINTED_ROCK.get(), MalumItems.TAINTED_ROCK_BRICKS.get(), MalumItems.TAINTED_ROCK_TILES.get())
                        .addList(MalumItems.TAINTED_ROCK_SLAB.get(), MalumItems.SMOOTH_TAINTED_ROCK_SLAB.get(), MalumItems.POLISHED_TAINTED_ROCK_SLAB.get(), MalumItems.TAINTED_ROCK_BRICKS_SLAB.get(), MalumItems.TAINTED_ROCK_TILES_SLAB.get())
                        .addList(MalumItems.TAINTED_ROCK_STAIRS.get(), MalumItems.SMOOTH_TAINTED_ROCK_STAIRS.get(), MalumItems.POLISHED_TAINTED_ROCK_STAIRS.get(), MalumItems.TAINTED_ROCK_BRICKS_STAIRS.get(), MalumItems.TAINTED_ROCK_TILES_STAIRS.get())
                        .addList(MalumItems.TAINTED_ROCK_PILLAR.get(), MalumItems.TAINTED_ROCK_PILLAR_CAP.get(), MalumItems.TAINTED_ROCK_COLUMN.get(), MalumItems.TAINTED_ROCK_COLUMN_CAP.get())
                        .addList(MalumItems.CUT_TAINTED_ROCK.get(), MalumItems.CHISELED_TAINTED_ROCK.get()));

        twistedRock = new BookEntry(MalumItems.TWISTED_ROCK.get(), "twisted_rock")
                .addPage(new HeadlineTextPage("twisted_rock"))
                .addPage(new TextPage("twisted_rock_again"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.COBBLESTONE, 16), new ItemStack(MalumItems.TWISTED_ROCK.get(),16), new ItemStack(MalumItems.DEATH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new HeadlineTextPage("twisted_rock_architecture"))
                .addPage(new ItemListPage(MalumItems.TWISTED_ROCK.get(), MalumItems.SMOOTH_TWISTED_ROCK.get(), MalumItems.POLISHED_TWISTED_ROCK.get(), MalumItems.TWISTED_ROCK_BRICKS.get(), MalumItems.TWISTED_ROCK_TILES.get())
                        .addList(MalumItems.TWISTED_ROCK_SLAB.get(), MalumItems.SMOOTH_TWISTED_ROCK_SLAB.get(), MalumItems.POLISHED_TWISTED_ROCK_SLAB.get(), MalumItems.TWISTED_ROCK_BRICKS_SLAB.get(), MalumItems.TWISTED_ROCK_TILES_SLAB.get())
                        .addList(MalumItems.TWISTED_ROCK_STAIRS.get(), MalumItems.SMOOTH_TWISTED_ROCK_STAIRS.get(), MalumItems.POLISHED_TWISTED_ROCK_STAIRS.get(), MalumItems.TWISTED_ROCK_BRICKS_STAIRS.get(), MalumItems.TWISTED_ROCK_TILES_STAIRS.get())
                        .addList(MalumItems.TWISTED_ROCK_PILLAR.get(), MalumItems.TWISTED_ROCK_PILLAR_CAP.get(), MalumItems.TWISTED_ROCK_COLUMN.get(), MalumItems.TWISTED_ROCK_COLUMN_CAP.get())
                        .addList(MalumItems.CUT_TWISTED_ROCK.get(), MalumItems.CHISELED_TWISTED_ROCK.get()));

        runewoodInfusion = new BookEntry(MalumItems.RUNEWOOD_PLANKS.get(), "runewood_infusion")
                .addPage(new HeadlineTextPage("runewood_infusion"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.OAK_LOG, 16), new ItemStack(MalumItems.RUNEWOOD_LOG.get(), 16), new ItemStack(MalumItems.EARTH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.OAK_PLANKS, 64), new ItemStack(MalumItems.RUNEWOOD_PLANKS.get(), 64), new ItemStack(MalumItems.EARTH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.OAK_SAPLING, 4), new ItemStack(MalumItems.RUNEWOOD_SAPLING.get(), 4), new ItemStack(MalumItems.EARTH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.DIRT, 32), new ItemStack(MalumItems.SUN_KISSED_GRASS_BLOCK.get(), 32), new ItemStack(MalumItems.EARTH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.OAK_LEAVES, 32), new ItemStack(MalumItems.SUN_KISSED_LEAVES.get(), 32), new ItemStack(MalumItems.EARTH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addLink(lifeSpirit).addLink(deathSpirit).addLink(magicSpirit).addLink(earthSpirit);
*/
        addEntries(basicsOfMagic, runewoodTrees, arcaneCharcoal, solarSap, blazingQuartz, grimslate, spiritHarvesting, spiritInfusion, taintedRock, twistedRock, lifeSpirit, deathSpirit, magicSpirit, earthSpirit, fireSpirit, airSpirit, waterSpirit, eldritchSpirit);
    }
}
