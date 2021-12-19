package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.common.block.ether.WallEtherTorchBlock;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.registry.content.SpiritRiteRegistry;
import com.sammy.malum.core.registry.enchantment.MalumEnchantments;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import com.sammy.malum.core.registry.misc.EffectRegistry;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.core.registry.block.BlockRegistry.BLOCKS;
import static com.sammy.malum.core.registry.item.ItemRegistry.ITEMS;
import static com.sammy.malum.core.registry.misc.SoundRegistry.SOUNDS;

public class MalumLang extends LanguageProvider
{
    public MalumLang(DataGenerator gen)
    {
        super(gen, MalumMod.MODID, "en_us");
    }

    @Override
    protected void addTranslations()
    {
        ProgressionBookScreen.setupEntries();
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        Set<RegistryObject<SoundEvent>> sounds = new HashSet<>(SOUNDS.getEntries());
        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(MalumEnchantments.ENCHANTMENTS.getEntries());
        Set<RegistryObject<MobEffect>> effects = new HashSet<>(EffectRegistry.EFFECTS.getEntries());
        Set<RegistryObject<Attribute>> attributes = new HashSet<>(AttributeRegistry.ATTRIBUTES.getEntries());
        ArrayList<BookEntry> coolerBookEntries = ProgressionBookScreen.entries;
        ArrayList<MalumRiteType> rites = SpiritRiteRegistry.RITES;
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem);
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallEtherTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallSignBlock);
        blocks.forEach(b ->
        {
            String name = b.get().getDescriptionId().replaceFirst("block.malum.", "");
            name = DataHelper.toTitleCase(specialBlockNameChanges(name), "_");
            add(b.get().getDescriptionId(), name);
        });

        items.forEach(i ->
        {
            String name = i.get().getDescriptionId().replaceFirst("item.malum.", "");
            name = DataHelper.toTitleCase(specialBlockNameChanges(name), "_");
            add(i.get().getDescriptionId(), name);
        });

        sounds.forEach(s -> {
            String name = DataHelper.toTitleCase(s.getId().getPath(), "_");
            add("malum.subtitle." + s.getId().getPath(), name);
        });
        enchantments.forEach(e -> {
            String name = DataHelper.toTitleCase(e.getId().getPath(), "_");
            add(e.get().getDescriptionId(), name);
        });

        effects.forEach(e -> {
            String name = DataHelper.toTitleCase(e.getId().getPath(), "_");
            add("effect.malum." + e.get().getRegistryName().getPath(), name);
        });

        attributes.forEach(a -> {
            String name = DataHelper.toTitleCase(a.getId().getPath(), "_");
            add("attribute.name.malum." + a.get().getRegistryName().getPath(), name);
        });

        rites.forEach(r -> add(r.translationIdentifier(), DataHelper.toTitleCase(r.identifier, "_")));

        coolerBookEntries.forEach(b -> add(b.translationKey(), DataHelper.toTitleCase(b.identifier, "_")));

        addDescription("introduction", "Welcome to evil");
        addHeadline("introduction", "Introduction");
        addPage("introduction_a", "Within this Level, every living body has a soul. The body is a physical vessel that the soul occupies. The soul is one's consciousness and what animates the body. Both represent who you are, one physically, the other magically.");
        addPage("introduction_b", "The encyclopedia arcana is a book that documents all that is known about arcane presence within this Level. It focuses on a rather wicked form of arcane arts known as soul magic. It is a very cruel, inhumane and generally frowned upon study of arcana.");
        addPage("introduction_c", "In order to properly harness the unspoken power of soul magics, you'll have to the very center of it. At the very core of every soul rests untapped potential in form of what's known as a spirit - a fragment of potential.");
        addPage("introduction_d", "The magical presence of a soul derives almost entirely from the spirits found within it. They may be dormant, but they are there. Each different soul is one and only, made up of different quantities of different spirits.");

        addDescription("spirit_magics", "Spirit Splinters");
        addHeadline("spirit_magics", "Spirit Basics");
        addPage("spirit_magics_a", "\"How do I get my greedy little hands on a spirit?\" You might ask yourself. To start, you'll need to prepare yourself a trusty yet wicked weapon for your job. On top of that, it's important to know which souls you're gonna want to hunt down.");
        addPage("spirit_magics_b", "Each soul contains a specific set of spirits, different souls contain different combinations. When you set out on a hunt you'll have to carefully think about which of your prey will have exactly what you need.");
        addPage("spirit_magics_c", "When hunting spirits it is important to not leave any of them behind. Uncollected spirits will slowly dissolve into the nearby area and alter it into a new twisted one. Uncontrolled soul magic is extremely volatile.");

        addDescription("soulstone", "Altered Minerals");
        addHeadline("soulstone", "Soulstone");
        addPage("soulstone_a", "The soulstone is a strange ore found both on the surface and in the deepslate layer of your Level. The ore itself is created when wicked spirit is infused into carbon based minerals such as coal or diamond.");
        addPage("soulstone_b", "Sadly this process takes an unbelievably long time, it's nearly impossible to replicate. At the end of it, a soul-reactive material was brought into this Level. Due to it's origins, soulstone is a great catalyst for soul magics and other diabolical fields.");
        addPage("soulstone_c", "To be more specific, wicked spirit found within the soulstone is so concentrated  that when brought close to an arcane presence it can be easily damaged. Furthermore, any piece of gear made from soulstone will be able to damage souls.");

        addDescription("runewood", "Arcane oak");
        addHeadline("runewood", "Runewood");
        addPage("runewood_a", "Similarly to soulstone, the runewood tree is yet another result of uncontrolled spirit infusion. This time however the result is rather unexpected from soul magics. Apart from being a good conductor for arcana, the tree doesn't pose much of a threat at all. How boring.");
        addPage("runewood_b", "Runewood trees are created with arcane and sacred spirit, hence their tame nature. Unlike the soulstone, this process is rather quick in comparison and can easily be replicated.");
        addHeadline("arcane_charcoal", "Arcane Charcoal");
        addPage("arcane_charcoal", "Due to the magic origins of the arcane oak, charcoal created from runewood is extra efficient and can smelt many more items. Additionally, charcoal, coal, blazing quartz and arcane charcoal can be turned into tiny fragments which smelt just one or two items.");
        addHeadline("holy_sap", "Holy Sap");
        addPage("holy_sap_a", "Within the runewood trees a special type of sap is born. To get your hands on this holy extract you'll need an axe. Start by stripping off the bark of an exposed piece of wood, and then bottle up as much as you can get.");
        addPage("holy_sap_b", "Holy extract can be used for quite a few things. Firstly, by combining it with a slimeball you'll obtain sapballs. Since sapballs are equal to slimeballs the crafting process results in triple the slime.");
        addPage("holy_sap_c", "Secondly, you can create holy syrup by heating up your sap. Drinking this rejuvenating substance will replenish plenty of hunger and even cure you of some negative effects. Additionally if you drink it during day time some of your health will also be restored and you'll gain a regenerative effect.");
        addPage("holy_sap_d", "Try your best to not think about reverting sapballs into slimeballs using a certain thermal-series centrifugal separator.");

        addDescription("scythes", "Primitive Soul Shattering");
        addHeadline("scythes", "Scythes");
        addPage("scythes_a", "The scythe is a very popular tool amongst soul hunters. To start, it provides a strong sweeping attack, even more powerful than the sword. Perfect for clearing through hordes of prey. Additionally, and most importantly it damages the soul and body with each attack.");
        addPage("scythes_b", "When you damage a living body you destroy the vessel of a soul. However if you damage both the body and the soul you'll be able to completely eradicate your enemy, this way of mercilessly executing your enemy is known as shattering their soul and serves as the basic way of obtaining spirits.");
        addPage("scythes_c", "The most primitive scythe available is the crude one, made from iron and a single soulstone.");

        addHeadline("haunted", "Haunted");
        addPage("haunted", "When a soulstone is brought close to a soul it radiates a small amount of arcana. The haunted enchantment converts that magic energy into extra magic damage to anyone you strike. Higher tiers hit with even stronger magic.");

        addHeadline("spirit_plunder", "Spirit Plunder");
        addPage("spirit_plunder", "The spirit plunder enchantment forces the scythe's soulstone to utilize even more magic, any soul you shatter with this enchanted scythe will yield extra spirits. This may however damage the soulstone, taking extra durability off of your scythe.");

        addHeadline("rebound", "Rebound");
        addPage("rebound", "The rebound enchantment is by far the strangest one of all. It grants you the ability to throw the scythe like a boomerang, allowing for a wacky ranged attack. Higher tiers allow you to throw the scythe more frequently.");

        addDescription("spirit_infusion", "Creating Magical Wonders");
        addHeadline("spirit_infusion", "Spirit Infusion");
        addPage("spirit_infusion_a", "Spirit infusion is a crafting process based around infusing spirit arcana into various items, twisting them to fit your evil desires. Every infusion recipe requires a prime item as well as spirits to infuse the item with. These must all be placed right on the altar.");
        addPage("spirit_infusion_b", "Some recipes may also ask for additional reagents to be infused into the prime item. These extra items must be placed on nearby item holders, such as ones made from runewood. These can be placed anywhere as long as they're within 4 blocks of the altar.");
        addPage("spirit_infusion_c", "Once everything is setup correctly, spirit infusion will begin and one by one each additional ingredient will be absorbed into the prime item alongside needed spirits. Once this lengthy process is done your desired item will be created.");
        addHeadline("hex_ash", "Hex Ash");
        addPage("hex_ash", "Hex ash is a simple magical powder used in various infusions as a minor component. The ash can animate inanimate things, even bring them to life in some extreme cases.");

        addDescription("simple_spirits", "Primal Arcana");
        addHeadline("sacred_spirit", "Sacred Spirit");
        addPage("sacred_spirit_a", "Sacred spirit derives from two concepts, the pure and the holy. Sacred arcana is generally focused on various forms of healing and other curative effects.");
        addPage("sacred_spirit_b", "The sacred spirit is found mainly within passive, relaxed souls. Easy targets really.");

        addHeadline("wicked_spirit", "Wicked Spirit");
        addPage("wicked_spirit_a", "Opposite of the sacred, wicked spirit represents various impurities and dark magics. Even just touching this spirit creates a mild pain. Wicked magics can be really dangerous in the right hands.");
        addPage("wicked_spirit_b", "Generally when a vessel rots, the soul peacefully fades out into the afterlife. However, if the body is to be reanimated back to life through necromancy fast enough the soul and vessel may be one once more. This takes quite a toll on the soul however, twisting it into a wicked shape.");

        addHeadline("arcane_spirit", "Arcane Spirit");
        addPage("arcane_spirit_a", "The arcane spirit is arcana in it's purest form. Raw arcana is often needed to fully utilize potential found within other arcana, it has little power alone but can greatly amplify other magic.");
        addPage("arcane_spirit_b", "When a vessel is made, depending on it's design it's host soul will be born with matching spirits. A fiery soul would naturally be born with infernal spirit. However, spirits aren't always one and done. Think about a witch. Did the witch always posses magic abilities? Or did the witch learn arcana overtime.");
        addPage("arcane_spirit_c", "Just like in the case of the witch, a soul can adapt to constant arcana exposure and absorb a minuscule portion of it, overtime developing new spirit. Other examples of this feature the pillagers and guardians. Arcane spirit is found within the magically gifted.");

        addDescription("elemental_spirits", "Focused Arcana");
        addHeadline("earthen_spirit", "Earthen Spirit");
        addPage("earthen_spirit_a", "Earthen arcana stands for three things, power, earth and nature. The sorcery is incredibly potent even when used in small amounts.");
        addPage("earthen_spirit_b", "With the strength of the Level you can do quite a lot. For one, infusing earthen arcana into items is bound to make them more resistant than most. For two, earthen magic can be used to bring forth basic life. This potent wizardry is rather uncommon, only found within certain passive and sturdy souls.");
        addHeadline("infernal_spirit", "Infernal Spirit");
        addPage("infernal_spirit_a", "Hellish magic is incredibly dangerous, and by far the most diabolical. PLaying with fire is usually not a good idea, unless you're a soul hunter.");
        addPage("infernal_spirit_b", "Infernal arcana is great at many things: light, explosions, fire, the list goes on. The magic isn't more potent than a mere flame however it is overly present in certain places. You definitely know where to hunt.");
        addHeadline("aerial_spirit", "Aerial Spirit");
        addPage("aerial_spirit_a", "Aerial arcana, light and versatile, weak in effect yet full of utility and potential.");
        addPage("aerial_spirit_b", "Everyone would love to fly, it's very convenient. When studying the nimble phantom soul you may derive two things. For one, immense uncontrollable anger. For two, aerial magic can easily be used to aid your movement. Any swift soul is bound to contain aerial spirits within for you to steal.");
        addHeadline("aqueous_spirit", "Aqueous Spirit");
        addPage("aqueous_spirit_a", "Similar to the earthen arcana, aqueous magics are immensely powerful.");
        addPage("aqueous_spirit_b", "For uncertain reasons, aqueous magic is unbelievably abundant within the Level. The seas are filled with plenty of strange things, robot-like guardians, the drowned, various ruins and monuments, there's too much to count. While aqueous sorcery isn't too potent, it makes up for it with lots of use cases.");

        addDescription("arcane_rock", "Perfect for an Evil Lair");
        addHeadline("tainted_rock", "Tainted Rock");
        addPage("tainted_rock", "By infusing cobblestone with sacred and raw arcana a new stone with plenty of building options is created. Tainted rock is a simple magical building block with quite a few neat usages, it can also be shaped into item pedestals and stands.");
        addHeadline("twisted_rock", "Twisted Rock");
        addPage("twisted_rock", "If we are to replace sacred arcana with it's opposite the result will become a much gloomier one. Twisted rock is indifferent from it's holy variant minus the darker tone.");

        addDescription("ether", "The Sunset can't Compare");
        addHeadline("ether", "Ether");
        addPage("ether_a", "Nowadays, most magics offer you a unique source of light for all purpose use, spirit arcana is no different. Ether, known to some as Nitor is a magical flame that burns and shines bright forever. It can be placed on a torch or an arcane rock brazier which can be placed or hung.");
        addPage("ether_b", "Additionally, the ether flame can be dyed into practically any color. Simply combine the ether item with any combination of dyes and it's color will be altered! For technical reasons monochromatic dyes may yield possibly undesirable results without other dyes mixed in.");

        addHeadline("iridescent_ether", "Iridescent Ether");
        addPage("iridescent_ether_a", "In contrast to most other magical lights aside from pleasing the eye ether has a double flame colored variant, iridescent ether. This pristine form of ether allows you to dye the item once more to alter the ending color of the burn. Much like normal ether it can be placed on a brazier and a torch");
        addPage("iridescent_ether_b", "Getting just the right coloring for your light may be a bit tricky however. You cannot alter the original color of ether once it's transformed into it's iridescent variant, applying any dye at this stage will only change the second color.");

        addDescription("spirit_fabric", "Wicked Weaves");
        addHeadline("spirit_fabric", "Spirit Fabric");
        addPage("spirit_fabric", "Spirit fabric is a light cloth used for a few key spirit infusions and crafting recipes. The material is very sturdy and has a faint wicked scent.");
        addHeadline("spirit_pouch", "Spirit Pouch");
        addPage("spirit_pouch", "If you've ever wished for a spirit backpack of sorts, the spirit pouch is just that. The item allows you to store a huge amount of spirits within it's internal inventory. On top of that, all collected spirits are automatically transferred into the pouch if it's in your possession.");

        addDescription("soul_hunter_gear", "Scythe Expert");
        addHeadline("soul_hunter_armor", "Soul Hunter Armor");
        addPage("soul_hunter_armor", "The soul hunter set is an offense focused piece of equipment made from spirit fabric. At the cost of below average protection the armor set boosts magic and scythe damage dealt by the user.");

        addDescription("spirit_alchemy", "Primitive Witchcraft");
        addHeadline("spirit_alchemy", "Spirit Alchemy");
        addPage("spirit_alchemy", "You may often find yourself scavenging for simple physical ingredients while having too much raw arcana to spend. If that is the case, you may come to utilize spirit alchemy. By infusing a simple material with matching spirit arcana the material will be replicated.");

        addDescription("expanded_spirit_alchemy", "Wider Reach");
        addHeadline("expanded_spirit_alchemy", "Expanded Spirit Alchemy");
        addPage("expanded_spirit_alchemy", "Material replication with spirits works on more than just simple alchemical powder. You may also replicate simple gemstones and crystals, all be it at a larger cost of spirit arcana.");

        addDescription("focused_spirit_alchemy", "Raw Avarice");
        addHeadline("focused_spirit_alchemy", "Focused Spirit Alchemy");
        addPage("focused_spirit_alchemy", "By pushing material replication to it's limits you may manufacture even the most complex materials.");

        addDescription("spirit_metallurgy", "Arcane metals");
        addHeadline("hallowed_gold", "Hallowed Gold");
        addPage("hallowed_gold_a", "Gold is very often used as a basis for various magics, this is also the case with spirit arcana. Infusing arcane and sacred arcana into a gold ingot will imbue it with magic and yield a much more desirable magic metal. A few additional reagents are also needed for this process.");
        addPage("hallowed_gold_b", "While not too useful in evil schemes or crafting powerful gear, hallowed gold is a metal perfect for spirit manipulation and transfer.");
        addHeadline("spirit_jar", "Spirit Jar");
        addPage("spirit_jar", "The spirit jar is a simple craft. It's a placeable jar block that can store a really really large amount of a single spirit, very convenient to have next to a spirit altar. You can input and output spirits by right clicking, sneaking will take out an entire stack.");
        addHeadline("soul_stained_steel", "Soul Stained Steel");
        addPage("soul_stained_steel_a", "The sacred origins of hallowed gold make it nearly impossible to use for harm. Soul stained steel is nothing like that, it's a tough metal twisted evil beyond recognition. It excels at heartless crimes, perfect for various gear and trinkets.");
        addPage("soul_stained_steel_b", "Any piece of gear made from soul stained steel is capable of shattering souls. Both metals can also be used to create a type of magic transmitter. A spirit resonator, a more complex crafting component meant for manipulating in-Level spirits.");

        addDescription("soul_stained_gear", "Tinkering");
        addHeadline("soul_stained_scythe", "Soul Stained Scythe");
        addPage("soul_stained_scythe", "After some time using the crude scythe you may start wishing for an upgrade or a stronger version, just like the one made from soul stained steel. It is a direct upgrade with slightly increased physical damage and a bonus to magic damage triggered by the scythe.");
        addHeadline("soul_stained_armor", "Soul Stained Armor");
        addPage("soul_stained_armor", "Just like with the crude scythe, you're able to reinforce iron armor with soul stained steel. In addition to providing near diamond levels of protection the armor also shields you from magic damage. On top of that collecting spirits while wearing the armor set will reward you with a brief resistance effect.");

        addDescription("spirit_trinkets", "Forging");
        addHeadline("spirit_trinkets", "Spirit Trinkets");
        addPage("spirit_trinkets_a", "A trinket is a simple accessory that will provide you with helpful benefits, some know them as baubles or curios. Hallowed gold and soul stained steel both offer two basic trinkets that all serve as a basic shape for various spirit infusion to build upon.");
        addPage("spirit_trinkets_b", "Gilded trinkets provide boosts to armor while ornate ones grant you armor toughness.");

        addDescription("soul_hunter_trinkets", "Innovation");
        addHeadline("arcane_reach", "Ring of Arcane Reach");
        addPage("arcane_reach", "Catching lost spirits may often prove to be tedious or tiresome. By infusing some potent aerial arcana into a gilded ring the resulting trinket yields us a bonus to spirit reach. Nearby spirits will home-in onto you from further out.");
        addHeadline("arcane_spoil", "Ring of Arcane Spoil");
        addPage("arcane_spoil", "Your greed can at times be uncontrollable. You want everything, and more. The ring of arcane spoil will yield extra spirits when shattering a soul, it also poses a horrific thought. How is it that we can shatter a soul into more spirits than it holds?");

        addDescription("ring_of_prowess", "Curiosity");
        addHeadline("ring_of_prowess", "Ring of Prowess");
        addPage("ring_of_prowess_a", "There is another thing besides spirit arcana that the soul holds, it is known as brilliance, or experience. Unlike spirits, brilliance has strong enough ties to the body and some of it is naturally dispersed from the soul upon the defeat of the vessel.");
        addPage("ring_of_prowess_b", "As with all things, we can't let the soul keep anything for itself. With some clever use of raw arcana combined with natural brilliance we can leech off some extra experience as spirits leave the dying soul. The ring of prowess does just that.");

        addDescription("ring_of_curative_talent", "Arcane Vigor");
        addHeadline("ring_of_curative_talent", "Ring of Curative Talent");
        addPage("ring_of_curative_talent", "Tears of the ghast have some really strong curative properties. Combining one with some sacred arcana and infusing both into a gilded ring will yield us the ring of curative talent. This simple trinket will recover a large portion of your health when collecting spirits.");

        addDescription("ring_of_wicked_intent", "Arcane Rage");
        addHeadline("ring_of_wicked_intent", "Ring of Wicked Intent");
        addPage("ring_of_wicked_intent", "The wicked ring is a simple yet practical trinket that will greatly assist you in combat. Taking note from the powerful wither army the ring will react to nearby spirit arcana and reward you with strength and rage when collecting spirits.");

        addDescription("necklace_of_the_mystic_mirror", "Mirror Magic");
        addHeadline("necklace_of_the_mystic_mirror", "Mystic Mirror Necklace");
        addPage("necklace_of_the_mystic_mirror", "A whole lot of gear, trinkets and armor alike grant an effect when collecting spirits. By utilizing primitive mirror magic we can siphon some raw arcana off of your magic attacks and utilize it to trigger those effects. This necklace does just that.");

        addDescription("necklace_of_the_narrow_edge", "Tradeoff");
        addHeadline("necklace_of_the_narrow_edge", "Narrow Edge Necklace");
        addPage("necklace_of_the_narrow_edge", "The scythe sweep attack can be extremely useful in clearing through hordes of prey, however in other cases it can be seen as unnecessary or even impractical. This iron necklace will completely remove your scythe's sweep attack in exchange for a strong damage increase.");

        addDescription("3000_dollars_for_a_brewing_stand", "microwave to recharge");
        addHeadline("3000_dollars_for_a_brewing_stand", "even works while bended");
        addPage("3000_dollars_for_a_brewing_stand", "hey apple");

        add("malum.jei.spirit_infusion", "Spirit Infusion");
        add("malum.jei.spirit_rite", "Spirit Rite");
        add("malum.jei.rune_table", "Rune Table");

        add("itemGroup.malum", "Malum");
        add("itemGroup.malum_shaped_stones", "Malum Arcane Rocks");
        add("itemGroup.malum_spirits", "Malum Spirits");
        add("itemGroup.malum_natural_wonders", "Malum Natural Wonders");
        add("itemGroup.malum_vanity", "Malum Drip");

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
    public void addDescription(String identifier, String tooltip)
    {
        add("malum.gui.book.entry." + identifier + ".description", tooltip);
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