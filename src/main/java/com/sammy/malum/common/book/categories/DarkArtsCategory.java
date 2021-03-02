package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.CraftingPage;
import com.sammy.malum.common.book.pages.HeadlineTextPage;
import com.sammy.malum.common.book.pages.SpiritInfusionPage;
import com.sammy.malum.common.book.pages.TextPage;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static com.sammy.malum.common.book.categories.DiscoveryCategory.*;

public class DarkArtsCategory extends BookCategory
{
    public static BookEntry ether;
    public static BookEntry elixirOfLife;
    public DarkArtsCategory()
    {
        super(MalumItems.HALLOWED_GOLD_INGOT.get().getDefaultInstance(), "dark_arts");
        Item EMPTY = Items.BARRIER;
        ether = new BookEntry(MalumItems.ORANGE_ETHER.get(), "ether")
                .addPage(new HeadlineTextPage("ether"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.GLOWSTONE_DUST, 4), new ItemStack(MalumItems.ORANGE_ETHER.get()), new ItemStack(MalumItems.FIRE_SPIRIT_SPLINTER.get(), 2)))
                .addPage(new CraftingPage(new ItemStack(MalumItems.ORANGE_ETHER_TORCH.get()), EMPTY, EMPTY, EMPTY, EMPTY, MalumItems.ORANGE_ETHER.get(), EMPTY, EMPTY, Items.STICK, EMPTY))
                .addPage(new CraftingPage(new ItemStack(MalumItems.ORANGE_ETHER_BRAZIER.get()), EMPTY, EMPTY, EMPTY, MalumItems.TAINTED_ROCK.get(), MalumItems.ORANGE_ETHER.get(), MalumItems.TAINTED_ROCK.get(), Items.STICK, MalumItems.TAINTED_ROCK.get(), Items.STICK))
                .addLink(fireSpirit);
                
        elixirOfLife = new BookEntry(MalumItems.ELIXIR_OF_LIFE.get(), "elixir_of_life")
                .addPage(new HeadlineTextPage("elixir_of_life"))
                .addPage(new SpiritInfusionPage(new ItemStack(MalumItems.SOLAR_SYRUP_BOTTLE.get()), new ItemStack(MalumItems.ELIXIR_OF_LIFE.get()), new ItemStack(MalumItems.LIFE_SPIRIT_SPLINTER.get(), 2)))
                .addLink(solarSap).addLink(lifeSpirit);
        addEntries(ether, elixirOfLife);
    }
}
