package com.sammy.malum.core.data;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.book.categories.BookCategory;
import com.sammy.malum.common.book.entries.BookEntry;
import com.sammy.malum.common.book.entries.BookEntryGrouping;
import com.sammy.malum.common.book.pages.BookPage;
import com.sammy.malum.common.book.pages.HeadlineTextPage;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.events.StartupEvents;
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
        addPage("sacred_spirit", "The sacred spirit is a symbol of the living, the untainted. It yields many curative abilities that will greatly aid your rise to power. It fills your mind with a strange sense of delight.");
        addPage("wicked_spirit", "Flip-side of the sacred, the wicked spirit is a symbol of the undead. It holds virulent properties, aches when touched even.");
        addPage("arcane_spirit", "The arcane spirit represents the supernatural. It's a very flexible magic, provides many uses.");
        addPage("eldritch_spirit", "Within our plane of existence you may often find, strange visitors. They hold powers that vastly surpass that of anything other. The eldritch spirit is found within souls of those from another realm.");
        addPage("infernal_spirit", "Have you ever wondered how a blaze flies? The infernal spirit is what powers it and many more nether-born creatures.");
        addPage("earthen_spirit", "Earthen spirit represents the strong, natural force of various earth wonders and magics. The earthen spirit is reminiscent of nature. It holds great strength within");
        addPage("aquatic_spirit", "Magic of the elder guardians and their weaker siblings is a strong one. The water spirit can be found within all aquatic creatures.");
        addPage("aerial_spirit", "The nimble phantom and the agile arthropods share one thing in common. The aerial spirit is found within the quick.");

        addPage("basics_of_magic", "Within our realm exists an arcane phenomenon; spirits. Spirits are a magical resource found primarily within a soul; the magical representation of a living being. The Encyclopedia Arcana will teach you everything you need to know about souls and spirits.");
        addPage("basics_of_magic_2", "When you kill a living being, you destroy it's body. To harvest spirits you need to go a step further, not only slay their body but also shatter their soul. Harvesting spirits is the most essential and first step towards mastering various soul manipulation magics.");

        addPage("runewood_trees", "Magic isn't limited just to souls and spirits, it appears that it comes in form of nature too. Throughout the world a strange tree grows; the runewood tree. The tree itself is a perfect mix of nature and magic, it's bark of the wood makes a great magical supplement.");

        addPage("solar_sap", "Solar Sap is a magical substance found within runewood. It can be exposed by stripping the bark of a runewood tree. Extract it using a glass bottle. By heating it at a furnace you can create solar syrup, a rejuvenating substance that will instantly recover health upon consumption.");

        addPage("blazing_quartz", "Within the hellish plane many know as the nether exist many bizarre oddities. One of them is the blazing quartz, a fiery gem that makes for a great furnace fuel.");

        addPage("soulstone", "Another natural example of magic is Soulstone, a soul reactive gem found deep underground and rarely on the surface. It's known to be able to draw out and even damage souls of your victims, this gem is filled with untapped evil.");

        addPage("spirit_harvesting", "\"The soul rules over it's body from a fortress of bone, learning of the world around it through fleshy portals\". The Scythe is a weapon that utilizes the soul reactive soulstone to hurt both the body and the soul at once. It serves as the simplest way to harvest spirits.");
        addPage("spirit_harvesting_2", "The sharp, long blade of any scythe allows it to hit many enemies at once, this effect is much more powerful that one of a sword. Applying sweeping edge will strengthen this effect even further. The simplest scythe is a crude one, made with iron ingots and a soulstone gem.");
        addPage("scythe_enchanting", "Spirit magic seems to have direct correlation to enchantment. Maybe it's due to the ties between experience and spirits, both found within souls. Regardless, there are many various enchantments applicable to scythes.");

        addPage("haunting", "The Haunting enchantment will allow the scythe to deal additional magic damage on top of the regular, physical damage.");
        addPage("rebound", "The Rebound enchantment allows you to throw the scythe much like a boomerang, greatly extending your range for spirit harvesting.");
        addPage("spirit_plunder", "The Spirit Plunder enchantment will yield extra spirits anytime a soul is shattered.");

        addPage("spirit_infusion", "The Spirit Altar provides the warlock with a means of forging complex components and magics. This wondrous workstation will allow you to infuse spirits into items through a process known as spirit infusion.");
        addPage("spirit_infusion_2", "Spirit Infusion is a rather long process, to start you'll need an item to focus your magic on, a prime item placed directly atop the altar. You'll also need spirits, these must also be input directly into the altar.");
        addPage("spirit_infusion_3", "Some Spirit Infusions also require additional reagents or components, these must be placed on nearby item providers, such as item pedestals or item stands.");
        addPage("hex_ash", "Hex ash is a haunted powder meant for various magics, most notably it can be used to speed up spirit infusion. Interacting with an active spirit altar will greatly greatly accelerate the infusion process.");

        addPage("tainted_rock", "\"A clean slate\". One of the simplest spirit infusions known revolves around the process of animating stone. Infusing cobblestone with sacred and arcane spirits will result in tainted rock.");
        addPage("tainted_rock_architecture", "Using a crafting table or the stone cutter, you can make many variants of tainted rock.");

        addPage("twisted_rock", "\"Turned wicked\". If we are to replace the sacred spirit with a wicked spirit, cobblestone will instead be animated into twisted rock.");
        addPage("twisted_rock_architecture", "Much alike the tainted rock, using a crafting table or the stone cutter, you can make many variants of twisted rock.");

        addPage("ether", "Think of a lit matchstick, but without the matchstick. Ether is just that! It's a magical substance that looks much like fire and shines bright forever. It can be put on a torch and a tainted rock brazier. It can be colored with most dyes and placed underwater.");

        addPage("sacrificial_dagger", "A scythe, while efficient at cutting through hordes of enemies, may prove to be quite ineffective at shattering just one soul. The sacrificial dagger is a fix to that problem, it's a simple dagger that can hit only one enemy.");

        addPage("spirit_architecture", "Not only cobblestone can be infused into an arcane rock. Granite, Diorite and Andesite all have their magic counterparts.");

        addPage("totem_magic", "The arcane properties of runewood is directly tied to spirits, making runewood a perfect candidate for totem magic; totem rites. Totem magic allows you to perform rituals known as spirit rites.");
        addPage("totem_magic_2", "To perform a spirit rite you will need a totem. A totem is made up of runewood logs supported by a totem base. Each spirit rite is represented by a set of spirits which must be engraved on runewood logs placed above your totem base. You can remove engraved spirits using an axe.");
        addPage("totem_magic_3", "Once your totem is ready, you may interact with the totem base in order to perform your desired spirit rite. Interacting with it again will cancel the rite. Spirit rites are either instant, or take effect over an indefinite amount of time. They're almost always confined to a limited area.");

        addPage("rite_of_growth", "The sacred spirit provides great rejuvenating, life bringing abilities for you to utilize. The rite of growth takes advantage of the sacred spirit to accelerate the growth of nearby plants and crops.");
        addPage("rite_of_death", "Through the use of the wicked spirit, the rite of death will quickly drain the life force of nearby living things. It attacks the body directly through magic.");
        addPage("rite_of_warding", "Earthen spirit combined with the arcane creates a really powerful spirit rite. The Rite of Warding will provide a strong shielding aura to those nearby.");
        addPage("rite_of_celerity", "The Rite of Celerity provides you with a strong agility boost. It also increases your reach distance by a short amount.");

        addPage("rune_table", "The rune table is a wide crafting station that utilizes a spirit rite to perform crafting. The multi-block has 3 slots for items, one on each of it's parts. Upon filling all needed slots, activate the rite of assembly to start crafting.");
        addPage("rite_of_assembly", "The Rite of Assembly allows an arcane practitioner to bring select nearby blocks into action. It is mostly needed to begin the crafting process on blocks such as the rune table.");

        addPage("creating_catalysts", "The Rune Table allows for quick and simple merging of items and spirits. Through this process, we can create quite a few rare items you may find required.");
        addPage("brilliance_confining", "Brilliance, otherwise known as experience is a strong magical representation of intelligence. Brilliance works, really similarly to how spirits work. Using the Rune Table we may artificially create Brilliance");
        addPage("creating_runes", "Utilizing the rune table once more, we create runes. A rune is a magical basis for spells, it can be shaped into an Effect Rune or Amplifier Rune. Assembling runes together at a rune table will allow you to make Spell Runes. This spell system is yet to be implemented.");

        addPage("soul_stained_steel", "Soul Stained Steel is a very strong magical metal, perfect for strong tools and armor. It's created by infusing iron with wicked and arcane spirit.");
        addPage("soul_stained_steel_gear", "Gear made from Soul Stained Steel is nearly as strong as gear made from diamond. It is also much more durable.");

        addPage("hallowed_gold", "Hallowed Gold is a very versatile metal meant for various arcane mechanisms and utilities. Most notably, spirits react to it strangely. It's created by infusing gold with sacred and arcane spirit.");
        addPage("spirit_jar", "One of the simplest arcane utilities hallowed gold can provide is the spirit Jar. It is a block designed for storing large quantities of spirits. It stores a nearly infinite amount of just one spirit.");

        addPage("curios", "Through hallowed gold and soul stained steel you may create various gilded and ornate equipment to aid you in combat. These simple curios will provide a moderate boost to your armor and toughness respectively.");
        addPage("curios_2","Each one of these simple trinkets functions as a basis for spirit infusion. They can be shaped into many other accessories with various uses.");

        addPage("spirit_resonators", "As you delve deeper into spirit tinkering, you'll need more complex composite ingredients for your infusions and crafts. Spirit Resonators serve as a 'link' of sorts, they allow a mechanism or magic practitioner to work with spirits on a further level.");
        addPage("spirit_resonators_2", "There are two types of Spirit Resonators, Hallowed and Stained. Both are made with their respective metal and serve their own, similar purposes.");

        addPage("arcane_spoil_ring", "Harvesting Spirits may often yield insufficient result, even after a long night of hunting you might not have enough spirit spoils for your needs. The Ring of Arcane Spoils will increase how many spirits you get from spirit harvesting.");
        addPage("arcane_spoil_ring_2", "This ring may bring up a good question, \"How can a soul shatter into more spirits than it holds\". Souls overtime adapt into the body they're placed into and therefore in a way 'grow spirits', using magic we can greatly accelerate this process. This however causes immense stress for the soul.");
        addPage("arcane_reach_ring", "Collecting harvested spirits can sometimes be a tiresome task. This simple yet practical trinket will greatly extend your spirit reach, allowing them to lock onto you from much further out.");
        addPage("ring_of_prowess", "Brilliance and Spirits are truly very close in their behavior and magic. You can even think of spirits as a 'wavelength' of brilliance, as if each spirit was just wisdom focused in a given concept or field. The Ring of Prowess will revert all collected spirits to confined brilliance.");

        addPage("tyrving", "The Tyrving is a type of twisted blade designed to crush armor. It is very proficient at dealing with tough opponents, increasing in damage for each point of armor your enemy has.");

        addPage("radiant_soulstone", "A radiant soulstone is the faultless form of a soulstone. Unlike a regular soulstone, it lacks impurities, possible cuts, or any other form of damage a soulstone may have. It's a perfect arcane craft.");
        addPage("stronghold_armor", "The Soul Stained Stronghold Armor Set is a perfect mix between magic and metallurgic strength. The set provides tons of resilience to it's bearer, much more than any other armor set available. Much like netherite, it provides knockback immunity.");

        addPage("cursed_nebulous", "Cursed Nebulous");

        addPage("awakened_tyrving", "Awakened Tyrving");

        add("malum.jei.spirit_infusion", "Spirit Infusion");
        add("malum.jei.spirit_rites", "Spirit Rites");
        add("malum.jei.rune_table", "Rune Table");

        add("itemGroup.malum", "Malum");
        add("itemGroup.malum_shaped_stones", "Malum Shaped Stones");
        add("itemGroup.malum_spirits", "Malum Spirits");
        add("itemGroup.malum_natural_wonders", "Malum Natural Wonders");

        add("enchantment.malum.haunting.desc", "Attacking enemies deals extra magic damage.");
        add("enchantment.malum.rebound.desc", "Allows the scythe to be thrown much like a boomerang, cooldown decreases with tier.");
        add("enchantment.malum.spirit_plunder.desc", "Increases spirit yields from each successful spirit harvest.");
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