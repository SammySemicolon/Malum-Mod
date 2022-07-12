package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.common.block.ether.EtherWallTorchBlock;
import com.sammy.malum.common.item.spirit.SpiritJarItem;
import com.sammy.malum.core.setup.content.DamageSourceRegistry;
import com.sammy.malum.core.setup.content.SpiritRiteRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import com.sammy.malum.core.systems.item.ISoulContainerItem;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.ortus.helpers.DataHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static com.sammy.malum.core.setup.content.AttributeRegistry.ATTRIBUTES;
import static com.sammy.malum.core.setup.content.SoundRegistry.SOUNDS;
import static com.sammy.malum.core.setup.content.block.BlockRegistry.BLOCKS;
import static com.sammy.malum.core.setup.content.entity.EntityRegistry.ENTITY_TYPES;
import static com.sammy.malum.core.setup.content.item.ItemRegistry.ITEMS;
import static com.sammy.malum.core.setup.content.item.MalumEnchantments.ENCHANTMENTS;
import static com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry.EFFECTS;

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
        ArrayList<MalumRiteType> rites = SpiritRiteRegistry.RITES;
        ArrayList<MalumSpiritType> spirits = new ArrayList<>(SpiritTypeRegistry.SPIRITS.values());
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof EtherWallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallSignBlock);
        blocks.forEach(b ->
        {
            String name = b.get().getDescriptionId().replaceFirst("block\\.malum\\.", "");
            name = makeProper(DataHelper.toTitleCase(correctBlockItemName(name), "_"));
            add(b.get().getDescriptionId(), name);
        });
        DataHelper.getAll(items, i -> i.get() instanceof ISoulContainerItem || i.get() instanceof SpiritJarItem).forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("item\\.malum\\.", "").replaceFirst("block\\.malum\\.", "");
            String filled = "filled_" + name;
            add("item.malum." + filled, makeProper(DataHelper.toTitleCase(filled, "_")));
        });
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && !(i.get() instanceof ItemNameBlockItem));
        items.forEach(i ->
        {
            String name = i.get().getDescriptionId().replaceFirst("item\\.malum\\.", "");
            name = makeProper(DataHelper.toTitleCase(correctBlockItemName(name), "_"));
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

        rites.forEach(r -> {
            add(r.translationIdentifier(false), r.basicName);
            add(r.translationIdentifier(true), r.corruptName);
        });

        spirits.forEach(s -> add(s.getDescription(), DataHelper.toTitleCase(s.identifier + "_spirit", "_")));

        addSimpleEntryHeader("introduction", "Introduction", "On the nature of souls");
        addPages("introduction",
            "\"Within our world, every living being has a soul. That soul is consciousness, what animates the body, and the meeting point between matter and magic. The two represent our existence; as the body is presented to the physical world, so the soul is to the arcane.\"",
            "I seem to have stumbled upon something peculiar: a form of magic heretofore undocumented. I could hardly call myself a magus if I refused the opportunity to study it. In this codex, the Encyclopedia Arcana, I, " + obfuscate("Youlost Thegame") + " write my research into this power.",
            "The energies this thaumaturgical discipline manipulates seem to be rooted in the soul. More accurate would be to say they are the energies of the soul, the inclinations and impulses that make up each one of us.", "So far, what I have described is basic. But I have found a way to separate, and then condense, the impulse of a soul into a physical form I call a spirit crystal. This forms the basis of my research.",
            """
                The natures of the soul I condense influence the crystal's properties. Each soul is slightly different, and that can result in changes to the crystals formed. And in those crystals is contained the capacity for magic. The capacity for change.

                The capacity for power.""");

        addSimpleEntryHeader("spirit_crystals", "Spirit Crystals", "Matter and magic");
        addPages("spirit_crystals",
            "The soul is a notoriously fickle thing. Even confirming its existence is difficult, requiring the highest thaumaturgies to get a reading. That is what sets spirit arcana apart from other magic. We don't need grand assemblies and esoteric artifice to see a soul.\n\n" +
                "Instead, we " + italic("touch") + " it.",
            "A material I have named Soulstone is the means by which we do so. It appears mundane until refined, but once it is rid of impurities, it seems... out of phase with the world. By creating a blade using it as a core, I should be able to strike the physical form, then the soul, shattering it to energy before it can disperse.",
            "These energies, as previously noted, have different 'flavors.' A being burning with light would have a soul that reflects that radiance, and a being prone to adaptation would have a soul as malleable as itself. Occasionally, the energy has no flavor to it at all, leaving only the raw impulse of creation behind. That type of crystal bears further study.");

        addSimpleEntryHeader("soulstone", "Soulstone", "Out of phase");
        addPages("soulstone",
            "Sometimes, it appears that matter can be charged with the energies of a soul, despite not having a soul of its own.",
            "This serves as the basis for spirit arcana - the ensouling of the soulless. Soulstone is an ore that exists more in the arcane than the physical, and, refined, presents many uses for my magic.");

        addSimpleEntryHeader("natural_quartz", "Natural Quartz", "Deep in the earth");
        addPages("natural_quartz",
            "Natural Quartz is, as the name implies, a natural equivalent of the nether resource. It's used for most of the same things. It's rare, and found deep underground, sometimes in geodes.");

        addSimpleEntryHeader("rare_earths", "Rare Earths", "[NOT COMPLETE]");
        addPages("rare_earths",
            "A rare resource found in golden geodes deep underground. Description to come.");

        addSimpleEntryHeader("runewood", "Runewood", "Like a magic sponge");
        addPages("runewood",
            "Runewood is a lush variety of tree, and a fairly common one. While pretty, I am more interested in practicality. Runewood is soaked in magic, and as such, can serve as the basis for a great deal of arcane machinery.");
        addHeadline("runewood.arcane_charcoal", "Arcane Charcoal");
        addPages("runewood.arcane_charcoal",
            "Runewood's charcoal, as magic-infused as it is, burns with an arcane fervor for longer than regular charcoal. This makes it rather useful for fueling any smelting I need to do.");
        addHeadline("runewood.holy_sap", "Holy Sap");
        addPages("runewood.holy_sap",
            "In addition, sometimes Runewood trees will have a buildup of sticky sap on the sides of their logs. When this happens, if you strip off the bark, you'll be able to bottle the sap and use it as slime, or heat and drink it for a minor regenerative effect.");

        addSimpleEntryHeader("blazing_quartz", "Blazing Quartz", "Ignition");
        addPages("blazing_quartz",
            "It stands to reason that a place like the nether would have a substance that was flammable, and Blazing Quartz certainly fits the bill. It acts much like coal, even being able to form torches. A useful substance, even if fairly mundane.");

        addSimpleEntryHeader("brilliance", "Brilliance", "The stuff of experience");
        addPages("brilliance",
            "Brilliance is a term I have heard bandied about for what others call experience. It is a part of the soul, though improperly attached, and can be collected and used for enchanting and repairs.",
            "What many don't know is that it can condense into a physical form. I have heard rumors of solid Brilliance coming from crushing ore, but the most reliable source is small clusters of ore where a soul faded away, leaving its experiences engraved on the stone.");

        addSimpleEntryHeader("scythes", "Scythes", "Harvest");
        addPages("scythes",
            "After several inert attempts, I have socketed Soulstone into a weapon that can reliably harvest these spirit crystals. The long blade allows time for the body to die before I strike the soul, while also providing a wide sweep attack. It isn't as sharp as a sword, but for my purposes, it will do nicely.",
            "What I had managed to do before with careful, painstaking experiments, the scythe did in a matter of seconds. The souls of the monsters I slew shattered, streaming bits of deeply hued matter towards me: the spirit crystals. Finally, my research can begin in earnest.");
        addHeadline("scythes.enchanting", "Enchanting a Scythe");
        addPages("scythes.enchanting",
            "At its core, the scythe enchants like other weapons or tools I've used. It has its own set of enchantments, of course, due to its differing nature, but can take Unbreaking and the like as well as a sword can.");
        addHeadline("scythes.enchanting.haunted", "Haunted");
        addPages("scythes.enchanting.haunted",
            "The Soulstone can be used in ways other than just shattering the soul. By enchanting the stone, the swing of the blade gains a bit of the strange properties of the stone, cutting deeper into the target's soul and doing extra magic damage.");
        addHeadline("scythes.enchanting.spirit_plunder", "Spirit Plunder");
        addPages("scythes.enchanting.spirit_plunder",
            "This is not a perfect method. Some of the soul is unavoidably lost in the moment between blade and stone. But by enchanting the blade, that loss can be mitigated, and more of the soul condensed. This unfortunately strains the stone, and can result in my scythe's durability decreasing.");
        addHeadline("scythes.enchanting.rebound", "Rebound");
        addPages("scythes.enchanting.rebound",
            "By working my enchantments into the wooden handle, I found that my scythe can be made to return to my hand. As strange as it looks, using my scythe as a boomerang can be useful to cut through hordes of monsters. The stronger the enchantment, the less time until I can throw the scythe again.");


        addSimpleEntryHeader("scythe_reaping", "Scythe Reaping", "Leaked magic");
        addPages("scythe_reaping",
            "When a being dies, its soul disperses. This is basic theory, and well proven by this point. It's been proposed that sometimes, that power leaks into the body of the creature as it dies, to explain the existence of reagents they drop. That hadn't been proven yet.",
            "But now, with my scythe, I have proved it beyond doubt. When a soul is shattered, the energy stays in physical form. It's much more likely for it to infuse a bit of that creature when that happens, so far more reagents are produced that way.",
            "I've found three such reagents that I can harvest this way. The flesh of zombies can curdle into Rotting Essence; the bones of skeletons can crystallize into Grim Talc; and the wings of phantoms can spin into Astral Weave.");
        addHeadline("scythe_reaping.calx", "Alchemical Calx");
        addPages("scythe_reaping.calx",
            "Witches carry alchemical reagents with them already, and, while the magic doesn't infuse their body, it transmutes those reagents into something else. Soul striking a witch is more likely to drop Alchemical Calx.");

        addSimpleEntryHeader("spirit_infusion", "Spirit Infusion", "Creation of wonders");
        addPages("spirit_infusion",
            "By using Runewood's natural magic as a base, I have designed the altar that will serve as the basis for my magecraft - the Spirit Altar. It is the other piece of the equation, the use for the arcana. By infusing them into items, and using the energies to effect other fusions, I can begin to explore this.",
            "To use the altar, I must lay the item I wish to infuse on top of it, along with an appropriate set of arcana. If I wish to fuse other items in the process, I must place them on some form of Runewood item holder. They must be within four blocks of the altar to work.",
            "Once all the arcana are present, the power within the crystals will begin to flow into the central item. If other items are fused in, they are pulled in during this process. When all of that is done, the product of the infusion will appear. It " + italic("is") + " rather slow, though...");
        addHeadline("spirit_infusion.hex_ash", "Hex Ash");
        addPages("spirit_infusion.hex_ash",
            "My first product with this process is a powder I call Hex Ash, after its color. It is a simple and useful grit, with the niter and sulfur mostly transmuted by the raw arcana, leaving a mixture of reagent and carbon.");

        addEntryHeader("primary_arcana", "Primary Arcana", "The components of magic");
        addHeadline("primary_arcana.sacred", "Sacred Spirit");
        addPages("primary_arcana.sacred",
            "Sacred arcana is essential to any magic that enhances life. It can be defined as holy, the energy of particularly vibrant life, or even the simplicity of youth. It is pure and untainted, making it a useful component.",
            "It is the impulse of purity, the desire for optimism. It is found in those who are passive, innocent, or holy in origin.");
        addHeadline("primary_arcana.wicked", "Wicked Spirit");
        addPages("primary_arcana.wicked",
            "Wicked arcana is inimical to life. It seeks death and pain, and warps the living into something else. Even touching the crystal makes my soul shudder in pain.",
            "It is the impulse of corruption, the desire to cause suffering. It is found in those whose souls lack life, or those twisted by malice.");
        addHeadline("primary_arcana.arcane", "Arcane Spirit");
        addPages("primary_arcana.arcane",
            "While other arcana are impulses of the soul, it would be more accurate to say that the arcane is the impulse of the arcana themselves. This " + bold("raw arcana") + " lacks any particular quality, simply being undirected spiritual power.",
            "It is the impulse of creation, the first principle of all things. It is found within those who have opened their soul to power, or whose origins lie in that power.",
            "I suspect that this arcana, unlike others, can join a soul over time. Most things about the soul are defined early on. The impulses that define you are woven into your very self, after all. But lacking an impulse, perhaps this arcana is different. A witch was not born a mage, after all.");

        addSimpleEntryHeader("elemental_arcana", "Earthen Spirit", "Focused Magic");
        addHeadline("elemental_arcana.aerial", "Aerial Spirit");
        addPages("elemental_arcana.aerial",
            "Aerial arcana is the simplest of the elemental arcana. That very simplicity that gives it its utility. I have heard tales of magi soaring on the winds, ruling the skies. If any arcana is to make those tales achievable, it is this.",
            "It is the impulse of speed given form, the desire to run and to soar. It is found in anything particularly swift or mobile.");
        addHeadline("elemental_arcana.earthen", "Earthen Spirit");
        addPages("elemental_arcana.earthen",
            "Earthen arcana is relatively simple as well. It lends itself easily to strength, communion with nature, and the force of vitality. If I wish to enhance myself, or reshape the world, this arcana will be the key.",
            "It is the impulse of stability, the desire to stand and endure. It is found in anything that is unconcerned with the world around it changing.");
        addHeadline("elemental_arcana.infernal", "Infernal Spirit");
        addPages("elemental_arcana.infernal",
            "Infernal arcana is more complex, but not nearly as malicious as it might seem. Fire is dangerous, yes, but it is also the source of light and heat. It can burn something down as easily as it can fuse two things together.",
            "It is the impulse of light, the desire to burn. It is found in anything that shines brightly, as well as most denizens of the nether.");
        addHeadline("elemental_arcana.aqueous", "Aqueous Spirit");
        addPages("elemental_arcana.aqueous",
            "And finally, Aqueous arcana. It is strange, to say the least. It is malleable, yet doesn't do much by itself. It grants an affinity for the sea, but beyond that, its effects are rather esoteric.",
            "It is the impulse of change, the desire to adapt. It is found in anything that embodies that adaptation, as well as anything which lives in the flowing waters.");

        addEntryHeader("eldritch_arcana", "Eldritch Arcana", "For every push there is a pull");
        addHeadline("eldritch_arcana", "Eldritch Spirit");
        addPages("eldritch_arcana",
            "Eldritch arcana is a mystery to me. It has no impulse, none that I can understand, at least. And yet, it doesn't act like raw arcana. It changes, emboldens, enlightens... Raw arcana merely amplifies. This... this alters.",
            "I am not sure I understand what impulse creates this arcana. I find it in very few beings, and those I find it in are those who already defy explanation. But if it must be the pair to raw arcana, then that would imply that it's the impulse of endings, the " + italic("last") + " principle of all things.\n\n" +
                "I do not like that thought.");

        addEntryHeader("spirit_stones", "Spirit Stones", "Arcana suffused");
        addHeadline("spirit_stones.tainted_rock", "Tainted Rock");
        addPages("spirit_stones.tainted_rock",
            "Stone is reluctant to change, but nothing can endure the power of an unchained soul forever. By using raw arcana to force that change, I have created stones with useful magical properties. With Sacred arcana as the catalyst, it forms Tainted Rock, a stone that dissipates magic nearby.");
        addHeadline("spirit_stones.twisted_rock", "Twisted Rock");
        addPages("spirit_stones.twisted_rock",
            "With Wicked arcana's nature as the opposite of Sacred, it follows that the stone produced with it would act opposite. Twisted Rock has most of the same properties as Tainted Rock, but pushes magic away from it instead of dissipating it. Both can be fashioned into item holders, as Runewood can.");

        addSimpleEntryHeader("ether", "Ether", "All the colors of the wind");
        addPages("ether",
            "A common task for an apprentice magus is to create a flame that burns without heat or fuel. It serves as a test of magical control, as well as the ability to circumvent natural phenomena. Spirit arcana, of course, can produce this wonder as well.",
            "A peculiarity of Ether's flame is that it resonates with colors. As if it was leather to be dyed, I can tint its appearance. It is an emitter of light, so dyeing it darker colors will lower the intensity rather than change the color of the flame itself.");
        addHeadline("ether.iridescent", "Iridescent Ether");
        addPages("ether.iridescent",
            "As if this was not enough, I have found a way to imbue a second color into my Ether, creating Iridescent Ether. When created, this form of Ether locks in its original color, leaving a new, " + italic("second") + " color open to dyeing. The light will shift from the original color into the new color towards the peak of the flames.",
            "Getting the right coloring for this can be tricky, though. As stated, once Ether is made Iridescent, its original color can no longer be changed. This is hardly an issue, but should be kept in mind when tinting your flames.");

        addSimpleEntryHeader("spirit_fabric", "Spirit Fabric", "Wicked weaves");
        addPages("spirit_fabric", "Spirit Fabric is a light yet sturdy material that acts as an insulator for spirit energies. While other materials have the same properties, it's not exactly practical to craft a pouch or clothing from stone. I'm not willing to go quite so far for my research as to try wearing something like " + italic("that") + ".");
        addHeadline("spirit_fabric.pouch", "Spirit Pouch");
        // addPages("spirit_fabric.pouch", "But this fabric works wonderfully for storing spirit crystals. It keeps the arcana condensed within in a massless state, and will even store spirits I pick up before they so much as clutter my pockets. The amount of crystals it can store is near-infinite, making it incredibly convenient to carry around.");
        addPages("spirit_fabric.pouch", "But this fabric works wonderfully for storing spirit crystals. It keeps the arcana condensed within, and will even store spirits I pick up before they so much as clutter my pockets. It can store as many spirits as a single chest, making it quite convenient to carry around.");

        addSimpleEntryHeader("soulhunter_gear", "Soulhunter Gear", "Glass cannon");
        addPages("soulhunter_gear",
            "Spirit Fabric is an insulator, but that doesn't mean it has to dampen magic. This set of armor is designed to focus that magic, effectively amplifying the user's arcane abilities. Unfortunately, it's not exactly the strongest of materials, and it protects me just about as much as leather clothing.");

        addSimpleEntryHeader("spirit_focusing", "Spirit Focusing", "Mystic replication");
        addPages("spirit_focusing",
            "Using the opposing polarities of Twisted and Tainted Rock, I have created a device that draws in and compresses the energy of arcana. Similar to the Spirit Altar, if given a compatible substrate, I can use this process to produce things.",
            "The basic substrate here is the Alchemical Impetus, an artifact similar to those I've seen in the past. By focusing arcana into it, I can cause bits of the calx to transmute into something new, though this damages the Impetus in the process.");

        addSimpleEntryHeader("focus_ashes", "Arising of Ashes", "Creating powdered reagents");
        addPages("focus_ashes", "By applying differing qualities of arcana to an Alchemical Impetus, I can cause powders of various forms to be created. This doesn't require modification of the Impetus to accomplish.");

        addSimpleEntryHeader("focus_metals", "Magecraft of Metals", "Forming banded crystals"); // TODO: 7/12/22 replace Rare Earths when we decide on name
        addPages("focus_metals",
            "By altering the composition of the Alchemical Impetus with niter, sulfur, and Rare Earths, it is possible to form nodes of most pure metals. I can then process these small lumps directly into nuggets.",
            "It isn't particularly efficient or fast, but it is certainly better than having to mine for every ingot I need.");

        addSimpleEntryHeader("focus_crystals", "Creation of Crystals", "Forming irregular crystals");
        addPages("focus_crystals", "By applying differing qualities of arcana to an Alchemical Impetus, I can cause more mundane crystals to be formed. This doesn't require modification of the Impetus to accomplish.");

        addSimpleEntryHeader("crucible_acceleration", "Crucible Acceleration", "Heating up");
        addPages("crucible_acceleration",
            "The Spirit Crucible is, unfortunately, a rather slow device. It takes time for it to coalesce the power of the arcana into the central item. This isn't without reason. Most matter simply can't take a faster stream, and you risk damaging the catalyst by overloading it.",
            "However, by heating the catalyst through mystic means, you can lessen this rejection and speed up the coalescence at once. That is what the Spirit Catalyzer is for. Unfortunately, this is not perfect, and instability often causes the catalyst to be damaged more than strictly necessary.",
            "Each fueled Catalyzer nearby to a Crucible will amplify the speed of focusing exponentially, up to a maximum of eight. The risk of instability proportionally rises with each one.");

        addSimpleEntryHeader("arcane_restoration", "Arcane Restoration", "Mystic repair");
        addPages("arcane_restoration",
            "The Spirit Crucible allows the transmutation of matter. I have been able to use it to break down an impetus, and it stands to reason I could use it to restore one. By using spirit crystals in the Crucible instead of Brilliance in an anvil, I can do more than that - I can restore nearly anything.",
            "Every metal and make of tool or armor requires its own set of spirits to repair, and its own repair material. For an Impetus, the repair material " + italic("is") + " the spirits instead. The repair material must be placed on a Twisted Tablet facing the Crucible. Once the spirits coalesce, the item will be repaired somewhat.",
            "It appears that materials in tune with spirit arcana, such as Soulstained Steel or Hallowed Gold, are more efficient in this process. They will be repaired more than their mundane counterparts would for the same cost.");

        addEntryHeader("spirit_metals", "Spirit Metals", "Arcana refined");
        addHeadline("spirit_metals.hallowed_gold", "Hallowed Gold");
        addPages("spirit_metals.hallowed_gold",
            "Gold is often used as a thaumaturgical base, its natural conductivity of magic making it quite useful. Spirit arcana are no exception. In fact, using Sacred arcana, we can enhance those properties.",
            "Hallowed Gold, as a metal, acts much like its mundane counterpart. The inherent innocence of the arcana infused into the alloy makes other arcana glide through it smoothly, creating the perfect conductor for my purposes.");
        addHeadline("spirit_metals.hallowed_gold.spirit_jar", "Spirit Jar");
        addPages("spirit_metals.hallowed_gold.spirit_jar", "A simple application of this metal is the Spirit Jar. As spirits in their raw form don't have mass, by trapping them under Hallowed Gold you can store far more than you could physically. The capacity of these jars is near-infinite, though each only stores one type of spirit.");
        addHeadline("spirit_metals.soulstained_steel", "Soulstained Steel");
        addPages("spirit_metals.soulstained_steel",
            "Iron is mundane, in a word. By using Hex Ash as my source of carbon, and attuning the metal with Soulstone, I can create a metal that is " + italic("simultaneously") + " in and out of phase with the world.",
            "Anything made from Soulstained Steel is capable of striking the soul, without the need for specifics of engineering like with my crude scythe. Wearing the metal in its base form as armor is dangerous, as it will touch your own soul as well, so I must engineer a countermeasure.");

        addSimpleEntryHeader("soulstained_scythe", "Soulstained Scythe", "Reap");
        addPages("soulstained_scythe", "The scythe I created to harvest spirits was useful, but ultimately has outlived that usefulness. I have grown fond of the utility it provides, though, and so instead of discarding it I sought to improve it. With Soulstained Steel, I was able to create a more effective weapon and maintain the scythe's advantages.");

        addSimpleEntryHeader("soulstained_armor", "Soulstained Armor", "Spiritual protection");
        addPages("soulstained_armor",
            "Much like the Soulstained Scythe, I have improved upon my mundane iron armor to create the Soulstained Armor. To avoid the metal touching me directly, and so jostling and rubbing against my very soul, I used thin plates of Twisted Rock beneath the metal of the armor.",
            "As it exists in both the arcane and physical realms, Soulstained Steel exhibits fascinating defensive properties. It can intercept attacks from both, creating an effect I call Soul Ward. It takes time to restore if the effect is disrupted, but it acts as additional armor which nearly absorbs magic damage completely, and dampens physical damage.",
            "This effect seems similar in nature to others I have studied, such as engraving runes into armor or invoking a black sun upon oneself. Though unlike those, it doesn't " + italic("appear") + " to have a cost. Where is the energy for Soul Ward coming from?");

        addSimpleEntryHeader("spirit_trinkets", "Spirit Trinkets", "Accessorizing");
        addPages("spirit_trinkets",
            "Many disciplines of magic, and even more mundane practices, allow the creation of useful trinkets. These are also referred to as baubles or curios by some. The metals I have alloyed have properties useful in their own rights, and can be used as the basis for even grander designs.",
            "In their most basic form, Hallowed Gold trinkets protect the user as if they were wearing weak armor, and Soulstained Steel trinkets increase the toughness of the armor being worn.");
        addHeadline("spirit_trinkets.guardian_ring", "Ring of the Guardian");
        addPages("spirit_trinkets.guardian_ring",
            "As a first attempt to create a trinket using these as a base, I have designed one that mimics Soulstained Armor's Soul Ward. ");


        addEntryHeader("reactive_trinkets", "Reactive Trinkets", "Harnessing the harvest");
        addHeadline("reactive_trinkets.ring_of_curative_talent", "Ring of Curative Talent");
        addPages("reactive_trinkets.ring_of_curative_talent",
            "The trinkets documented within cause effects whenever a spirit crystal is collected, feeding off the excess energy. This trinket restores my health whenever I collect arcana, for example.");
        addHeadline("reactive_trinkets.ring_of_alchemical_mastery", "Ring of Alchemical Mastery");
        addPages("reactive_trinkets.ring_of_alchemical_mastery",
            "This ring, through alchemical trickery, is able to manipulate the potions running through my blood. Negative effects are filtered out, shortening their duration, while positive effects are maintained for longer than normal. Whenever I collect arcana, the ring momentarily works better.");
        addHeadline("reactive_trinkets.ring_of_prowess", "Ring of Prowess");
        addPages("reactive_trinkets.ring_of_prowess",
            "Brilliance is attached to the soul, but isn't an impulse like the arcana. It is accumulated knowledge, and so is not inherently tied to the soul that learned it. Even strikes which pass through the soul harmlessly are capable of dislodging it.",
            "By using condensed Brilliance, I have created a ring that filters out that Brilliance out of arcana I collect, giving me a burst of Brilliant knowledge whenever I collect arcana.");

        addSimpleEntryHeader("ring_of_esoteric_spoils", "Ring of Esoteric Spoils", "Be fruitful and multiply");
        addPages("ring_of_esoteric_spoils",
            "It can be tiring, harvesting the sheer quantities of arcana I need for my research. This ring can increase the efficiency of the harvest, allowing me to reap an additional spirit of each type from every soul shattered. At a certain point, though, \"efficiency\" ceases to explain it. How am I obtaining more power than the soul itself has?");

        addSimpleEntryHeader("belt_of_the_starved", "Belt of the Starved", "Channeling voracity");
        addPages("belt_of_the_starved",
            "The arcana I collect occasionally have scraps of wishes and desires woven in. Often, given the base nature of what I reap, this comes in the form of hunger, lust, or petty grudges. All of these can be harnessed, and beyond that, I can infuse this power into my magic.",
            "Doing this carries the perhaps predictable effect that my own hunger amplifies. And... by eating things which revile me, things which are rotten, I can satisfy my body's hunger without satisfying my soul's. The magic proficiency this grants is immense, but... I must say, the means are rather distasteful.");
        addHeadline("belt_of_the_starved.ring_of_desperate_voracity", "Ring of Desperate Voracity");
        addPages("belt_of_the_starved.ring_of_desperate_voracity",
            "To combat the toxic nature of these foods, I have created a ring to alleviate some of these effects. This ring makes me resistant to food poisoning... though it can't do anything about the taste.");

        addSimpleEntryHeader("necklace_of_the_mystic_mirror", "Mystic Mirror Necklace", "[NOT COMPLETE] Empowerment");
        addPages("necklace_of_the_mystic_mirror", "[temp effect] Makes reactive trinkets trigger when you deal magic damage");

        addEntryHeader("necklace_of_the_narrow_edge", "Necklace of the Narrow Edge", "Focused and sharpened");
        addHeadline("necklace_of_the_narrow_edge", "The Narrow Edge");
        addPages("necklace_of_the_narrow_edge",
            "The sweep of the scythe is its main draw. The ability to cut my targets like so much wheat is invaluable. But that comes at the cost of damage to a single target. This necklace mystically focuses the edge of my attack, directing all of the power into one target for a strong damage boost.");

        addSimpleEntryHeader("mirror_magic", "Mirror magic", "[NOT COMPLETE] Quick and easy transmission");
        addPages("mirror_magic", "Coming soon!");

        addSimpleEntryHeader("voodoo_magic", "Voodoo magic", "[NOT COMPLETE] Forbidden arts");
        addPages("voodoo_magic", "Coming soon!");

        addEntryHeader("altar_acceleration", "Altar Acceleration", "Obelisks");
        addHeadline("altar_acceleration.runewood_obelisk", "Runewood Obelisk");
        addPages("altar_acceleration.runewood_obelisk",
            "Spirit Infusion, as essential as it is, has grown... annoying. Even producing a stack of simple Hex Ash takes several minutes. Using a Hallowed Spirit Resonator, I have found a way to accelerate it, with up to a maximum of four placed near the altar increasing the processing speed.");
        addHeadline("altar_acceleration.brilliant_obelisk", "Brilliant Obelisk");
        addPages("altar_acceleration.brilliant_obelisk",
            "While not useful for Infusion, per-se, the design of the obelisk can be used in another way as well. By socketing Brilliance instead of a Resonator, the obelisk will harmonize with the Brilliance of enchanting, causing it to provide as much force of enchanting as five bookshelves do.");

        addSimpleEntryHeader("totem_magic", "Totem Magic", "Arcana unleashed");
        addPages("totem_magic",
            "Up until this point, when performing spirit arcana, I have limited my research to personal enhancement and material production. That has finally begun to change. Runewood accepts crystals almost hungrily, forming runes as it does.",
            "If unwanted, they can be stripped off with an axe, but I have uses in mind. With a Runewood Totem Base, and then a specific set of runes in a Runewood Log pillar reaching up to five blocks above it, I can make a Spirit Rite. These rituals soak power into the world around them, changing the beings or blocks in the area.",
            "For what I term \"aura rites\", they affect anything within eight blocks of the base. For other rituals which alter beings, most affect anything within four blocks of the base. For those which alter blocks, most affect the five-by-five area on the level beneath the base, towards where the runes are facing.",
            "A ritual that affects blocks can also be made more selective with the use of Item Stands. By placing them on the sides of the rune pillars, you can make the ritual only recognize and alter the types of blocks lying on the stands.",
            "One caveat is that no two identical rituals can have their areas overlap. If you try, the first one will simply fizzle out, the energies coursing through it disrupted.");

        //uncharted territory down 'ere

        addSimpleEntryHeader("arcane_rite", "A Rite Unchained", "Creation uncontrolled");
        addPages("arcane_rite.description",
            "Raw arcana provides the basis for all rites. Without power, nothing would be accomplished. This naturally makes one wonder what effect raw arcana would have as the focus of a rite. The answer is a complex and dangerous one.",
            "It requires far more to focus than other rites, taking the entire five runes to activate. It's as though I am pushing on some threshold, and need to break through. And in breaking through... momentum is conserved.",
            "And once altered, the wood's color changes, along with its magical disposition. What I dub Soulwood bears scars from the violent method of its creation. Those scars warp magic, altering its fundamental nature. And, since the totem was already performing the Undirected Rite...");
        addPage("arcane_rite",
            "The rite - if you could call something so chaotic that - explodes and burns through the totem, altering its very base nature, and transmuting the world around it into some indeterminate blighted powder.");
        addPage("arcane_rite.corrupt",
            "Now already scarred, the power bleeds from the totem, corrupting and warping blocks before it that are placed upon that blighted substance or Soulwood pedestals. Unfiltered, this is useless, and always ends in more blight. [NYI]");

        addEntryHeader("sacred_rite", "Sacred Rites", "Invigorating the soul");
        addPage("sacred_rite", "Nearby friendly beings are slowly healed.");
        addPage("sacred_rite.greater", "By twisting the power of vigor, you can cause crops planted on soil before the totem base to grow more quickly.");

        addEntryHeader("corrupt_sacred_rite", "Corrupting the Sacred Rites", "Stimulating the soul");
        addPage("corrupt_sacred_rite", "Nearby animals are nourished spiritually, making them grow up faster.");
        addPage("corrupt_sacred_rite.greater", "Nearby animals are made... " + italic("vigorous") + ", as if I had fed them myself.");

        addEntryHeader("wicked_rite", "Wicked Rites", "Maligning the soul");
        addPage("wicked_rite", "Nearby hostile beings are slowly brought to within an inch of death.");
        addPage("wicked_rite.greater", "By twisting the power of malice like a knife, nearby beings on the brink of death are dealt a fatal blow, shattering their soul.");

        addEntryHeader("corrupt_wicked_rite", "Corrupting the Wicked Rites", "Endangering the soul");
        addPage("corrupt_wicked_rite", "Rather than harm, this rite enhances nearby hostile beings, granting protection, force, and speed. Rather useless, but might have niche applications.");
        addPage("corrupt_wicked_rite.greater", "This rite is rather cruel, but necessary. It culls my herds, completely annihilating animals who would otherwise overcrowd.");

        addEntryHeader("aerial_rite", "Aerial Rites", "Uplifting the soul");
        addPage("aerial_rite", "A simple aura rite, nearby friendly beings will find their movements sped up.");
        addPage("aerial_rite.greater", "By twisting the power of the air, blocks before the totem will be made to fall as though they were sand.");

        addEntryHeader("corrupt_aerial_rite", "Corrupting the Aerial Rites", "Scattering the soul");
        addPage("corrupt_aerial_rite", "A simple aura rite, nearby friendly beings will have their connection to the earth disrupted, lowering their gravity.");
        addPage("corrupt_aerial_rite.greater", "Slowly eases the stress of time on the mind, offsetting the effects of insomnia for those around it.");

        addEntryHeader("earthen_rite", "Earthen Rites", "Grounding the soul");
        addPage("earthen_rite", "A simple aura rite, nearby friendly beings will find their bodies are tougher and more resistant to damage.");
        addPage("earthen_rite.greater", "By twisting the power of the earth, you can cause blocks before the totem base to be broken.");

        addEntryHeader("corrupt_earthen_rite", "Corrupting the Earthen Rites", "Honing the soul");
        addPage("corrupt_earthen_rite", "A simple aura rite, nearby friendly beings will find their attacks deal more damage.");
        addPage("corrupt_earthen_rite.greater", "The earth coalesces, and like lava meeting water, cobblestone is created before the totem base.");

        addEntryHeader("infernal_rite", "Infernal Rites", "Igniting the soul");
        addPage("infernal_rite", "A simple aura rite, nearby friendly beings will find that their motions are infused with fiery vigor, letting them swing weapons and tools faster.");
        addPage("infernal_rite.greater", "By twisting the power of fire, you can cause blocks before the totem base to be smelted.");

        addEntryHeader("corrupt_infernal_rite", "Corrupting the Infernal Rites", "Extinguishing the soul");
        addPage("corrupt_infernal_rite", "Nearby friendly beings and close fires will have the heat sucked out of them, extinguishing them and healing those who were burned.");
        addPage("corrupt_infernal_rite.greater", "Instead of generating heat, this rite compresses it, causing furnaces to operate more quickly.");

        addEntryHeader("aqueous_rite", "Aqueous Rites", "Molding the soul");
        addPage("aqueous_rite", "A simple aura rite, nearby friendly beings will find that their reach is extended, letting them more easily interact with the world.");
        addPage("aqueous_rite.greater", "By twisting the power of the water, you can squeeze more fluids from the air, vastly increasing the drip speed of dripstone.");

        addEntryHeader("corrupt_aqueous_rite", "Corrupting the Aqueous Rites", "Deforming the soul");
        addPage("corrupt_aqueous_rite", "A simple aura rite, nearby friendly beings will find themselves better at fishing.");
        addPage("corrupt_aqueous_rite.greater", "Zombies near this rite will find themselves choking on their own breath, drowning even on land.");

        addSimpleEntryHeader("soulwood", "Soulwood", "[NOT COMPLETE] Twisted Roots");
        addPages("soulwood", "Spirit magics focus on turning everything good in the world to evil. Sometimes, by pure coincidence a great thing is created instead, as is the case with runewood. It is important to realize this mistake and correct it.",
            "By utilizing the dangerous arcane spirit rite I can easily create soulwood, any nearby runewood block is a valid target for this transmutation. Much like it's name implies, soulwood has a strong connection to more direct soul magics. It'll be sure to come in use.");

        addSimpleEntryHeader("tyrving", "Tyrving", "[NOT COMPLETE] Ancient Relic");
        addPages("tyrving", "The Tyrving is a rather esoteric blade. It's strange design makes it appear as a weak weapon not suited for combat. However, it's hex ash lining and twisted rock form cause it to deal extra magic damage to the soul, the greater the soul the more benefit.",
            "The weapon can also be repaired using arcane restoration quite efficiently.");

        addSimpleEntryHeader("magebane_belt", "Magebane Belt", "[NOT COMPLETE] Retaliation");
        addPages("magebane_belt", "By twisting a warded belt into it's rather sinister alter ego I may create the magebane belt. This alteration exchanges defense for offense, providing greater magic resistance and extra soul ward. Additionally, any damage absorbed by soul ward will make it's way to the inflicter.");

        addSimpleEntryHeader("ceaseless_impetus", "Ceaseless Impetus", "[NOT COMPLETE] Rebirth");
        addPages("ceaseless_impetus", "The totem of undying is a very interesting artifact sought out by many, it seems to be an effigy for some sort of greater god, an opposition to an undocumented evil it seems. By utilizing advanced sacred spirit arcana I can feed into this wonder and alter it's effect.",
            "In addition to a more sturdy design made out of hallowed gold, the ceaseless impetus allows for two uses before needing repair. This however results in the phoenix blessing effect being generally weaker, needing activations in quick succession to match it's former glory.");

        addSimpleEntryHeader("the_device", "The Device.", "microwave to recharge");
        addPages("the_device", "even works while bended");

        add("malum.spirit.description.stored_spirit", "Contains: ");
        add("malum.spirit.description.stored_soul", "Stores Soul With: ");

        add("malum.spirit.flavour.sacred", "Innocent");
        add("malum.spirit.flavour.wicked", "Malicious");
        add("malum.spirit.flavour.arcane", "Creation");
        add("malum.spirit.flavour.eldritch", "Outside");
        add("malum.spirit.flavour.aerial", "Swift");
        add("malum.spirit.flavour.aqueous", "Malleable");
        add("malum.spirit.flavour.infernal", "Radiant");
        add("malum.spirit.flavour.earthen", "Steady");

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

        add("death.attack." + DamageSourceRegistry.VOODOO_DAMAGE, "%s's soul shattered");
        add("death.attack." + DamageSourceRegistry.VOODOO_DAMAGE + ".player", "%s's soul was shattered by %s");
        add("death.attack." + DamageSourceRegistry.FORCED_SHATTER_DAMAGE, "%s's soul shattered");
        add("death.attack." + DamageSourceRegistry.FORCED_SHATTER_DAMAGE + ".player", "%s's soul was shattered by %s");
        add("death.attack." + DamageSourceRegistry.MAGEBANE_DAMAGE, "%s got too confident with a soul hunter");
        add("death.attack." + DamageSourceRegistry.MAGEBANE_DAMAGE + ".player", "%s got too confident with %s");
        add("death.attack." + DamageSourceRegistry.SCYTHE_SWEEP_DAMAGE, "%s was sliced in twain");
        add("death.attack." + DamageSourceRegistry.SCYTHE_SWEEP_DAMAGE + ".player", "%s was sliced in twain by %s");

        addEffectDescription(MalumMobEffectRegistry.GAIAN_BULWARK, "You are protected by an earthen bulwark, increasing your armor.");
        addEffectDescription(MalumMobEffectRegistry.EARTHEN_MIGHT, "Your fists and tools are reinforced with earth, increasing your overall damage.");
        addEffectDescription(MalumMobEffectRegistry.MINERS_RAGE, "Your tools are bolstered with radiance, increasing your mining and attack speed.");
        addEffectDescription(MalumMobEffectRegistry.IFRITS_EMBRACE, "The warm embrace of fire coats your soul, mending your seared scars.");
        addEffectDescription(MalumMobEffectRegistry.ZEPHYRS_COURAGE, "The zephyr propels you forward, increasing your movement speed.");
        addEffectDescription(MalumMobEffectRegistry.AETHERS_CHARM, "The heavens call for you, increasing jump height and decreasing gravity.");
        addEffectDescription(MalumMobEffectRegistry.POSEIDONS_GRASP, "You reach out for further power, increasing your reach and item pickup distance.");
        addEffectDescription(MalumMobEffectRegistry.ANGLERS_LURE, "Let any fish who meets my gaze learn the true meaning of fear; for I am the harbinger of death. The bane of creatures sub-aqueous, my rod is true and unwavering as I cast into the aquatic abyss. A man, scorned by this uncaring Earth, finds solace in the sea. My only friend, the worm upon my hook. Wriggling, writhing, struggling to surmount the mortal pointlessness that permeates this barren world. I am alone. I am empty. And yet, I fish.");
        addEffectDescription(MalumMobEffectRegistry.GLUTTONY, "You feed on the vulnerable, increasing scythe proficiency and gradually restoring lost hunger.");

        addTetraMaterial("soul_stained_steel", "Soulstained Steel");
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

    public String makeProper(String s) {
        s = s
            .replaceAll("Of", "of")
            .replaceAll("The", "the")
            // Temp
            .replaceAll("Soul Stained", "Soulstained")
            .replaceAll("Soul Hunter", "Soulhunter");
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public String obfuscate(String s) {
        return ChatFormatting.OBFUSCATED + s + ChatFormatting.RESET;
    }

    public String italic(String s) {
        return ChatFormatting.ITALIC + s + ChatFormatting.RESET;
    }

    public String bold(String s) {
        return ChatFormatting.BOLD + s + ChatFormatting.RESET;
    }

    public String strike(String s) {
        return ChatFormatting.STRIKETHROUGH + s + ChatFormatting.RESET;
    }

    public String underline(String s) {
        return ChatFormatting.UNDERLINE + s + ChatFormatting.RESET;
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

    public void addEntryHeader(String identifier, String name, String description) {
        add("malum.gui.book.entry." + identifier, name);
        addDescription(identifier, description);
    }

    public void addSimpleEntryHeader(String identifier, String name, String description) {
        addHeadline(identifier, name);
        addEntryHeader(identifier, name, description);
    }

    public void addPage(String identifier, String tooltip) {
        add("malum.gui.book.entry.page.text." + identifier, tooltip);
    }

    public void addPages(String identifier, String... tooltip) {
        int i = 1;
        for (String s : tooltip) {
            addPage(identifier + "." + i++, s);
        }
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

    public void addEffectDescription(Supplier<MobEffect> mobEffectSupplier, String description) {
        add(mobEffectSupplier.get().getDescriptionId() + ".description", description);
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
