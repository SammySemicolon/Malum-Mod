package com.sammy.malum.core.data;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.book.pages.BookPage;
import com.sammy.malum.common.book.pages.HeadlineTextPage;
import com.sammy.malum.core.modcontent.MalumBookCategories;
import com.sammy.malum.common.book.categories.BookCategory;
import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.entries.BookEntryGrouping;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.StartupEvents;
import com.sammy.malum.core.init.enchantments.MalumEnchantments;
import com.sammy.malum.core.modcontent.*;
import net.minecraft.block.Block;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.core.init.MalumItems.ITEMS;
import static com.sammy.malum.core.init.blocks.MalumBlocks.BLOCKS;

public class MalumLangProvider extends LanguageProvider
{
    public MalumLangProvider(DataGenerator gen)
    {
        super(gen, MalumMod.MODID, "en_us");
    }
    
    @Override
    protected void addTranslations()
    {
        StartupEvents.registerModContents(null);
        MalumBookCategories.init();
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(MalumEnchantments.ENCHANTMENTS.getEntries());
        Set<RegistryObject<Effect>> effects = new HashSet<>(MalumEffects.EFFECTS.getEntries());
        ArrayList<MalumRites.MalumRite> rites = MalumRites.RITES;
        ArrayList<BookCategory> bookChapters = MalumBookCategories.CATEGORIES;
        MalumHelper.takeAll(items, i -> i.get() instanceof BlockItem);
        MalumHelper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        blocks.forEach(b -> {
            String name = b.get().getTranslationKey().replaceFirst("block.malum.", "");
            name = MalumHelper.toTitleCase(specialBlockNameChanges(name), "_");
            add(b.get().getTranslationKey(), name);
        });
        
        items.forEach(i -> {
            if (!(i.get() instanceof BlockItem))
            {
                String name = i.get().getTranslationKey().replaceFirst("item.malum.", "");
                name = MalumHelper.toTitleCase(specialBlockNameChanges(name), "_");
                add(i.get().getTranslationKey(), name);
            }
        });
        
        enchantments.forEach(e -> {
            String name = MalumHelper.toTitleCase(e.getId().getPath(), "_");
            add(e.get().getName(), name);
        });
        
        effects.forEach(e -> {
            String name = MalumHelper.toTitleCase(e.getId().getPath(), "_");
            add("effect.malum." + e.get().getRegistryName().getPath(), name);
        });
        rites.forEach(r -> add(r.translationKey, MalumHelper.toTitleCase(r.identifier, "_")));
        bookChapters.forEach(r -> {
            add(r.translationKey, MalumHelper.toTitleCase(r.translationKey.substring("malum.gui.book.chapter.".length()), "_"));
            for (BookEntryGrouping grouping : r.groupings)
            {
                for (BookEntry entry : grouping.entries)
                {
                    add(entry.translationKey, MalumHelper.toTitleCase(entry.translationKey.substring("malum.gui.book.entry.".length()), "_"));
                    for (BookPage page : entry.pages)
                    {
                        if (page instanceof HeadlineTextPage)
                        {
                            HeadlineTextPage textPage = (HeadlineTextPage) page;
                            add(textPage.headline, MalumHelper.toTitleCase(textPage.headline.substring("malum.gui.book.page.headline.".length()), "_"));
                        }
                    }
                }
            }
        });
        add("malum.gui.book.page.arcane_basics", "Welcome to the Arcane Codex! Here you can delve into all sorts of sinister magics, starting with soul energy; naturally found in soul sand.");
        add("malum.gui.book.page.soul_sand", "This bizarre hellish sand only found in the darkest of places is a key component to many magical phenomena. Most notably, it's the key component to drawing out one's soul");
        add("malum.gui.book.page.redstone_dust", "This natural resource not only has great engineering capabilities, it also makes for a great magical reagent.");
    
        add("malum.gui.book.page.runewood_trees", "Magic isn't present only in certain resources, it can also be found in nature. A good example of that is the runewood tree, it can be found in forests and plains.");
        add("malum.gui.book.page.sun_kissed_leaves", "The sun kissed leaves of the runewood tree appear in a gradient. Their yellow-orange colors can be altered with the use of glowstone.");
        add("malum.gui.book.page.sun_kissed_grass", "Sun kissed grass can always be found surrounding runewood trees, it's bright yellow and quite tall. Additionally, lavender can be often found.");
    
        add("malum.gui.book.page.arcane_charcoal", "Runewood seems to react differently to heat than other wood types. Instead of creating normal charcoal, arcane charcoal is made. It can smelt three times as many items");
        add("malum.gui.book.page.blaze_quartz", "Blaze quartz is a strange gem only found in the nether realm. It makes for a great fuel source, smelting twice as many items as coal");
    
        add("malum.gui.book.page.solar_sap", "The runewood tree can often be found with sap in it, ready to be harvested. You can quite easily take it out with a bottle.");
        add("malum.gui.book.page.solar_sap_again", "Do note however, sap will not naturally regenerate inside of the tree. Sap can only be obtained by planting new trees.");
        add("malum.gui.book.page.solar_syrup", "By heating up sap in a furnace, you can create syrup. This tasty mixture will recover some of your health when consumed");
        add("malum.gui.book.page.solar_sapball", "When combining solar sap with slimeballs, solar sapballs will be made. They work just the same and can be used for whatever slimy needs you have.");
    
        add("malum.gui.book.page.unholy_blend", "Your dark journey into the unknown begins with this unholy powder. Soul sand and rotten flesh combined with redstone dust is the simplest way to create such magical phenomena");
        add("malum.gui.book.page.unholy_blend_again", "The unholy blend has many uses. Notably by exposing it to heat can create arcane grit, a more refined version of the powder.");
    
        add("malum.gui.book.page.soul_gem", "By combining diamonds with arcane grit, you can create this pristine gem. You can see the image of your soul inside of it if you look hard enough");
    
        add("malum.gui.book.page.spirit_basics", "In the world of magic exists a precious resource, some know it as Vis, some as Mana, Blood, Will. We know it as Spirits.");
        add("malum.gui.book.page.spirit_basics_part_two", "Spirits are a magical energy found primarily inside souls, they're like magical subcomponents of said soul. Every living being has a soul, some however are too complex to be easily turned into spirits");
        add("malum.gui.book.page.spirit_basics_part_three", "There are many types of spirits that each reside within different souls. Each have different properties and abilities, waiting to be uncovered.");
    
        add("malum.gui.book.page.spirit_harvesting", "Hunting for this resource is no easy task, it requires proper tools and knowledge. The simplest way of doing so is through the use of a scythe.");
        add("malum.gui.book.page.spirit_harvesting_part_two", "Scythes are powerful weapons, much like axes they are slow to use. In exchange they have great area of effect damage, perfect for use against tons of enemies.");
        add("malum.gui.book.page.spirit_harvesting_part_three", "Most notably however; upon slaying a living being it's soul will shatter into a few spirit splinters; the natural exposed form of a splinter. There's few creatures that are immune to this process");
    
        add("malum.gui.book.page.spirit_altar", "The spirit altar is the most essential tool in your journey of spirit magic. It holds slots for up to 5 spirit splinters and a single item. With this block you'll be able to create many new wonders.");
    
        add("malum.gui.book.page.tainted_rock", "Through the use of spirit infusion you can turn cobblestone into tainted rock. Tainted rock is a greater, magic reactive material perfect for your magic works.");
        add("malum.gui.book.page.darkened_rock", "If we replace the life spirit with a death spirit, darkened rock will be created instead. This material is harder and very blast resistant.");
        add("malum.gui.book.page.runewood_replication", "By infusing normal wood with earth and magic spirits, runewood will be created. This reaction can happen with logs, planks and saplings.");
    
        add("malum.gui.book.page.life_spirit", "Life is by far the most commonly present spirit of the three most basic spirits. It's life bringing capabilities are phenomenal, close to a miracle even.");
        add("malum.gui.book.page.death_spirit", "Death, something unavoidable. The death spirit represents the undead, strange beings found in this world that were somehow brought back to life.");
        add("malum.gui.book.page.magic_spirit", "The magic spirit shines brightly with potential. Anything born out of magic will have this spirit in their soul. Advanced magicians such as an evoker overtime have earned this spirit too.");
        add("malum.gui.book.page.earth_spirit", "Named after our planet, the earth spirit resides within metal. Anything even remotely associated with iron or copper will have earth in it's soul.");
        add("malum.gui.book.page.fire_spirit", "The fire spirit, reminiscent of the nether is also most commonly found there. The flame of the blaze burns forever due to this spirit.");
        add("malum.gui.book.page.air_spirit", "The air spirit represents agility, any nimble soul will have plenty of it. The spider for example; it's wall-climbing abilities are born from the air in it's soul.");
        add("malum.gui.book.page.water_spirit", "Water, found in every body, but so few souls. Any aquatic creature will contain the spirit; it can take any form, it's easy to manipulate. ");
        add("malum.gui.book.page.eldritch_spirit", "The mere thought of this spirit fills your mind with wicked thoughts. This powerful element is only found within the unexplainable, strange abominations brought into this realm.");
    
        add("malum.gui.book.page.ether", "Made from glowstone and infused with fire, ether is a magical flame that burns forever.  It can be dyed, placed in a brazier or on a torch.");
        
        add("malum.gui.book.page.elixir_of_life", "By infusing solar syrup with life spirits we can create the elixir of life. This wondrous cocktail will recover even more health and grant you the regeneration effect.");
    
        add("malum.subtitle.tainted_rock_break", "Tainted Rock Broken");
        add("malum.subtitle.tainted_rock_step", "Tainted Rock Footsteps");
        add("malum.subtitle.tainted_rock_place", "Tainted Rock Placed");
        add("malum.subtitle.tainted_rock_hit", "Tainted Rock Breaking");
    
        add("malum.subtitle.transmissive_alloy_break", "Transmissive Alloy Broken");
        add("malum.subtitle.transmissive_alloy_step", "Transmissive Alloy Footsteps");
        add("malum.subtitle.transmissive_alloy_place", "Transmissive Alloy Placed");
        add("malum.subtitle.transmissive_alloy_hit", "Transmissive Alloy Breaking");
    
        add("malum.subtitle.spirited_steel_break", "Spirited Steel Broken");
        add("malum.subtitle.spirited_steel_step", "Spirited Steel Footsteps");
        add("malum.subtitle.spirited_steel_place", "Spirited Steel Placed");
        add("malum.subtitle.spirited_steel_hit", "Spirited Steel Breaking");
    
        add("malum.subtitle.abstruse_block_return", "Abstruse Block Returns");
        add("malum.subtitle.taint_spread", "Taint Spreads");
        add("malum.subtitle.scythe_strike", "Scythe Cuts");
        add("malum.subtitle.spirit_harvest", "Spirit Harvested");
        add("malum.subtitle.spirit_collect", "Spirit Collected");
    
        add("malum.subtitle.spirit_kiln_consume", "Spirit Kiln Item Consumed");
        add("malum.subtitle.spirit_kiln_fail", "Spirit Kiln Processing Failed");
        add("malum.subtitle.spirit_kiln_finish", "Spirit Kiln Processing Finished");
        add("malum.subtitle.spirit_kiln_repair", "Spirit Kiln Repaired");
        add("malum.subtitle.spirit_kiln_fuel", "Spirit Kiln Fueled");
    
        add("malum.subtitle.totem_charge", "Totem Charges");
        add("malum.subtitle.totem_complete", "Totem Fully Charges");
    
        add("malum.subtitle.karmic_holder_activate", "Karmic Holder Activates");
        add("malum.subtitle.nexus_overload_jump", "Nexus Drive Booster");
    
        add("death.attack.bleeding", "%1$s bled out");
        add("death.attack.bleeding.player", "%1$s bled out whilst trying to escape %2$s");
        
        addTooltip("contains", "Contains: ");
        addTooltip("sneak", "Sneak");
        addTooltip("hold", "Hold: ");
        addTooltip("slot", "Slot");
        addTooltip("spirit", "Spirit");
        addTooltip("fuel", "Fuel: ");
        addTooltip("progress", "Progress: ");
        
        add("malum.jei.spirit_infusion", "Spirit Infusion");
        add("malum.jei.runic_chiseling", "Runic Chiseling");
        add("malum.jei.totem_rites", "Totem Rites");
        add("itemGroup.malum", "Malum");
        add("itemGroup.malum_building_blocks", "Malum Building Blocks");
    }
    
    @Override
    public String getName()
    {
        return "Malum Lang Entries";
    }
    
    public void addTooltip(String identifier, String tooltip)
    {
        add("malum.tooltip." + identifier, tooltip);
    }
    
    public String specialBlockNameChanges(String name)
    {
        if ((!name.endsWith("_bricks")))
        {
            if (name.contains("bricks"))
            {
                name = name.replaceFirst("bricks", "brick");
            }
        }
        if (name.contains("_fence") || name.contains("_button"))
        {
            if (name.contains("planks"))
            {
                name = name.replaceFirst("_planks", "");
            }
        }
        return name;
    }
}