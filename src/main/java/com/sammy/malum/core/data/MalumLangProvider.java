package com.sammy.malum.core.data;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.book.MalumBookCategories;
import com.sammy.malum.common.book.categories.BookCategory;
import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.entries.BookEntryGrouping;
import com.sammy.malum.common.book.pages.BookPage;
import com.sammy.malum.common.book.pages.TextPage;
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
        
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(MalumEnchantments.ENCHANTMENTS.getEntries());
        Set<RegistryObject<Effect>> effects = new HashSet<>(MalumEffects.EFFECTS.getEntries());
        ArrayList<MalumRites.MalumRite> rites = MalumRites.RITES;
        ArrayList<BookCategory> bookChapters = MalumBookCategories.categories;
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
                        if (page instanceof TextPage)
                        {
                            TextPage textPage = (TextPage) page;
                            add(textPage.text, MalumHelper.toTitleCase(textPage.text.substring("malum.gui.book.page.".length()), "_"));
                        }
                    }
                }
            }
        });
        add("malum.gui.book.page.ether.detailed", "Rawr x3 nuzzles how are you pounces on you you're so warm o3o notices you have a bulge o: someone's happy ;) nuzzles your necky wecky~ murr~ hehehe rubbies your bulgy wolgy you're so big :oooo rubbies more on your bulgy wolgy it doesn't stop growing ·///· kisses you and lickies your necky daddy likies (; nuzzles wuzzles I hope daddy really likes $: wiggles butt and squirms I want to see your big daddy meat~ wiggles butt I have a little itch o3o wags tail can you please get my itch~ puts paws on your chest nyea~ its a seven inch itch rubs your chest can you help me pwease squirms pwetty pwease sad face I need to be punished runs paws down your chest and bites lip like I need to be punished really good~ paws on your bulge as I lick my lips I'm getting thirsty. I can go for some milk unbuttons your pants as my eyes glow you smell so musky :v licks shaft mmmm~ so musky drools all over your cock your daddy meat I like fondles Mr. Fuzzy Balls hehe puts snout on balls and inhales deeply oh god im so hard~ licks balls punish me daddy~ nyea~ squirms more and wiggles butt I love your musky goodness bites lip please punish me licks lips nyea~ suckles on your tip so good licks pre of your cock salty goodness~ eyes role back and goes balls deep mmmm~ moans and suckles");
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