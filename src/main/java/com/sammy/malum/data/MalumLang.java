package com.sammy.malum.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.block.ether.EtherWallTorchBlock;
import com.sammy.malum.common.item.spirit.SpiritJarItem;
import com.sammy.malum.core.systems.rites.*;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.data.PackOutput;
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
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.helpers.DataHelper;

import java.util.*;
import java.util.function.Supplier;

import static com.sammy.malum.registry.common.AttributeRegistry.ATTRIBUTES;
import static com.sammy.malum.registry.common.MobEffectRegistry.EFFECTS;
import static com.sammy.malum.registry.common.SoundRegistry.SOUNDS;
import static com.sammy.malum.registry.common.block.BlockRegistry.BLOCKS;
import static com.sammy.malum.registry.common.entity.EntityRegistry.ENTITY_TYPES;
import static com.sammy.malum.registry.common.item.EnchantmentRegistry.ENCHANTMENTS;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class MalumLang extends LanguageProvider {
    public MalumLang(PackOutput gen) {
        super(gen, MalumMod.MALUM, "en_us");
    }

    @Override
    protected void addTranslations() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        Set<RegistryObject<SoundEvent>> sounds = new HashSet<>(SOUNDS.getEntries());
        Set<RegistryObject<Enchantment>> enchantments = new HashSet<>(ENCHANTMENTS.getEntries());
        Set<RegistryObject<MobEffect>> effects = new HashSet<>(EFFECTS.getEntries());
        Set<RegistryObject<Attribute>> attributes = new HashSet<>(ATTRIBUTES.getEntries());
        Set<RegistryObject<EntityType<?>>> entities = new HashSet<>(ENTITY_TYPES.getEntries());
        List<MalumSpiritType> spirits = new ArrayList<>(SpiritTypeRegistry.SPIRITS.values());

        add(DataHelper.take(blocks, BlockRegistry.PRIMORDIAL_SOUP).get(), "The Weeping Well");
        add(DataHelper.take(blocks, BlockRegistry.VOID_CONDUIT).get(), "The Weeping Well");

        DataHelper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof EtherWallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallSignBlock);
        blocks.forEach(b ->
        {
            String name = b.get().getDescriptionId().replaceFirst("block\\.malum\\.", "");
            name = makeProper(DataHelper.toTitleCase(correctBlockItemName(name), "_"));
            add(b.get().getDescriptionId(), name);
        });
        DataHelper.getAll(items, i -> i.get() instanceof SpiritJarItem).forEach(i -> {
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

        sounds.forEach(s -> {
            String name = correctSoundName(s.getId().getPath()).replaceAll("_", " ");
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            add("malum.subtitle." + s.getId().getPath(), name);
        });

        enchantments.forEach(e -> {
            String name = DataHelper.toTitleCase(e.getId().getPath(), "_");
            add(e.get().getDescriptionId(), name);
        });

        effects.forEach(e -> {
            String[] replacements = new String[]{"ns_", "rs_", "ts_"};
            String alteredPath = e.getId().getPath();
            for (String replacement : replacements) {
                int index = alteredPath.indexOf(replacement);
                if (index != -1) {
                    alteredPath = alteredPath.replaceFirst("s_", "'s_");
                    break;
                }
            }
            String name = DataHelper.toTitleCase(alteredPath, "_");
            add("effect.malum." + ForgeRegistries.MOB_EFFECTS.getKey(e.get()).getPath(), name);
        });

        attributes.forEach(a -> {
            String name = DataHelper.toTitleCase(a.getId().getPath(), "_");
            add("attribute.name.malum." + ForgeRegistries.ATTRIBUTES.getKey(a.get()).getPath(), name);
        });

        entities.forEach(e -> {
            String name = DataHelper.toTitleCase(e.getId().getPath(), "_");
            add("entity.malum." + ForgeRegistries.ENTITY_TYPES.getKey(e.get()).getPath(), name);
        });

        spirits.forEach(s -> add(s.getSpiritDescription(), DataHelper.toTitleCase(s.identifier + "_spirit", "_")));

        for (CrucibleTuning.CrucibleAttributeType value : CrucibleTuning.CrucibleAttributeType.values()) {
            if (value.equals(CrucibleTuning.CrucibleAttributeType.NONE)) {
                continue;
            }
            final String translation = value.translation();
            String name = DataHelper.toTitleCase(value.toString().toLowerCase(Locale.ROOT), "_");
            add(translation, name);
        }
        add("malum.gui.crucible.attribute.weakest_boost", "Weakest Boost");
        add("malum.gui.crucible.attribute.tuning_potency", "Tuning Potency");

        add("malum.gui.augment.slot", "Slot: ");
        add("malum.gui.augment.installed", "When installed: ");
        add("malum.gui.augment.type.augment", "Augment");
        add("malum.gui.augment.type.core_augment", "Core Augment");


        add("malum.gui.rite.type", "Type: ");
        add("malum.gui.rite.coverage", "Coverage: ");

        addRiteEffectCategory(MalumRiteEffect.MalumRiteEffectCategory.AURA);
        addRiteEffectCategory(MalumRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT);
        addRiteEffectCategory(MalumRiteEffect.MalumRiteEffectCategory.DIRECTIONAL_BLOCK_EFFECT);
        addRiteEffectCategory(MalumRiteEffect.MalumRiteEffectCategory.RADIAL_BLOCK_EFFECT);
        addRiteEffectCategory(MalumRiteEffect.MalumRiteEffectCategory.ONE_TIME_EFFECT);

        addRite(SpiritRiteRegistry.SACRED_RITE, "Rite of Healing", "Rite of Nourishment");
        addRite(SpiritRiteRegistry.WICKED_RITE, "Rite of Decay", "Rite of Empowerment");
        addRite(SpiritRiteRegistry.EARTHEN_RITE, "Rite of Warding", "Rite of the Challenger");
        addRite(SpiritRiteRegistry.INFERNAL_RITE, "Rite of Haste", "Rite of the Extinguisher");
        addRite(SpiritRiteRegistry.AERIAL_RITE, "Rite of Motion", "Rite of the Aether");
        addRite(SpiritRiteRegistry.AQUEOUS_RITE, "Rite of Loyalty", "Rite of the Seas");

        addRite(SpiritRiteRegistry.ARCANE_RITE, "Undirected Rite", "Unchained Rite");

        addRite(SpiritRiteRegistry.ELDRITCH_SACRED_RITE, "Rite of Growth", "Rite of Lust");
        addRite(SpiritRiteRegistry.ELDRITCH_WICKED_RITE, "Rite of Exorcism", "Rite of Culling");
        addRite(SpiritRiteRegistry.ELDRITCH_EARTHEN_RITE, "Rite of Destruction", "Rite of Shaping");
        addRite(SpiritRiteRegistry.ELDRITCH_INFERNAL_RITE, "Rite of Smelting", "Rite of Quickening");
        addRite(SpiritRiteRegistry.ELDRITCH_AERIAL_RITE, "Rite of Gravity", "Rite of Unwinding");
        addRite(SpiritRiteRegistry.ELDRITCH_AQUEOUS_RITE, "Rite of Sapping", "Rite of Drowning");

        add("malum.gui.ritual.type", "Ritual Type: ");
        add("malum.gui.ritual.tier", "Ritual Tier: ");

        for (MalumRitualType ritualType : RitualRegistry.RITUALS) {
            final String id = ritualType.identifier.getPath();
            String name = DataHelper.toTitleCase(id, "_");
            add("malum.gui.ritual." + id, name);
        }
        for (MalumRitualTier ritualTier : MalumRitualTier.TIERS) {
            final String id = ritualTier.identifier.getPath();
            String name = DataHelper.toTitleCase(id, "_");
            add("malum.gui.ritual.tier." + id, name);
        }


        add("curios.identifier.brooch", "Brooch");
        add("curios.modifiers.brooch", "When worn:");

        add("curios.identifier.rune", "Rune");
        add("curios.modifiers.rune", "When equipped:");

        add("malum.gui.curio.positive", "+%s");
        add("malum.gui.curio.negative", "-%s");

        add("malum.gui.curio.effect.passive_healing", "Passive Healing");
        add("malum.gui.curio.effect.scythe_chain", "Scythe Kill Chaining");
        add("malum.gui.curio.effect.erratic_damage", "Erratic Damage Output");
        add("malum.gui.curio.effect.crits", "Critical Strikes");
        add("malum.gui.curio.effect.silence", "Silences Attackers");
        add("malum.gui.curio.effect.extend_positive_effect", "Extends Positive Effects");
        add("malum.gui.curio.effect.shorten_negative_effect", "Shortens Negative Effects");
        add("malum.gui.curio.effect.attacked_resistance", "Damage Resistance When Attacked");
        add("malum.gui.curio.effect.low_health_speed", "Speed at Low Health");
        add("malum.gui.curio.effect.always_sprint", "Sprinting Always Available");
        add("malum.gui.curio.effect.haste", "Increased Mining Speed");
        add("malum.gui.curio.effect.burning_resistance", "Damage Resistance While Burning");

        add("malum.gui.curio.effect.spirits_heal", "Spirit Collection Replenishes Health");
        add("malum.gui.curio.effect.spirits_xp", "Spirit Collection Generates Experience Points");
        add("malum.gui.curio.effect.spirits_extend_effect", "Spirit Collection Aids Potion Durations");
        add("malum.gui.curio.effect.spirits_add_health", "Spirit Collection Grants Extra Hearts");
        add("malum.gui.curio.effect.hunger_drain", "Actively Drains Hunger");
        add("malum.gui.curio.effect.eat_rotten", "Rotten Foods are Tastier");
        add("malum.gui.curio.effect.growing_gluttony", "Eating Rotten Foods Extends Gluttony");
        add("malum.gui.curio.effect.explosion_drops_collected", "Automatic Collection of Explosion Drops");
        add("malum.gui.curio.effect.bigger_explosions", "Improves Explosions");
        add("malum.gui.curio.effect.better_conduit_power", "Conduit Power Provides Numerous Benefits");
        add("malum.gui.curio.effect.no_sweep", "Disables Scythe Sweeping");
        add("malum.gui.curio.effect.friendly_enemies", "Reduces Enemy Aggression");
        add("malum.gui.curio.effect.soul_ward_magic_resilience", "Soul Ward Magic Resilience");
        add("malum.gui.curio.effect.rotten_gluttony", "Rotten Food Generates Gluttony");
        add("malum.gui.curio.effect.scythe_counterattack", "Powerful Scythe Counterattack When Struck");
        add("malum.gui.curio.effect.full_health_fake_collection", "Striking Full Health Targets Triggers Spirit Collection Effects");
        add("malum.gui.curio.effect.soul_ward_physical_absorption", "Soul Ward Absorbs Physical Damage Equally to Magic Damage");
        add("malum.gui.curio.effect.spirits_gluttony", "Spirit Collection Generates Gluttony");
        add("malum.gui.curio.effect.gluttony_magic_proficiency", "Gluttony Exchanges Hunger for Magic Proficiency");
        add("malum.gui.curio.effect.enchanted_explosions", "Explosions are Enchanted with %s");
        add("malum.gui.curio.effect.explosions_spare_valuables", "Protects Valuable Items from Explosions");

        add("malum.gui.rite_effect", "Effect: ");

        addSimpleEntryHeader("chronicles_of_the_void", "Chronicles of the Void", "A magecraft of madness");
        addSimpleEntryHeader("chronicles_of_the_soul", "Chronicles of the Soul", "A magecraft of miracles");

        addSimpleEntryHeader("void.introduction", "Introduction", "Secrets uncovered");
        addPages("void.introduction",
            "\"The soul's existence creates the body, as the body's existence then informs the soul. One cannot exist without the other, but could a soul be diminished? Expanded? Bound to a new vessel, leaving a husk in its wake?\"",
            "I appear to have stumbled onto an inexplicable new branch of magic, one that is built further on the operation of souls. Writings come close, but none fit the mechanics I have now seen at play. My soul itself seems to... " + italic("stir") + " at these acts of magecraft, shying away, yet eagerly seeking more. I do not think I could consider myself a magus were I to forgo this opportunity.");

        addSimpleEntryHeader("void.null_slate", "Null Slate", "Original form");
        addPages("void.null_slate",
            "Soulstone when thrown into the weeping well nets you null slate, a material suspected to be the true form of soulstone. Pretend that this description is a lot more cryptic and imagine there being some cool page on the right.");

        addSimpleEntryHeader("void.void_salts", "Void Salts", "Necrotic powder");
        addPages("void.void_salts",
            "Hex ash when thrown into the weeping well nets you void salts, a weird and seemingly alive powder. Pretend that this description is a lot more cryptic and imagine there being some cool page on the right.");

        addSimpleEntryHeader("void.auric_embers", "Auric Embers", "Potent flames");
        addPages("void.auric_embers",
            "Blaze Powder when thrown into the weeping well nets you Auric Embers, a demonic essence of fire. Pretend that this description is a lot more cryptic and imagine there being some cool page on the right.");

        addSimpleEntryHeader("void.mnemonic_fragment", "Mnemonic Fragment", "Memories solidified");
        addPages("void.mnemonic_fragment",
            "Brilliance Clusters or Chunks when thrown into the weeping well nets you Mnemonic Fragments, a material full of memories turned into matter. Pretend that this description is a lot more cryptic and imagine there being some cool page on the right.");

        addSimpleEntryHeader("void.soul_stained_steel_staff", "Soulstained Staff", "Spell-casting");
        addPages("void.soul_stained_steel_staff",
            "Staves are hybrid melee and ranged weapons. They swing faster than scythes but deal less physical damage. The simplest one is the Mnemonic Hex Staff.",
            "What makes staves special is the right click functionality, the right click function lets you shoot long range projectiles. This ranged attack has a relatively short cooldown, and requires a one second charge-up window before being fired.",
            "The Mnemonic Hex Staff in particular allows the user to lob a burst of 3 gravity affected hex bolts, each dealing 100% of the staff's magical damage output.");
        addHeadline("void.ring_of_the_plentiful", "Ring of the Plentiful");
        addPages("void.ring_of_the_plentiful",
            "This ring will stockpile up to 3 reserve staff charges, which when present are consumed to avoid placing a cooldown on the staff's ranged functionality. Each staff charge takes 15 seconds to recover, and currently there's no indication for it.");

        addSimpleEntryHeader("void.ring_of_growing_flesh", "Ring of Growing Flesh", "Cancerous growths");
        addPages("void.ring_of_growing_flesh",
            "The ring will give you a stacking max health buff every time you collect spirits, at full potency doubling your max health. The buff lasts quite a long time.");

        addSimpleEntryHeader("void.anomalous_design", "Anomalous Design", "Unknown origins");
        addPages("void.anomalous_design",
            "Weird cult like symbol made by throwing an iron block into the weeping well. Lore wise the idea is that like, the entity behind the weeping well instructs you to actually create this thing and just, feed it the spirits and stuff. ");

        addSimpleEntryHeader("void.fused_consciousness", "Fused Consciousness", "Magic and mind");
        addPages("void.fused_consciousness",
            "Cool arguably expensive material obtained by throwing the complete design into the weeping well. ");

        addSimpleEntryHeader("void.belt_of_the_limitless", "Belt of the Limitless", "Imaginary strength");
        addPages("void.belt_of_the_limitless",
            "powerful belt item thing. Doubles ur soulward and also makes it absorb physical damage as well as magical damage.");

        addSimpleEntryHeader("void.staff_of_the_auric_flame", "Staff of the Auric Flame", "Seeking flames");
        addPages("void.staff_of_the_auric_flame",
            "staff upgrade! Longer cooldown, shoots 5 homing fire bolts, deals more magic damage. Also sets things on fire but not with the melee attack which will be changed I'm just lazy.");

        addSimpleEntryHeader("void.stellar_mechanism", "Stellar Mechanism", "Finer tuning");
        addPages("void.stellar_mechanism",
            "powerful crucible augment. Doubles the strength of the bonus applied by the tuning fork, without affecting the negative side of things. It's a core augment, which means it can only be slotted in the crucible itself, and is stored in a separate inventory than the one used for regular augments.");


        addSimpleEntryHeader("introduction", "Introduction", "On the nature of souls");
        addPages("introduction",
            "\"Within our world, every living being has a soul. That soul is consciousness, what animates the body, and the meeting point between matter and magic. These represent our existence; as the body is presented to the physical world, so the soul is to the arcane.\"",
            "I seem to have stumbled upon something peculiar: a form of magic so far undocumented. I could hardly call myself a magus if I refused the opportunity to study it. In this codex, the Encyclopedia Arcana, I write my research into this power, hoping to document everything about it.",
            "The energies this thaumaturgical discipline manipulates seem to be rooted in the soul. More accurately, they are the energies of the soul, the inclinations and impulses that make up each one of us.",
            "So far, what I have described is basic. But I have found a way to separate, and then condense, the impulse of a soul into a physical form I call a spirit crystal. This forms the basis of my research.",
            "The natures of the soul I condense influence the crystal's properties. Each soul is slightly different, and that can result in changes to the crystals formed. I believe these spirit crystals to be just the breakthrough I need.");

        addSimpleEntryHeader("spirit_crystals", "Spirit Crystals", "Matter and magic");
        addPages("spirit_crystals",
            "The soul is a notoriously fickle thing. Even confirming its existence is difficult, requiring the highest thaumaturgies to get a reading. That is what sets spirit arcana apart from other magic. We don't need grand assemblies and esoteric artifice to see a soul. Simply destroying it is proof enough.",
            "A material I have named Soulstone is the means by which we do so. It appears mundane until refined, but once it is rid of impurities, it seems... out of phase with the world. By creating a blade using it as a core, I should be able to strike not only the physical form, but also the soul, shattering it to energy before it can disperse.",
            "These energies, as previously noted, have different 'frequencies' of sorts. A being burning with light would have a soul that reflects that radiance, and a being prone to adaptation would have a soul as malleable as itself. Occasionally, the energy has no flavor to it at all, leaving only the raw impulse of creation behind. That type of crystal bears further study.");

        addSimpleEntryHeader("soulstone", "Soulstone", "Out of phase");
        addPages("soulstone",
            "Sometimes, it appears that matter can be charged with the energies of a soul, despite not having a soul of its own.",
            "This serves as the basis for spirit arcana - the ensouling of the soulless. Soulstone is an ore that exists more in the arcane than the physical, and, refined, presents many uses for my magic. It strongly radiates magic.");

        addSimpleEntryHeader("natural_quartz", "Natural Quartz", "Deep in the earth");
        addPages("natural_quartz",
            "Natural Quartz is, as the name implies, a natural equivalent of the nether resource. It's used for most of the same things. It's rare, and found deep underground, sometimes in geodes.");

        addSimpleEntryHeader("cthonic_gold", "Cthonic Gold", "Fused with the arcane");
        addPages("cthonic_gold",
            "Cthonic Gold is a strange yet useful metal. Its physical makeup is that of gold, yet its properties are entirely distinct. The ore is found deep underground in the deepslate layer of the world, rooted deeply into existing veins of gold.",
            "Physically, Cthonic Gold resembles pyrite, albeit with the density of true gold. The altered nature of the metal appears to derive from a mix of earthen and infernal arcana somehow bonded to its physical structure, creating a strange alloy. Arcana does not normally interact with metal in this way, at least in my experiments.",
            "The alloy of physical and metaphysical causes this material to serve as a bridging point, a gate between realms, so to speak. Or perhaps a guardian of those gates? It exists as purely physical, yet the arcane acknowledges its passage.",
            "I'm not sure what use this metal will have quite yet, but I doubt I will be short for applications for a material with such atypical properties.");

        addSimpleEntryHeader("runewood", "Runewood", "Arcane oak");
        addPages("runewood",
            "Runewood is a strange mix of magic and nature, and a fairly common one at that. While pretty, I am more interested in practicality. Runewood is soaked in magic, and as such, can serve as the basis for the arcane.",
            "The tree is predominantly found within large open plains, however it can also be found in forests. The tree can be best identified by it's leaves, proudly displaying an orange-yellow palette.");
        addHeadline("runewood.arcane_charcoal", "Arcane Charcoal");
        addPages("runewood.arcane_charcoal",
            "Runewood's charcoal, as magic-infused as it is, burns with an arcane fervor for longer than regular charcoal. This makes it rather useful for fueling any smelting I need to do.");
        addHeadline("runewood.runic_sap", "Runic Sap");
        addPages("runewood.runic_sap",
            "Runewood trees tend to have a buildup of sticky sap on the sides of their logs. When this happens, if you strip off the bark, you'll be able to bottle the sap, which makes for a rejuvenating drink.",
            "The sap can also be used to create sapballs, able to be used interchangeably with slimeballs for most recipes. Runic sap can also be turned into a block form, which is rather sticky.");

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


        addSimpleEntryHeader("esoteric_reaping", "Esoteric Reaping", "Leaked magic");
        addPages("esoteric_reaping",
            "When a being dies, its soul disperses. This is basic theory, and well proven by this point. It's been proposed that sometimes, that power leaks into the body of the creature as it dies, to explain the existence of reagents they drop. That hadn't been proven yet.",
            "But now, with my scythe, I have proved it beyond doubt. When a soul is shattered, even if only for a brief moment, the energy collides with what's left of it's vessel. This phenomena appears to create a strong reaction, a change of sorts.",
            "I have discovered four reagents born through this process, which I will detail further in this entry. In summary, the flesh of zombies can curdle to Rotting Essence; the bones of skeletons can crystallize to Grim Talc; the wings of phantoms can spin to Astral Weave; and the magic of endermen can coalesce into Warp Flux.");
        addHeadline("esoteric_reaping.rotting_essence", "Rotting Essence");
        addPages("esoteric_reaping.rotting_essence",
            "When exposed to this magic, the flesh of the undead can curdle into Rotting Essence, a toxic and foul substance that smells like death itself.");
        addHeadline("esoteric_reaping.grim_talc", "Grim Talc");
        addPages("esoteric_reaping.grim_talc",
            "Bones exposed to this magic can crystallize into Grim Talc, a useful mineral that can also be broken down into bonemeal.");
        addHeadline("esoteric_reaping.astral_weave", "Astral Weave");
        addPages("esoteric_reaping.astral_weave",
            "The membrane of a phantom will spin into Astral Weave with this magic, a mystic cloth with strange arcane properties.");
        addHeadline("esoteric_reaping.warp_flux", "Warp Flux");
        addPages("esoteric_reaping.warp_flux",
            "The magic that envelops the endermen coalesces into Warp Flux, a strange essence that seems to be inimical to natural law.");

        addSimpleEntryHeader("spirit_infusion", "Spirit Infusion", "Creation of wonders");
        addPages("spirit_infusion",
            "By using Runewood's natural magic as a base, I have designed the altar that will serve as the basis for my magecraft - the Spirit Altar. It is the other piece of the equation, the use for the arcana. By infusing them into items, and using the energies to effect other fusions, I can begin to explore this.",
            "To use the altar, I must lay the item I wish to infuse on top of it, along with an appropriate set of arcana. If I wish to fuse other items in the process, I must place them on some form of Runewood item holder. They must be within four blocks of the altar to work.",
            "Once all the arcana are present, the power within the crystals will begin to flow into the central item. If other items are fused in, they are pulled in during this process. When all of that is done, the product of the infusion will appear. It " + italic("is") + " rather slow, though...");
        addHeadline("spirit_infusion.hex_ash", "Hex Ash");
        addPages("spirit_infusion.hex_ash",
            "My first product with this process is a powder I call Hex Ash, after its color. It is a simple and useful grit, with the niter and sulfur mostly transmuted by the raw arcana, leaving a mixture of reagent and carbon.");
        addHeadline("spirit_infusion.living_flesh", "Living Flesh");
        addPages("spirit_infusion.living_flesh",
            "Next, for the sake of understanding how spirit arcana interacts with living substance, I have created... " + italic("something") + " which is now known as Living Flesh. It is a disgusting meaty chunk completely unfit for human consumption. Who knows if I'll end up finding a proper use for it.");
        addHeadline("spirit_infusion.alchemical_calx", "Alchemical Calx");
        addPages("spirit_infusion.alchemical_calx",
            "Lastly, I have created an experimental substrate I named Alchemical Calx. It's initially strong and tallow-like, but when met with a lesser amount of force it turns extremely malleable. It's bound to prove an useful ingredient.");

        addEntryHeader("primary_arcana", "Primary Arcana", "The components of magic");
        addHeadline("primary_arcana.sacred", "Sacred Spirit");
        addPages("primary_arcana.sacred",
            "Sacred arcana is essential to any magic that enhances life. It can be defined as holy, the energy of particularly vibrant life, or even the simplicity of youth. It is pure and untainted, making it a useful component.",
            "It is the impulse of purity, the desire for optimism. It is found in those who are passive, innocent, or holy in origin.");
        addHeadline("primary_arcana.wicked", "Wicked Spirit");
        addPages("primary_arcana.wicked",
            "Wicked arcana is inimical to life. It seeks death and despair, and warps the living into something else. Even touching the crystal makes my soul shudder in pain.",
            "It is the impulse of corruption, the desire to cause suffering. It is found in those whose souls lack life, or those twisted by malice.");
        addHeadline("primary_arcana.arcane", "Arcane Spirit");
        addPages("primary_arcana.arcane",
            "While other arcana are impulses of the soul, it would be more accurate to say that the arcane is the impulse of the arcana themselves. This " + bold("raw arcana") + " lacks any particular quality, simply being undirected spiritual power.",
            "It is the impulse of creation, the first principle of all things. It is found within those who have opened their soul to power, or whose origins lie in that power.",
            "I suspect that this arcana, unlike others, can join a soul over time. Most things about the soul are defined early on. The impulses that define you are woven into your very self, after all. But lacking an impulse, perhaps this arcana is different. A witch was not born a mage, after all.");

        addSimpleEntryHeader("elemental_arcana", "Elemental Arcana", "Focused magic");
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
            "Using the opposing polarities of Twisted and Tainted Rock, I have created a device that draws in and focuses arcane energy. If given a compatible substrate, I can use this process to create things.",
            "The basic substrate here is the Alchemical Impetus, an artifact similar to those I've seen in the past. By focusing arcana into it, I can cause bits of the calx to transmute into something new, though this damages the Impetus in the process.");

        addSimpleEntryHeader("focus_ashes", "Arising of Ashes", "Creating powdered reagents");
        addPages("focus_ashes", "By applying differing qualities of arcana to an Alchemical Impetus, I can cause powders of various forms to be created. It is a simple yet very useful arcane recipe.");

        addSimpleEntryHeader("focus_metals", "Magecraft of Metals", "Forming banded crystals");
        addPages("focus_metals",
            "By altering the composition of the Alchemical Impetus with niter, sulfur, and cthonic gold, it is possible to alter the artifact in such a way that allows for forming nodes of most pure metals.",
            "It isn't particularly efficient or fast, but it is certainly better than having to mine for every ingot I need. Each metallic node can be processed at a furnace of any kind into two thirds of an ingot worth of metal nuggets.");

        addSimpleEntryHeader("focus_crystals", "Creation of Crystals", "Forming irregular crystals");
        addPages("focus_crystals", "By applying differing qualities of arcana to an Alchemical Impetus, I can cause more mundane crystals to be formed.");

        addSimpleEntryHeader("crucible_acceleration", "Crucible Acceleration", "Heating up");
        addPages("crucible_acceleration",
            "The Spirit Crucible is, unfortunately, a rather slow device. It takes time for it to coalesce the power of the arcana into the central item. This isn't without reason. Most matter simply can't take a faster stream, and you risk damaging the catalyst by overloading it.",
            "However, by heating the catalyst through mystic means, you can lessen this rejection and speed up the coalescence at once. That is what the Spirit Catalyzer is for. Unfortunately, this is not perfect, and instability often causes the catalyst to be damaged more than strictly necessary.",
            "Each fueled Catalyzer nearby to a Crucible will amplify the speed of the focusing process, up to a maximum of eight. Unfortunately, the risk of instability proportionally rises with each one, resulting in your impetus potentially receiving more damage than necessary.");

        addSimpleEntryHeader("arcane_restoration", "Arcane Restoration", "Mystic repair");
        addPages("arcane_restoration",
            "The Spirit Crucible allows the transmutation of matter. I have been able to use it to break down an impetus, and it stands to reason I could use it to restore one. By using spirit crystals in the Crucible instead of Brilliance in an anvil, I can do more than that - I can restore nearly anything.",
            "Every metal and make of tool or armor requires its own set of spirits to repair, and its own repair material. For an Impetus, the repair material " + italic("is") + " the spirits instead. The repair material must be placed on a Twisted Tablet facing the Crucible. Once the spirits coalesce, the item will be repaired somewhat.",
            "It appears that materials in tune with spirit arcana, such as Soulstained Steel or Hallowed Gold, are more efficient in this process. They will be repaired more than their mundane counterparts would for the same cost.");

        addSimpleEntryHeader("crucible_augmentation", "Crucible Augmentation", "Tuning the attuner");
        addPages("crucible_augmentation",
            "The Spirit Crucible is a machine of great, but largely unrealized, potential. Through a process I call Augmentation, revolving around foci of Alchemical Calx, this potential can be extracted.",
            "Each augment provides a unique effect that can be activated by inserting it in the spirit catalyzer, or placing up to four in the spirit crucible itself. Using more than one instance of the same augment type will compound their effects.",
            "To assist in controlling this process, I have modified a Tuning Fork for the purpose. While held, I can see all the unique resonances of the crucible and it's augments. On top of that, by using this Tuning Fork on the crucible, I may choose an attribute to improve, at the cost of other attributes lessening in potency.");

        addSimpleEntryHeader("mending_diffuser", "Mending Diffuser", "Unliving scar tissue");
        addPages("mending_diffuser",
            "By using Living Flesh to sympathize with natural healing, the Mending Diffuser will, upon the Crucible completing a focusing cycle, potentially repair any impetus by a small amount. It cannot mend an already cracked impetus.");

        addSimpleEntryHeader("impurity_stabilizer", "Impurity Stabilizer", "Potency from weakness");
        addPages("impurity_stabilizer",
            "The wicked spirit is drawn to the weak, seeking to cull. The Impurity Stabilizer subverts that property, providing a powerful percentage bonus to the weakest crucible attribute besides fuel usage rate and instability, which receive a small improvement overall instead.");

        addSimpleEntryHeader("shielding_apparatus", "Shielding Apparatus", "A bulwark against the storm");
        addPages("shielding_apparatus",
            "By utilizing the multiphasic property of Soulstained Steel, the Shielding Apparatus provides a chance for the damage imposed upon the impetus to be " + italic("completely") + " absorbed, while also slightly stabilizing the focusing process. It does, however, reduce focusing speed.");

        addSimpleEntryHeader("warping_engine", "Warping Engine", "Suspension of linear time");
        addPages("warping_engine",
            "Warp Flux rejects natural law, and its application here is no less concerning. The Warping Engine rejects the sequence of cause and effect, allowing an additional cycle to sometimes be completed almost before it is begun whenever a cycle is completed normally.",
            "Furthermore, chained activations of the Warping Engine provide a stacking benefit to " + italic("all") + " other attributes. Perhaps predictably, this behavior takes quite a toll on the stability and fuel requirements of the focusing process.");

        addSimpleEntryHeader("prismatic_focus_lens", "Prismatic Focus Lens", "Stability");
        addPages("prismatic_focus_lens",
            "Sometimes, simplicity is the best goal. The Prismatic Focus Lens bends not light, but the flow of arcana, reducing instability of the spirit focusing process. It is important to note, stability can only prevent the impetus from suffering " + italic("additional") + " damage.");

        addSimpleEntryHeader("accelerating_inlay", "Accelerating Inlay", "Doubling down");
        addPages("accelerating_inlay",
            "Through the use of a superior conductor in Astral Weave, Accelerating Inlay simply provides a substantial bonus to the focusing speed of a Crucible without any drawbacks.");

        addSimpleEntryHeader("blazing_diode", "Blazing Diode", "The strongest force in the world");
        addPages("blazing_diode",
            "The Blazing Diode extracts the full force of a soul on fire, lessening the fuel requirement of any catalyzer powering the crucible while also slightly hastening the entire process.");

        addSimpleEntryHeader("intricate_assembly", "Intricate Assembly", "Fudging the numbers");
        addPages("intricate_assembly",
            "The Intricate Assembly, as its name suggests, draws its power from the unbounded nature of its fractal complexity. At the cost of an increased dependency on fuel, as well as a reduced focusing speed, it enables the crucible to potentially produce double the usual amount of items during each focusing cycle.");

        addEntryHeader("spirit_metals", "Spirit Metals", "Arcana refined");
        addHeadline("spirit_metals.soulstained_steel", "Soulstained Steel");
        addPages("spirit_metals.soulstained_steel",
            "Iron is mundane, in a word. By attuning the metal with Soulstone, I can create a steel that is " + italic("simultaneously") + " in and out of phase with the world.",
            "Anything made from Soulstained Steel is capable of striking the soul, without the need for specifics of engineering like with my crude scythe. Wearing the metal in its base form as armor is dangerous, as it will touch your own soul as well, so I must engineer a countermeasure.");
        addHeadline("spirit_metals.hallowed_gold", "Hallowed Gold");
        addPages("spirit_metals.hallowed_gold",
            "Gold is often used as a thaumaturgical base, its natural conductivity of magic making it quite useful. Spirit arcana are no exception. In fact, using Sacred arcana, we can enhance those conductive properties.",
            "Hallowed Gold, as a metal, acts much like its mundane counterpart. The inherent innocence of the arcana infused into the alloy makes other arcana glide through it smoothly, creating the perfect conductor for my purposes.");
        addHeadline("spirit_metals.hallowed_gold.spirit_jar", "Spirit Jar");
        addPages("spirit_metals.hallowed_gold.spirit_jar", "A simple application of this metal is the Spirit Jar. As spirits in their raw form don't have mass, by trapping them under Hallowed Gold you can store far more than you could physically. The capacity of these jars is near-infinite, though each only stores one type of spirit.");

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

        addEntryHeader("reactive_trinkets", "Reactive Trinkets", "Harnessing the harvest");
        addHeadline("reactive_trinkets.ring_of_curative_talent", "Ring of Curative Talent");
        addPages("reactive_trinkets.ring_of_curative_talent",
            "The trinkets documented within cause effects whenever a spirit crystal is collected, feeding off the excess energy. As an example, this restorative trinket will replenish a small division of my health any time I collect arcana.");
        addHeadline("reactive_trinkets.ring_of_alchemical_mastery", "Ring of Alchemical Mastery");
        addPages("reactive_trinkets.ring_of_alchemical_mastery",
            "This ring, through alchemical trickery, is able to manipulate the potions running through my blood. Negative effects are filtered out, shortening their duration, while positive effects are maintained for longer than normal. Whenever I collect arcana, the ring momentarily works better.");
        addHeadline("reactive_trinkets.ring_of_prowess", "Ring of Prowess");
        addPages("reactive_trinkets.ring_of_prowess",
            "Brilliance is attached to the soul, but isn't an impulse like the arcana. It is accumulated knowledge, and so is not inherently tied to the soul that learned it. Even strikes which pass through the soul harmlessly are capable of dislodging it.",
            "By using condensed Brilliance, I have created a ring that filters out that Brilliance out of arcana I collect, giving me a burst of Brilliant knowledge whenever I collect arcana.");

        addSimpleEntryHeader("ring_of_esoteric_spoils", "Ring of Esoteric Spoils", "Be fruitful and multiply");
        addPages("ring_of_esoteric_spoils",
            "It can be tiring, harvesting the sheer quantities of arcana I need for my research. This ring can increase the efficiency of the harvest, allowing me to reap an additional spirit from every slain soul. At a certain point, though, \"efficiency\" ceases to explain it. How am I obtaining more power than the soul itself has?");

        addSimpleEntryHeader("belt_of_the_starved", "Belt of the Starved", "Channeling voracity");
        addPages("belt_of_the_starved",
            "The arcana I collect occasionally have scraps of wishes and desires woven in. Often, given the base nature of what I reap, this comes in the form of hunger, lust, or petty grudges. All of these impurities can be harnessed, and beyond that, I can infuse this power into my magic.",
            "Doing this carries the perhaps predictable effect that my own hunger amplifies, draining quicker in the process. The magic proficiency this grants is immense, but... I must say, the means are rather distasteful.");
        addHeadline("belt_of_the_starved.ring_of_desperate_voracity", "Ring of Desperate Voracity");
        addPages("belt_of_the_starved.ring_of_desperate_voracity",
            "This ring makes rotten foods just a little bit more bearable, allowing me to amass more hunger and saturation from such an unusual diet. Normally, such a diet would be ill-advised, however, a secondary function of the ring allows it to extend the duration of the gluttony status effect that the belt of the starved grants.");

        addSimpleEntryHeader("belt_of_the_prospector", "Belt of the Prospector", "Treasures of the earth");
        addPages("belt_of_the_prospector",
            "To fuel my various magics and other goals I more often than not find myself needing various earthen treasures. This belt prevents explosions " + italic("directly") + " caused by me from harming valuable items on the ground, and causes those explosions to break blocks as though I were using a Fortune III tool.");
        addHeadline("belt_of_the_prospector.ring_of_the_hoarder", "Ring of The Hoarder");
        addPages("belt_of_the_prospector.ring_of_the_hoarder",
            "While wearing this ring, any item created from the destruction caused by your explosions is directly teleported to your location.");

        addEntryHeader("necklace_of_blissful_harmony", "Necklace of Blissful Harmony", "No sign of morning coming");
        addHeadline("necklace_of_blissful_harmony", "The Blissful Harmony");
        addPages("necklace_of_blissful_harmony",
            "To focus on my magics I more often than not need peace and clarity. As such, I have devised a tool to redirect attention around me. While worn, this accessory will hide my presence from nearby adversaries, decreasing their likelihood of taking interest in me.",
            "Upon further studies, it would appear that the effects of my newly forged trinket are " + italic("especially") + " potent when exerting their influence over any soul bearing wicked spirit.");

        addEntryHeader("necklace_of_the_mystic_mirror", "Necklace of the Mystic Mirror", "As without, so within");
        addHeadline("necklace_of_the_mystic_mirror", "The Mystic Mirror");
        addPages("necklace_of_the_mystic_mirror",
            "I have devised another way to capture some of the lost energy from loose spirits. The Resonant Lens I socketed in is able to focus magic, collecting a little bit of excess energy as I pick up arcana. This energy is then redistributed to the rest of my trinkets, increasing the effect of any that act upon collecting spirits.");

        addEntryHeader("necklace_of_the_narrow_edge", "Necklace of the Narrow Edge", "Focused and sharpened");
        addHeadline("necklace_of_the_narrow_edge", "The Narrow Edge");
        addPages("necklace_of_the_narrow_edge",
            "The sweep of the scythe is its main draw. The ability to cut my targets like so much wheat is invaluable. But that comes at the cost of damage to a single target. This necklace mystically focuses the edge of my attack, directing all of the power into one target for a strong damage boost.");

        addEntryHeader("runeworking", "Runeworking", "The central pin");
        addHeadline("runeworking", "Runeworking");
        addPages("runeworking",
            "Every trinket I've made thus far has proven to have its place in my work, but it has become somewhat frustrating to have to spend time choosing out my jewelery before every task. After all, some effects may be able to be divorced from their genesis.",
            "The process of Runeworking allows me to do so via Brooches and Runes. The brooch is simply the focus I have chosen, its placement over the heart symbolizing the price of suffering inherent to runic power.",
            "To begin, I will need to create a Runic Workbench to inscribe these runes. The simplest beginning will be the Runic Brooch, which will allow me to equip what I have created.");

        addEntryHeader("runic_brooch", "Runic Brooch", "Power in the palms");
        addHeadline("runic_brooch", "Runic Brooch");
        addPages("runic_brooch",
            "The simplest of brooches is the Runic Brooch. It represents the quest for power at price, and the bloodied palms with which one grasps a razored enlightenment. When worn, by symbolically removing one of my hands' capacity to channel magic from a ring, it will grant me the opportunity to inscribe a rune in each palm.");

        addEntryHeader("glass_brooch", "Glass Brooch", "Fragile power");
        addHeadline("glass_brooch", "Glass Brooch");
        addPages("glass_brooch",
            "The Runic Brooch works well, but my hands are not the only symbolic home of power. I can instead imbue them into my blood with the Glass Brooch, stripping a portion of my physical health away to inscribe two runes within my chest.");

        addEntryHeader("elaborate_brooch", "Elaborate Brooch", "A change in purpose");
        addHeadline("elaborate_brooch", "Elaborate Brooch");
        addPages("elaborate_brooch",
            "Brooches symbolize a sacrifice, but that sacrifice can seem abstract, even comical, from the outside. A necklace is not so different from a belt in form, and by stripping away notions of " + italic("fashion") + " and propriety, I can wear a belt's power around my neck.");

        addEntryHeader("gluttonous_brooch", "Gluttonous Brooch", "Endlessly unsated");
        addHeadline("gluttonous_brooch", "Gluttonous Brooch");
        addPages("gluttonous_brooch",
            "Some sacrifices are mental, rather than physical. The Gluttonous Brooch strips away satiation, causing my body to crave food even when it should be full. In exchange for this, my loosened belly has the space to accommodate an additional belt.");

        addEntryHeader("rune_of_idle_restoration", "Rune of Idle Restoration", "The impulse to mend");
        addHeadline("rune_of_idle_restoration", "Rune of Idle Restoration");
        addPages("rune_of_idle_restoration",
            "The Rune of Idle Restoration implores a body to restore itself, passively restoring the user's health at a rate of one half heart every two seconds.");
        addEntryHeader("rune_of_culling", "Rune of Culling", "The impulse to break");
        addHeadline("rune_of_culling", "Rune of Culling");
        addPages("rune_of_culling",
            "The Rune of Culling implores a mind to seek ruin, granting the user a bonus to Magic Proficiency which improves magic damage output by roughly two fifths.");
        addEntryHeader("rune_of_aliment_cleansing", "Rune of Aliment Cleansing", "The impulse to process");
        addHeadline("rune_of_aliment_cleansing", "Rune of Aliment Cleansing");
        addPages("rune_of_aliment_cleansing",
            "The Rune of Aliment Cleansing implores a body to catalyze and change substance, allowing it to burn through and process negative effects quicker than normal.");
        addEntryHeader("rune_of_haste", "Rune of Haste", "The impulse to expend");
        addHeadline("rune_of_haste", "Rune of Haste");
        addPages("rune_of_haste",
            "The Rune of Haste implores a mind to move with fervor, improving the user's mining speed by roughly a quarter.");
        addEntryHeader("rune_of_reactive_shielding", "Rune of Reactive Shielding", "The impulse to withstand");
        addHeadline("rune_of_reactive_shielding", "Rune of Reactive Shielding");
        addPages("rune_of_reactive_shielding",
            "The Rune of Reactive Shielding implores a body to stand fast, granting an increase to effective Armor and Armor Toughness by one tenth upon taking damage. The strength of this effect scales as the user receives more damage, reaching an increase of three tenths at full power.");
        addEntryHeader("rune_of_dexterity", "Rune of Dexterity", "The impulse to flee");
        addHeadline("rune_of_dexterity", "Rune of Dexterity");
        addPages("rune_of_dexterity",
            "The Rune of Haste implores a mind to move when cornered, boosting movement speed which can up to double as the user's health pool diminishes.");
        addEntryHeader("rune_of_reinforcement", "Rune of Reinforcement", "The impulse to make");
        addHeadline("rune_of_reinforcement", "Rune of Reinforcement");
        addPages("rune_of_reinforcement",
            "The Rune of Reinforcement, rather than imploring the body, simply provides pressure to the Arcane quality of its existence, granting their Soul Ward capacity and strength.");
        addEntryHeader("rune_of_volatile_distortion", "Rune of Volatile Distortion", "The impulse to putrefy");
        addHeadline("rune_of_volatile_distortion", "Rune of Volatile Distortion");
        addPages("rune_of_volatile_distortion",
            "The Rune of Volatile Distortion, rather than imploring the mind, corrupts its actions with random chance, making the user's attacks erratic in damage - anywhere from nine tenths as powerful to twelve tenths. Sometimes, this randomness aligns with weaknesses by chance, doubling the strength of the attack.");

        addSimpleEntryHeader("mirror_magic", "Mirror magic", "Magic Funnels");
        addPages("mirror_magic", "The future holds many secrets..");

        addSimpleEntryHeader("voodoo_magic", "Voodoo magic", "Forbidden arts");
        addPages("voodoo_magic", "The future holds many secrets..");

        addSimpleEntryHeader("ritual_magic", "Ritual magic", "Grand Magics");
        addPages("ritual_magic", "The future holds many secrets..");

        addEntryHeader("altar_acceleration", "Altar Acceleration", "Obelisks");
        addHeadline("altar_acceleration.runewood_obelisk", "Runewood Obelisk");
        addPages("altar_acceleration.runewood_obelisk",
            "Spirit Infusion, as essential as it is, has grown to be tedious. Even producing a stack of simple Hex Ash takes several minutes. Using Hallowed Gold, I have found a way to accelerate it. By placing up to four hallowed obelisks nearby the altar I may increase the processing speed by one fourth with each obelisk.");
        addHeadline("altar_acceleration.brilliant_obelisk", "Brilliant Obelisk");
        addPages("altar_acceleration.brilliant_obelisk",
            "While not useful for Infusion, per-se, the design of the obelisk can be used in another way as well. By socketing Brilliance instead of Hallowed Gold, the obelisk will harmonize with the Brilliance of enchanting, causing it to provide as much force of enchanting as five bookshelves do.");

        addSimpleEntryHeader("totem_magic", "Totem Magic", "Arcana unleashed");
        addPages("totem_magic",
            "Up until now, when performing spirit arcana, I have limited my research to personal enhancement and material production. Now, I affect the world.",
            "To begin with Totem Magic, I may engrave spirit arcana into Runewood Logs, forming a rune representing the magic. If unwanted, engraved spirits can be stripped off with an axe, but I have uses in mind. With a Runewood Totem Base, and then a specific set of runes in a totem pole placed above my totem base, I can perform a Spirit Rite.",
            "While each rite does offer a unique function, they follow patterns and categorize easily. For what I term \"aura rites\", they are simple, effect-providing rites that affect anything living within eight blocks of the base. For other rituals which alter beings, most affect anything within half the range of an aura rite.",
            "For those which alter blocks, most affect the five-by-five area on the level beneath the base, towards where the runes are facing. These rituals can also be made more selective with the use of Item Stands. By placing them on the sides of the rune pillars, you can make the ritual only recognize and alter the types of blocks lying on the stands.",
            "One caveat is that no rite's totem may function within the range of another, identical ritual. If you try, the first one will simply fizzle out, the energies coursing through it disrupted.");

        addSimpleEntryHeader("arcane_rite", "A Rite Unchained", "Creation uncontrolled");
        addPages("arcane_rite.description",
            "Raw arcana provides the basis for all rites. Without power, nothing would be accomplished. This naturally makes one wonder what effect raw arcana would have as the focus of a rite. The answer is a complex and dangerous one.",
            "It requires far more to focus than other rites, taking the entire five runes to activate. It's as though I am pushing on some threshold, and need to break through. And in breaking through... momentum is conserved.",
            "Once complete, the rite brings about erratic change to the totem; what I dub Soulwood bears scars from the violent method of its creation. Those scars warp magic, altering its fundamental nature. Any spirit rite performed with a Soulwood totem will produce a vastly different effect.",
            "The scars of this process linger, allowing me to make more Soulwood by placing Runewood on the results of the ritual.");
        addHeadline("arcane_rite.soulwood", "Soulwood Transmutation");

        addRiteEntry("arcane_rite",
            "The rite - if you could call something so chaotic that - corrupts and burns through the totem, altering its very base nature, and transmuting the world around it into some indeterminate blighted substance.",
            "Converts the totem structure into one made up of Soulwood and alters the nearby terrain into a blighted substance.\n- Soulwood totems produce different rite effects.");
        addRiteEntry("corrupt_arcane_rite",
            "Now already scarred, the power bleeds from the soulwood totem, corrupting and warping the nearby area. Any nearby block placed atop that blighted substance will be altered.",
            "Transmutes nearby blocks placed atop blighted gunk.");

        addEntryHeader("sacred_rite", "Sacred Rites", "Invigorating the soul");
        addRiteEntry("sacred_rite",
            "A simple rite, while active it will slowly mend the wounds of nearby entities.\n Avoids hostiles.",
            "Recovers one heart of damage every two seconds.");
        addRiteEntry("greater_sacred_rite",
            "An advanced rite, while active nearby crops planted on soil are filled with vigor and will grow more quickly.",
            "Periodically ages nearby crops. Coverage matches water coverage.");

        addEntryHeader("corrupt_sacred_rite", "Corrupting the Sacred Rites", "Stimulating the soul");
        addRiteEntry("corrupt_sacred_rite",
            "A simple rite, while active it will apply a spiritually nourishing effect to nearby animals, accelerating growth and certain biological processes.",
            """
                Affected animals instantly gain 25 seconds worth of age
                 - Sheep will feed on grass more frequently
                 - Bees pollinate faster and more frequently
                 - Chickens lay eggs more frequently""");
        addRiteEntry("corrupt_greater_sacred_rite",
            "An advanced rite, while active... nearby animals are made... " + italic("vigorous") + ", as if I had fed them myself.",
            "Affected animals are fed until there are more than twenty.\n - This limit applies separately for each type of animal within the range of the rite.");

        addEntryHeader("wicked_rite", "Wicked Rites", "Maligning the soul");
        addRiteEntry("wicked_rite",
            "A simple rite, while active it will slowly bring nearby hostile beings to within an inch of death.",
            "Deals one heart of non-lethal damage every two seconds.");
        addRiteEntry("greater_wicked_rite",
            "An advanced rite, while active nearby beings on the brink of death are dealt a fatal blow to the body and soul.",
            "Affected entities are dealt a fatal blow, dropping items and spirits on death.\n - Avoids entities with more than two and a half hearts remaining.");

        addEntryHeader("corrupt_wicked_rite", "Corrupting the Wicked Rites", "Endangering the soul");
        addRiteEntry("corrupt_wicked_rite",
            "Rather than harm, this rite enhances nearby hostile beings, granting protection, force, and speed. Rather useless, but might have niche applications.",
            "Grants all nearby hostiles resistance, strength, and speed.");
        addRiteEntry("corrupt_greater_wicked_rite",
            "An advanced rite, while active it will cull herds of nearby overcrowded animals.",
            "While there are more than twenty animals within the range of the rite, the excess is removed.\n - This limit applies separately for each type of animal within the range of the rite.");

        addEntryHeader("aerial_rite", "Aerial Rites", "Uplifting the soul");
        addRiteEntry("aerial_rite", "A simple aura rite, while active nearby friendly beings will find their movements sped up.",
            "Applies the Zephyr's Courage effect, increasing movement speed by two fifths.");
        addRiteEntry("greater_aerial_rite",
            "An advanced rite, by twisting the power of the air, blocks before the totem will be made to fall as though they were sand. Nothing Silk Touch cannot grab will be affected, though.",
            "Causes targeted blocks to fall downwards if there is nothing underneath them.");

        addEntryHeader("corrupt_aerial_rite", "Corrupting the Aerial Rites", "Scattering the soul");
        addRiteEntry("corrupt_aerial_rite",
            "A simple aura rite, while active nearby friendly beings will have their connection to the earth disrupted, lowering their gravity and increasing jump height.",
            "Applies the Aether's Charm effect, decreasing gravity by three fifths while also providing a substantial benefit to jump height.");
        addRiteEntry("corrupt_greater_aerial_rite",
            "An advanced rite, while active it will slowly ease the stress of time on the mind, offsetting the effects of insomnia for those around it over time.",
            "Passively reduces the insomnia value of nearby players.\n - Assuming phantoms are just starting to appear, it will take a single totem executing the rite two and two fifths of a minute to fully cleanse insomnia.\n - Naturally, the totem will take longer to fully cleanse insomnia if the player has already been suffering from it for some time.");

        addEntryHeader("earthen_rite", "Earthen Rites", "Grounding the soul");
        addRiteEntry("earthen_rite",
            "A simple aura rite, while active nearby friendly beings will find their bodies are tougher and more resistant to damage.",
            "Applies the Gaian Bulwark effect, increasing armor by four and armor toughness by two.");
        addRiteEntry("greater_earthen_rite",
            "An advanced rite, while active it will cause blocks before the totem base to be broken.",
            "Breaks targeted blocks. Unbreakable blocks behave as to be expected.");

        addEntryHeader("corrupt_earthen_rite", "Corrupting the Earthen Rites", "Honing the soul");
        addRiteEntry("corrupt_earthen_rite",
            "A simple aura rite, while active nearby friendly beings will find their attacks deal more damage.",
            "Applies the Earthen Might effect, increasing damage dealt by two hearts.");
        addRiteEntry("corrupt_greater_earthen_rite",
            "An advanced rite, while active the earth coalesces, and like lava meeting water, cobblestone is created before the totem base.",
            "Creates cobblestone in place of empty space.");

        addEntryHeader("infernal_rite", "Infernal Rites", "Igniting the soul");
        addRiteEntry("infernal_rite",
            "A simple aura rite, while active nearby friendly beings will find that their motions are infused with fiery vigor, letting them swing weapons and tools faster.",
            "Applies the Miner's Rage effect, increasing attack rate and dig speed by two fifths.");
        addRiteEntry("greater_infernal_rite",
            "An advanced rite, while active it will cause blocks before the totem base to be smelted.",
            "Smelts targeted blocks that can be smelted into other blocks.");

        addEntryHeader("corrupt_infernal_rite", "Corrupting the Infernal Rites", "Extinguishing the soul");
        addRiteEntry("corrupt_infernal_rite",
            "A simple aura rite, while active nearby friendly beings and close fires will have the heat sucked out of them, extinguishing them and healing those who were burned.",
            "Extinguishes nearby flames, be it affecting the world or an entity.\n - Extinguished entities receive the Ifrit's Embrace effect, recovering two hearts a second.");
        addRiteEntry("corrupt_greater_infernal_rite",
            "An advanced rite, instead of generating heat, this rite compresses it, causing nearby furnaces to operate more quickly.",
            "Speeds up nearby furnaces by one fourth.\n - Fuel consumption rate is unaffected, meaning the rite also improves fuel efficiency.");

        addEntryHeader("aqueous_rite", "Aqueous Rites", "Molding the soul");
        addRiteEntry("aqueous_rite",
            "A simple aura rite, while active nearby friendly beings will find that their reach is extended, letting them more easily interact with the world.",
            "Applies the Poseidon's Grasp effect, increasing block reach by two units of space and increasing item pickup range significantly.");
        addRiteEntry("greater_aqueous_rite",
            "An advanced rite, while active, it will vastly increasing the drip speed of dripstone, causing more fluid to be produced.",
            "Speeds up dripstone fluid production, works on both lava and water.\n - Only the tip of hanging dripstone needs to be within range for the effect to trigger.");

        addEntryHeader("corrupt_aqueous_rite", "Corrupting the Aqueous Rites", "Deforming the soul");
        addRiteEntry("corrupt_aqueous_rite",
            "A simple aura rite, while active nearby friendly beings will find themselves better at fishing.",
            "Applies the Angler's Lure effect, providing benefits to fishing skills equal to Lure I and Luck of the Sea I.\n - The effects stack with any enchantment already present on a fishing rod.");
        addRiteEntry("corrupt_greater_aqueous_rite",
            "An advanced rite, while active zombies near this rite will find themselves choking on their own breath, drowning even on land.",
            "Converts nearby zombies to drowned.");

        addEntryHeader("blight", "A Study on Blight", "What, why, and how");
        addHeadline("blight.intro", "Blight Study: Preface");
        addPages("blight.intro",
            "Blight. " + italic("Something which spoils or damages.") + " What the Undirected Rite has created has many strange properties, and I intend to categorize them.\n" +
                "The naïve explanation is that it is simply another form of power that taints the world, but that isn't right. Blight isn't harmful, not inof itself. It's just... " + italic("gunk."));
        addHeadline("blight.composition", "Blight Study: Substance");
        addPages("blight.composition",
            "The Undirected Rite, as the name suggests, is random. It transmutes, but it has no pattern to transmute things to. So, instead, you get something random, bits of disparate matter all jumbled together into a foul-smelling powder. I wouldn't recommend eating it, or growing things on it, but it's otherwise harmless.");
        addHeadline("blight.spread", "Blight Study: Spread");
        addPages("blight.spread",
            "Blight does not spread on its own. It's just random matter, after all. But it has a spiritual memory, a pattern which to replicate. When given arcana, or a valid fertilizer, blight will haphazardly echo this pattern on the nearby area.");
        addHeadline("blight.arcane_rite", "Blight Study: Resonance");
        addPages("blight.arcane_rite",
            "That echo is why this substance is important for the Unchained Rite. The Rite remembers the violence of its creation, and resonates with the memory within the blight, applying its power to things laying on top of it.");

        addEntryHeader("soulwood", "A Study on Soulwood", "Twisted trees");
        addHeadline("soulwood.intro", "Soulwood Study: Preface");
        addPages("soulwood.intro",
            "After further study, I have discovered that the Soulwood produced by the Unchained Rite has actually become an entirely different species from the Runewood it is made from. It grows differently, it acts differently... it appears the spiritual scars that created it go deeper than just its color and magic.");
        addHeadline("soulwood.bonemeal", "Soulwood Study: Growth");
        addPages("soulwood.bonemeal",
            "Much like blight, the sapling accepts both spirit arcana and common fertilizers such as bonemeal. The end result is roughly the same across both options.");
        addHeadline("soulwood.color", "Soulwood Study: Color");
        addPages("soulwood.color",
            "The most obvious differences with the tree itself are shape and leaf color. Soulwood is more spindly than Runewood, and its leaves are a sickly purple-red hue instead of a rich orange-yellow. It can still be used for many of the same things, though.");
        addHeadline("soulwood.blight", "Soulwood Study: Blight");
        addPages("soulwood.blight",
            "Another obvious difference is in its effect on the surroundings - namely, the fact that it echoes the Undirected Rite with the energies of its growth, transmuting the world around it into blight. I ought to create a safely contained area if I wish to grow these trees.");
        addHeadline("soulwood.sap", "Soulwood Study: Sap");
        addPages("soulwood.sap",
            "The sticky lifeblood of the tree also seems to well up much more often in a tree than in Runewood. The sap's effects are corrupted as well; rather than restoring my vigor, Unholy Syrup enhances it, increasing my attack strength and providing damage resistance.");

        addSimpleEntryHeader("transmutation", "Transmutation", "Volatile reactions");
        addPages("transmutation.intro",
            "The Unchained Rite's echoes can scar more than simply Runewood. The patterns seem somewhat random, but then, blight is a substance of randomness. Trial and error has identified three categories; transmutation trees, of sorts.",
            "The effect of applying these scars depends on what material we start with. The pulsing of the Rite will shift any given block forward in the tree, with it degrading into blight given enough time.");
        addHeadline("transmutation.stone", "Transmutation: Basic");
        addHeadline("transmutation.deepslate", "Transmutation: Endothermic");
        addHeadline("transmutation.smooth_basalt", "Transmutation: Exothermic");

        addSimpleEntryHeader("corrupted_resonance", "Corrupted Resonance", "Advanced magics");
        addPages("corrupted_resonance", "To fuel any further magics, I will need a catalyst. Something beautiful, full of magic, serving as the basis for any complex craft born from it. Corrupted Resonance is perfect for the role.");

        addSimpleEntryHeader("tyrving", "Tyrving", "Ancient relic");
        addPages("tyrving", "The Tyrving is a rather esoteric blade. It's strange design makes it appear as a weak weapon not suited for combat. However, it's hex ash lining and twisted rock form cause it to deal extra magic damage to the soul, the greater the soul the more benefit.",
            "The weapon can also be repaired using arcane restoration quite efficiently.");

        addSimpleEntryHeader("belt_of_the_magebane", "Belt of the Magebane", "Newfound ruin");
        addPages("belt_of_the_magebane", "The Belt of the Magebane is a simple innovation, but a dangerously effective one. Normally, after being struck by any attack, soul ward will not recover until a long moment after. That moment of downtime has proven itself detrimental far too frequently. But that ends now.",
            "While worn, the belt provides a substantial bonus to soul ward recovery rate, while also improving capacity slightly. Furthermore, the belt will absorb the arcane essence of any instance of magical damage that strikes its bearer, converting that repurposed energy into immediate recovery of my wards.");

        addSimpleEntryHeader("the_device", "The Device.", "microwave to recharge");
        addPage("the_device", "even works while bended");

        add("malum.spirit.description.stored_spirit", "Contains: ");
        add("malum.spirit.description.stored_soul", "Stores Soul With: ");

        add("malum.spirit.flavour.sacred", "Innocent");
        add("malum.spirit.flavour.wicked", "Malicious");
        add("malum.spirit.flavour.arcane", "Fundamental");
        add("malum.spirit.flavour.eldritch", "Esoteric");
        add("malum.spirit.flavour.aerial", "Swift");
        add("malum.spirit.flavour.aqueous", "Malleable");
        add("malum.spirit.flavour.infernal", "Radiant");
        add("malum.spirit.flavour.earthen", "Steady");

        add("malum.jei.spirit_infusion", "Spirit Infusion");
        add("malum.jei.spirit_focusing", "Spirit Focusing");
        add("malum.jei.spirit_repair", "Spirit Repair");
        add("malum.jei.spirit_rite", "Spirit Rites");
        add("malum.jei.spirit_transmutation", "The Unchained Rite");

        add("itemGroup.malum_basis_of_magic", "Malum: Basis of Magic");
        add("itemGroup.malum_arcane_construct", "Malum: Arcane Construct");
        add("itemGroup.malum_natural_wonders", "Malum: Born from Arcana");
        add("itemGroup.malum_metallurgic_magics", "Malum: Metallurgic Magics");
        add("itemGroup.malum_void_chronicles", "Malum: Chronicles of the Void");
        add("itemGroup.malum_ritual_shards", "Malum: Ritual Shards");
        add("itemGroup.malum_cosmetics", "Malum: Self Expression");

        add("enchantment.malum.haunted.desc", "Deals extra magic damage.");
        add("enchantment.malum.rebound.desc", "Allows the item to be thrown much like a boomerang, cooldown decreases with tier.");
        add("enchantment.malum.spirit_plunder.desc", "Increases the amount of spirits created when shattering a soul.");

        add("death.attack." + DamageTypeRegistry.VOODOO_IDENTIFIER, "%s had their soul shattered");
        add("death.attack." + DamageTypeRegistry.VOODOO_IDENTIFIER + ".player", "%s had their soul shattered by %s");
        add("death.attack." + DamageTypeRegistry.SCYTHE_SWEEP_IDENTIFIER, "%s was sliced in half");
        add("death.attack." + DamageTypeRegistry.SCYTHE_SWEEP_IDENTIFIER + ".player", "%s was sliced in half by %s");

        addEffectDescription(MobEffectRegistry.GAIAN_BULWARK, "You are protected by an earthen bulwark, increasing your armor.");
        addEffectDescription(MobEffectRegistry.EARTHEN_MIGHT, "Your fists and tools are reinforced with earth, increasing your overall damage.");
        addEffectDescription(MobEffectRegistry.MINERS_RAGE, "Your tools are bolstered with radiance, increasing your mining and attack speed.");
        addEffectDescription(MobEffectRegistry.IFRITS_EMBRACE, "The warm embrace of fire coats your soul, mending your seared scars.");
        addEffectDescription(MobEffectRegistry.ZEPHYRS_COURAGE, "The zephyr propels you forward, increasing your movement speed.");
        addEffectDescription(MobEffectRegistry.AETHERS_CHARM, "The heavens call for you, increasing jump height and decreasing gravity.");
        addEffectDescription(MobEffectRegistry.POSEIDONS_GRASP, "You reach out for further power, increasing your reach and item pickup distance.");
        addEffectDescription(MobEffectRegistry.ANGLERS_LURE, "Let any fish who meets my gaze learn the true meaning of fear; for I am the harbinger of death. The bane of creatures sub-aqueous, my rod is true and unwavering as I cast into the aquatic abyss. A man, scorned by this uncaring Earth, finds solace in the sea. My only friend, the worm upon my hook. Wriggling, writhing, struggling to surmount the mortal pointlessness that permeates this barren world. I am alone. I am empty. And yet, I fish.");
        addEffectDescription(MobEffectRegistry.GLUTTONY, "You feed on the vulnerable, increasing scythe proficiency and gradually restoring lost hunger.");

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
        return "$k" + s + "/$";
    }

    public String italic(String s) {
        return "$i" + s + "/$";
    }

    public String bold(String s) {
        return "$b" + s + "/$";
    }

    public String strike(String s) {
        return "$s" + s + "/$";
    }

    public String underline(String s) {
        return "$u" + s + "/$";
    }

    public void addTetraMaterial(String identifier, String name) {
        add("tetra.material." + identifier, name);
        add("tetra.material." + identifier + ".prefix", name);
    }

    public void addRite(MalumRiteType riteType, String basicName, String corruptName) {
        add(riteType.translationIdentifier(false), basicName);
        add(riteType.translationIdentifier(true), corruptName);
    }

    public void addRiteEffectCategory(MalumRiteEffect.MalumRiteEffectCategory category) {
        add(category.getTranslationKey(), DataHelper.toTitleCase(category.name().toLowerCase(), "_"));
    }

    public void addTetraImprovement(String identifier, String name, String description) {
        add("tetra.improvement." + identifier + ".name", name);
        add("tetra.improvement." + identifier + ".description", description);
    }

    public void addRiteEntry(String identifier, String riteDescription, String riteHoverDescription) {
        add("malum.gui.book.entry.page.text." + identifier, riteDescription);
        add("malum.gui.book.entry.page.text." + identifier + ".hover", riteHoverDescription);
    }

    public void addEntryHeader(String identifier, String name, String description) {
        add("malum.gui.book.entry." + identifier, name);
        addDescription(identifier, description);
    }

    public void addSimpleEntryHeader(String identifier, String name, String description) {
        addHeadline(identifier, name);
        addEntryHeader(identifier, name, description);
    }

    public void addPage(String identifier, String page) {
        add("malum.gui.book.entry.page.text." + identifier, page);
    }

    public void addPages(String identifier, String... pages) {
        int i = 1;
        for (String s : pages) {
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
        add("death.attack." + identifier, "%s " + description);
        add("death.attack." + identifier + ".player", "%s " + description + " by %s");
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
