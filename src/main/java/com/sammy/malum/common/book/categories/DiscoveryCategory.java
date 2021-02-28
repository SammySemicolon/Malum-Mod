package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.*;
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
                .addPage(new CraftingPage(new ItemStack(Items.PURPLE_DYE, 2), MalumItems.LAVENDER.get()));
    
        BookEntry arcaneFuels = new BookEntry(MalumItems.ARCANE_CHARCOAL.get(), "arcane_fuels")
                .addPage(new HeadlineTextPage("arcane_charcoal"))
                .addPage(new SmeltingPage(MalumItems.RUNEWOOD_LOG.get(), MalumItems.ARCANE_CHARCOAL.get()))
                .addPage(new HeadlineTextPage("blaze_quartz"))
                .addLink(runewoodTrees);
        
        BookEntry solarSap = new BookEntry(MalumItems.SOLAR_SAP_BOTTLE.get(), "solar_sap")
                .addPage(new HeadlineTextPage("solar_sap"))
                .addPage(new TextPage("solar_sap_again"))
                .addPage(new HeadlineTextPage("solar_syrup"))
                .addPage(new SmeltingPage(MalumItems.SOLAR_SAP_BOTTLE.get(), MalumItems.SOLAR_SYRUP_BOTTLE.get()))
                .addPage(new HeadlineTextPage("solar_sapball"))
                .addPage(new CraftingPage(new ItemStack(MalumItems.SOLAR_SAPBALL.get(),3), MalumItems.SOLAR_SAP_BOTTLE.get(), Items.SLIME_BALL))
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
    
        BookEntry spiritBasics = new BookEntry(MalumItems.CRUDE_SCYTHE.get(), "spirit_basics")
                .addPage(new HeadlineTextPage("spirit_basics"))
                .addPage(new TextPage("spirit_basics_part_two"))
                .addPage(new TextPage("spirit_basics_part_three"))
                .addPage(new HeadlineTextPage("spirit_harvesting"))
                .addPage(new TextPage("spirit_harvesting_part_two"))
                .addPage(new TextPage("spirit_harvesting_part_three"))
                .addPage(new CraftingPage(MalumItems.CRUDE_SCYTHE.get(), Items.IRON_INGOT, Items.IRON_INGOT, MalumItems.SOUL_GEM.get(), EMPTY, Items.STICK, Items.IRON_INGOT, Items.STICK));
    
        BookEntry spiritAltar = new BookEntry(MalumItems.SPIRIT_ALTAR.get(), "spirit_altar")
                .addPage(new HeadlineTextPage("spirit_altar"))
                .addPage(new CraftingPage(MalumItems.SPIRIT_ALTAR.get(), EMPTY, MalumItems.SOUL_GEM.get(), EMPTY, Items.GOLD_INGOT, MalumItems.RUNEWOOD_PLANKS.get(), Items.GOLD_INGOT, MalumItems.RUNEWOOD_PLANKS.get(), MalumItems.RUNEWOOD_PLANKS.get(), MalumItems.RUNEWOOD_PLANKS.get()));
    
        BookEntry simpleInfusion = new BookEntry(MalumItems.TAINTED_ROCK.get(), "simple_infusion")
                .addPage(new HeadlineTextPage("tainted_rock"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.COBBLESTONE, 16), new ItemStack(MalumItems.TAINTED_ROCK.get(),16), new ItemStack(MalumItems.LIFE_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new HeadlineTextPage("darkened_rock"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.COBBLESTONE, 16), new ItemStack(MalumItems.DARKENED_ROCK.get(),16), new ItemStack(MalumItems.DEATH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new HeadlineTextPage("runewood_replication"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.OAK_LOG, 16), new ItemStack(MalumItems.RUNEWOOD_LOG.get(), 16), new ItemStack(MalumItems.EARTH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.OAK_PLANKS, 64), new ItemStack(MalumItems.RUNEWOOD_PLANKS.get(), 64), new ItemStack(MalumItems.EARTH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.OAK_SAPLING, 4), new ItemStack(MalumItems.RUNEWOOD_SAPLING.get(), 4), new ItemStack(MalumItems.EARTH_SPIRIT_SPLINTER.get()), new ItemStack(MalumItems.MAGIC_SPIRIT_SPLINTER.get())));
    
        BookEntry ether = new BookEntry(MalumItems.ORANGE_ETHER.get(), "ether")
                .addPage(new HeadlineTextPage("ether"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.GLOWSTONE_DUST, 4), new ItemStack(MalumItems.ORANGE_ETHER.get()), new ItemStack(MalumItems.FIRE_SPIRIT_SPLINTER.get(), 2)))
                .addPage(new CraftingPage(new ItemStack(MalumItems.ORANGE_ETHER_TORCH.get()), EMPTY, EMPTY, EMPTY, EMPTY, MalumItems.ORANGE_ETHER.get(), EMPTY, EMPTY, Items.STICK, EMPTY))
                .addPage(new CraftingPage(new ItemStack(MalumItems.ORANGE_ETHER_BRAZIER.get()), EMPTY, EMPTY, EMPTY, MalumItems.TAINTED_ROCK.get(), MalumItems.ORANGE_ETHER.get(), MalumItems.TAINTED_ROCK.get(), Items.STICK, MalumItems.TAINTED_ROCK.get(), Items.STICK));
    
        BookEntry elixirOfLife = new BookEntry(MalumItems.ELIXIR_OF_LIFE.get(), "elixir_of_life")
                .addPage(new HeadlineTextPage("elixir_of_life"))
                .addPage(new SpiritInfusionPage(new ItemStack(MalumItems.SOLAR_SYRUP_BOTTLE.get()), new ItemStack(MalumItems.ELIXIR_OF_LIFE.get()), new ItemStack(MalumItems.LIFE_SPIRIT_SPLINTER.get(), 2)))
                .addLink(solarSap);
    
        addEntries(arcaneBasics, runewoodTrees, arcaneFuels, solarSap, unholyBlend, soulGem, spiritBasics, spiritAltar, simpleInfusion, ether, elixirOfLife);
    }
}
