package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.*;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static com.sammy.malum.common.book.categories.DiscoveryCategory.*;
import static com.sammy.malum.core.init.MalumItems.*;

public class DarkArtsCategory extends BookCategory
{
    public static BookEntry ether;
    public static BookEntry elixirOfLife;
    public static BookEntry voodooMagic;
    public static BookEntry offensivePoppets;
    public static BookEntry defensivePoppets;
    public static BookEntry totemMagic;
    public static BookEntry primeRites;
    public static BookEntry advancedRites;
    public DarkArtsCategory()
    {
        super(HALLOWED_GOLD_INGOT.get().getDefaultInstance(), "dark_arts");
        Item EMPTY = Items.BARRIER;

        ether = new BookEntry(ORANGE_ETHER.get(), "ether")
                .addPage(new HeadlineTextPage("ether"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.GLOWSTONE_DUST, 4), new ItemStack(ORANGE_ETHER.get()), new ItemStack(FIRE_SPIRIT_SPLINTER.get(), 2)))
                .addPage(new CraftingPage(new ItemStack(ORANGE_ETHER_TORCH.get()), EMPTY, EMPTY, EMPTY, EMPTY, ORANGE_ETHER.get(), EMPTY, EMPTY, Items.STICK, EMPTY))
                .addPage(new CraftingPage(new ItemStack(ORANGE_ETHER_BRAZIER.get()), EMPTY, EMPTY, EMPTY, TAINTED_ROCK.get(), ORANGE_ETHER.get(), TAINTED_ROCK.get(), Items.STICK, TAINTED_ROCK.get(), Items.STICK))
                .addLink(fireSpirit);
        
        elixirOfLife = new BookEntry(ELIXIR_OF_LIFE.get(), "elixir_of_life")
                .addPage(new HeadlineTextPage("elixir_of_life"))
                .addPage(new SpiritInfusionPage(new ItemStack(SOLAR_SYRUP_BOTTLE.get()), new ItemStack(ELIXIR_OF_LIFE.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 2)))
                .addLink(solarSap).addLink(lifeSpirit);
        
        
        addEntries(ether, elixirOfLife);
    }
}
