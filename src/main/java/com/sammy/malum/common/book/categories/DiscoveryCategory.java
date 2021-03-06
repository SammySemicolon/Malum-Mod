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
    
    public static BookEntry arcaneBasics;
    public static BookEntry runewoodTrees;
    public static BookEntry arcaneFuels;
    public static BookEntry solarSap;
    public static BookEntry unholyBlend;
    public static BookEntry soulGem;
    public static BookEntry spiritBasics;
    public static BookEntry spiritAltar;
    public static BookEntry simpleInfusion;
    
    public DiscoveryCategory()
    {
        super(MalumItems.MALUM_BOOK.get().getDefaultInstance(), "discovery");
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
        
        arcaneBasics = new BookEntry(Items.SOUL_SAND, "arcane_basics")
                .addPage(new HeadlineTextPage("arcane_basics"))
                .addPage(new HeadlineTextPage("soul_sand"))
                .addPage(new HeadlineTextPage("redstone_dust"));
    
        runewoodTrees = new BookEntry(MalumItems.RUNEWOOD_SAPLING.get(), "runewood_trees")
                .addPage(new HeadlineTextPage("runewood_trees"))
                .addPage(new HeadlineTextPage("sun_kissed_leaves"))
                .addPage(new HeadlineTextPage("sun_kissed_grass"))
                .addPage(new CraftingPage(new ItemStack(Items.PURPLE_DYE, 2), MalumItems.LAVENDER.get()));
    
        arcaneFuels = new BookEntry(MalumItems.ARCANE_CHARCOAL.get(), "arcane_fuels")
                .addPage(new HeadlineTextPage("arcane_charcoal"))
                .addPage(new SmeltingPage(MalumItems.RUNEWOOD_LOG.get(), MalumItems.ARCANE_CHARCOAL.get()))
                .addPage(new HeadlineTextPage("blaze_quartz"))
                .addLink(runewoodTrees);
        
        solarSap = new BookEntry(MalumItems.SOLAR_SAP_BOTTLE.get(), "solar_sap")
                .addPage(new HeadlineTextPage("solar_sap"))
                .addPage(new TextPage("solar_sap_again"))
                .addPage(new HeadlineTextPage("solar_syrup"))
                .addPage(new SmeltingPage(MalumItems.SOLAR_SAP_BOTTLE.get(), MalumItems.SOLAR_SYRUP_BOTTLE.get()))
                .addPage(new HeadlineTextPage("solar_sapball"))
                .addPage(new CraftingPage(new ItemStack(MalumItems.SOLAR_SAPBALL.get(),3), MalumItems.SOLAR_SAP_BOTTLE.get(), Items.SLIME_BALL))
                .addLink(runewoodTrees);
    
        unholyBlend = new BookEntry(MalumItems.UNHOLY_BLEND.get(), "unholy_blend")
                .addPage(new HeadlineTextPage("unholy_blend"))
                .addPage(new TextPage("unholy_blend_again"))
                .addPage(new CraftingPage(MalumItems.UNHOLY_BLEND.get(), Items.REDSTONE, Items.ROTTEN_FLESH, Items.SOUL_SAND))
                .addPage(new SmeltingPage(MalumItems.UNHOLY_BLEND.get(), MalumItems.ARCANE_GRIT.get()))
                .addLink(arcaneBasics);
    
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
    
        simpleInfusion = new BookEntry(MalumItems.TAINTED_ROCK.get(), "simple_infusion")
                .addPage(new HeadlineTextPage("tainted_rock"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.COBBLESTONE, 16), new ItemStack(MalumItems.TAINTED_ROCK.get(),16), new ItemStack(MalumItems.LIFE_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new HeadlineTextPage("darkened_rock"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.COBBLESTONE, 16), new ItemStack(MalumItems.DARKENED_ROCK.get(),16), new ItemStack(MalumItems.DEATH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new HeadlineTextPage("runewood_replication"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.OAK_LOG, 16), new ItemStack(MalumItems.RUNEWOOD_LOG.get(), 16), new ItemStack(MalumItems.EARTH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.OAK_PLANKS, 64), new ItemStack(MalumItems.RUNEWOOD_PLANKS.get(), 64), new ItemStack(MalumItems.EARTH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.OAK_SAPLING, 4), new ItemStack(MalumItems.RUNEWOOD_SAPLING.get(), 4), new ItemStack(MalumItems.EARTH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.DIRT, 32), new ItemStack(MalumItems.SUN_KISSED_GRASS_BLOCK.get(), 32), new ItemStack(MalumItems.EARTH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new HeadlineTextPage("bringing_forth_life"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.DIRT, 32), new ItemStack(Items.GRASS_BLOCK, 32), new ItemStack(MalumItems.EARTH_SPIRIT_SPLINTER.get())))
                .addLink(lifeSpirit).addLink(deathSpirit).addLink(magicSpirit).addLink(earthSpirit);
    
        
        addEntries(arcaneBasics, runewoodTrees, arcaneFuels, solarSap, unholyBlend, soulGem, spiritBasics, spiritAltar, simpleInfusion, lifeSpirit, deathSpirit, magicSpirit, earthSpirit,fireSpirit,airSpirit,waterSpirit,eldritchSpirit);
    }
}
