package com.sammy.malum.core.data;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.cooler_book.CoolerBookEntry;
import com.sammy.malum.client.screen.cooler_book.CoolerBookScreen;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.enchantment.MalumEnchantments;
import com.sammy.malum.core.init.event.StartupEvents;
import com.sammy.malum.core.mod_content.MalumBookCategories;
import com.sammy.malum.core.mod_content.MalumRites;
import com.sammy.malum.core.mod_systems.rites.MalumRiteType;
import net.minecraft.block.Block;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WallSignBlock;
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
import static com.sammy.malum.core.init.block.MalumBlocks.BLOCKS;

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
        CoolerBookScreen.setupEntries();
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        Set<RegistryObject<SoundEvent>> sounds = new HashSet<>(SOUNDS.getEntries());
        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(MalumEnchantments.ENCHANTMENTS.getEntries());
        Set<RegistryObject<Effect>> effects = new HashSet<>(MalumEffects.EFFECTS.getEntries());
        ArrayList<CoolerBookEntry> coolerBookEntries = CoolerBookScreen.entries;
        ArrayList<MalumRiteType> rites = MalumRites.RITES;
        MalumHelper.takeAll(items, i -> i.get() instanceof BlockItem);
        MalumHelper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        MalumHelper.takeAll(blocks, i -> i.get() instanceof WallSignBlock);
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
        coolerBookEntries.forEach(b ->
        {
            String name = MalumHelper.toTitleCase(b.identifier, "_");
            add(b.translationKey(), name);
        });
        rites.forEach(r -> add("malum.gui.rite." + r.identifier, MalumHelper.toTitleCase(r.identifier, "_")));

        addHeadline("introduction", "Introduction");
        addPage("introduction_a", "Within this world exists an arcane phenomenon, spirits. A spirit is a magical property or element depending on how you look at it that makes up souls. The soul is an arcane image or representation of every sentient being. The body is the opposite, it's a physical vessel for the soul.");
        addPage("introduction_b", "Soul magic is a quite complex field with a simpler predecessor: spirit magic. Spirit magic is a much simpler and easier way to access most of the arcane phenomenon that derive from the soul. To put it short, soul magic almost entirely derives from the spirits within a soul and by extension spirits alone can be utilized for magic!");
        addPage("introduction_c", "Getting your power hungry hands on some magical spirits may not be so simple though. When a body dies it leaves behind various items and precious loot, likewise when a soul is shattered it bursts out into magical spirit. This brutal way of completely eradicating both the body and the soul from existence is the main way of obtaining spirits.");

        addHeadline("spirit_magics", "Basics of spirit magic");
        addPage("spirit_magics_a", "When a spirit is brought out of a soul it takes on a physical form, a crystal of sorts. Due to how spirits react to nearby souls, the spirit crystal will resonate with only your soul and be pulled towards you making it quite easy to collect. No one can steal your precious spirits, they resonate with your soul only.");
        addPage("spirit_magics_b", "Once in crystal form a spirit's energy can be easily released, it can naturally interact with the world around you, it can be infused into various catalysts and reagents through complex magic processes and more things you'll learn about soon enough. Do note though that if a spirit is left uncollected, it will dissolve into the space around it.");
        addPage("spirit_magics_c", "Spirits left uncollected can have a very strange impact on the world, there are many curiosities you may have already found that only exist due to some careless spirit hunter. Spirits can easily change the world around you.");

        addHeadline("soulstone", "Soulstone");
        addPage("soulstone_a", "The soulstone is a strange ore found both on the surface and in the deepest ends of every cave. The ore itself was created from carelessness, when a dissolved spirit merges with ore it'll shape the mineral to be something new. Soulstone is extremely valuable to any spirit hunter.");
        addPage("soulstone_b", "Soulstone is very precious as it is able to resonate and mangle with souls and spirits in various ways. This unique ability of the soulstone makes it the perfect mineral to create spirit hunting equipment out of. Any weapon made out of soulstone is most certainly sufficient for damaging an opponent's soul.");

        addHeadline("runewood", "Runewood");
        addPage("runewood_a", "The runewood tree is yet another result of spirits dissolving into nature. When an oak tree is touched by infernal or more rarely arcane spirit it quickly takes on a new appearance, it seems to appear most commonly within open plains and forests. Some call this type of tree arcane oak.");
        addPage("runewood_b", "Runewood is perfect for a magic conductor or relay of sorts, it works very well for any arcane craft really.");
        addHeadline("solar_sap", "Solar Sap");
        addPage("solar_sap_a", "The runewood tree often grows with a special sap in it! The mixture known as solar sap naturally flows through the arcane oak and can easily be harvested. To harvest sap you need to locate some sap filled runewood, strip the bark off with an axe and then use a bottle to store the sap.");
        addPage("solar_sap_b", "Solar sap has some select uses, mainly solar syrup and solar sapballs. Solar syrup is a delicious mixture you can obtain my heating up solar sap in a furnace. This rejuvenating drink will restore health atop hunger and provide a regenerative effect. Combining solar sap with slimeballs will yield sapballs, an alternative to slime.");
        addPage("solar_sap_c", "...There's probably some technology out there that would allow you to remove the magic from sapballs and turn them back into slime, best not to worry about that.");

        addHeadline("scythes", "Scythes!");
        addPage("scythes_a", "A scythe is a weapon of magic origin designed for cutting through hordes of enemies. The axe exchanges attack speed for extra damage, the scythe on the other hand trades it off for crowd control. Any attack done with the scythe will count as a stronger version of a sword's sweep attack.");
        addPage("scythes_b", "The scythe is also the most important tool of a spirit hunter, it's excellence at crowd control combined with it's ability to shatter souls makes it the perfect weapon for hunting spirits.");
        addPage("scythes_c", "The simplest scythe you can make uses iron for it's blade and a basic soulstone as a reagent. The scythe also accepts many exclusive enchantments!");

        addHeadline("haunted", "Haunted");
        addPage("haunted", "The haunted enchantment utilizes magic created when a soulstone is brought close to a hurt soul to deal extra magic damage to anyone you strike! Higher tiers hit with even stronger magic");

        addHeadline("spirit_plunder", "Spirit Plunder");
        addPage("spirit_plunder", "The spirit plunder enchantment forces the scythe's soulstone to utilize even more magic, any soul you shatter with this enchanted scythe will yield extra spirits. This may however damage the soulstone, taking extra durability off of your scythe.");

        addHeadline("rebound", "Rebound");
        addPage("rebound", "The rebound enchantment is by far the strangest one of them all. It grants you the ability to throw the scythe like a boomerang, allowing for a wacky ranged attack. Higher tiers allow you to throw the scythe more frequently.");

        addPage("sacred_spirit", "Sacred spirit is a symbol of the living, it pains wicked beings such as zombies or skeletons. It is a direct opposite of the undead, found only in pure and untainted souls. It yields many curative properties");
        addPage("wicked_spirit", "The enemy of the sacred, this spirit represents the undead. The undead are weird, bizarre beings. Their souls are basically in a state of limbo, stuck in a quickly decaying corpse or an animated pile of bones.");
        addPage("arcane_spirit", "Arcane spirit is the purest, most common form of spirit magics. This rather simple spirit is found within all sorts of creatures, there's 2 criteria. A given soul can either be born from magic, or actively use magic. Think of a skeleton or a witch.");
        addPage("eldritch_spirit", "The deeper you dive into various obscurities and phenomenon that seem completely separate from spirit magics, the more you realize how intertwined everything is. Eldritch spirits are found almost exclusively in the end and whatever comes out of it.");
        addPage("infernal_spirit", "The nether is an anomaly, yet it fits so well. Infernal spirit originates from hell itself, souls adapted to the extreme heat of hell all contain infernal spirit within them. Some rare outsiders have also grown fire immunity, it's unclear how.");
        addPage("earthen_spirit", "Earthen spirit represents nature, some select natural lifeforms all bear earthen spirit in their soul. Earthen spirit presence is most commonly connected with some form of growth or life, like cows producing milk or sheep growing wool.");
        addPage("aquatic_spirit", "The deep waters of our world are all but clear. Guardians for example, these weird spiked fish with thorns on their scales may have just mastered the use of aquatic spirit magics. Aquatic spirit is found within all sea life.");
        addPage("aerial_spirit", "Aerial spirit is found within the swift. Anything that roams the skies is bound to have their soul grow aerial spirit. Other land based beings capable of fast movement also develop aerial spirits. Think of foxes or phantoms.");

        addPage("basics_of_magic", "Within our realm exists an arcane phenomenon known as spirits. Spirits are a magical resource found primarily within a soul, the magical representation of a living being. Inside a soul they exist as a form of essence, well versed warlocks know it as \"essentia\".");
        addPage("basics_of_magic_2", "This essence is not enough for magics however, we can't utilize it until it's in a more physical state. Spirit magics are only accessible through an item form of spirits, known as a spirit splinter. Greater means of processing spirits through refining raw essence may be possible however.");
        addPage("basics_of_magic_3", "Like previously mentioned, spirits reside within souls. Which spirits a given soul contains determines on 2 things, the environment around the body and gathered expertise on various things. For example, a nether-born being would inherently have infernal spirit in their soul.");
        addPage("basics_of_magic_4", "A dedicated magic practitioner who has spent time mastering magics would have their soul develop arcane spirit parallel to their gathered wisdom. A witch is a good example of this. As your body and mind develops, your soul does just the same.");

        addPage("runewood_trees", "Spirit magics aren't limited just to souls and spirits, through a rare and unforeseen string of events a new type of tree was born. It's hard to tell how it first grew, overexposure to arcane spirit magic might be the most plausible scenario.");
        addPage("runewood_trees_2", "Runewood trees are an odd mixture of arcane spirit combined with what used to be a large oak tree. Due to this, runewood is a perfect basis for magic and is the basis of many arcane devices and various ritualistic appliances. Processing it in a furnace also yields a special, arcane charcoal.");

        addPage("solar_sap", "Solar Sap is a magical substance found naturally within runewood. Overtime as the tree grew more magical and abstract it developed this partially arcane sap. It's a very delicate, thick substance with extraordinary rejuvenating properties.");
        addPage("solar_sap_2", "Gathering this sap is a rather mundane process. First, the runewood bark must be stripped off of a log. If sap is exposed it then needs to be collected using a glass container, resulting in bottled solar sap.");
        addPage("solar_sap_3", "You may heat up bottled sap to turn it into solar syrup, a delicious substance that will instantly recover health as well as grant a regenerative effect. Sapballs can also be made from solar sap, they're a more efficient form of slimeballs.");

        addPage("blazing_quartz", "The nether is a strange place, there are many things found within that seemingly escape the realm of plausibility. It's hard to tell if it's due to natural or magical causes. The entirety of the nether could've been the work of just one warlock with way too much free time.");
        addPage("blazing_quartz_2", "Regardless of why it's so bizarre, there are many handy resources found inside. One that you may not be familiar with is blazing quartz, an inferno infused form of quartz. It works wonders as a fuel source.");

        addPage("soulstone", "Another instance of magic mangled together with nature is soulstone. This gem is one of purely magical origin, and is solely responsible for the discovery of spirit magics. Through sheer chance people discovered it's interactions with souls.");

        addPage("spirit_harvesting", "The scythe is a strong weapon designed for dealing with large groups of enemies at a time. It is extremely powerful at doing just that, unparalleled to any other weapon. The simplest scythe is made with iron and soulstone.");
        addPage("spirit_harvesting_2", "Scythes aren't limited to just being a weapon however. Due to soulstone, scythes are able to harm both the body and the soul at the same time. When a soul \"dies\" alongside it's body, it is shattered. Soul shattering is the simplest way to obtain spirits.");
        addPage("scythe_enchanting", "Spirit magics are very close to experience, and furthermore enchantment. There are many enchantments applicable to scythes.");

        addPage("spirit_infusion", "The Spirit Altar is an essential altar meant for creating various complexities, components and magics. The altar uses a prime item, spirits and occasionally extra side-items placed on nearby item pedestals or stands. This arcane process is known as spirit infusion.");
        addPage("spirit_infusion_2", "Spirit infusion is a rather long process, it starts with a prime item placed right in the middle of your spirit altar. This item will be the center of your infusion, all extra spirits and items needed will be fused into the prime item. Spirits are also placed directly in the altar, surrounding the prime item.");
        addPage("spirit_infusion_3", "It is very important to understand the difference between spirits and items in this process. When supplied with a valid combination of prime item and spirits, the altar will start the infusion process. It will soon enough look and collect nearby items from valid item providers.");

        addPage("hex_ash", "Hex ash is a messy powder meant for various magic, some claim it to be possessed. It's most notable ability is to speed up the process of spirit infusion, greatly accelerating it. Interacting with an active spirit altar will speed it up.");

        addPage("tainted_rock", "One of the simplest infusions known results in tainted rock. The process is known as animating stone and can be performed likewise on granite, diorite and andesite. Infusing cobblestone with sacred and arcane spirit will yield tainted rock.");
        addPage("tainted_rock_architecture", "Using a crafting table or the stone cutter, you can make many variants of tainted rock.");

        addPage("twisted_rock", "When making tainted rock, if we are to replace the sacred spirit with a wicked one twisted rock will instead be made. This is a much stronger rock, perhaps evil wicked spirit is naturally stronger than sacred.");
        addPage("twisted_rock_architecture", "Much alike the tainted rock, using a crafting table or the stone cutter, you can make many variants of twisted rock.");

        addPage("ether", "Long gone are the days of nitor, however ether is much the same. It's a partially-translucent magic matter that looks much like fire and shines even brighter. It can be placed on a torch or a tainted rock blazier. It can also be dyed any combination of colors.");

        addPage("sacrificial_dagger", "A scythe is a very efficient tool at cutting through hordes of enemies. However, this perk may often become a detriment in certain situations. The sacrificial dagger will strike only one enemy at a time, allowing for easier, more precise soul shattering.");

        addPage("spirit_architecture", "Not only cobblestone can be infused into an arcane rock. Granite, Diorite and Andesite all have their magic counterparts.");

        addPage("totem_magic", "Totem magic is a subtype of spirit magic specifically revolving around ritual like effects and designs. These rituals are known as spirit rites, alternatively totem rites. Each spirit rite is represented by a list of spirits.");
        addPage("totem_magic_2", "To perform a spirit rite you must engrave these spirits onto runewood logs placed above a totem base. A rite can theoretically require a nearly infinite amount of spirits, however the tallest known rite only requires 5. If you make a mistake while engraving, use an axe to strip of the engraved spirit.");
        addPage("totem_magic_3", "Once your spirit rite is ready, you need to interact with the totem base in order to activate it. Each engraved spirit will light up one by one, as soon as the very last spirit is lit up your spirit rite will begin. Rites can be either instant or take effect over a prolonged period of time.");

        addPage("rite_of_growth", "A sacred spirit oriented rite known as the rite of growth is a very simple, yet handy arcane invention. The rite will accelerate growth of nearby plants in a short radius.");
        addPage("rite_of_death", "Taking advantage of thw wicked spirit, the rite of death quickly damages nearby living bodies. The target is attacked directly through magic, piercing right through armor.");
        addPage("rite_of_warding", "A vast majority of totem rites are centered around providing various buffs to those around it. The rite of warding for example greatly strengthens one's defensive abilities, as well as providing health regeneration.");
        addPage("rite_of_celerity", "The rite of celerity creates a strong aura around itself. Any entity found within will receive a strong increase in agility, as well as reach distance.");

        addPage("rune_table", "The rune table is a multi-block designed for simple and quick combining of items and spirits. It has three slots for ingredients, one on each of it's three parts. Assembling things together requires a rite of assembly to be performed nearby.");
        addPage("rite_of_assembly", "The Rite of Assembly allows an arcane practitioner to bring select nearby blocks into action. It is needed to begin the crafting process on blocks such as the rune table.");

        addPage("creating_catalysts", "The Rune Table allows for quick and simple merging of items and spirits. Through this process, we can create quite a few rare items you may find required.");
        addPage("brilliance_confining", "Brilliance, otherwise known as experience is a strong magical representation of wisdom. Brilliance works really similarly to how spirits do. Using the Rune Table we may artificially create Brilliance.");
        addPage("creating_runes", "Utilizing the rune table once more, we can create runes. A rune is a magical basis for spells, it can be shaped into an Effect Rune or Amplifier Rune. Assembling runes together at a rune table will allow you to make Spell Runes. This spell system is yet to be implemented.");

        addPage("soul_stained_steel", "Soul Stained Steel is a very strong magical metal perfect for creating strong tools, armor and various trinkets. The wicked metal is a successor to iron combined with wicked magics.");
        addPage("soul_stained_steel_gear", "Gear made from Soul Stained Steel is nearly as strong as diamond in terms of defense and damage, in exchange it is much more durable.");

        addPage("hallowed_gold", "Opposite of soul stained steel, hallowed gold is a very versatile holy metal meant for various arcane mechanisms and utilities. Both metals share an odd link with spirits allowing them to attract spirits rather easily, this effect is much more visible on hallowed gold.");
        addPage("spirit_jar", "One of the simplest arcane utilities hallowed gold can provide is the spirit Jar. It is a block designed for storing large quantities of spirits. It stores a nearly infinite amount of just one spirit.");

        addPage("curios", "The two spirit metals, hallowed gold and soul stained steel can be turned into quite a few gilded or ornate trinkets. They're simple curios that provide a small boost to your defense and toughness, they also function as a basis for spirit infusion.");
        addPage("curios_2", "Every single one of these simple trinkets functions as a basis for spirit infusion, they can be shaped into many other accessories with various magical uses.");

        addPage("spirit_resonators", "As you delve deeper into spirit tinkering, you'll need more complex composite ingredients for your infusions and crafts. Spirit resonators serve as a 'link' of sorts, they allow a mechanism or magic practitioner to work with spirits on a further level.");
        addPage("spirit_resonators_2", "There are two types of spirit resonators, Hallowed and stained. Both are made with their respective metal and serve their own, similar purposes.");

        addPage("arcane_spoil_ring", "Harvesting spirits may often yield insufficient result, even after a long night of hunting you might not have enough spirit spoils for your needs. The ring of arcane spoils will increase how many spirits you get from spirit harvesting.");
        addPage("arcane_spoil_ring_2", "This ring may bring up a good question, \"How can a soul shatter into more spirits than it holds\". Souls overtime adapt into the body they're placed into and therefore in a way 'grow spirits', using magic we can greatly accelerate this process. To put it nicely, the soul will be upset.");
        addPage("arcane_reach_ring", "Collecting harvested spirits can sometimes be a tiresome task. This simple yet practical trinket will greatly extend your spirit reach, allowing nearby spirits to lock onto you from much further out.");
        addPage("ring_of_prowess", "Brilliance and Spirits are truly very close in their behavior and magic. You can even think of spirits as a wavelength of brilliance, as if each spirit was just wisdom focused in a given concept or field. The Ring of Prowess will revert all collected spirits to confined brilliance.");

        addPage("tyrving", "The tyrving is a twisted blade specifically made to deal with tough, armored opponents. It is very proficient at crushing through armor, for each point of defense your target has the damage of your tyrving will be increased.");

        addPage("radiant_soulstone", "A radiant soulstone is the faultless form of a soulstone. Unlike the regular soulstone, it lacks impurities. It's a much greater form of the regular soulstone required for complex magics.");
        addPage("stronghold_armor", "The Soul Stained Stronghold Armor Set is a perfect mix between magical and metallurgic strength. The set provides tons of resilience to it's bearer. It also grants knockback immunity.");

        addPage("cursed_nebulous", "The Cursed Nebulous, a perfected form of the nether star. This immensely powerful artifact is used in some of the most complex spirit infusions.");
        addPage("awakened_tyrving", "The awakened tyrving is an even stronger form of the tyrving. More features are planned for this sword so, expect some more cool text here! <3");

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
        add("malum.gui.book.entry.page.text." + identifier, tooltip);
    }
    public void addHeadline(String identifier, String tooltip)
    {
        add("malum.gui.book.entry.page.headline." + identifier, tooltip);
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