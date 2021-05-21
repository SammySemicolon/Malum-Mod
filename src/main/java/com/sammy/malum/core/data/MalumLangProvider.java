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
        blocks.forEach(b ->
        {
            String name = b.get().getTranslationKey().replaceFirst("block.malum.", "");
            name = MalumHelper.toTitleCase(specialBlockNameChanges(name), "_");
            add(b.get().getTranslationKey(), name);
        });

        items.forEach(i ->
        {
            String name = i.get().getTranslationKey().replaceFirst("item.malum.", "");
            name = MalumHelper.toTitleCase(specialBlockNameChanges(name), "_");
            add(i.get().getTranslationKey(), name);
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
        addPage("basics_of_magic", "Within our realm exists an arcane phenomenon; spirits. Spirits are a magical resource found primarily within a soul; the magical representation of a living being. This book will teach you everything you need to know about souls and spirits.");
        addPage("basics_of_magic_2", "When you kill a living being, you destroy it's body. Harvesting spirits requires you to go a step further, not only slay their body but also shatter their soul. Harvesting spirits is the first step in your journey to becoming a warlock.");

        addPage("runewood_trees", "Throughout various plains and forests a strange tree grows; the runewood tree. The tree itself is a perfect mix of nature and magic, the bark of the wood makes a great magical supplement.");

        addPage("solar_sap", "Within the runewood tree flows a fiery sap known as solar sap. This rejuvenating substance is gathered by stripping the bark of a runewood tree, collect with a bottle.");

        addPage("blazing_quartz", "Within the hellish plane many know as the nether exists many bizarre oddities. One of them is the blazing quartz, a fiery gem that makes for a great furnace fuel.");

        addPage("soulstone", "Within the deepest layers of deepslate, in the darkest corners of our world exists a pristine gem, soulstone. This soul reactive crystal will be your start to evil schemes.");

        addPage("spirit_harvesting", "\"The soul rules over it's body from a fortress of bone, learning of the world around it through fleshy portals\". When you slay an enemy with a scythe, you destroy the body and shatter the soul.");
        addPage("spirit_harvesting_2", "A scythe is a powerful weapon with many uses. It's sharp blade allows it to hit many enemies at once, much alike a sword enchanted with sweeping edge. The simplest scythe is a crude one, made with iron ingots and a soulstone gem.");
        addPage("scythe_enchanting", "There is very little magic that doesn't revolve around spirits, the enchanting table is one of them. A scythe can be enchanted in various ways, providing it with many different buffs and perks.");

        addPage("haunting", "The Haunting enchantment will allow the scythe to deal additional magic damage on top of the regular, physical damage.");
        addPage("rebound", "The Rebound enchantment allows you to throw the scythe much like a boomerang, greatly extending your range for spirit harvesting.");
        addPage("spirit_plunder", "The Spirit Plunder enchantment will yield extra spirits anytime a soul is shattered.");

        addPage("spirit_infusion", "The Spirit Altar is a warlock's crafting table. This wondrous workstation will allow you to infuse spirits into items through a process known as spirit infusion. The process takes a moderate amount of time.");
        addPage("spirit_infusion_2", "Spirit infusion requires a prime item, spirits and additional items. The prime item needs to be placed in the altar and surrounded with spirits. Additional items must be placed on surrounding item pedestals or stands.");
        addPage("hex_ash", "Hex ash is a haunted powder meant for various magics, most notably it can be used to speed up spirit infusion. Interacting with an active spirit altar will greatly greatly accelerate the infusion process.");

        addPage("tainted_rock", "\"A clean slate\". One of the simplest spirit infusions known revolves around the process of animating stone. Infusing cobblestone with holy and arcane spirits will result in tainted rock.");
        addPage("tainted_rock_architecture", "Using a crafting table or the stone cutter, you can make many variants of tainted rock.");

        addPage("twisted_rock", "\"Turned wicked\". If we are to replace the holy spirit with a wicked spirit, cobblestone will instead be animated into twisted rock.");
        addPage("twisted_rock_architecture", "Much alike the tainted rock, using a crafting table or the stone cutter, you can make many variants of twisted rock.");

        addPage("ether", "Think of a lit matchstick, but without the matchstick. Ether is just that! It's a magical substance that looks much like fire and shines bright forever. It can be put on a torch and a tainted rock brazier. It can also be colored with most dyes.");

        addPage("spirit_architecture", "Not only cobblestone can be infused into an arcane rock. Granite, Diorite and Andesite all have their magic counterparts.");

        addPage("soul_stained_steel", "Soul Stained Steel is a very strong magical metal, perfect for strong tools and armor. It's created by infusing iron with wicked and arcane spirit.");
        addPage("soul_stained_steel_gear", "Gear made from Soul Stained Steel is nearly as strong as gear made from diamond. It is also much more durable.");

        addPage("hallowed_gold", "Hallowed Gold is a very versitile metal meant for various arcane mechanisms and utilities. Most notably, spirits react to it strangely. It's created by infusing gold with holy and arcane spirit.");
        addPage("spirit_jar", "One of the simplest arcane utilities hallowed gold can provide is the spirit Jar. It is a block designed for storing large quantities of spirits. It stores a nearly infinite amount of just one spirit.");

        addPage("curios", "Through hallowed gold and soul stained steel you may create various gilded and ornate equipment to aid you in combat. These simple curios will provide a moderate boost to your armor and toughness respectively.");
        addPage("curios_2","Each one of these simple trinkets functions as a basis for spirit infusion. They can be shaped into many other accessories with various uses.");

        addPage("spirit_resonators", "As you delve deeper into spirit tinkering, you'll need more complex composite ingredients for your infusions and crafts. Spirit Resonators serve as a 'link' of sorts, they allow a mechanism or magic practitioner to work with spirits on a further level.");
        addPage("spirit_resonators_2", "There are two types of Spirit Resonators, Hallowed and Stained. Both are made with their respective metal and serve their own, similar purposes.");

        addPage("arcane_spoil_ring", "Harvesting Spirits may often yield insufficient result, even after a long night of hunting you might not have enough spirit spoils for your needs. The Arcane Spoils ring will increase how many spirits you get from spirit harvesting.");
        addPage("arcane_reach_ring", "Collecting harvested spirits can sometimes be a tiresome task. This simple yet practical trinket will greatly extend your spirit reach, allowing them to lock onto you from much further out.");

        addPage("radiant_soulstone", "A radiant soulstone is the penumbral form of a soulstone. Unlike a regular soulstone, it lacks impurities, possible cuts, or any other form of damage a soulstone may have. It's a perfect arcane craft.");
        addPage("tyrving", "The Tyrving is a type of twisted spell-blade designed to crush armor. It is very proficient at dealing with tough opponents, increasing in magic damage for each point of armor your enemy has.");

        addPage("stronghold_armor", "The Soul Stained Stronghold Armor Set is a perfect mix between magic and metallurgic strength. The set provides tons of resilience to it's bearer, much more than any other armor set available. Much like netherite, it provides knockback immunity.");

        addPage("holy_spirit", "The holy spirit is a symbol of the living. It contains many strange curative properties that will greatly aid your rise to power.");
        addPage("wicked_spirit", "The wicked spirit is a symbol of the reanimated. It's deathly properties sure are useful.");
        addPage("arcane_spirit", "The arcane spirit represents the supernatural. It's a very flexible magic, provides many uses.");
        addPage("eldritch_spirit", "From darkness a great power is born, the eldritch spirit represents that dark power. It's found only within the strangest of souls.");
        addPage("infernal_spirit", "Have you ever wondered how a blaze flies? The infernal spirit is what powers it and many more hellish creatures.");
        addPage("earthen_spirit", "The earthen spirit is like a magical molding clay found within those who are strong. It represents strength, strength of earth.");
        addPage("aquatic_spirit", "Magic of the elder guardians and their weaker siblings is a strong one. The water spirit can be found within all aquatic creatures.");
        addPage("aerial_spirit", "The nimble phantom and the agile arthropods share one thing in common. The aerial spirit is found within the quick.");


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