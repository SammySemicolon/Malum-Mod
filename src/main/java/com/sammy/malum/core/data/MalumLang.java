package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.common.block.ether.EtherWallTorchBlock;
import com.sammy.malum.common.item.spirit.SpiritJarItem;
import com.sammy.malum.core.setup.content.SpiritRiteRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.setup.content.DamageSourceRegistry;
import com.sammy.malum.core.systems.item.ISoulContainerItem;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.ortus.helpers.DataHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
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

import static com.sammy.malum.core.setup.content.AttributeRegistry.ATTRIBUTES;
import static com.sammy.malum.core.setup.content.potion.EffectRegistry.EFFECTS;
import static com.sammy.malum.core.setup.content.entity.EntityRegistry.ENTITY_TYPES;
import static com.sammy.malum.core.setup.content.SoundRegistry.SOUNDS;
import static com.sammy.malum.core.setup.content.block.BlockRegistry.BLOCKS;
import static com.sammy.malum.core.setup.content.item.MalumEnchantments.ENCHANTMENTS;
import static com.sammy.malum.core.setup.content.item.ItemRegistry.ITEMS;

public class MalumLang extends LanguageProvider {
    public MalumLang(DataGenerator gen) {
        super(gen, MalumMod.MALUM, "en_us");
    }

    @Override
    protected void addTranslations() {
        ProgressionBookScreen.setupEntries();
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        Set<RegistryObject<SoundEvent>> sounds = new HashSet<>(SOUNDS.getEntries());
        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(ENCHANTMENTS.getEntries());
        Set<RegistryObject<MobEffect>> effects = new HashSet<>(EFFECTS.getEntries());
        Set<RegistryObject<Attribute>> attributes = new HashSet<>(ATTRIBUTES.getEntries());
        Set<RegistryObject<EntityType<?>>> entities = new HashSet<>(ENTITY_TYPES.getEntries());
        ArrayList<BookEntry> coolerBookEntries = ProgressionBookScreen.ENTRIES;
        ArrayList<MalumRiteType> rites = SpiritRiteRegistry.RITES;
        ArrayList<MalumSpiritType> spirits = new ArrayList<>(SpiritTypeRegistry.SPIRITS.values());
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof EtherWallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallSignBlock);
        blocks.forEach(b ->
        {
            String name = b.get().getDescriptionId().replaceFirst("block.malum.", "");
            name = DataHelper.toTitleCase(correctBlockItemName(name), "_").replaceAll("Of", "of");
            add(b.get().getDescriptionId(), name);
        });
        DataHelper.getAll(items, i -> i.get() instanceof ISoulContainerItem || i.get() instanceof SpiritJarItem).forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("item.malum.", "").replaceFirst("block.malum.", "");
            String filled = "filled_" + name;
            add("item.malum." + filled, DataHelper.toTitleCase(filled, "_"));
        });
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem);
        items.forEach(i ->
        {
            String name = i.get().getDescriptionId().replaceFirst("item.malum.", "");
            name = DataHelper.toTitleCase(correctBlockItemName(name), "_").replaceAll("Of", "of");
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

        entities.forEach(e -> {
            String name = DataHelper.toTitleCase(e.getId().getPath(), "_");
            add("entity.malum." + e.get().getRegistryName().getPath(), name);
        });

        rites.forEach(r -> add(r.translationIdentifier(), DataHelper.toTitleCase(r.identifier, "_")));

        spirits.forEach(s -> add(s.getDescription(), DataHelper.toTitleCase(s.identifier + "_spirit", "_")));

        coolerBookEntries.forEach(b -> add(b.translationKey(), DataHelper.toTitleCase(b.identifier, "_")));

        addDescription("introduction", "Welcome to Malum");
        addHeadline("introduction", "Introduction");
        addPage("introduction_a", "Within our world, every living body has a soul. The soul is one's consciousness and what animates the body, a physical vessel for the arcane presence of a soul. Both represent who we are, one is an arcane image, while the other a physical fortress.");
        addPage("introduction_b", "The Encyclopedia Arcana is a book containing all my gathered research on everything that is known about the soul. To be more exact, this codex focuses on a rather wicked form of arcane arts known as soul magic. It is a very cruel, inhumane and generally frowned upon study of arcana.");
        addPage("introduction_c", "In order to properly harness the unspoken power of soul magics, you'll have to start with the basics. At the very core of every soul rests an untapped source of power in form of what's known as spirit arcana - fragments of potential.");
        addPage("introduction_d", "The power of a specific soul derives almost entirely from the spirits found within it's core. These spirits serve as the basis for the very existence of the arcane presence of a soul. Each different soul is one and only, powered by different combinations of eight known types of spirit.");

        addDescription("spirit_magics", "Spirit Splinters");
        addHeadline("spirit_magics", "Spirit Basics");
        addPage("spirit_magics_a", "\"How do I get my greedy little hands on a spirit?\" You might ponder. To start, you'll need to prepare yourself a trusty yet wicked weapon for your job. One capable of damaging the soul of your target.");
        addPage("spirit_magics_b", "On top of that, it's important to know which souls you're gonna want to hunt down. Depending on which spirits you need you'll be hunting for different creature, It's important to carefully think about which of your prey will have the spirits you're seeking out.");
        addPage("spirit_magics_c", "When hunting for spirits it is important to not leave any behind. Uncollected spirits will slowly decay into the nearby area, leaving their precious power wasted. Uncontrolled soul magic can be extremely dangerous, it is responsible for bringing forth various anomalies which are now present within our world.");

        addDescription("soulstone", "Altered Minerals");
        addHeadline("soulstone", "Soulstone");
        addPage("soulstone_a", "The soulstone is a strange mineral found both nearby the surface and in the deepslate layer of our world. The ore itself is created when wicked spirit is infused into carbon based minerals such as coal or diamond.");
        addPage("soulstone_b", "Sadly this process takes an unbelievably long time and furthermore it's nearly impossible to safely replicate in a reasonable amount of time, you'll have to find the mere leftovers of uncontrolled soul magic. Due to it's arcane origins, soulstone is a great catalyst for soul magics and other diabolical fields.");
        addPage("soulstone_c", "To be exact, wicked spirit is known for being especially capable of harming the soul, furthermore due to the high concentration of wicked arcana within the soulstone, any piece of gear augmented with the gem will be able to harm the soul.");

        addDescription("runewood", "Arcane oak");
        addHeadline("runewood", "Runewood");
        addPage("runewood_a", "Similarly to soulstone, the runewood tree is yet another result of uncontrolled soul magic. This time however the nature of the result is much less grim. These trees are known for their life giving sap and for their wood; a fine arcana conductor.");
        addPage("runewood_b", "Runewood trees are created with arcane and sacred spirit, hence their tame nature. Unlike the soulstone, this process is rather quick in comparison and can easily be replicated with a good understanding of soul magic.");
        addPage("runewood_c", "Additionally, item holders such as pedestals or stands can be created using runewood planks. These simple receptacles are often used as means of supplying your magical processes with ingredients.");
        addPage("runewood_d", "The runewood tree can be found commonly within various plains and fields alike, and also rarely within forest biomes.");
        addHeadline("arcane_charcoal", "Arcane Charcoal");
        addPage("arcane_charcoal", "Due to the esoteric origins of the arcane oak, charcoal created from runewood is extra efficient and can burn for twice as long. You may also turn it into several smaller pieces, much like with other fuels you're more accustomed to.");
        addHeadline("holy_sap", "Holy Sap");
        addPage("holy_sap_a", "Within the runewood trees a special type of sap is created. To get your hands on this holy extract you'll need an axe. Start by stripping off the bark of an exposed piece of runewood, and then collect it all with a glass bottle.");
        addPage("holy_sap_b", "Holy sap can be used for quite a few things. Firstly, by combining it with a slimeball you'll obtain several sapballs, which serve the same purpose.");
        addPage("holy_sap_c", "Secondly, you can create holy syrup by heating up your sap. Drinking this rejuvenating substance will replenish plenty of hunger and even provide you with a healing effect.");
        addPage("holy_sap_d", "Try your best to not think about reverting sapballs into their slimy precursor using a certain thermal-series centrifugal separator.");

        addDescription("scythes", "Shatter the Soul");
        addHeadline("scythes", "Scythes");
        addPage("scythes_a", "The scythe is a very popular tool amid soul hunters. For one, it provides a strong sweeping attack even more powerful than that of the sword. Secondly, due to the soulstone augmented into the scythe it's capable of damaging both the soul and body with each attack.");
        addPage("scythes_b", "When you damage a living body you destroy the vessel of a soul, allowing it to fade away peacefully into the afterlife. However when you slay a soul, you will shatter it alongside the death of the vessel. This merciless execution denies the soul eternal peace and is your key to obtaining your very first batch of spirit arcana.");
        addPage("scythes_c", "The most primitive scythe available is a crude one, made from iron and a single soulstone.");
        addHeadline("scythe_enchanting", "Scythe Enchanting");
        addPage("scythe_enchanting", "The scythe is much like any other weapon within your arsenal, it isn't just a tool for harnessing spirit arcana. Furthermore, you may enchant your scythe with several unique enchantments that enhance it's functionality.");

        addHeadline("haunted", "Haunted");
        addPage("haunted", "When a soul is hurt some of it's magic is lost. The haunted enchantment converts that magic energy into extra magic damage to anyone you strike. Higher tiers hit with even stronger magic.");

        addHeadline("spirit_plunder", "Spirit Plunder");
        addPage("spirit_plunder", "The spirit plunder enchantment forces the scythe's soulstone to utilize more of it's potential, any soul you shatter with this enchanted scythe will yield extra spirits. This may however damage the soulstone, taking extra durability off of your scythe.");

        addHeadline("rebound", "Rebound");
        addPage("rebound", "The rebound enchantment is by far the strangest one of all. It grants you the ability to throw the scythe like a boomerang, allowing for a wacky ranged attack. Higher tiers allow you to throw the scythe more frequently.");

        addDescription("spirit_infusion", "Creating Magical Wonders");
        addHeadline("spirit_infusion", "Spirit Infusion");
        addPage("spirit_infusion_a", "Spirit infusion is a crafting process based around infusing spirit arcana into various items, twisting them to fit your evil desires. Every infusion recipe requires a prime item as well as a set of spirits to merge with the target item. These must all be placed right on the altar.");
        addPage("spirit_infusion_b", "Some recipes may also ask for additional reagents to be infused alongside spirit arcana. These extra items must be placed on nearby item holders, such as ones made from runewood. These can be placed anywhere as long as they're within 4 blocks of the altar.");
        addPage("spirit_infusion_c", "Once everything is setup correctly, spirit infusion will begin and one by one each additional ingredient will be absorbed into the prime item alongside needed spirits. Once this lengthy process is done your desired item will be created.");
        addHeadline("hex_ash", "Hex Ash");
        addPage("hex_ash", "Hex ash is a simple magical powder used in various infusions as a minor component. The ash is known for it's ability to animate objects, even bring some to life in some extreme cases.");

        addDescription("primary_arcana", "Basic Spellforms");
        addHeadline("sacred_spirit", "Sacred Spirit");
        addPage("sacred_spirit_a", "Sacred spirit represents two main concepts, pure beings and holy origins. Sacred arcana is generally focused on various forms of healing and other curative effects.");
        addPage("sacred_spirit_b", "The sacred spirit is found mainly within passive, relaxed souls. Easy targets really.");

        addHeadline("wicked_spirit", "Wicked Spirit");
        addPage("wicked_spirit_a", "Flipside of the sacred, wicked spirit represents various impurities and dark magics. Even just touching this spirit creates a mild pain. Wicked magics can be really dangerous in the right hands.");
        addPage("wicked_spirit_b", "Generally when a vessel rots, the soul peacefully fades out into the afterlife. However, if the body is to be reanimated back to life through necromancy fast enough the soul and vessel may be one once more. This yields strange results on the soul, twisting it into a wicked form.");

        addHeadline("arcane_spirit", "Arcane Spirit");
        addPage("arcane_spirit_a", "The arcane spirit is magic in it's purest form. Raw arcana is often used to fully utilize potential found within other spirit magics, it has little power alone but serves as a strong amplifier.");
        addPage("arcane_spirit_b", "When a vessel is born, depending on it's design it's host soul will be born with matching spirits. A fiery creature would naturally be born with an infernal soul. However, spirits aren't always one and done. Think about a witch. Did the witch always posses magic abilities? Or did the witch learn arcana overtime.");
        addPage("arcane_spirit_c", "Just like in the case of the witch, a soul can adapt to constant arcana exposure and develop a minuscule portion of it, overtime bringing forth new spirit. Other examples of this phenomenon include the pillagers and guardians. Arcane spirit is found within the magically gifted, and those with an esoteric origin.");

        addDescription("elemental_arcana", "Focused Arcana");
        addHeadline("earthen_spirit", "Earthen Spirit");
        addPage("earthen_spirit_a", "Earthen arcana stands for three things, power, earth and nature. The sorcery is incredibly potent even when used in small amounts.");
        addPage("earthen_spirit_b", "With the strength of the world in your greedy hands you can do quite a lot. For one, infusing earthen arcana into items is bound to reinforce them. For two, advanced earthen magic can be used to bring forth new ground into the world. This potent wizardry is rather uncommon, only found within certain passive and sturdy souls.");
        addHeadline("infernal_spirit", "Infernal Spirit");
        addPage("infernal_spirit_a", "Hellish magic is incredibly dangerous, and by far the most diabolical. Playing with fire is usually not a good idea, unless you're a soul hunter.");
        addPage("infernal_spirit_b", "Infernal arcana is great at many things: light, explosions, fire, the list goes on. The magic isn't more potent on it's own than a mere flame however it is overly present in certain places. You definitely know where to hunt.");
        addHeadline("aerial_spirit", "Aerial Spirit");
        addPage("aerial_spirit_a", "Aerial arcana, light and versatile, weak in effect yet full of utility and potential.");
        addPage("aerial_spirit_b", "Many would love to explore the sky, it's an incredible fantasy. When studying the nimble phantom soul you may derive two things. For one, vexation at the night predator. For two, aerial magic can easily utilized in flight and other means of traversal. Any swift soul is bound to contain aerial spirits within for you to steal.");
        addHeadline("aqueous_spirit", "Aqueous Spirit");
        addPage("aqueous_spirit_a", "Similar to the earthen arcana, aqueous magics are immensely powerful.");
        addPage("aqueous_spirit_b", "For reasons unknown, the seas in our world are full of mysteries. They're filled with plenty of inexplicable things of unknown origin, automaton-like guardians, drowned risen from the undead, various ruins and monuments, there's too much we don't know. Aqueous sorcery is extremely potent, but usually hard to come by");

        addDescription("eldritch_arcana", "Abstract Arcana");
        addHeadline("eldritch_spirit", "Eldritch Spirit");
        addPage("eldritch_spirit_a", "While raw arcana functions as an amplifier, eldritch arcana works much like a strong catalyst. It is much more potent but incredibly rare.");
        addPage("eldritch_spirit_b", "There are many otherworldly things within realms known to us, for uncertain reasons powerful enough souls born in these alternate twisted dimensions have eldritch origin");

        addDescription("arcane_rock", "Perfect for a Crypt");
        addHeadline("tainted_rock", "Tainted Rock");
        addPage("tainted_rock", "By infusing the earth with sacred and raw arcana a new stone with plenty of building options is created. Tainted rock is a simple magical building block with quite a few neat usages, it can also be shaped into item pedestals and stands.");
        addHeadline("twisted_rock", "Twisted Rock");
        addPage("twisted_rock", "If we are to replace sacred arcana with it's opposite the result will become a much grimmer one. Twisted rock is indifferent from it's holy variant apart from the darker tone, however it is capable of suppressing magic.");

        addDescription("ether", "The Sunset can't Compare");
        addHeadline("ether", "Ether");
        addPage("ether_a", "Nowadays, most magics offer you a unique source of light for all purpose use, spirit arcana is no different. Ether, known to some as Nitor is a magical flame that burns and shines bright forever. It can be placed on a torch or a magical brazier which can be placed or hung.");
        addPage("ether_b", "Additionally, the ether flame can be dyed into practically any color. Simply combine the ether item with any combination of dyes and it's light will be altered. For technical reasons monochromatic dyes may yield possibly undesirable results without other dyes mixed in.");

        addHeadline("iridescent_ether", "Iridescent Ether");
        addPage("iridescent_ether_a", "While ether is exceptionally gorgeous, iridescent ether takes this glimmering shine a step further. This pristine form of ether allows you to dye the item once more to alter the ending color of the burn. Much like normal ether it can be placed on a brazier and a torch");
        addPage("iridescent_ether_b", "Getting just the right coloring for your light may be a bit tricky however. You cannot alter the original color of ether once it's transformed into it's iridescent variant, applying any dye at this stage will only change the second color.");

        addDescription("spirit_fabric", "Wicked Weaves");
        addHeadline("spirit_fabric", "Spirit Fabric");
        addPage("spirit_fabric", "Spirit fabric is a light cloth used for a few key spirit infusions and crafting recipes. The material is very sturdy and has a faint wicked scent.");
        addHeadline("spirit_pouch", "Spirit Pouch");
        addPage("spirit_pouch", "If you've ever found your inventory filled with spirits, the spirit pouch is just the thing you need. The item allows you to store a large amount of spirits within it's internal inventory. On top of that, any collected spirits are transferred into the pouch if it's in your possession.");

        addDescription("soul_hunter_gear", "Scythe Expert");
        addHeadline("soul_hunter_armor", "Soul Hunter Armor");
        addPage("soul_hunter_armor", "The soul hunter set is an offense focused set of equipment made from spirit fabric and leather armor. At the cost of below average protection the armor set boosts magic and scythe damage dealt by the user.");

        addDescription("spirit_focusing", "Focusing with Spirits");
        addHeadline("spirit_focusing", "Spirit Focusing");
        addPage("spirit_focusing_a", "The spirit crucible is a wondrous device created to operate on items using an alchemical process named spirit focusing. It revolves around a single key catalyst and the interactions of it and spirit arcana. One of the more common catalysts found in this magical process is an alchemical impetus.");
        addPage("spirit_focusing_b", "The impetus is a clay-made artifact of ancient design, it's very easy to shape it's earthen mass into new form. By utilizing spirits in the crucible you may shape some of the impetus' mass into something new at the cost of a slight toll on the clay artifact's durability.");

        addDescription("working_with_ashes", "Spirits to ashes, ashes to soot");
        addHeadline("working_with_ashes", "Working with Ashes");
        addPage("working_with_ashes", "With primitive spirit focusing you may successfully create simple powders often needed in all sorts of studies of arcana.");

        addDescription("crucible_acceleration", "Heating Up");
        addHeadline("crucible_acceleration", "Crucible Acceleration");
        addPage("crucible_acceleration_a", "The crucible is a rather heavy and slow piece of spirit engineering, and that is not without a reason. For you see; the impetus can be really easily damaged, the process of spirit focusing heavily relies on a slow and constant input of arcana to not cause unnecessary damage to the catalyst.");
        addPage("crucible_acceleration_b", "Increasing the rate at which arcana is streamed into the impetus would yield a faster result but it could lead to your catalyst getting damaged more than necessary. With that in mind the spirit catalyzer is a heavily augmented pedestal designed specifically for utilizing solid fuel to accelerate a nearby machination.");
        addPage("crucible_acceleration_c", "Each nearby spirit catalyzer will amplify the speed of the crucible at the cost of greater risk of catalyst instability, you may observe exponentially higher results with each new catalyzer; reaching the limit at 8.");

        addDescription("metallurgic_magic", "Focusing into hardware");
        addHeadline("metallurgic_magic", "Metallurgic Magic");
        addPage("metallurgic_magic_a", "With some clever tricks you may imbue your impetus with a metallic shell, when fed spirits it'll sprout a metallic node of your desired metal which you may then smelt directly into nuggets. Due to the more rigid material of the impetus, more durability is lost in the process.");
        addPage("metallurgic_magic_b", "It is speculated that these catalysts were previously used as some sort of ashen aspects in an infernal arcana focused alchemical exchange system. It might be possible to replicate the process by studying the history of infernal arcana.");

        addDescription("impetus_restoration", "Mending sorceries");
        addHeadline("impetus_restoration", "Impetus Restoration");
        addPage("impetus_restoration_a", "When an impetus sustains enough damage it will crack, becoming unusable for spirit focusing. To combat this issue, the crucible has a secondary function known to most as arcane restoration. This process expands on spirit focusing and flips it upside down.");
        addPage("impetus_restoration_b", "Instead of splitting off mass from our catalyst such as the impetus, arcane restoration utilizes spirit arcana to recover the form of your item. While repairing an impetus with the crucible alone is theorized to be possible, there's no reason not to take the easy way out.");
        addPage("impetus_restoration_c", "The twisted tablet is a simple augmentation of your usual item stand; the difference is that the tablet has the ability to channel spirit arcana without the need for an outside source to draw it out. It must be placed facing the crucible on a matching axis position.");
        addHeadline("expanded_focusing", "Expanded Restoration");
        addPage("expanded_focusing_a", "Furthermore, arcane restoration isn't restricted to just repairing your impetus; it extends much further. Using the restoring process you may repair any one of your damaged items.");
        addPage("expanded_focusing_b", "Each item will require a unique set of spirits that need to be slotted in the crucible as well as a valid repair ingredient aimed at the crucible in the twisted tablet.");
        addPage("expanded_focusing_c", "Additionally, gear closely connected with spirit arcana can be repaired more efficiently.");

        addDescription("crystal_creation", "Focusing into jewelry");
        addHeadline("crystal_creation", "Crystal Creation");
        addPage("crystal_creation", "By utilizing more complex combinations of spirits in spirit focusing you may naturally expect more complex results, this is very much the case. You may create various gems from all corners of your world using spirit focusing.");

        addDescription("spirit_metals", "Arcane metals");
        addHeadline("hallowed_gold", "Hallowed Gold");
        addPage("hallowed_gold_a", "Gold is very often used as a basis for various magics, this is also the case with spirit arcana. Infusing arcane and sacred arcana into a gold ingot will imbue it with magic and yield a much more desirable magic metal. A few additional reagents are also needed for this process.");
        addPage("hallowed_gold_b", "While not too useful in evil schemes or crafting powerful gear, hallowed gold is a metal perfect for spirit manipulation and transfer.");
        addHeadline("spirit_jar", "Spirit Jar");
        addPage("spirit_jar", "The spirit jar is a simple craft. It's a placeable jar block that can store a really really large amount of a single spirit, very convenient to have next to a spirit altar. You can input and output spirits by right clicking, sneaking will take out an entire stack.");
        addHeadline("soul_stained_steel", "Soul Stained Steel");
        addPage("soul_stained_steel_a", "The sacred origins of hallowed gold make it nearly impossible to use for harm. Soul stained steel is nothing like that, it's a tough metal twisted evil beyond recognition. It excels at heartless crimes, perfect for various gear and trinkets.");
        addPage("soul_stained_steel_b", "Any piece of gear made from soul stained steel is capable of shattering souls and deals both magic and physical damage. Both metals can also be used to create a type of magic transmitter. A spirit resonator, a more complex crafting component meant for manipulating in-world spirits.");

        addDescription("soul_stained_scythe", "Tinkering");
        addHeadline("soul_stained_scythe", "Soul Stained Scythe");
        addPage("soul_stained_scythe", "After some time using the crude scythe you may grow tired of the weak weapon. The soul stained steel scythe is a direct upgrade with overall higher damage distributed between physical and magic.");

        addDescription("soul_stained_armor", "Augmenting");
        addHeadline("soul_stained_armor", "Soul Stained Armor");
        addPage("soul_stained_armor_a", "Just like with the scythe, you're able to reinforce iron armor with soul stained steel. In addition to providing near diamond levels of protection the armor also shields you from magic damage. On top of that, the armor grants you a protective barrier known as soul ward.");
        addPage("soul_stained_armor_b", "Soul ward is a potent arcane shield designed for magic attacks. It will recover over time, and absorb almost all of magic damage taken as well as a small amount of physical damage. It's commonly used, and utilized by many trinkets.");
        addPage("soul_stained_armor_c", "Wearing an armor set made out of soul stained steel is not a sane thought, the nature of the metal would quickly rend the soul. To prevent this unfortunate scenario, the armor is built with a protective twisted rock layer underneath the dangerous steel.");

        addDescription("spirit_trinkets", "Forging");
        addHeadline("spirit_trinkets", "Spirit Trinkets");
        addPage("spirit_trinkets_a", "A trinket is a simple accessory that will provide you with helpful benefits, some know them as baubles or curios. Hallowed gold and soul stained steel both offer two basic trinkets that all serve as a basic shape for various spirit infusion to build upon.");
        addPage("spirit_trinkets_b", "Gilded trinkets provide boosts to armor while ornate ones grant you armor toughness.");
        addHeadline("arcane_reach", "Ring of Arcane Reach");
        addPage("arcane_reach", "Catching lost spirits may often prove to be tedious or tiresome. By infusing some potent aerial arcana into a gilded ring the resulting trinket yields us a bonus to spirit reach. Nearby spirits will home-in onto you from further out.");
        addHeadline("arcane_spoil", "Ring of Arcane Spoil");
        addPage("arcane_spoil", "Your greed can at times be uncontrollable. You want everything, and more. The ring of arcane spoil will yield extra spirits when shattering a soul, it also poses a horrific thought. How is it that we can shatter a soul into more spirits than it holds?");

        addDescription("ring_of_prowess", "Curiosity");
        addHeadline("ring_of_prowess", "Ring of Prowess");
        addPage("ring_of_prowess_a", "There is another thing besides spirit arcana that the soul holds, it is known as brilliance, or experience. Unlike spirits, brilliance has strong enough ties to the body and some of it is naturally dispersed from the soul upon the defeat of the vessel.");
        addPage("ring_of_prowess_b", "As with all things, we can't let the soul keep anything for itself. With some clever use of raw arcana combined with natural brilliance we can leech off some extra experience as spirits leave the dying soul. The ring of prowess does just that.");

        addDescription("warded_belt", "Immovable");
        addHeadline("warded_belt", "Warded Belt");
        addPage("warded_belt", "By taking the already armored design of a gilded belt and imbuing it with earthen arcana and a tainted rock reinforcement we can create a powerful belt which serves as a great protective piece of gear.");

        addDescription("ring_of_curative_talent", "Arcane Vigor");
        addHeadline("ring_of_curative_talent", "Ring of Curative Talent");
        addPage("ring_of_curative_talent", "Tears of the ghast have some really strong curative properties. Combining one with some sacred arcana and infusing both into a gilded ring will yield us the ring of curative talent. This simple trinket will recover a large portion of your health when collecting spirits.");

        addDescription("ring_of_wicked_intent", "Arcane Rage");
        addHeadline("ring_of_wicked_intent", "Ring of Wicked Intent");
        addPage("ring_of_wicked_intent", "The wicked ring is a simple yet practical trinket that will greatly assist you in combat. Taking note from the powerful wither army the ring will react to nearby spirit arcana and reward you with strength and rage when collecting spirits.");

        addDescription("necklace_of_the_mystic_mirror", "Reflection");
        addHeadline("necklace_of_the_mystic_mirror", "Mystic Mirror Necklace");
        addPage("necklace_of_the_mystic_mirror", "A whole lot of gear, trinkets and armor alike grant an effect when collecting spirits. By utilizing primitive mirror magic we can siphon some raw arcana off of your magic attacks and utilize it to trigger those effects. This necklace does just that.");

        addDescription("necklace_of_the_narrow_edge", "Tradeoff");
        addHeadline("necklace_of_the_narrow_edge", "Narrow Edge Necklace");
        addPage("necklace_of_the_narrow_edge", "The scythe sweep attack can be extremely useful in clearing through hordes of prey, however in other cases it can be seen as unnecessary or even impractical. This iron necklace will completely remove your scythe's sweep attack in exchange for a strong damage increase.");

        addDescription("mirror_magic", "Quick and Easy Transmission");
        addHeadline("mirror_magic", "Mirror magic");
        addPage("mirror_magic", "Coming soon!");

        addDescription("voodoo_magic", "Forbidden Arts");
        addHeadline("voodoo_magic", "Voodoo magic");
        addPage("voodoo_magic", "Coming soon!");

        addDescription("altar_acceleration", "Obelisks");
        addHeadline("runewood_obelisk", "Runewood Obelisk");
        addPage("runewood_obelisk", "If you've ever wished for a means of speeding up your spirit infusion, the runewood obelisk is perfect for your needs. It functions as a booster pillar of sorts, the spirit arcana imbued within allow it to accelerate the infusion process.");
        addHeadline("brilliant_obelisk", "Brilliant Obelisk");
        addPage("brilliant_obelisk", "If we replace the spirit resonator with a set of brilliance, the result instead becomes a brilliant obelisk. Each one of these boosts the power of a nearby enchantment table, reaching full efficiency with just three obelisks.");

        addDescription("totem_magic", "Spirit Rites");
        addHeadline("totem_magic", "Totem Magic");
        addPage("totem_magic_a", "The primary use of spirits tends to be selfish power, however spirit arcana isn't limited to just greed. By engraving spirits onto runewood above a totem base in a specific order you may create a spirit rite, a dark ritual that mangles with the nearby area.");
        addPage("totem_magic_b", "Each spirit rite starts at the totem base, above which you'll need to stack runewood logs. Once that is done, engrave the needed spirits onto the wood by simply right clicking it. Spirits can be scraped off by using an axe. When you lay out your rite correctly, activate the totem base.");
        addPage("totem_magic_c", "Individual spirit rites may not be within the range of another rite, however if the rite has a different spirit combination it may be brought closer.");

        addHeadline("rite_effect", "Rite Effect:");
        addHeadline("corrupted_rite_effect", "Corrupted Rite Effect:");

        addDescription("arcane_rite", "Corruption with Spirits");
        addHeadline("totem_corruption", "Totem Corruption");
        addPage("totem_corruption_a", "Raw arcana is often used to amplify other magics, it is usually used on within a spirit rite as a starting point of sorts. Now what if, we execute a rite made out of entirely arcane spirit, where would the built up magics go? The answer is corruption.");
        addPage("totem_corruption_b", "The uncontrolled and volatile result of arcane spirit channels all the built up power and spontaneously corrupts the totem, twisting the simple and organized runewood into a much grimmer new purple look. Any spirit rite performed on a soulwood totem will yield different effects.");
        addPage("arcane_rite", "The arcane rite yields the volatile result of uncontrolled spirit arcana, quickly engulfing the totem.");
        addPage("corrupted_arcane_rite", "With the totem pole already altered, the volatile arcane energies distribute into the area beneath instead.");
        addPage("totem_corruption_c", "You may also craft a corrupted totem base directly by changing the ingredients.");

        addDescription("sacred_rite", "Primitive Healing");
        addPage("sacred_rite", "The sacred rite is the simplest rite there is. It'll provide a weak healing effect to all nearby players.");
        addPage("corrupted_sacred_rite", "When laid on soulwood, healing becomes maturation. Nearby animals experience rapid aging until they're adult sized.");
        addPage("eldritch_sacred_rite", "When combined with eldritch magic, healing turns into growth. Nearby crops become more lively.");
        addPage("corrupted_eldritch_sacred_rite", "A soulwood totem will redirect sacred arcana into a relaxing effect, nearby animals will automatically breed.");

        addDescription("wicked_rite", "Automated Murder");
        addPage("wicked_rite", "The wicked rite is a harmful one. Nearby hostiles will slowly receive non-lethal magic damage.");
        addPage("corrupted_wicked_rite", "As if in perfect parity with it's natural counterpart, the corrupted wicked rite will shatter souls of almost-dead creatures.");
        addPage("eldritch_wicked_rite", "Adding in eldritch magic enhances the already dangerous rite and allows it to kill.");
        addPage("corrupted_eldritch_wicked_rite", "A very cruel rite. Nearby animals will be instantly and completely annihilated upon mating when there are too many.");

        addDescription("earthen_rite", "Rock and Stone!");
        addPage("earthen_rite", "The earthen rite provides one of many auras. Nearby players will receive increased armor.");
        addPage("corrupted_earthen_rite", "Fortitude turns to strength, nearby players will deal extra damage.");
        addPage("eldritch_earthen_rite", "This complex earthen rite will break nearby blocks below that match the block directly underneath the totem base.");
        addPage("corrupted_eldritch_earthen_rite", "Destruction turns into creation, the earth shifts and cobblestone is fabricated under the rite.");

        addDescription("infernal_rite", "Immense warmth");
        addPage("infernal_rite", "The infernal rite provides one of many auras. Nearby players will receive haste.");
        addPage("corrupted_infernal_rite", "Simple yet convenient, nearby players are granted immunity to fire.");
        addPage("eldritch_infernal_rite", "The impossible heat produced by infernal arcana is brought into motion, smelting blocks below the rite.");
        addPage("corrupted_eldritch_infernal_rite", "Bringing forth the nether, stone beneath turns into netherrack.");

        addDescription("aerial_rite", "Light Arcana");
        addPage("aerial_rite", "The aerial rite provides one of many auras. Nearby players will receive a boost to movement speed.");
        addPage("corrupted_aerial_rite", "The effect of the rite is altered, horizontal mobility turns into vertical agility.");
        addPage("eldritch_aerial_rite", "The rite is twisted into something greater, blocks beneath the rite will start to experience gravity.");
        addPage("corrupted_eldritch_aerial_rite", "Have you ever wondered what the clouds feel like?");

        addDescription("aqueous_rite", "Loyalty");
        addPage("aqueous_rite", "The aquatic rite provides one of many auras. Nearby players will receive a boost to reach.");
        addPage("corrupted_aqueous_rite", "Simple yet useful, nearby players are granted water breathing.");
        addPage("eldritch_aqueous_rite", "The advanced aqueous rite will motivate nearby pointed dripstone blocks and bring them into work, yielding even more liquid.");
        addPage("corrupted_eldritch_aqueous_rite", "The energizing effect is reversed, freezing over nearby water or lava instead.");

        addDescription("soulwood", "Twisted Roots");
        addHeadline("soulwood", "Soulwood");
        addPage("soulwood_a", "Spirit magics focus on turning everything good in the world to evil. Sometimes, by pure coincidence a great thing is created instead, as is the case with runewood. It is important to realize this mistake and correct it.");
        addPage("soulwood_b", "By utilizing the dangerous arcane spirit rite we can easily create soulwood, any nearby runewood block is a valid target for this transmutation. Much like it's name implies, soulwood has a strong connection to more direct soul magics. It'll be sure to come in use.");

        addDescription("tyrving", "Ancient Relic");
        addHeadline("tyrving", "Tyrving");
        addPage("tyrving_a", "The Tyrving is a rather esoteric blade. It's strange design makes it appear as a weak weapon not suited for combat. However, it's hex ash lining and twisted rock form cause it to deal extra magic damage to the soul, the greater the soul the more benefit.");
        addPage("tyrving_b", "The weapon can also be repaired using arcane restoration quite efficiently.");

        addDescription("magebane_belt", "Retaliation");
        addHeadline("magebane_belt", "Magebane Belt");
        addPage("magebane_belt", "By twisting a warded belt into it's rather sinister alter ego we may create the magebane belt. This alteration exchanges defense for offense, providing greater magic resistance and extra soul ward. Additionally, any damage absorbed by soul ward will make it's way to the inflicter.");

        addDescription("ceaseless_impetus", "Rebirth");
        addHeadline("ceaseless_impetus", "Ceaseless Impetus");
        addPage("ceaseless_impetus_a", "The totem of undying is a very interesting artifact sought out by many, it seems to be an effigy for some sort of greater god, an opposition to an undocumented evil it seems. By utilizing advanced sacred spirit arcana we can feed into this wonder and alter it's effect.");
        addPage("ceaseless_impetus_b", "In addition to a more sturdy design made out of hallowed gold, the ceaseless impetus allows for two uses before needing repair. This however results in the phoenix blessing effect being generally weaker, needing activations in quick succession to match it's former glory.");

        addDescription("huh", "microwave to recharge");
        addHeadline("the_device", "The Device.");
        addPage("the_device", "even works while bended");

        add("malum.spirit.description.stored_spirit", "Contains: ");
        add("malum.spirit.description.stored_soul", "Stores Soul With: ");

        add("malum.jei.spirit_infusion", "Spirit Infusion");
        add("malum.jei.spirit_focusing", "Spirit Focusing");
        add("malum.jei.spirit_repair", "Spirit Repair");
        add("malum.jei.spirit_rite", "Spirit Rites");
        add("malum.jei.block_transmutation", "Block Transmutation");

        add("itemGroup.malum", "Malum");
        add("itemGroup.malum_shaped_stones", "Malum: Arcane Rocks");
        add("itemGroup.malum_spirits", "Malum: Spirits");
        add("itemGroup.malum_natural_wonders", "Malum: Natural Wonders");
        add("itemGroup.malum_impetus", "Malum: Metallurgic Magic");

        add("enchantment.malum.haunted.desc", "Deals extra magic damage.");
        add("enchantment.malum.rebound.desc", "Allows the item to be thrown much like a boomerang, cooldown decreases with tier.");
        add("enchantment.malum.spirit_plunder.desc", "Increases the amount of spirits created when shattering a soul.");

        add("death.attack."+ DamageSourceRegistry.VOODOO_DAMAGE, "%s had their soul shattered");
        add("death.attack."+ DamageSourceRegistry.VOODOO_DAMAGE+".player", "%s had their soul shattered by %s");
        add("death.attack."+ DamageSourceRegistry.FORCED_SHATTER_DAMAGE, "%s had their soul shattered");
        add("death.attack."+ DamageSourceRegistry.FORCED_SHATTER_DAMAGE+".player", "%s had their soul shattered by %s");
        add("death.attack."+ DamageSourceRegistry.MAGEBANE_DAMAGE, "%s got too confident with a soul hunter");
        add("death.attack."+ DamageSourceRegistry.MAGEBANE_DAMAGE+".player", "%s got too confident with %s");
        add("death.attack."+ DamageSourceRegistry.SCYTHE_SWEEP_DAMAGE, "%s was sliced into two pieces");
        add("death.attack."+ DamageSourceRegistry.SCYTHE_SWEEP_DAMAGE+".player", "%s was sliced into two pieces by %s");

        addTetraMaterial("soul_stained_steel", "Soul Stained Steel");
        addTetraMaterial("hallowed_gold", "Hallowed Gold");
        addTetraMaterial("runewood", "Runewood");
        addTetraMaterial("soulwood", "Soulwood");
        addTetraMaterial("tainted_rock", "Tainted Rock");
        addTetraMaterial("twisted_rock", "Twisted Rock");
        addTetraMaterial("spirit_fabric", "Spirit Fabric");

        addTetraImprovement("malum.soul_strike", "Soul Strike", "Allows your item to shatter souls.");
    }

    @Override
    public String getName() {
        return "Malum Lang Entries";
    }

    public void addTetraMaterial(String identifier, String name) {
        add("tetra.material." + identifier, name);
        add("tetra.material." + identifier + ".prefix", name);
    }

    public void addTetraImprovement(String identifier, String name, String description) {
        add("tetra.improvement." + identifier + ".name", name);
        add("tetra.improvement." + identifier + ".description", description);
    }

    public void addTetraSocket(String identifier, String socket, String start, String end) {
        add("tetra.variant.double_socket/" + identifier, "Socket " + socket);
        add("tetra.variant.double_socket/" + identifier + ".description", start + ", fitted onto the side of the tool head. " + end);

        add("tetra.variant.single_socket/" + identifier, "Socket " + socket);
        add("tetra.variant.single_socket/" + identifier + ".description", start + ", attached where the head meets the handle. " + end);

        add("tetra.variant.sword_socket/" + identifier, "Socket " + socket);
        add("tetra.variant.sword_socket/" + identifier + ".description", start + ", fitted onto the sword where the blade meets the hilt." + end);
    }

    public void addPage(String identifier, String tooltip) {
        add("malum.gui.book.entry.page.text." + identifier, tooltip);
    }

    public void addDescription(String identifier, String tooltip) {
        add("malum.gui.book.entry." + identifier + ".description", tooltip);
    }

    public void addHeadline(String identifier, String tooltip) {
        add("malum.gui.book.entry.page.headline." + identifier, tooltip);
    }

    public void addTooltip(String identifier, String tooltip) {
        add("malum.tooltip." + identifier, tooltip);
    }

    public void addEffectDescription(String identifier, String description) {
        add("effect.malum." + identifier + ".description", description);
    }

    public void addDamageTypeDeathDescription(String identifier, String description) {
        add("death.attack."+ identifier, "%s " + description);
        add("death.attack."+ identifier+".player", "%s " + description +  " by %s");
    }

    public String correctBlockItemName(String name) {
        if ((!name.endsWith("_bricks"))) {
            if (name.contains("bricks")) {
                name = name.replaceFirst("bricks", "brick");
            }
        }
        if (name.contains("_fence") || name.contains("_button")) {
            if (name.contains("planks")) {
                name = name.replaceFirst("_planks", "");
            }
        }
        return name;
    }
}