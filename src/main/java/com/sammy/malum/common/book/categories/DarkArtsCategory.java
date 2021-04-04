package com.sammy.malum.common.book.categories;

import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.pages.*;
import com.sammy.malum.core.modcontent.MalumRites;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static com.sammy.malum.common.book.categories.DiscoveryCategory.*;
import static com.sammy.malum.core.init.items.MalumItems.*;

public class DarkArtsCategory extends BookCategory
{
    public static BookEntry voodooMagic;
    public static BookEntry offensivePoppets;
    public static BookEntry defensivePoppets;
    public static BookEntry poppetOfUndying;
    public static BookEntry totemMagic;
    public static BookEntry poppetBlessings;
    public static BookEntry riteOfLife;
    public static BookEntry riteOfDeath;
    public static BookEntry riteOfMagic;
    public static BookEntry riteOfEarth;
    public static BookEntry riteOfFire;
    public static BookEntry riteOfAir;
    public static BookEntry riteOfWater;
    public static BookEntry riteOfGrowth;
    public static BookEntry riteOfDrought;
    public static BookEntry riteOfRain;
    public DarkArtsCategory()
    {
        super(POPPET.get().getDefaultInstance(), "dark_arts");
        Item EMPTY = Items.BARRIER;
    
        voodooMagic = new BookEntry(POPPET.get(), "voodoo_magic")
                .addPage(new HeadlineTextPage("voodoo_magic"))
                .addPage(new SpiritInfusionPage(new ItemStack(Items.HAY_BLOCK), new ItemStack(POPPET.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 3), new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 3), new ItemStack(EARTH_SPIRIT_SPLINTER.get(), 3)))
                .addLink(lifeSpirit).addLink(earthSpirit).addLink(magicSpirit);
    
        offensivePoppets = new BookEntry(POPPET_OF_VENGEANCE.get(), "offensive_poppets")
                .addPage(new HeadlineTextPage("offensive_poppets"))
                .addPage(new ItemListPage(POPPET_OF_VENGEANCE.get(), POPPET_OF_DEFIANCE.get(), POPPET_OF_MISFORTUNE.get(), POPPET_OF_SAPPING.get()))
                .addPage(new HeadlineTextPage("poppet_of_vengeance"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_VENGEANCE.get()), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 8),new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 2),new ItemStack(EARTH_SPIRIT_SPLINTER.get(), 2)))
                .addPage(new HeadlineTextPage("poppet_of_defiance"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_DEFIANCE.get()), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 8),new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 4)))
                .addPage(new HeadlineTextPage("poppet_of_misfortune"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_MISFORTUNE.get()), new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 6),new ItemStack(WATER_SPIRIT_SPLINTER.get(), 6)))
                .addPage(new HeadlineTextPage("poppet_of_sapping"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_SAPPING.get()), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 4),new ItemStack(EARTH_SPIRIT_SPLINTER.get(), 4),new ItemStack(WATER_SPIRIT_SPLINTER.get(), 4)))
                .addLink(voodooMagic);
    
        defensivePoppets = new BookEntry(POPPET_OF_SHIELDING.get(), "defensive_poppets")
                .addPage(new HeadlineTextPage("defensive_poppets"))
                .addPage(new ItemListPage(POPPET_OF_SHIELDING.get(), POPPET_OF_FIRE.get(), POPPET_OF_EARTH.get()))
                .addPage(new HeadlineTextPage("poppet_of_shielding"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_SHIELDING.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 8),new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 2),new ItemStack(EARTH_SPIRIT_SPLINTER.get(), 2)))
                .addPage(new HeadlineTextPage("poppet_of_infernal_protection"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_FIRE.get()), new ItemStack(LIFE_SPIRIT_SPLINTER.get(), 6),new ItemStack(FIRE_SPIRIT_SPLINTER.get(), 6)))
                .addPage(new HeadlineTextPage("poppet_of_terran_protection"))
                .addPage(new SpiritInfusionPage(new ItemStack(POPPET.get()), new ItemStack(POPPET_OF_EARTH.get()), new ItemStack(DEATH_SPIRIT_SPLINTER.get(), 4),new ItemStack(EARTH_SPIRIT_SPLINTER.get(), 8)))
                .addLink(voodooMagic);
    
        poppetOfUndying = new BookEntry(POPPET_OF_UNDYING.get(), "poppet_of_undying")
                .addPage(new HeadlineTextPage("poppet_of_undying"))
                .addPage(new CraftingPage(POPPET_OF_UNDYING.get(), POPPET.get(), Items.TOTEM_OF_UNDYING))
                .addLink(voodooMagic);
    
        totemMagic = new BookEntry(TOTEM_CORE.get(), "totem_magic")
                .addPage(new HeadlineTextPage("totem_magic"))
                .addPage(new TextPage("totem_magic_part_two"))
                .addPage(new TextPage("totem_magic_part_three"))
                .addPage(new SpiritInfusionPage(new ItemStack(RUNEWOOD_LOG.get(), 4), new ItemStack(TOTEM_CORE.get()), new ItemStack(MAGIC_SPIRIT_SPLINTER.get(), 4)))
                .addLink(magicSpirit);
    
        poppetBlessings = new BookEntry(BLESSED_POPPET.get(), "poppet_blessings")
                .addPage(new HeadlineTextPage("poppet_blessings"))
                .addLink(totemMagic).addLink(voodooMagic);
    
        int riteIndex = 0;
        riteOfLife = new BookEntry(LIFE_SPIRIT_SPLINTER.get(), "rite_of_life")
                .addPage(new HeadlineTextPage("rite_of_life"))
                .addPage(new RitePage(MalumRites.RITES.get(riteIndex++)))
                .addLink(totemMagic).addLink(lifeSpirit).addLink(magicSpirit);
    
        riteOfDeath = new BookEntry(DEATH_SPIRIT_SPLINTER.get(), "rite_of_death")
                .addPage(new HeadlineTextPage("rite_of_death"))
                .addPage(new RitePage(MalumRites.RITES.get(riteIndex++)))
                .addLink(totemMagic).addLink(deathSpirit).addLink(magicSpirit);
    
        riteOfMagic = new BookEntry(MAGIC_SPIRIT_SPLINTER.get(), "rite_of_magic")
                .addPage(new HeadlineTextPage("rite_of_magic"))
                .addPage(new RitePage(MalumRites.RITES.get(riteIndex++)))
                .addLink(totemMagic).addLink(magicSpirit);
        
        riteOfEarth = new BookEntry(EARTH_SPIRIT_SPLINTER.get(), "rite_of_earth")
                .addPage(new HeadlineTextPage("rite_of_earth"))
                .addPage(new RitePage(MalumRites.RITES.get(riteIndex++)))
                .addLink(totemMagic).addLink(magicSpirit).addLink(earthSpirit);
        
        riteOfFire = new BookEntry(FIRE_SPIRIT_SPLINTER.get(), "rite_of_fire")
                .addPage(new HeadlineTextPage("rite_of_fire"))
                .addPage(new RitePage(MalumRites.RITES.get(riteIndex++)))
                .addLink(totemMagic).addLink(magicSpirit).addLink(fireSpirit);
    
        riteOfAir = new BookEntry(AIR_SPIRIT_SPLINTER.get(), "rite_of_air")
                .addPage(new HeadlineTextPage("rite_of_air"))
                .addPage(new RitePage(MalumRites.RITES.get(riteIndex++)))
                .addLink(totemMagic).addLink(magicSpirit).addLink(airSpirit);
    
        riteOfWater = new BookEntry(WATER_SPIRIT_SPLINTER.get(), "rite_of_water")
                .addPage(new HeadlineTextPage("rite_of_water"))
                .addPage(new RitePage(MalumRites.RITES.get(riteIndex++)))
                .addLink(totemMagic).addLink(magicSpirit).addLink(waterSpirit);
    
        riteOfGrowth = new BookEntry(Items.BONE_MEAL, "rite_of_growth")
                .addPage(new HeadlineTextPage("rite_of_growth"))
                .addPage(new RitePage(MalumRites.RITES.get(riteIndex++)))
                .addLink(totemMagic).addLink(lifeSpirit).addLink(earthSpirit);
    
        riteOfDrought = new BookEntry(Items.DEAD_BUSH, "rite_of_drought")
                .addPage(new HeadlineTextPage("rite_of_drought"))
                .addPage(new RitePage(MalumRites.RITES.get(riteIndex++)))
                .addLink(totemMagic).addLink(deathSpirit).addLink(waterSpirit);
    
        riteOfRain = new BookEntry(Items.WATER_BUCKET, "rite_of_rain")
                .addPage(new HeadlineTextPage("rite_of_rain"))
                .addPage(new RitePage(MalumRites.RITES.get(riteIndex++)))
                .addLink(totemMagic).addLink(lifeSpirit).addLink(waterSpirit);
    
        addEntries(voodooMagic, offensivePoppets, defensivePoppets, poppetOfUndying, totemMagic, poppetBlessings, riteOfLife, riteOfDeath, riteOfMagic, riteOfEarth, riteOfFire, riteOfAir, riteOfWater, riteOfGrowth, riteOfDrought, riteOfRain);
    }
}
