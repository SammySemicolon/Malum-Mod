package com.sammy.malum.datagen.lang;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.data.component.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.compat.create.*;
import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.common.block.ether.EtherWallTorchBlock;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.core.systems.registry.*;
import com.sammy.malum.core.systems.rite.effect.SpiritRiteEffectTag;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import com.sammy.malum.core.systems.spirit.SpiritTextData;
import com.sammy.malum.datagen.lang.effect.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.enchantment.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import team.lodestar.lodestone.helpers.DataHelper;

import java.util.*;
import java.util.function.Supplier;

import static com.sammy.malum.registry.common.MalumAttributes.ATTRIBUTES;
import static com.sammy.malum.registry.common.MalumMobEffects.MOB_EFFECTS;
import static com.sammy.malum.registry.common.sound.MalumSoundEvents.SOUND_EVENTS;
import static com.sammy.malum.registry.common.magic.MalumGeasEffectTypes.GEAS_TYPES;
import static com.sammy.malum.registry.common.MalumContent.BLOCKS;
import static com.sammy.malum.registry.common.entity.MalumEntityTypes.ENTITY_TYPES;

public class MalumLangDatagen extends LanguageProvider {
    public static MalumLangDatagen lang;

    public MalumLangDatagen(PackOutput gen) {
        super(gen, MalumMod.MALUM, "en_us");
        lang = this;
    }

    @Override
    protected void addTranslations() {
        CodexLangDatagen.generateEntries();
        IntroductionChapterLangDatagen.generateEntries();

        CreativeTabLangDatagen.addTranslations();
        WaveformInterfaceLangDatagen.addTranslations();
        SoulstoneMetallicsLangDatagen.addTranslations();

        EffectKeywordLangDatagen.addTranslations();
        CurioEffectLangDatagen.addTranslations();
        GeasEffectLangDatagen.addTranslations();
        ItemEffectLangDatagen.addTranslations();

        add("container.malum.magehand_coffer", "Magehand Coffer");
        add("container.malum.wand_tinkerer", "Wand Tinkerer");
        add("container.malum.conjuncture_crystallarium", "Conjuncture Crystallarium");

        var blocks = new HashSet<>(BLOCKS.getEntries());
        var items = new HashSet<>(MalumContent.ITEMS.getEntries());
        var sounds = new HashSet<>(SOUND_EVENTS.getEntries());
        var effects = new HashSet<>(MOB_EFFECTS.getEntries());
        var attributes = new HashSet<>(ATTRIBUTES.getEntries());
        var entities = new HashSet<>(ENTITY_TYPES.getEntries());
        var geasa = new HashSet<>(GEAS_TYPES.getEntries());
        var soulwovenBanners = SoulwovenBannerPatternDataComponent.REGISTERED_PATTERNS;
        var crucibleAttributes = ArtificeAttributeType.CRUCIBLE_ATTRIBUTES;

        add(DataHelper.take(blocks, MalumContent.Enscription.WEEPING_WELL.block()).get(), "The Weeping Well");
        add(DataHelper.take(blocks, MalumContent.Enscription.WEEPING_WELL_CENTERPIECE.block()).get(), "The Weeping Well");

        if (CreateCompat.LOADED) { //If Create is loaded, the copper nugget won't exist.
            add("item.malum.copper_nugget", "Copper Nugget");
        }
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof EtherWallTorchBlock);
        DataHelper.takeAll(blocks, i -> i.get() instanceof WallSignBlock);
        blocks.forEach(b -> {
            String name = b.get().getDescriptionId().replaceFirst("block\\.malum\\.", "");
            name = makeProper(DataHelper.toTitleCase(correctItemName(name), "_"));
            add(b.get().getDescriptionId(), name);
        });
        DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && !(i.get() instanceof ItemNameBlockItem));
        items.forEach(i -> {
            String name = i.get().getDescriptionId().replaceFirst("item\\.malum\\.", "");
            name = makeProper(DataHelper.toTitleCase(correctItemName(name), "_"));
            add(i.get().getDescriptionId(), name);
        });

        sounds.forEach(s -> {
            String id = s.getId().getPath();
            String name = correctSoundName(id).replaceAll("_", " ");
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            add("malum.subtitle." + id, name);
        });

        effects.forEach(e -> {
            String id = e.getId().getPath();
            String name = DataHelper.toTitleCase(makeProperEnglish(id), "_");
            add("effect.malum." + id, name);
        });

        attributes.forEach(a -> {
            String name = DataHelper.toTitleCase(a.getId().getPath(), "_");
            add("attribute.name.malum." + BuiltInRegistries.ATTRIBUTE.getKey(a.get()).getPath(), name);
        });

        entities.forEach(e -> {
            String name = DataHelper.toTitleCase(e.getId().getPath(), "_");
            add("entity.malum." + BuiltInRegistries.ENTITY_TYPE.getKey(e.get()).getPath(), name);
        });
        geasa.forEach(g -> {
            GeasEffectType effect = g.get();
            String name = DataHelper.toTitleCase(g.getId().getPath(), "_");
            add(effect.getLangKey(), name);
        });
        soulwovenBanners.forEach(p -> {
            String name = DataHelper.toTitleCase(p.type().getPath(), "_");
            add(p.translationKey(), name);
        });
        crucibleAttributes.forEach(a -> {
            String name = DataHelper.toTitleCase(a.getId().getPath(), "_");
            add(a.getLangKey(), name);
        });




        add("item.malum.filled_spirit_jar", "Filled Spirit Jar");
        add(SpiritTextData.STORED_SPIRITS, "Contains Stored Arcana");
        addSpiritLang(MalumSpiritTypes.SACRED_SPIRIT, "Innocent",
                "'The world itself bears no such concept as a moral truth, it is improper to see human sentiment as anything other than simply our perspective. And yet, through Sacred and it's opposite, a primordial divider does indeed exist.'",
                "Over-application has proven to enable ceaseless growth of nearby life. Caution must never be omitted, especially here."
        );
        addSpiritLang(MalumSpiritTypes.WICKED_SPIRIT, "Malicious",
                "'The reaper's tithe, an emblem of undeath, strife, and sheer, unfiltered malice. Wicked is a spirit that signifies humanity's downfall, born from our primal vices, it serves to fuel them.'",
                "Prolonged exposure creates a painful sensation at the meeting point. Higher dosages of Wicked produce an even more tangible sensation."
        );
        addSpiritLang(MalumSpiritTypes.ARCANE_SPIRIT, "Fundamental",
                "'That which forms consciousness in its thinnest details. A manifestation of a potentiality, Arcane serves as a fundamental fuel for the many arts of sorcery.'",
                "When calculating the amount used the upmost precision is required, even the slightest deviation can lead to cascading flaws."
        );
        addSpiritLang(MalumSpiritTypes.ELDRITCH_SPIRIT, "Esoteric",
                "'To warp the unchanging, to influence causality, to realign with the truth, Eldritch bears unnerving implications.'",
                "The smallest possible amount of Eldritch needed in order to create a localized distortion phenomena happens to be exactly one. Spirits simply cannot be divided."
        );
        addSpiritLang(MalumSpiritTypes.AERIAL_SPIRIT, "Swift",
                "'It stands as a cornerstone in the magecraft of aviation, Aerial proves capable of swaying the core laws of motion ever so slightly.'",
                "It wishes to defy the concept of stillness. If properly directed, this wish allows for the creation of motion with no traceable origin."
        );
        addSpiritLang(MalumSpiritTypes.AQUEOUS_SPIRIT, "Malleable",
                "'All life originates from the oceans of our Overworld, and yet as evolution progressed, the definition of life and that which is Aqueous eventually differed. As I collect and document each Soul I shatter, I wonder more and more about the different permutations of Arcana. Are we all just arrangements of different forms and impulses? Perhaps it is not in Arcana where consciousness rests and thus such thoughts serve as pointless rumination.'",
                ""
        );
        addSpiritLang(MalumSpiritTypes.INFERNAL_SPIRIT, "Radiant",
                "'The borrowed flame visible emanating from the Infernal crystal is not to be confused with actual light. The spirit defines itself as an unending desire to burn, so much so that the sensation of flame does manifest in the eyes of the passing observer. It is nothing more than a harmless possibility of fire that we are made to observe. Fascinating.''",
                "The sound of crackling fire can be heard just barely when brought close to the ear."
        );
        addSpiritLang(MalumSpiritTypes.EARTHEN_SPIRIT, "Steady",
                "''",
                ""
        );
        addSpiritLang(MalumSpiritTypes.UMBRAL_SPIRIT, "Antithesis",
                "''",
                ""
        );

        add("malum.gui.slot", "Slot: ");

        add("malum.gui.augment.installed", "When installed: ");
        add("malum.gui.augment.type.augment", "Augment");
        add("malum.gui.augment.type.core_augment", "Core Augment");

        addRiteTag(SpiritRiteEffectTag.RUNEWOOD, "Runewood");
        addRiteTag(SpiritRiteEffectTag.SOULWOOD, "Soulwood");
        addRiteTag(SpiritRiteEffectTag.AURA, "Aura");

        addRiteTag(SpiritRiteEffectTag.LESSER_RITE, "Lesser Rite");
        addRiteTag(SpiritRiteEffectTag.GREATER_RITE, "Greater Rite");

        addRiteTag(SpiritRiteEffectTag.RADIAL_EFFECT, "Area of Effect");
        addRiteTag(SpiritRiteEffectTag.LOCUS_EFFECT, "Bound Locus Effect");
        addRiteTag(SpiritRiteEffectTag.STRANGE_EFFECT, "Strange Effect");

        add(GeasItem.GEAS, "Geas");
        add(GeasItem.SWORN, "When Sworn: ");
        add(GeasItem.CREATIVE, "Creative Item for Debug Purposes.");
        add(GeasItem.CREATIVE_HELP, "Use To Swear/Forswear Geas Effect.");


        addGeasDescription(MalumGeasEffectTypes.PACT_OF_DEFIANCE, "Rage, rage, against the dying of your might");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_PARASITE, "Why work for what others have");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_LIFEWEAVER, "Weave your life into miracles");

        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_WARLOCK, "Weave the arcane");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_REAPER, "Swear loyalty to the edge");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_BERSERKER, "Your pain, their pain");

        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_FORTRESS, "Be strong when you are needed");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_SHIELD, "Always be ready to stand and fight");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_RECIPROCATION, "Prove your strength by wielding it");

        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_SHATTERING_ADDICT, "Claim what you want, and never stop");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_ARCANAPHAGE, "Seek magic in any form");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_RUNE_EXPLOITATION, "Gather them all, exhaust every possibility");

        addGeasDescription(MalumGeasEffectTypes.PACT_OF_SELF_CARE, "Eat, lest your body consume itself");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_HIGH_PRIEST, "Become what they believe, so long as they believe");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_PATIENCE_REPAID, "A warped echo");

        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_WINDSWEPT, "Run as the wind");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_CONTINUING_SHOT, "Step. Form. Ready. Raise. Begin. Draw. Release.");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_SKYBREAKER, "Move and be moved");

        addGeasDescription(MalumGeasEffectTypes.PACT_OF_CONTENTEDNESS, "To be full is to be anchored");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_LONE_DRUID, "Shed your second skin");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_PROFANE_ASCETIC, "Forswear indulgence, and be healed by rot");

        addGeasDescription(MalumGeasEffectTypes.PACT_OF_COMBUSTION, "To ignite and watch");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_THE_PROSPECTOR, "Engulf yourself with greed");
        addGeasDescription(MalumGeasEffectTypes.PACT_OF_WYRD_RECONSTRUCTION, "Witness oblivion and forge yourself anew");


//        addGeasDescription(MalumGeasEffectTypeRegistry.BOND_OF_BELOVED_CHAINS, "Tie your fates as one");
//        addGeasDescription(MalumGeasEffectTypeRegistry.BOND_OF_DEATHS_SEEKERS, "Find your ends together");

        addGeasDescription(MalumGeasEffectTypes.OATH_OF_THE_OVERKEEN_EYE, "Measure twice, cut once");
        addGeasDescription(MalumGeasEffectTypes.OATH_OF_THE_OVERBURDENED_MIND, "Measure carefully, cut later");
        addGeasDescription(MalumGeasEffectTypes.OATH_OF_THE_OVEREAGER_FIST, "Cut twice, never measure");

        addGeasDescription(MalumGeasEffectTypes.OATH_OF_UNMAKERS_DISDAIN, "Acknowledge no one, and be acknowledged by none");
        addGeasDescription(MalumGeasEffectTypes.OATH_OF_UNSIGHTED_RESISTANCE, "See no evil, feel no evil");
        addGeasDescription(MalumGeasEffectTypes.OATH_OF_THE_UNDISCERNED_MAW, "Take the life of your enemies");

        addGeasDescription(MalumGeasEffectTypes.AUTHORITY_OF_THE_INVERTED_HEART, "Your heart is the world, and the world beats");
//        addGeasDescription(MalumGeasEffectTypes.AUTHORITY_OF_CRUSHING_MELANCHOLY, "Dissociate from the weight of your existence");
        addGeasDescription(MalumGeasEffectTypes.AUTHORITY_OF_THE_GLEEFUL_TARGET, "Take it all, let it never stop, more, and more, and more");

        add("jukebox_song.malum.arcane_elegy", "Kultik - Arcane Elegy");
        add("jukebox_song.malum.aesthetica", "Kultik - Aesthetica");

        add("curios.identifier.brooch", "Brooch");
        add("curios.modifiers.brooch", "When worn:");

        add("curios.identifier.rune", "Rune");
        add("curios.modifiers.rune", "When equipped:");

        add("malum.jei.spirit_infusion", "Spirit Infusion");
        add("malum.jei.spirit_focusing", "Spirit Focusing");
        add("malum.jei.spirit_repair", "Spirit Repair");
        add("malum.jei.spirit_rite", "Spirit Rites");
        add("malum.jei.runeworking", "Runeworking");
        add("malum.jei.weeping_well", "The Weeping Well");
        add("malum.jei.spirit_transmutation", "The Unchained Rite");


        addPlayerlessDeathMessage(MalumDamageTypes.CULTIST_MAGIC, "%1$s met their end", "%1$s met their end while trying to escape %2$s");

        addDeathMessage(MalumDamageTypes.VOODOO, "%1$s had their soul shattered by %2$s", "%1$s had their soul shattered by %2$s using %3$s");
        addPlayerlessDeathMessage(MalumDamageTypes.VOODOO_PLAYERLESS, "%1$s had their soul shattered", "%1$s had their soul shattered while trying to escape %2$s");

        addPlayerlessDeathMessage(MalumDamageTypes.VOID, "%1$s underwent reality erosion", "%1$s underwent reality erosion while trying to escape %2$s");
        addPlayerlessDeathMessage(MalumDamageTypes.KARMIC, "%1$s was forsworn", "%1$s was forsworn while trying to escape %2$s");
        addPlayerlessDeathMessage(MalumDamageTypes.ROT, "%1$s was consumed by rot", "%1$s was consumed by rot while trying to escape %2$s");

        addDeathMessage(MalumDamageTypes.NITRATE, "%1$s had their soul detonated by %2$s", "%1$s had their soul detonated by %2$s using %3$s");
        addPlayerlessDeathMessage(MalumDamageTypes.NITRATE_PLAYERLESS, "%1$s had their soul detonated", "%1$s had their soul detonated while trying to escape %2$s");

        addDeathMessage(MalumDamageTypes.SCYTHE_MELEE, "%1$s was sliced in half by %2$s", "%1$s was sliced in half by %2$s using %3$s");
        addDeathMessage(MalumDamageTypes.SCYTHE_SWEEP, "%1$s was sliced in half by %2$s", "%1$s was sliced in half by %2$s using %3$s");
        addDeathMessage(MalumDamageTypes.SCYTHE_REBOUND, "%1$s was boomeranged by %2$s", "%1$s was boomeranged by %2$s using %3$s");
        addDeathMessage(MalumDamageTypes.SCYTHE_ASCENSION, "%1$s was cleaved using ascension by %2$s", "%1$s was cleaved using ascension by %2$s using %3$s");
        addDeathMessage(MalumDamageTypes.SCYTHE_COMBO, "%1$s was sliced in half and then again by %2$s", "%1$s was sliced in half and then again by %2$s using %3$s");
        addDeathMessage(MalumDamageTypes.SCYTHE_MAELSTROM, "%1$s was sliced and diced by %2$s", "%1$s was sliced and diced by %2$s using %3$s");

        addDeathMessage(MalumDamageTypes.HIDDEN_BLADE_PHYSICAL_COUNTER, "%1$s was sliced into innumerable pieces by %2$s", "%1$s was sliced into innumerable pieces by %2$s using %3$s");
        addDeathMessage(MalumDamageTypes.HIDDEN_BLADE_MAGIC_COUNTER, "%1$s had their soul shattered into innumerable pieces by %2$s", "%1$s had their soul shattered into innumerable pieces by %2$s using %3$s");

        addDeathMessage(MalumDamageTypes.TYRVING, "%1$s had their soul scarred by %2$s", "%1$s had their soul scarred by %2$s using %3$s");

        addDeathMessage(MalumDamageTypes.SUNDERING_ANCHOR_PHYSICAL_COMBO, "%1$s was struck down through torment by %2$s", "%1$s was struck down through torment by %2$s using %3$s");
        addDeathMessage(MalumDamageTypes.SUNDERING_ANCHOR_MAGIC_COMBO, "%1$s had their soul struck down through torment by %2$s", "%1$s had their soul struck down through torment by %2$s using %3$s");

        addDeathMessage(MalumDamageTypes.VINDICTIVE_BRAND_MELEE, "%1$s was marked by %2$s", "%1$s was marked by %2$s using %3$s");
        addDeathMessage(MalumDamageTypes.VINDICTIVE_BRAND_SWEEP, "%1$s was marked by %2$s", "%1$s was marked by %2$s using %3$s");
        addDeathMessage(MalumDamageTypes.VINDICTIVE_BRAND_COMBO, "%1$s was carved by %2$s", "%1$s was carved by %2$s using %3$s");

        addDeathMessage(MalumDamageTypes.WARLOCK_SPIRIT_IMPACT, "%1$s had their soul shattered by %2$s", "%1$s had their soul shattered by %2$s using %3$s");
        addDeathMessage(MalumDamageTypes.BERSERKER_SPIRIT_IMPACT, "%1$s had their soul shattered by %2$s", "%1$s had their soul shattered by %2$s using %3$s");

        addDeathMessage(MalumDamageTypes.INVERTED_HEART_PROPAGATION, "%1$s was caught in %2$s's karmic flow", "%1$s was caught in %2$s's karmic flow using %3$s");
        addDeathMessage(MalumDamageTypes.INVERTED_HEART_RETALIATION, "%1$s was caught in %2$s's karmic flow", "%1$s was caught in %2$s's karmic flow using %3$s");

        addJEEDEffectDescription(MalumMobEffects.STONE_WARD, "An earthen carapace surrounds your body, functioning as extra armor.");
        addJEEDEffectDescription(MalumMobEffects.OAKEN_MIGHT, "Your fists and tools are reinforced with earth, increasing your strength.");
        addJEEDEffectDescription(MalumMobEffects.BURNING_FERVOR, "Your tools are bolstered with radiance, increasing your mining and attack speed.");
        addJEEDEffectDescription(MalumMobEffects.FIERY_EMBRACE, "The warm embrace of fire coats your soul, mending your seared scars.");
        addJEEDEffectDescription(MalumMobEffects.HOWLING_GALE, "The zephyr propels you forward, increasing your movement speed.");
        addJEEDEffectDescription(MalumMobEffects.SKY_TETHER, "The heavens call for you, increasing jump height and decreasing gravity.");
        addJEEDEffectDescription(MalumMobEffects.FLOWING_GRASP, "You reach out for further power, increasing your reach and item pickup distance.");
        addJEEDEffectDescription(MalumMobEffects.GOOD_TIDES, "Let any fish who meets my gaze learn the true meaning of fear; for I am the harbinger of death. The bane of creatures sub-aqueous, my rod is true and unwavering as I cast into the aquatic abyss. A man, scorned by this uncaring Earth, finds solace in the sea. My only friend, the worm upon my hook. Wriggling, writhing, struggling to surmount the mortal pointlessness that permeates this barren world. I am alone. I am empty. And yet, I fish.");

        addJEEDEffectDescription(MalumMobEffects.ASCENSION, "Eases your fall and reduces gravity after a successful Scythe Ascenison.");
        addJEEDEffectDescription(MalumMobEffects.GLUTTONY, "You feed on the vulnerable, increasing magic proficiency at the expense of hunger./");
        addJEEDEffectDescription(MalumMobEffects.CANCEROUS_GROWTH, "You are emboldened by uncontrolled growth, increasing maximum health.");
        addJEEDEffectDescription(MalumMobEffects.ECHOING_ARCANA, "You are made wiser by uncontrolled magnification, increasing arcane resonance.");
        addJEEDEffectDescription(MalumMobEffects.WICKED_INTENT, "You bring forth a powerful counter attack, your next scythe attack will unleash an impossible volley of cuts.");
        addJEEDEffectDescription(MalumMobEffects.SILENCED, "You are silenced, leaving your magical capabilities neutered.");
        addJEEDEffectDescription(MalumMobEffects.GRIM_CERTAINTY, "The Weight of Worlds oscillates, sealing the next strike as a critical blow.");
        addJEEDEffectDescription(MalumMobEffects.IMMINENT_DELIVERANCE, "The Edge of Deliverance oscillates, sealing it's next strike as a critical blow.");

        addTetraMaterial("malignant_pewter", "Malignant Pewter");
        addTetraMaterial("soul_stained_steel", "Soulstained Steel");
        addTetraMaterial("hallowed_gold", "Hallowed Gold");
        addTetraMaterial("runewood", "Runewood");
        addTetraMaterial("soulwood", "Soulwood");
        addTetraMaterial("tainted_rock", "Tainted Rock");
        addTetraMaterial("twisted_rock", "Twisted Rock");
        addTetraMaterial("soulwoven_silk", "Soulwoven Silk");

        addTetraImprovement("malum.soul_strike", "Soul Strike", "The item's material allows it to strike the soul.");

        addEnchantmentNameAndDescription(EnchantmentKeys.ANIMATED, "Improves attack speed.");
        addEnchantmentNameAndDescription(EnchantmentKeys.HAUNTED, "Improves the Weapon's Magic Damage");
        addEnchantmentNameAndDescription(EnchantmentKeys.SPIRIT_PLUNDER, "Reaps extra Spirits when shattering a soul.");

        addEnchantmentNameAndDescription(EnchantmentKeys.REBOUND, "Allows the Scythe to be thrown when used.");
        addEnchantmentNameAndDescription(EnchantmentKeys.ASCENSION, "Enables the Scythe to propel the player upwards, damaging nearby enemies when used.");

        addEnchantmentNameAndDescription(EnchantmentKeys.WEAVERS_PROPAGATION, "Weaver's Propagation", "Summons additional spell loci when breaking a block with the tool.");
        addEnchantmentNameAndDescription(EnchantmentKeys.WEAVERS_HASTE, "Weaver's Haste", "Accelerates any spawned spell loci by the tool.");

        addEnchantmentNameAndDescription(EnchantmentKeys.REPLENISHING, "Restores Spell Charges when dealing melee damage with the staff.");
        addEnchantmentNameAndDescription(EnchantmentKeys.CAPACITOR, "Adds additional Spell Charges for use with the staff");

        addAttributeLibAttributeDescription(MalumAttributes.SCYTHE_PROFICIENCY, "Damage multiplier for Scythes");
        addAttributeLibAttributeDescription(MalumAttributes.SPIRIT_SPOILS, "Flat increase to spirits looted from slain foes");
        addAttributeLibAttributeDescription(MalumAttributes.ARCANE_RESONANCE, "Bonus potency for spirit-collection effects");

        //addAttributeLibAttributeDescription(MalumAttributes.HEALING_MULTIPLIER, "An increase in healing received");

        addAttributeLibAttributeDescription(MalumAttributes.SOUL_WARD_INTEGRITY, "A percentile increase in durability for Soul Ward");
        addAttributeLibAttributeDescription(MalumAttributes.SOUL_WARD_RECOVERY_RATE, "A percentile increase in recovery rate for Soul Ward");
        addAttributeLibAttributeDescription(MalumAttributes.SOUL_WARD_RECOVERY_GAIN, "A percentile increase the amount of Soul Ward recovered");
        addAttributeLibAttributeDescription(MalumAttributes.SOUL_WARD_CAPACITY, "The capacity for Soul Ward");

        addAttributeLibAttributeDescription(MalumAttributes.CHARGE_DURATION, "The duration for charging a staff");
        addAttributeLibAttributeDescription(MalumAttributes.CHARGE_CAPACITY, "The capacity for Spell Charges");
        addAttributeLibAttributeDescription(MalumAttributes.CHARGE_RECOVERY_RATE, "A percentile increase in recovery rate for Spell Charges");

        addAttributeLibAttributeDescription(MalumAttributes.GEAS_LIMIT, "The limit for active Geas Bindings that can be sworn at once");

        addAttributeLibAttributeDescription(MalumAttributes.MALIGNANT_CONVERSION, "A percentile conversion rate in which certain magical attributes are converted into Malignant Aegis");
        addAttributeLibAttributeDescription(MalumAttributes.MALIGNANT_AEGIS_CAPACITY, "A powerful multipurpose damage resistance attribute gained through Malignant Aegis");

    }

    private void addSpiritLang(SpiritHolder<SpiritArcanaType> spirit, String flavor, String verboseFlavour, String info) {
        spirit.getTextData().addLangDatagen(this, flavor, verboseFlavour, info);
    }

    @Override
    public String getName() {
        return "Malum Lang Entries";
    }

    public String makeProper(String s) {
        s = s
                .replaceAll("Of", "of")
                .replaceAll("The", "the")
                .replaceAll("Soul Stained", "Soulstained")
                .replaceAll("Soul Hunter", "Soulhunter");
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public void addRiteTag(SpiritRiteEffectTag tag, String name) {
        add(tag.getLangKey(), name);
    }

    public void addGeasDescription(Holder<GeasEffectType> effectType, String description) {
        add(effectType.value().getDescription(), description);
    }

    public void addTetraMaterial(String identifier, String name) {
        add("tetra.material." + identifier, name);
        add("tetra.material." + identifier + ".prefix", name);
    }

    public void addTetraImprovement(String identifier, String name, String description) {
        add("tetra.improvement." + identifier + ".name", name);
        add("tetra.improvement." + identifier + ".description", description);
    }

    public void addPlayerlessDeathMessage(ResourceKey<DamageType> damageType, String base, String player) {
        final String key = "death.attack." + damageType.location().getPath();
        add(key, base);
        add(key + ".player", player);
    }

    public void addDeathMessage(ResourceKey<DamageType> damageType, String base, String item) {
        final String key = "death.attack." + damageType.location().getPath();
        add(key, base);
        add(key + ".item", item);
    }

    public void addEnchantmentNameAndDescription(ResourceKey<Enchantment> enchantment, String desc) {
        var id = enchantment.location().getPath();
        addEnchantmentNameAndDescription(enchantment, DataHelper.toTitleCase(id, "_"), desc);
    }
    public void addEnchantmentNameAndDescription(ResourceKey<Enchantment> enchantment, String name, String desc) {
        var id = enchantment.location().getPath();
        var key = "enchantment.malum." + id;
        add(key, name);
        add(key + ".desc", desc);
    }

    public void addAttributeLibAttributeDescription(DeferredHolder<Attribute, Attribute> attribute, String desc) {
        add("attribute.name.malum." + attribute.getId().getPath() + ".desc", desc);
    }

    public void addJEEDEffectDescription(Supplier<MobEffect> mobEffectSupplier, String description) {
        add(mobEffectSupplier.get().getDescriptionId() + ".description", description);
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

    public String correctItemName(String name) {
        if (name.contains("music_disc")) {
            return "music_disc";
        }
        if ((!name.endsWith("_bricks"))) {
            if (name.contains("bricks")) {
                name = name.replaceFirst("bricks", "brick");
            }
        }
        if ((!name.endsWith("_boards"))) {
            if (name.contains("boards")) {
                name = name.replaceFirst("boards", "board");
            }
        }
        if (name.contains("_fence") || name.contains("_button")) {
            if (name.contains("planks")) {
                name = name.replaceFirst("_planks", "");
            }
        }
        if (name.startsWith("trans_")) {
            //TODO: replace this with just...
            // replace(ItemRegistry.WEAVERS_WORKBENCH.get(), this::makeProperEnglish);
            // no need to run the damn code on every single item, while filtering prideweaves
            return name;
        }
        return makeProperEnglish(name);
    }

    public String makeProperEnglish(String name) {
        String[] replacements = new String[]{"ns_", "rs_", "ts_"};
        String properName = name;
        for (String replacement : replacements) {
            int index = properName.indexOf(replacement);
            if (index != -1) {
                properName = properName.replaceFirst("s_", "'s_");
                break;
            }
        }
        return properName;
    }
}
