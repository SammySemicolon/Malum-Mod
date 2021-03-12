package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.CraftingPage;
import com.sammy.malum.common.book.pages.HeadlineTextPage;
import com.sammy.malum.common.book.pages.ItemListPage;
import com.sammy.malum.common.book.pages.SpiritInfusionPage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static com.sammy.malum.common.book.categories.DiscoveryCategory.*;
import static com.sammy.malum.core.init.MalumItems.*;

public class TinkeringCategory extends BookCategory
{
    public static BookEntry hallowedGold;
    public static BookEntry soulStainedSteel;
    public static BookEntry ether;
    public static BookEntry basicBaubles;
    public static BookEntry elixirOfLife;
    public static BookEntry voidBerries;
    public static BookEntry enderQuarks;
    public static BookEntry abstruseBlock;
    public static BookEntry imperviousRock;
    public static BookEntry ringOfAttraction;
    public static BookEntry imperviousSeal;
    public static BookEntry archangelRing;
    public static BookEntry livingCapacitor;
    public static BookEntry soulStainedStrongholdArmor;
    
    public TinkeringCategory()
    {
        super(HALLOWED_GOLD_INGOT.get().getDefaultInstance(), "tinkering");
        Item EMPTY = Items.BARRIER;
        
        hallowedGold = new BookEntry(HALLOWED_GOLD_INGOT.get(), "hallowed_gold")
                .addPage(new HeadlineTextPage("hallowed_gold"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.GOLD_INGOT, 2), new ItemStack(HALLOWED_GOLD_INGOT.get(), 2), new ItemStack(EARTH_SPIRIT_SPLINTER.get()), new ItemStack(FIRE_SPIRIT_SPLINTER.get()), new ItemStack(AIR_SPIRIT_SPLINTER.get()), new ItemStack(WATER_SPIRIT_SPLINTER.get())))
                .addPage(new CraftingPage(new ItemStack(HALLOWED_GOLD_NUGGET.get(), 9), EMPTY, EMPTY, EMPTY, EMPTY, HALLOWED_GOLD_INGOT.get(), EMPTY, EMPTY, EMPTY, EMPTY))
                .addPage(new CraftingPage(new ItemStack(HALLOWED_GOLD_BLOCK.get()), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get()))
                .addPage(new CraftingPage(new ItemStack(SPIRIT_JAR.get()), Items.GLASS_PANE, HALLOWED_GOLD_INGOT.get(), Items.GLASS_PANE, Items.GLASS_PANE, EMPTY, Items.GLASS_PANE, Items.GLASS_PANE, Items.GLASS_PANE, Items.GLASS_PANE))
                .addPage(new HeadlineTextPage("spirit_jar"))
                .addLink(earthSpirit).addLink(fireSpirit).addLink(airSpirit).addLink(waterSpirit);
        
        soulStainedSteel = new BookEntry(SOUL_STAINED_STEEL_INGOT.get(), "soul_stained_steel")
                .addPage(new HeadlineTextPage("soul_stained_steel"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.IRON_INGOT, 2), new ItemStack(SOUL_STAINED_STEEL_INGOT.get(), 2), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 2), new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 2)))
                .addPage(new CraftingPage(new ItemStack(SOUL_STAINED_STEEL_NUGGET.get(), 9), EMPTY, EMPTY, EMPTY, EMPTY, SOUL_STAINED_STEEL_INGOT.get(), EMPTY, EMPTY, EMPTY, EMPTY))
                .addPage(new CraftingPage(new ItemStack(SOUL_STAINED_STEEL_BLOCK.get()), SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_INGOT.get()))
                .addPage(new ItemListPage(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_HOE.get()).addList(SOUL_STAINED_STEEL_HELMET.get(), SOUL_STAINED_STEEL_CHESTPLATE.get(), SOUL_STAINED_STEEL_LEGGINGS.get(), SOUL_STAINED_STEEL_BOOTS.get()))
                .addPage(new HeadlineTextPage("soul_stained_gear"))
                .addLink(deathSpirit).addLink(magicSpirit);
        
        ether = new BookEntry(ORANGE_ETHER.get(), "ether")
                .addPage(new HeadlineTextPage("ether"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.GLOWSTONE_DUST, 4), new ItemStack(ORANGE_ETHER.get()), new ItemStack(FIRE_SPIRIT_SPLINTER.get(), 2)))
                .addPage(new CraftingPage(new ItemStack(ORANGE_ETHER_TORCH.get()), EMPTY, EMPTY, EMPTY, EMPTY, ORANGE_ETHER.get(), EMPTY, EMPTY, Items.STICK, EMPTY))
                .addPage(new CraftingPage(new ItemStack(ORANGE_ETHER_BRAZIER.get()), EMPTY, EMPTY, EMPTY, TAINTED_ROCK.get(), ORANGE_ETHER.get(), TAINTED_ROCK.get(), Items.STICK, TAINTED_ROCK.get(), Items.STICK))
                .addLink(fireSpirit);
    
        basicBaubles = new BookEntry(GILDED_RING.get(), "basic_baubles")
                .addPage(new HeadlineTextPage("basic_baubles"))
                .addPage(new CraftingPage(new ItemStack(GILDED_RING.get()), EMPTY, Items.LEATHER, HALLOWED_GOLD_INGOT.get(), Items.LEATHER, EMPTY, Items.LEATHER, EMPTY, Items.LEATHER, EMPTY))
                .addPage(new CraftingPage(new ItemStack(GILDED_BELT.get()), Items.LEATHER, Items.LEATHER, Items.LEATHER, HALLOWED_GOLD_INGOT.get(), SOUL_GEM.get(), HALLOWED_GOLD_INGOT.get(), EMPTY, HALLOWED_GOLD_INGOT.get(), EMPTY))
                .addLink(hallowedGold);
        
        elixirOfLife = new BookEntry(ELIXIR_OF_LIFE.get(), "elixir_of_life")
                .addPage(new HeadlineTextPage("elixir_of_life"))
                .addPage(new SpiritInfusionPage(new ItemStack(SOLAR_SYRUP_BOTTLE.get()), new ItemStack(ELIXIR_OF_LIFE.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 2)))
                .addLink(solarSap).addLink(lifeSpirit);
    
        voidBerries = new BookEntry(VOID_BERRIES.get(), "void_berries")
                .addPage(new HeadlineTextPage("void_berries"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.SWEET_BERRIES, 8), new ItemStack(VOID_BERRIES.get(), 8), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 2)))
                .addLink(deathSpirit);
    
        enderQuarks = new BookEntry(ENDER_QUARKS.get(), "ender_quarks")
                .addPage(new HeadlineTextPage("ender_quarks"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.ENDER_PEARL), new ItemStack(ENDER_QUARKS.get(), 2), new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 2)))
                .addLink(magicSpirit);
    
        abstruseBlock = new BookEntry(ABSTRUSE_BLOCK.get(), "abstruse_block")
                .addPage(new HeadlineTextPage("abstruse_block"))
                .addPage(new SpiritInfusionPage(new ItemStack(TAINTED_ROCK.get(), 8), new ItemStack(ABSTRUSE_BLOCK.get(), 8), new ItemStack(AIR_SPIRIT_SPLINTER.get()), new ItemStack(WATER_SPIRIT_SPLINTER.get())))
                .addLink(airSpirit).addLink(waterSpirit);
        
        imperviousRock = new BookEntry(IMPERVIOUS_ROCK.get(), "impervious_rock")
                .addPage(new HeadlineTextPage("impervious_rock"))
                .addPage(new SpiritInfusionPage(new ItemStack(TAINTED_ROCK.get(), 8), new ItemStack(IMPERVIOUS_ROCK.get(), 8), new ItemStack(DEATH_SPIRIT_SPLINTER.get())))
                .addLink(basicBaubles).addLink(deathSpirit);
        
        imperviousSeal = new BookEntry(UNRELENTING_BELT.get(), "unrelenting_belt")
                .addPage(new HeadlineTextPage("unrelenting_belt"))
                .addPage(new CraftingPage(new ItemStack(UNRELENTING_BELT.get()), EMPTY, IMPERVIOUS_ROCK.get(), EMPTY, Items.LEATHER, SOUL_GEM.get(), Items.LEATHER, EMPTY, IMPERVIOUS_ROCK.get(), EMPTY))
                .addLink(basicBaubles).addLink(imperviousRock);
    
        archangelRing = new BookEntry(ARCHANGEL_RING.get(), "archangel_ring")
                .addPage(new HeadlineTextPage("archangel_ring"))
                .addPage(new SpiritInfusionPage(new ItemStack(GILDED_RING.get()), new ItemStack(ARCHANGEL_RING.get()), new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 12), new ItemStack(EARTH_SPIRIT_SPLINTER.get(), 6), new ItemStack(AIR_SPIRIT_SPLINTER.get(), 24)))
                .addLink(magicSpirit).addLink(earthSpirit).addLink(airSpirit);
    
        livingCapacitor = new BookEntry(LIVING_CAPACITOR.get(), "living_capacitor")
                .addPage(new HeadlineTextPage("living_capacitor"))
                .addPage(new SpiritInfusionPage(new ItemStack(ABYSSAL_APPARATUS.get()), new ItemStack(LIVING_CAPACITOR.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 16), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 16)))
                .addLink(voidBerries).addLink(lifeSpirit).addLink(deathSpirit).addLink(eldritchSpirit);
    
        soulStainedStrongholdArmor = new BookEntry(SOUL_STAINED_STRONGHOLD_CHESTPLATE.get(), "stronghold_armor")
                .addPage(new HeadlineTextPage("stronghold_armor"))
                .addPage(new ItemListPage(SOUL_STAINED_STRONGHOLD_HELMET.get(), SOUL_STAINED_STRONGHOLD_CHESTPLATE.get(), SOUL_STAINED_STRONGHOLD_LEGGINGS.get(), SOUL_STAINED_STRONGHOLD_BOOTS.get()))
                .addPage(new SpiritInfusionPage(new ItemStack(SOUL_STAINED_STEEL_HELMET.get()), new ItemStack(SOUL_STAINED_STRONGHOLD_HELMET.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 4), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 4), new ItemStack(EARTH_SPIRIT_SPLINTER.get(), 8), new ItemStack(ELDRITCH_SPIRIT_SPLINTER.get(), 2)))
                .addPage(new SpiritInfusionPage(new ItemStack(SOUL_STAINED_STEEL_CHESTPLATE.get()), new ItemStack(SOUL_STAINED_STRONGHOLD_CHESTPLATE.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 4), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 4), new ItemStack(FIRE_SPIRIT_SPLINTER.get(), 8), new ItemStack(ELDRITCH_SPIRIT_SPLINTER.get(), 2)))
                .addPage(new SpiritInfusionPage(new ItemStack(SOUL_STAINED_STEEL_LEGGINGS.get()), new ItemStack(SOUL_STAINED_STRONGHOLD_LEGGINGS.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 4), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 4), new ItemStack(AIR_SPIRIT_SPLINTER.get(), 8), new ItemStack(ELDRITCH_SPIRIT_SPLINTER.get(), 2)))
                .addPage(new SpiritInfusionPage(new ItemStack(SOUL_STAINED_STEEL_BOOTS.get()), new ItemStack(SOUL_STAINED_STRONGHOLD_BOOTS.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 4), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 4), new ItemStack(WATER_SPIRIT_SPLINTER.get(), 8), new ItemStack(ELDRITCH_SPIRIT_SPLINTER.get(), 2)))
                .addLink(soulStainedSteel).addLink(eldritchSpirit);
    
        addEntries(hallowedGold, soulStainedSteel, ether, basicBaubles, elixirOfLife, voidBerries, abstruseBlock, imperviousRock, imperviousSeal, archangelRing, livingCapacitor, soulStainedStrongholdArmor);
    }
}
