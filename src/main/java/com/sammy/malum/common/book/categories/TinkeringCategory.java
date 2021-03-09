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
    public static BookEntry hallowed_gold;
    public static BookEntry soul_stained_steel;
    public static BookEntry ether;
    public TinkeringCategory()
    {
        super(HALLOWED_GOLD_INGOT.get().getDefaultInstance(), "tinkering");
        Item EMPTY = Items.BARRIER;
        
        hallowed_gold = new BookEntry(HALLOWED_GOLD_INGOT.get(), "hallowed_gold")
                .addPage(new HeadlineTextPage("hallowed_gold"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.GOLD_INGOT, 2), new ItemStack(HALLOWED_GOLD_INGOT.get(), 2), new ItemStack(EARTH_SPIRIT_SPLINTER.get()), new ItemStack(FIRE_SPIRIT_SPLINTER.get()), new ItemStack(AIR_SPIRIT_SPLINTER.get()), new ItemStack(WATER_SPIRIT_SPLINTER.get())))
                .addPage(new CraftingPage(new ItemStack(HALLOWED_GOLD_NUGGET.get(), 9), EMPTY, EMPTY, EMPTY, EMPTY, HALLOWED_GOLD_INGOT.get(), EMPTY, EMPTY, EMPTY, EMPTY))
                .addPage(new CraftingPage(new ItemStack(HALLOWED_GOLD_BLOCK.get()), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_INGOT.get()))
                .addPage(new CraftingPage(new ItemStack(SPIRIT_JAR.get()), Items.GLASS_PANE, HALLOWED_GOLD_INGOT.get(), Items.GLASS_PANE, Items.GLASS_PANE, EMPTY, Items.GLASS_PANE, Items.GLASS_PANE, Items.GLASS_PANE, Items.GLASS_PANE))
                .addPage(new HeadlineTextPage("spirit_jar"))
                .addLink(earthSpirit).addLink(fireSpirit).addLink(airSpirit).addLink(waterSpirit);
        
        soul_stained_steel = new BookEntry(SOUL_STAINED_STEEL_INGOT.get(), "soul_stained_steel")
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
        
        addEntries(hallowed_gold, soul_stained_steel, ether);
    }
}
