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
import com.sammy.ortus.systems.blockentity.OrtusBlockEntityInventory;
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
import static com.sammy.malum.core.setup.content.item.ItemRegistry.SOUL_HUNTER_BOOTS;
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


        for (RegistryObject<SoundEvent> sound : sounds) {
            String name = correctSoundName(sound.getId().getPath()).replaceAll("_", " ");
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            add("malum.subtitle." + sound.getId().getPath(), name);
        }

        enchantments.forEach(e -> {
            String name = DataHelper.toTitleCase(e.getId().getPath(), "_");
            add(e.get().getDescriptionId(), name);
        });

        effects.forEach(e -> {
            String alteredPath = e.getId().getPath().replaceFirst("s_", "'s_");
            String name = DataHelper.toTitleCase(alteredPath, "_");
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

        addDescription("introduction", "First Steps");
        addHeadline("introduction", "Introduction");
        addPage("introduction_a", "Within our world, every living body has a soul. The soul is one's consciousness and what animates the body, the physical vessel for the arcane presence. Both represent who we are, while the body is your physical fortress, the soul is your arcane image.");
        addPage("introduction_b", "The Encyclopedia Arcana, as I have named it, is a journal which will contain all my gathered knowledge on everything that is known about the soul. To be more exact, this codex of mine will focus on a rather wicked form of arcane arts known as soul magic. It is a very cruel, inhumane and generally frowned upon study of arcana.");
        addPage("introduction_c", "At the very core of every soul there seems to exist an untapped source of power, which appears to be closely tied to the very existence of the soul. While normally it is a simple flux within the soul, I believe if we are to separate this flux from the arcane presence, it will solidify into a crystal of sorts.");
        addPage("introduction_d", "The power of each individual soul derives almost entirely from the spirit flux found within it's core. These spirits serve as the basis for the very existence of the magical properties of every soul there is. I speculate that if a soul is removed from it's spirit flux within, it will become a blank slate without any true purpose.");

        addDescription("spirit_magics", "Fuel for the Soul");
        addHeadline("spirit_magics", "Basics of Magic");
        addPage("spirit_magics_a", "\"How do I get my greedy little hands on a spirit?\" You might wonder, as I did. To start, we must forge a trusty yet wicked weapon. This weapon we hope to create should be capable of making direct contact with the core of the soul, and hopefully destroying it.");
        addPage("spirit_magics_b", "Once this tool is in our hands, we should be ready to start hunting for spirit arcana. After some research, I have concluded that there are eight different primary forms of this magic, which should all reside within different types of living souls. Depending on which type of spirit arcana we wish to obtain, we will need to set out to hunt different foes.");
        addPage("spirit_magics_c", "As I previously noted each living soul is powered by the spirit flux found deep within, but that spirit flux isn't just a simple fuel for the soul. It is a magical element, often mixed in with other forms of itself, all found within the same soul! What this means is that each soul is unique, composed of a different combination of spirit flux.");
        addPage("spirit_magics_d", "For example, I believe that the soul of a fiery being, like the blaze, should in some form represent the fiery nature of it's vessel. Likewise, the soul of a peaceful, vigorous being, should also contain flux which represents the calm nature of the being.");

        addDescription("soulstone", "Strange Minerals");
        addHeadline("soulstone", "Soulstone");
        addPage("soulstone_a", "The soulstone is a strange mineral found both nearby the surface and in the deepslate layer of our world. It's existence is most certainly a result of spirit magics, and seems to closely relate to carbon. I'll research it's origin further in the future.");
        addPage("soulstone_b", "I can't exactly replicate, or even comprehend the process used to create Soulstone, so the deposits of ore I found underground will have to suffice. Furthermore, Soulstone appears to be the exact thing we need, a great catalyst for soul magics and other diabolical fields, perfect for our weapon.");
        addPage("soulstone_c", "Note: Do not touch the mineral in it's base form! As I gripped the purple gemstone, I felt a strange surge of energy approach my very soul. It was a small yet very uncomfortable pain that I do not want to feel ever again.");

        addDescription("runewood", "Arcane oak");
        addHeadline("runewood", "Runewood");
        addPage("runewood_a", "While adventuring I have found a strange tree, it grows in plains and forests and holds a yellow crown. It is a display of pure arcane energy, I believe it's bark and wood will prove itself useful to me. I shall name it Runewood, in note of it's magical nature.");
        addPage("runewood_b", "Due to it's magical nature, the wood of the tree appears to function as a great conductor for magic. Even just touching the sapling creates a warm feeling, which I can feel in my very soul. It appears that anything with enough magical energy within is able to impose itself on your very soul, almost as if touching it directly.");
        addHeadline("arcane_charcoal", "Arcane Charcoal");
        addPage("arcane_charcoal", "After running short on fuel, I had decided to process my newly found runewood into charcoal. To my surprise however, it appears that the resulting charcoal retains some of this magical property, and as a result functions as a longer lasting fuel source. From my so-far brief use of it, I can tell that it is twice as potent as regular fuel.");
        addHeadline("holy_sap", "Holy Sap");
        addPage("holy_sap_a", "This strange phenomena keeps surprising me, I have found a special type of sap that grows within the tree. To get your hands on this holy extract you'll need an axe. Start by stripping off the bark of an exposed piece of runewood, and then collect it all with a glass bottle.");
        addPage("holy_sap_b", "After a brief taste test, I could feel a nourishing feeling course through me. It appears this substance holds some potent restorative properties, I should be able to further develop this ability by processing the sap into holy syrup, which should function as a minor healing elixir.");
        addPage("holy_sap_c", "Furthermore, nothing was stopping me from mixing the sap with some slime. The result of my curiosity comes in the form of several holy sapballs, which should be able to function just like slime, effectively expanding my supply of the hard to come by resource. Gross.");

        addDescription("scythes", "Shatter the Soul");
        addHeadline("scythes", "Scythes");
        addPage("scythes_a", "With the Soulstone in my possession, I have designed a weapon which I will use as my primary gate to harnessing the power that is spirit arcana, the scythe; for I am the reaper. I will embed the Soulstone in the crude iron blade to allow it to damage the soul of the target, and hopefully destroy it.");
        addPage("scythes_b", "The weapon is created, and after sharpening the blade I waited for the night to set and went on my first soul hunt. To say the least, it went well. I felt a powerful egocentric feeling course through me as I heard the soul of my many victims shatter and release the spirit flux found within.");
        addPage("scythes_c", "Just as I speculated, when the flux is released from a shattered soul it takes on a crystal form which we can even hold. I shall name this form a spirit arcana splinter, or spirit for short. I must further research my newly attained power.");
        addHeadline("scythe_enchanting", "Scythe Enchanting");
        addPage("scythe_enchanting", "At it's core, the scythe is much like any other weapon within my arsenal, I believe I enchant it and bring out more of it's power.");

        addHeadline("haunted", "Haunted");
        addPage("haunted", "When a soul is damaged a lot of it's integrity is lost, resulting in some of it's magical energy being released. The haunted enchantment converts that magic energy into extra magic damage to anyone cut by the blade. Higher tiers damage the target further.");

        addHeadline("spirit_plunder", "Spirit Plunder");
        addPage("spirit_plunder", "The spirit plunder enchantment brings out more of the potential found within the precious soulstone, any soul shattered through the use of this enchanted scythe will yield extra spirits. This may however damage the soulstone, taking extra durability off of your scythe.");

        addHeadline("rebound", "Rebound");
        addPage("rebound", "The rebound enchantment is by far the strangest one of all. It will grant the user the ability to throw the scythe like a boomerang, allowing for a wacky ranged attack. Higher tiers reduce the delay before the scythe can be thrown once more.");

        addDescription("spirit_infusion", "Creating Magical Wonders");
        addHeadline("spirit_infusion", "Spirit Infusion");
        addPage("spirit_infusion_a", "By utilizing the magical Runewood I have created an altar which I will utilize for all my magical studies and crafts. Here I can perform a process known as Spirit Infusion. It is a crafting process based around infusing spirit arcana into various items, twisting them to fit your evil desires.");
        addPage("spirit_infusion_b", "Every spirit infusion recipe requires an item to function as an epicenter of the process. You will also need a set of spirit arcana to merge with your item. These ingredients must all be placed within the spirit altar. As soon as all of them are present the spirit altar will spur into motion and begin to infuse magic into the item.");
        addPage("spirit_infusion_c", "More often than not, spirit infusion will require additional reagents to be infused alongside spirit arcana. To provide the altar with these extra ingredients I have created some runewood item holders, an item stand, and an item pedestal. These hold a single stack of an item, and must be placed within 4 blocks of the altar.");
        addHeadline("hex_ash", "Hex Ash");
        addPage("hex_ash", "As a test of this altar, I have created a powder which I will call Hex ash. It is a simple grit which I will use as a minor ingredient in various magics. The ash is imbued with arcane spirit, which I have taken from a skeleton.");

        addDescription("primary_arcana", "Simplest of Magic");
        addHeadline("sacred_spirit", "Sacred Spirit");
        addPage("sacred_spirit_a", "That which is sacred originates from two main concepts, the vigorous and the holy. Sacred arcana provides various forms of healing and other curative effects.");
        addPage("sacred_spirit_b", "Sacred Arcana can be found mainly within passive, relaxed souls. Beings of holy origin should also hold this magic within their soul.");

        addHeadline("wicked_spirit", "Wicked Spirit");
        addPage("wicked_spirit_a", "Flipside of the sacred, wicked spirit represents various impurities and dark magics. Even just touching this spirit creates a mild pain within your soul.");
        addPage("wicked_spirit_b", "Generally when a vessel rots, the soul peacefully fades out into the afterlife. However, if the body is to be reanimated back to life through necromancy, or some other arcane art, the soul and vessel may be one once more. This however scars the the soul, twisting it into a more wicked shape.");

        addHeadline("arcane_spirit", "Arcane Spirit");
        addPage("arcane_spirit_a", "The arcane spirit is magic in it's purest form. Raw arcana is often used to fully utilize potential found within other spirit magics, it functions as a powerful amplifier.");
        addPage("arcane_spirit_b", "When a vessel is born, depending on it's design it's very soul will be full of spirit flux matching the properties of the vessel. A fiery creature would naturally be born with an infernal soul. However, spirits aren't always one and done. I've thought of the witch. Were they always capable of utilizing magic?");
        addPage("arcane_spirit_c", "I suspect that the soul can adapt to constant arcana exposure and furthermore develop some matching spirit flux of it's own, overtime bringing forth new spirit. Another example I can think of are illagers, I bet those dammed hunters weren't always so evil. We may find Arcane spirit within the magically gifted, and those with an esoteric origin.");

        addDescription("elemental_arcana", "Focused Magic");
        addHeadline("earthen_spirit", "Earthen Spirit");
        addPage("earthen_spirit_a", "Earthen arcana can be attributed to three main traits, strength, nature, and more rarely, vitality. The sorcery is incredibly potent even when used in small amounts.");
        addPage("earthen_spirit_b", "With the strength of the world in your greedy hands we can do quite a lot. This potent wizardry is bound to prove itself useful, it is found within certain passive, and also powerful souls.");
        addHeadline("infernal_spirit", "Infernal Spirit");
        addPage("infernal_spirit_a", "Hellish magic is incredibly dangerous, and by far the most diabolical. Playing with fire is usually not a good idea, but that's not gonna stop me.");
        addPage("infernal_spirit_b", "Infernal arcana is great at many things: light, explosions, fire, the list goes on. The magic allows for a wide variety of effects, however I have only found it in a rather narrow selection of souls. I believe the nether will prove very useful to me here.");
        addHeadline("aerial_spirit", "Aerial Spirit");
        addPage("aerial_spirit_a", "Aerial arcana is light and versatile, it is the least potent and yet, it presents the most possibilities and potential.");
        addPage("aerial_spirit_b", "I often experience a recurring dream, I find myself high up in the sky, with strong wings and a gust propelling me forward. I believe I can utilize aerial arcana to one day make this dream a reality. Any swift soul is bound to hold this magic.");
        addHeadline("aqueous_spirit", "Aqueous Spirit");
        addPage("aqueous_spirit_a", "Aqueous arcana is the most mysterious type of spirit magic. It is very malleable and represents loyalty to the seas.");
        addPage("aqueous_spirit_b", "Prior to my discovery of soul magics, I used to look for revelation within the seas of our world. They're filled with plenty of inexplicable; automaton-like guardians, drowned ruins, monuments, there's too much we don't know, I believe aqueous magic inherits this esotericism.");

        addDescription("eldritch_arcana", "Abstract Arcana");
        addHeadline("eldritch_spirit", "Eldritch Spirit");
        addPage("eldritch_spirit_a", "While raw arcana functions as an amplifier, eldritch arcana works much like a strong catalyst. It is much more potent but incredibly rare.");
        addPage("eldritch_spirit_b", "I am a little puzzled by this spirit. Much like the otherworldly soul that used to hold the magic, I am unsure what to make of it. It seems that there is something within the sealed off dimension that brings forth this spirit within the souls born in the realm. I am not sure if it belongs to any other creature.");

        addDescription("arcane_rock", "Perfect for a Crypt");
        addHeadline("tainted_rock", "Tainted Rock");
        addPage("tainted_rock", "By infusing the earth with sacred and raw arcana a new stone with plenty of building options is created. Tainted rock is a simple magical construct which happens to hold strong magic-suppressing properties, and can be shaped into item holders much like runewood.");
        addHeadline("twisted_rock", "Twisted Rock");
        addPage("twisted_rock", "If we are to replace sacred arcana with it's opposite the resulting stone yields a much grimmer appearance. Twisted rock is indifferent from it's holy variant apart from the darker tone.");

        addDescription("ether", "The Sunset can't Compare");
        addHeadline("ether", "Ether");
        addPage("ether_a", "I have heard gossip of a flame that can burn forever, and decided to bring forth such wonder. Ether is a harmless, magical flame that burns and shines bright forever. It can be placed on a torch or a magical brazier which can may be grounded or hung.");
        addPage("ether_b", "Additionally, Ether can be dyed into practically any color. Simply combining the ether item with any combination of dyes will change the light it emits. Utilizing monochromatic colors however seems to instead create a much dimmer result, difficult to see. It allowing for a hidden light of sorts.");

        addHeadline("iridescent_ether", "Iridescent Ether");
        addPage("iridescent_ether_a", "While ether is already quite appealing, iridescent ether takes that glimmering shine a step further. This pristine form of ether allows us to dye the item once more to alter the ending color of the burn. Much like normal ether it can be placed on a brazier and a torch");
        addPage("iridescent_ether_b", "Getting just the right coloring for your light may be a bit tricky however. You cannot alter the original color of ether once it's transformed into it's iridescent variant, applying any dye at this stage will only change the second color.");

        addDescription("spirit_fabric", "Wicked Weaves");
        addHeadline("spirit_fabric", "Spirit Fabric");
        addPage("spirit_fabric", "Spirit fabric is a light cloth used for a few key spirit infusions and crafting recipes. The material is very sturdy and produces a powerful wicked aura.");
        addHeadline("spirit_pouch", "Spirit Pouch");
        addPage("spirit_pouch", "I have grown very tired of carrying all my spirit arcana, the spirit pouch offers a solution to this annoyance. The item allows us to store a large amount of spirits within it's internal inventory. On top of that, any spirits we gather are automatically stored in the pouch.");

        addDescription("soul_hunter_gear", "Scythe Expert");
        addHeadline("soul_hunter_armor", "Soul Hunter Armor");
        addPage("soul_hunter_armor", "Through the use of Spirit Fabric, I have designed an armor set befitted specifically for expanding the users magical ability. Due to it's primitive and rather dangerous nature however, the armours protective abilities are rather lacking.");

        addDescription("spirit_focusing", "Focusing with Spirits");
        addHeadline("spirit_focusing", "Spirit Focusing");
        addPage("spirit_focusing_a", "The spirit crucible is a wondrous device. It is used to perform an alchemical process named spirit focusing, it revolves around a single key catalyst and the interactions between it and spirit arcana. One of the more common catalysts found in this magical process is an alchemical impetus.");
        addPage("spirit_focusing_b", "The impetus is a clay-made artifact of ancient design, it's very easy to shape it's earthen mass into new form. By utilizing spirits in the crucible we may shape some of the impetus' mass into something new at the cost of a slight toll on the clay artifact's durability.");

        addDescription("working_with_ashes", "Spirits to ashes, ashes to soot");
        addHeadline("working_with_ashes", "Working with Ashes");
        addPage("working_with_ashes", "With primitive spirit focusing we may successfully create simple powders often needed in all sorts of studies of arcana.");

        addDescription("crucible_acceleration", "Heating Up");
        addHeadline("crucible_acceleration", "Crucible Acceleration");
        addPage("crucible_acceleration_a", "By design, The Spirit Crucible is a rather slow esoteric mechanism. This is not without reason, for you see; the impetus is a fragile thing, the process of spirit focusing heavily relies on a slow and constant input of arcana in order to avoid causing unnecessary damage to the catalyst.");
        addPage("crucible_acceleration_b", "Increasing the rate at which arcana is streamed into the impetus would yield a faster result but it could lead to your catalyst getting damaged more than necessary. With that in mind the spirit catalyzer is a heavily augmented pedestal designed specifically for utilizing solid fuel to accelerate a nearby machination.");
        addPage("crucible_acceleration_c", "With that in mind, each nearby spirit catalyzer placed nearby the machine will amplify the speed of the crucible at the cost of greater risk of catalyst instability. You may observe exponentially higher results with each new catalyzer added; reaching the limit at eight nearby accelerators.");

        addDescription("metallurgic_magic", "Focusing into hardware");
        addHeadline("metallurgic_magic", "Metallurgic Magic");
        addPage("metallurgic_magic_a", "With some clever tricks I have created a metallic impetus, which can be used to form metallic nodes of nearly any metal desired. These nodes can then be processed directly into nuggets. Due to the more rigid material of the catalyst, more durability is lost in the process.");
        addPage("metallurgic_magic_b", "This design is not my own however, these metallic catalysts were previously used as some sort of ashen aspectus. I believe their purpose served as a tuning mechanism in an alchemical exchange system, I do not know all the details to it, nor do I wish to delve into it.");

        addDescription("impetus_restoration", "Mending sorceries");
        addHeadline("impetus_restoration", "Impetus Restoration");
        addPage("impetus_restoration_a", "When an impetus sustains enough damage it will crack, rendering the catalyst unusable in spirit focusing. To combat this issue, the crucible allows for a secondary process known to most as arcane restoration. This process is, in a way, the flipside of spirit focusing.");
        addPage("impetus_restoration_b", "Instead of splitting off mass from any given item, an impetus for example, arcane restoration utilizes spirit arcana to recover the lost form of your damaged item. To do this you will need to create a twisted tablet.");
        addPage("impetus_restoration_c", "The twisted tablet is a simple augmentation of your usual item stand with a more esoteric design allowing for greater functionality. It must be placed facing the crucible on a matching axis position.");

        addHeadline("expanded_focusing", "Expanded Restoration");
        addPage("expanded_focusing_a", "Furthermore, arcane restoration allows for more than just fixing your catalysts; it reaches out much further. Using the restoration process we are able to repair nearly any damaged item, utilizing spirit arcana instead of experience.");
        addPage("expanded_focusing_b", "Each individual item we wish to repair requires it's own unique set of spirits that need to be slotted in the crucible and a valid repair ingredient aimed at the crucible in the twisted tablet. Upon completion of the process, the item's lost durability will be partially restored.");
        addPage("expanded_focusing_c", "Additionally, items with a close, direct connection to spirit arcana are able to absorb incoming spirit arcana much more effectively, resulting in more lost durability being recovered.");

        addDescription("crystal_creation", "Focusing into jewelry");
        addHeadline("crystal_creation", "Crystal Creation");
        addPage("crystal_creation", "By utilizing more complex combinations of spirits in spirit focusing we may naturally expect more complex results, and our theory is most certainly confirmed. Through the use of more complex spirit focusing we may create various gemstones.");

        addDescription("spirit_metals", "Arcane metals");
        addHeadline("hallowed_gold", "Hallowed Gold");
        addPage("hallowed_gold_a", "Gold is very often used as a basis for various magics, I believe this property to be relevant when dealing with soul magic as well. Infusing raw and sacred arcana into a gold ingot will imbue it with magic and yield a much more desirable arcane metal. A few additional reagents are also required for this infusion.");
        addPage("hallowed_gold_b", "While not too useful in evil schemes or crafting powerful gear, hallowed gold is a metal perfect for spirit manipulation and transfer.");
        addHeadline("spirit_jar", "Spirit Jar");
        addPage("spirit_jar", "The spirit jar is a simple yet convenient block. It can store what I presume to be an infinite amount of a single spirit. Spirits may be inserted and taken from the jar by interacting with it, sneaking will take out an entire stack.");
        addHeadline("soul_stained_steel", "Soul Stained Steel");
        addPage("soul_stained_steel_a", "The sacred origins of hallowed gold make it nearly impossible to use for harm. Soul stained steel is nothing like that, it's a tough metal twisted beyond recognition. It is designed to inflict pain.");
        addPage("soul_stained_steel_b", "Any piece of gear made from soul stained steel is capable of shattering souls much like a scythe. Said gear also holds the ability to deal magic damage after every strike. Both metals can also be used to create a type of magic transmitter. A spirit resonator, a more complex crafting component in various arcane designs.");

        addDescription("soul_stained_scythe", "Tinkering");
        addHeadline("soul_stained_scythe", "Soul Stained Scythe");
        addPage("soul_stained_scythe", "After some time using the crude scythe I have grown tired of the primitive weapon. By utilizing soul stained steel we can create a direct upgrade with overall higher damage distributed between physical and magic.");

        addDescription("soul_stained_armor", "Augmenting");
        addHeadline("soul_stained_armor", "Soul Stained Armor");
        addPage("soul_stained_armor_a", "Just like with the scythe, we can reinforce crude and boring iron armor using soul stained steel. In it's new purple form, apart from rather high levels of protection the armor also provides a passive barrier against magic damage. On top of that, the armor coats the wearer's soul with a protective barrier known as Soul ward.");
        addPage("soul_stained_armor_b", "Soul Ward is a potent arcane barrier designed for magic attacks. It will recover over time, and absorb almost all of magic damage taken as well as a small amount of physical damage dealt to the wearer. I hope to research this barrier further and unlock more of it's potential");
        addPage("soul_stained_armor_c", "Wearing an armor set made out of soul stained steel was a daunting thought at first, the nature of the metal would quickly rend my soul. To prevent this unfortunate scenario, the armor must be forged with a protective twisted rock layer underneath the dangerous metal.");

        addDescription("spirit_trinkets", "Forging");
        addHeadline("spirit_trinkets", "Spirit Trinkets");
        addPage("spirit_trinkets_a", "A trinket is a simple accessory that will provide the wearer with helpful benefits, some know them as baubles or curios. Hallowed Gold and Soul Stained Steel both offer two basic trinkets that all serve as a basic form, which can be further mangled with through spirit magics.");
        addPage("spirit_trinkets_b", "In their most primitive form, Gilded trinkets provide boosts to armor while ornate steelwear yields armor toughness.");
        addHeadline("arcane_spoil", "Ring of Arcane Spoil");
        addPage("arcane_spoil", "I have found myself needing more and more spirit arcana as I delve deeper into this magic. To satisfy my greed for power I have forged the ring of arcane spoil, which will allow the wearer to reap an extra spirit from every soul shattered. I am a little concerned with how this extra spirit comes to be, but I must not worry about it now. I'm sure it won't pose any real problems.");

        addDescription("ring_of_prowess", "Curiosity");
        addHeadline("ring_of_prowess", "Ring of Prowess");
        addPage("ring_of_prowess_a", "The soul holds more than just spirit flux within it, brilliance, also known as experience can be found within. Unlike spirits, brilliance has a rather loose connection to the soul, even just slaying the vessel through regular means releases some of this stored brilliance.");
        addPage("ring_of_prowess_b", "As with all things, we can't let the soul keep anything for itself. With some clever manipulation of raw arcana combined with natural brilliance we can leech off some extra experience from the dying soul. The ring of prowess does just that, awarding the bearer with experience as they collect each individual spirit.");

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
        addPage("brilliant_obelisk", "If we replace the spirit resonator with a set of brilliance, the resulting obelisk is instead fitted with brilliance. Each one of these boosts the power of a nearby enchantment table, reaching full efficiency with just three obelisks.");

        addDescription("totem_magic", "Spirit Rites");
        addHeadline("totem_magic", "Totem Magic");
        addPage("totem_magic_a", "Through the use of spirit arcana we may not only forge new wonders, but also perform rituals. By engraving spirits onto runewood in a specific order you may create a spirit rite, a dark ritual that mangles with the nearby area.");
        addPage("totem_magic_b", "Each spirit rite starts at the totem base, above which there must be sufficient runewood logs to hold the array of engraved spirits. Spirits can be stripped off of runewood by using an axe. When you lay out your rite correctly, activate the totem base to activate the ritual.");
        addPage("totem_magic_c", "There exists one limitation to this primitive system, no two matching spirit rites can be active within the range of one another.");

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

        add("malum.spirit.flavour.sacred", "Blessed");
        add("malum.spirit.flavour.wicked", "Cursed");
        add("malum.spirit.flavour.arcane", "Stellar");
        add("malum.spirit.flavour.eldritch", "Null");
        add("malum.spirit.flavour.aerial", "Light");
        add("malum.spirit.flavour.aqueous", "Malleable");
        add("malum.spirit.flavour.infernal", "Radiant");
        add("malum.spirit.flavour.earthen", "Sturdy");

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

        add("death.attack." + DamageSourceRegistry.VOODOO_DAMAGE, "%s had their soul shattered");
        add("death.attack." + DamageSourceRegistry.VOODOO_DAMAGE + ".player", "%s had their soul shattered by %s");
        add("death.attack." + DamageSourceRegistry.FORCED_SHATTER_DAMAGE, "%s had their soul shattered");
        add("death.attack." + DamageSourceRegistry.FORCED_SHATTER_DAMAGE + ".player", "%s had their soul shattered by %s");
        add("death.attack." + DamageSourceRegistry.MAGEBANE_DAMAGE, "%s got too confident with a soul hunter");
        add("death.attack." + DamageSourceRegistry.MAGEBANE_DAMAGE + ".player", "%s got too confident with %s");
        add("death.attack." + DamageSourceRegistry.SCYTHE_SWEEP_DAMAGE, "%s was sliced into two pieces");
        add("death.attack." + DamageSourceRegistry.SCYTHE_SWEEP_DAMAGE + ".player", "%s was sliced into two pieces by %s");

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

    public String correctSoundName(String name) {
        if ((name.endsWith("_step"))) {
            return "footsteps";
        }
        if ((name.endsWith("_place"))) {
            return "block_placed";
        }
        if ((name.endsWith("_break"))) {
            return "block_broken";
        }
        if ((name.endsWith("_hit"))) {
            return "block_breaking";
        }
        return name;
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