package com.sammy.malum.core.data;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.book.MalumBookCategories;
import com.sammy.malum.common.book.categories.BookCategory;
import com.sammy.malum.common.book.pages.BookPage;
import com.sammy.malum.common.book.pages.BookPageGrouping;
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
        rites.forEach(
                r -> add(r.translationKey, MalumHelper.toTitleCase(r.identifier, "_"))
        );
        bookChapters.forEach(
                r -> {
                    add(r.translationKey, MalumHelper.toTitleCase(r.translationKey.substring("malum.gui.book.chapter.".length()), "_"));
                    for (BookPageGrouping grouping : r.groupings)
                    {
                        for (BookPage page : grouping.pages)
                        {
                            add(page.translationKey, MalumHelper.toTitleCase(page.translationKey.substring("malum.gui.book.page.".length()), "_"));
                        }
                    }
                }
        );
        add("malum.subtitle.tainted_rock_break" , "Tainted Rock Broken");
        add("malum.subtitle.tainted_rock_step" , "Tainted Rock Footsteps");
        add("malum.subtitle.tainted_rock_place" , "Tainted Rock Placed");
        add("malum.subtitle.tainted_rock_hit" , "Tainted Rock Breaking");
    
        add("malum.subtitle.transmissive_alloy_break" , "Transmissive Alloy Broken");
        add("malum.subtitle.transmissive_alloy_step" , "Transmissive Alloy Footsteps");
        add("malum.subtitle.transmissive_alloy_place" , "Transmissive Alloy Placed");
        add("malum.subtitle.transmissive_alloy_hit" , "Transmissive Alloy Breaking");
    
        add("malum.subtitle.spirited_steel_break" , "Spirited Steel Broken");
        add("malum.subtitle.spirited_steel_step" , "Spirited Steel Footsteps");
        add("malum.subtitle.spirited_steel_place" , "Spirited Steel Placed");
        add("malum.subtitle.spirited_steel_hit" , "Spirited Steel Breaking");
    
        add("malum.subtitle.abstruse_block_return" , "Abstruse Block Returns");
        add("malum.subtitle.taint_spread" , "Taint Spreads");
        add("malum.subtitle.scythe_strike" , "Scythe Cuts");
        add("malum.subtitle.spirit_harvest" , "Spirit Harvested");
        add("malum.subtitle.spirit_collect" , "Spirit Collected");
    
        add("malum.subtitle.spirit_kiln_consume" , "Spirit Kiln Item Consumed");
        add("malum.subtitle.spirit_kiln_fail" , "Spirit Kiln Processing Failed");
        add("malum.subtitle.spirit_kiln_finish" , "Spirit Kiln Processing Finished");
        add("malum.subtitle.spirit_kiln_repair" , "Spirit Kiln Repaired");
        add("malum.subtitle.spirit_kiln_fuel" , "Spirit Kiln Fueled");
    
        add("malum.subtitle.totem_charge" , "Totem Charges");
        add("malum.subtitle.totem_complete" , "Totem Fully Charges");
    
        add("malum.subtitle.karmic_holder_activate" , "Karmic Holder Activates");
        add("malum.subtitle.nexus_overload_jump" , "Nexus Drive Booster");
    
        add("death.attack.bleeding" , "%1$s bled out");
        add("death.attack.bleeding.player" , "%1$s bled out whilst trying to escape %2$s");
        
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