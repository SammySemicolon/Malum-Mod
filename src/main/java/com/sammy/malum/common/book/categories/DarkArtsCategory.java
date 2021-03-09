package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.*;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static com.sammy.malum.common.book.categories.DiscoveryCategory.*;
import static com.sammy.malum.common.book.categories.TinkeringCategory.basicBaubles;
import static com.sammy.malum.core.init.MalumItems.*;

public class DarkArtsCategory extends BookCategory
{
    public static BookEntry voodooMagic;
    public static BookEntry poppetBelt;
    public static BookEntry offensivePoppets;
    public static BookEntry defensivePoppets;
    public static BookEntry totemMagic;
    public static BookEntry primeRites;
    public static BookEntry advancedRites;
    public static BookEntry poppetBlessings;
    public DarkArtsCategory()
    {
        super(POPPET.get().getDefaultInstance(), "dark_arts");
        Item EMPTY = Items.BARRIER;
    
        voodooMagic = new BookEntry(POPPET.get(), "voodoo_magic")
                .addPage(new HeadlineTextPage("voodoo_magic"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.HAY_BLOCK), new ItemStack(POPPET.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 3), new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 3), new ItemStack(EARTH_SPIRIT_SPLINTER.get(), 3)))
                .addLink(lifeSpirit).addLink(earthSpirit).addLink(magicSpirit);
    
        poppetBelt = new BookEntry(POPPET_BELT.get(), "poppet_belt")
                .addPage(new HeadlineTextPage("poppet_belt"))
                .addPage(new SpiritInfusionPage(new ItemStack(GILDED_BELT.get()), new ItemStack(POPPET_BELT.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 3), new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 3), new ItemStack(EARTH_SPIRIT_SPLINTER.get(), 3)))
                .addLink(basicBaubles).addLink(lifeSpirit).addLink(earthSpirit).addLink(magicSpirit);
    
        offensivePoppets = new BookEntry(POPPET_OF_VENGEANCE.get(), "offensive_poppets")
                .addPage(new HeadlineTextPage("offensive_poppets"))
                .addPage(new ItemListPage(POPPET_OF_VENGEANCE.get(), POPPET_OF_BLEEDING.get(), POPPET_OF_DEFIANCE.get(), POPPET_OF_MISFORTUNE.get(), POPPET_OF_SAPPING.get()))
                .addPage(new HeadlineTextPage("poppet_of_vengeance"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_VENGEANCE.get()), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 8),new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 2),new ItemStack(EARTH_SPIRIT_SPLINTER.get(), 2)))
                .addPage(new HeadlineTextPage("poppet_of_bleeding"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_BLEEDING.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 6),new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 6)))
                .addPage(new HeadlineTextPage("poppet_of_defiance"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_DEFIANCE.get()), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 8),new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 4)))
                .addPage(new HeadlineTextPage("poppet_of_misfortune"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_MISFORTUNE.get()), new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 6),new ItemStack(WATER_SPIRIT_SPLINTER.get(), 6)))
                .addPage(new HeadlineTextPage("poppet_of_sapping"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_SAPPING.get()), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 4),new ItemStack(EARTH_SPIRIT_SPLINTER.get(), 4),new ItemStack(WATER_SPIRIT_SPLINTER.get(), 4)))
                .addLink(voodooMagic);
    
        defensivePoppets = new BookEntry(POPPET_OF_SHIELDING.get(), "defensive_poppets")
                .addPage(new HeadlineTextPage("defensive_poppets"))
                .addPage(new ItemListPage(POPPET_OF_SHIELDING.get(), POPPET_OF_INFERNAL_PROTECTION.get(), POPPET_OF_TERRAN_PROTECTION.get()))
                .addPage(new HeadlineTextPage("poppet_of_shielding"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_SHIELDING.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 8),new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 2),new ItemStack(EARTH_SPIRIT_SPLINTER.get(), 2)))
                .addPage(new HeadlineTextPage("poppet_of_infernal_protection"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_INFERNAL_PROTECTION.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 6),new ItemStack(FIRE_SPIRIT_SPLINTER.get(), 6)))
                .addPage(new HeadlineTextPage("poppet_of_terran_protection"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_TERRAN_PROTECTION.get()), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 4),new ItemStack(EARTH_SPIRIT_SPLINTER.get(), 8)))
                .addLink(voodooMagic);
        
        addEntries(voodooMagic, poppetBelt, offensivePoppets, defensivePoppets);
    }
}
