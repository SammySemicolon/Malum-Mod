package com.sammy.malum.core.data;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.book.categories.BookCategory;
import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.entries.BookEntryGrouping;
import com.sammy.malum.common.book.pages.BookPage;
import com.sammy.malum.common.book.pages.HeadlineTextPage;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.StartupEvents;
import com.sammy.malum.core.init.enchantments.MalumEnchantments;
import com.sammy.malum.core.modcontent.MalumBookCategories;
import net.minecraft.block.Block;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.core.init.items.MalumItems.ITEMS;
import static com.sammy.malum.core.init.MalumSounds.SOUNDS;
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
        Set<RegistryObject<SoundEvent>> sounds = new HashSet<>(SOUNDS.getEntries());
        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(MalumEnchantments.ENCHANTMENTS.getEntries());
        Set<RegistryObject<Effect>> effects = new HashSet<>(MalumEffects.EFFECTS.getEntries());
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

        sounds.forEach(s -> {
            String name = MalumHelper.toTitleCase(s.getId().getPath(), "_");
            add("malum.subtitle." + s.getId().getPath(), name);
        });
        enchantments.forEach(e -> {
            String name = MalumHelper.toTitleCase(e.getId().getPath(), "_");
            add(e.get().getName(), name);
        });

        effects.forEach(e -> {
            String name = MalumHelper.toTitleCase(e.getId().getPath(), "_");
            add("effect.malum." + e.get().getRegistryName().getPath(), name);
        });
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
        addPage("basics_of_magic", "Within our realm exists an arcane phenomenon; spirits. A spirit is found within a soul. The soul is one's magical representation. The Encyclopedia Arcana will be your guide for everything arcane.");
        addPage("basics_of_magic_2", "When you kill a living thing, it's body dies. To harvest spirits you must go a step further, not only slay their body but also shatter their soul. Shattering different souls will yield different spirit types.");

        addPage("runewood_trees", "Throughout plains and various forests a strange tree is often found; the runewood tree. It's wood makes a fine magical supplement. The runewood tree seems to react differently to heat than most, yielding different results.");

        addPage("solar_sap", "Within the runewood tree flows a fiery sap known as solar sap. This rejuvenating substance is gathered by stripping the bark of a runewood tree, collect with a bottle.");

        addPage("blazing_quartz", "Within the hellish plane many know as the nether exists many bizarre oddities such as the blazing quartz. This fiery gem makes for a great furnace fuel.");

        addPage("grimslate", "Within the deepest layers of deepslate, in the darkest corners of our world exists a pristine resource, grimslate. This soul reactive material will be your start to evil schemes.");

        addPage("spirit_harvesting", "\"The soul rules over it's body from a fortress of bone, learning of the world around it through fleshy portals\". When you slay an enemy with a scythe, you destroy the body and shatter the soul.");
        addPage("spirit_harvesting_2", "A scythe is a powerful weapon with many uses. It's sharp blade allows it to hit many enemies at once, much alike a sword enchanted with sweeping edge. The simplest scythe is a crude one, made with iron ingots and grimslate plating.");

        addPage("spirit_infusion", "The Spirit Altar is a warlock's crafting table. This wondrous workstation will allow you to infuse spirits into items. Often, additional items must be placed on nearby item providers. This process is known as spirit infusion.");
        addPage("hex_ash", "Hex ash is a fine powder often used for various rituals. It can also be used to speed up the process of spirit infusion. Interacting with an active spirit altar will greatly accelerate the infusion process.");

        addPage("tainted_rock", "\"A clean slate\" One of the simplest spirit infusions known revolves around the process of animating stone. Infusing cobblestone with life and magic will result in tainted rock. ");
        addPage("tainted_rock_architecture", "Using a crafting table or the stone cutter, you can make many variants of tainted rock.");

        addPage("twisted_rock", "\"Turned wicked\". If we are to replace the life spirit with one of death, cobblestone will instead be animated into twisted rock.");
        addPage("twisted_rock_architecture", "Much alike the tainted rock, using a crafting table or the stone cutter, you can make many variants of twisted rock.");

        addPage("ether", "\"The flame that burns Twice as bright burns half as long\". Ether is a magical substance that looks like flame, but shines bright forever. This pretty light source is a great arcane alternative to your regular coal based torches.");

        addPage("spirit_architecture", "The Spirit Altar proves to be one of the most useful wonders of spirit magic, allowing you to easily infuse items into other, more precious items.");

        addPage("holy_spirit", "The holy spirit is a symbol of the living. It contains many strange curative properties that will greatly aid your rise to power.");
        addPage("wicked_spirit", "The wicked spirit is a symbol of the reanimated. It's deathly properties sure are useful.");
        addPage("arcane_spirit", "The arcane spirit represents the supernatural. It's a very flexible magic, provides many uses.");
        addPage("eldritch_spirit", "From darkness a great power is born, the eldritch spirit represents that dark power. It's found only within the strangest of souls.");
        addPage("infernal_spirit", "Have you ever wondered how a blaze flies? The infernal spirit is what powers it and many more hellish creatures.");
        addPage("earthen_spirit", "The earthen spirit is like a magical molding clay found within those who are strong. It represents strength, strength of earth.");
        addPage("aquatic_spirit", "Magic of the elder guardians and their weaker siblings is a strong one. The water spirit can be found within all aquatic creatures.");
        addPage("aerial_spirit", "The nimble phantom and the agile arthropods share one thing in common. The aerial spirit is found within the quick.");

        add("malum.jei.totem_rites", "Totem Rites");
        add("malum.jei.spirit_altar", "Spirit Altar");
        add("itemGroup.malum", "Malum");
        add("itemGroup.malum_shaped_stones", "Malum Shaped Stones");
        add("itemGroup.malum_spirits", "Malum Spirits");
        add("itemGroup.malum_natural_wonders", "Malum Natural Wonders");
    }

    @Override
    public String getName()
    {
        return "Malum Lang Entries";
    }

    public void addPage(String identifier, String tooltip)
    {
        add("malum.gui.book.page." + identifier, tooltip);
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