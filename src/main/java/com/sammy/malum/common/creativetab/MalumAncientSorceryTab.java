package com.sammy.malum.common.creativetab;

import com.sammy.malum.common.creativetab.button.ItemChoiceEntry;
import com.sammy.malum.registry.common.*;
import team.lodestar.lodestone.modules.toolkit.creative_tab.CategorizedBuilder;

import static com.sammy.malum.registry.common.MalumContent.*;

public class MalumAncientSorceryTab extends AbstractMalumCreativeTab {

        public MalumAncientSorceryTab(CategorizedBuilder categorizedBuilder) {
                super(categorizedBuilder);
        }

        public void buildCategories() {
                createCategory("fundamentals_of_arcana")
                        .addItems(
                                Spirits.SACRED_SPIRIT, Spirits.WICKED_SPIRIT, Spirits.ARCANE_SPIRIT, Spirits.ELDRITCH_SPIRIT,
                                Spirits.AERIAL_SPIRIT, Spirits.AQUEOUS_SPIRIT, Spirits.EARTHEN_SPIRIT, Spirits.INFERNAL_SPIRIT,
                                Spirits.UMBRAL_SPIRIT
                        ).nextLine()
                        .addItems(
                                MalumContent.ENCYCLOPEDIA_ARCANA,
                                MalumContent.ENCYCLOPEDIA_ESOTERICA,
                                Sorcery.SPIRIT_ALTAR,
                                Sorcery.SPIRIT_JAR,
                                Sorcery.RUNEWOOD_OBELISK,
                                Sorcery.BRILLIANT_OBELISK,
                                Sorcery.ARCANA_PYLON
                        ).nextLine()
                        .addItems(
                                Sorcery.WAND_TINKERER,
                                Sorcery.RUNIC_WORKBENCH,
                                Sorcery.WEAVERS_WORKBENCH
                        ).nextLine()
                        .addItems(
                                Artifice.ARTIFICERS_CLAW,
                                Artifice.CONJUNCTURE_CRYSTALLARIUM,
                                Artifice.WAVECHARGER,
                                Artifice.WAVEBANKER,
                                Artifice.WAVEMAKER,
                                Artifice.WAVEBREAKER,
                                Artifice.GUST_IGNITER,
                                Artifice.WIND_TUNNEL
                        ).nextLine()
                        .addItems(
                                Focusing.TUNING_FORK,
                                Focusing.SPIRIT_CRUCIBLE,
                                Focusing.SPIRIT_CATALYZER,
                                Focusing.REPAIR_PYLON
                        ).nextLine()
                        .addItems(
                                Totemancy.TOTEMIC_STAFF,
                                Totemancy.RUNEWOOD_TOTEM_BASE,
                                Totemancy.SOULWOOD_TOTEM_BASE,
                                Totemancy.WAVEFORM_RUNEWOOD_TOTEM_BASE,
                                Totemancy.WAVEFORM_SOULWOOD_TOTEM_BASE,
                                Totemancy.RITE_ANCHOR,
                                Totemancy.RITE_UNWEAVER,
                                Totemancy.RITE_SPREADER,
                                Totemancy.RITE_CHANNEL
                        )
                        .bake();
                createCategory("gear_and_trinkets")
                        .addItems(
                                Gear.CRUDE_SCYTHE,
                                Gear.SOULWOVEN_POUCH,
                                Gear.RAVENOUS_POUCH
                        ).nextLine()
                        .addItems(
                                Gear.SOUL_STAINED_STEEL_HELMET,
                                Gear.SOUL_STAINED_STEEL_CHESTPLATE,
                                Gear.SOUL_STAINED_STEEL_LEGGINGS,
                                Gear.SOUL_STAINED_STEEL_BOOTS,
                                Gear.SOUL_STAINED_STEEL_SWORD,
                                Gear.SOUL_STAINED_STEEL_PICKAXE,
                                Gear.SOUL_STAINED_STEEL_AXE,
                                Gear.SOUL_STAINED_STEEL_SHOVEL,
                                Gear.SOUL_STAINED_STEEL_HOE
                        )
                        .addItems(
                                Gear.SOUL_HUNTER_CLOAK,
                                Gear.SOUL_HUNTER_ROBE,
                                Gear.SOUL_HUNTER_LEGGINGS,
                                Gear.SOUL_HUNTER_BOOTS,
                                Gear.SOUL_STAINED_STEEL_SCYTHE,
                                Gear.SOUL_STAINED_STEEL_KNIFE,
                                Gear.TYRVING,
                                Gear.RAVENOUS_SCYTHE,
                                Gear.GLUTTONOUS_BLUDGEON
                        )
                        .addItems(
                                Gear.MALIGNANT_STRONGHOLD_HELMET,
                                Gear.MALIGNANT_STRONGHOLD_CHESTPLATE,
                                Gear.MALIGNANT_STRONGHOLD_LEGGINGS,
                                Gear.MALIGNANT_STRONGHOLD_BOOTS,
                                Gear.WEIGHT_OF_WORLDS,
                                Gear.EDGE_OF_DELIVERANCE,
                                Gear.SUNDERING_ANCHOR,
                                Gear.SPELLWEAVING_PICKAXE,
                                Gear.SPELLWEAVING_AXE
                        ).nextLine()
                        .addItems(
                                Gear.MNEMONIC_HEX_STAFF,
                                Gear.EROSION_SCEPTER,
                                Gear.UNWINDING_CHAOS
                        ).nextLine()
                        .addItems(
                                Gear.GILDED_RING,
                                Gear.GILDED_BELT,
                                Gear.ORNATE_RING,
                                Gear.ORNATE_NECKLACE
                        )
                        .addItems(
                                Gear.RUNIC_BROOCH,
                                Gear.ELABORATE_BROOCH,
                                Gear.GLASS_BROOCH,
                                Gear.GLUTTONOUS_BROOCH
                        ).nextLine()
                        .addItems(
                                Gear.RING_OF_ESOTERIC_SPOILS,
                                Gear.RING_OF_CURATIVE_TALENT,
                                Gear.RING_OF_ALCHEMICAL_MASTERY,
                                Gear.RING_OF_DESPERATE_VORACITY,
                                Gear.RING_OF_THE_RISING_EDGE,
                                Gear.RING_OF_HEARTY_AVARICE
                        ).nextLine()
                        .addItems(
                                Gear.RING_OF_ESOTERIC_SHADOW,
                                Gear.RING_OF_MANAWEAVING,
                                Gear.RING_OF_ARCANE_PROWESS,
                                Gear.RING_OF_SWARMING_ROT,
                                Gear.RING_OF_THE_HOWLING_MAELSTROM,
                                Gear.RING_OF_HEAVY_DISCHARGE
                        ).nextLine()
                        .addItems(
                                Gear.NECKLACE_OF_MYSTIC_POTENCY,
                                Gear.NECKLACE_OF_THE_NARROW_EDGE,
                                Gear.NECKLACE_OF_THE_WINDWEAVER
                        )
                        .addItems(
                                Gear.BELT_OF_THE_STARVED,
                                Gear.BELT_OF_THE_PROSPECTOR,
                                Gear.BELT_OF_THE_TIDEBOUND,
                                Gear.BELT_OF_OPULENT_INOCULATION,
                                Gear.BELT_OF_THE_MAGEBANE
                        ).nextLine()
                        .addItems(
                                Gear.RING_OF_THE_ENDLESS_WELL,
                                Gear.RING_OF_ECHOING_ARCANA,
                                Gear.RING_OF_GROWING_FLESH,
                                Gear.RING_OF_GRUESOME_CONCENTRATION,
                                Gear.NECKLACE_OF_THE_HIDDEN_BLADE,
                                Gear.NECKLACE_OF_THE_WATCHER,
                                Gear.BELT_OF_THE_LIMITLESS
                        ).nextLine()
                        .addItems(
                                Gear.RUNE_OF_VITALITY,
                                Gear.RUNE_OF_CULLING,
                                Gear.RUNE_OF_REINFORCEMENT,
                                Gear.RUNE_OF_VOLATILE_DISTORTION,
                                Gear.RUNE_OF_DEXTERITY,
                                Gear.RUNE_OF_AILMENT_CLEANSING,
                                Gear.RUNE_OF_PROTECTION,
                                Gear.RUNE_OF_SCORCHING
                        ).nextLine()
                        .addItems(
                                Gear.RUNE_OF_HOWLING_GALE,
                                Gear.RUNE_OF_FLOWING_GRASP,
                                Gear.RUNE_OF_STONE_WARD,
                                Gear.RUNE_OF_BURNING_FERVOR,
                                Gear.RUNE_OF_SKY_TETHER,
                                Gear.RUNE_OF_GOOD_TIDES,
                                Gear.RUNE_OF_OAKEN_MIGHT,
                                Gear.RUNE_OF_FIERY_EMBRACE
                        ).nextLine()
                        .addItems(
                                Gear.RUNE_OF_BOLSTERING,
                                Gear.RUNE_OF_RADIAL_EMPOWERMENT,
                                Gear.RUNE_OF_SPELL_MASTERY,
                                Gear.RUNE_OF_HERESY,
                                Gear.RUNE_OF_UNNATURAL_STAMINA,
                                Gear.RUNE_OF_TWINNED_DURATION,
                                Gear.RUNE_OF_INDOMITABILITY,
                                Gear.RUNE_OF_IGNEOUS_SOLACE
                        )
                        .bake();

                createCategory("soulformed_substance")
                        .addEntries(
                                ItemChoiceEntry.choice(Materials.ROTTING_ESSENCE, CompactBlocks.BLOCK_OF_ROTTING_ESSENCE),
                                ItemChoiceEntry.choice(Materials.GRIM_TALC, CompactBlocks.BLOCK_OF_GRIM_TALC),
                                ItemChoiceEntry.choice(Materials.EERIE_WEAVE, CompactBlocks.BLOCK_OF_EERIE_WEAVE),
                                ItemChoiceEntry.choice(Materials.WARP_FLUX, CompactBlocks.BLOCK_OF_WARP_FLUX),

                                ItemChoiceEntry.choice(Materials.WIND_NUCLEUS, CompactBlocks.BLOCK_OF_WIND_NUCLEI),
                                ItemChoiceEntry.choice(Materials.PYRE_NUCLEUS, CompactBlocks.BLOCK_OF_PYRE_NUCLEI)
                        ).nextLine()
                        .addEntries(
                                ItemChoiceEntry.choice(Materials.HEX_ASH, CompactBlocks.BLOCK_OF_HEX_ASH),
                                ItemChoiceEntry.choice(Materials.LIVING_FLESH, CompactBlocks.BLOCK_OF_LIVING_FLESH),
                                ItemChoiceEntry.choice(Materials.ALCHEMICAL_CALX, CompactBlocks.BLOCK_OF_ALCHEMICAL_CALX),
                                ItemChoiceEntry.choice(Materials.ARCANE_CHARCOAL, CompactBlocks.BLOCK_OF_ARCANE_CHARCOAL),

                                ItemChoiceEntry.choice(Materials.CALCIFIED_EBONY, CompactBlocks.BLOCK_OF_EBONY)
                        ).nextLine()
                        .addItems(
                                Materials.NULL_SLATE,
                                Materials.VOID_SALTS,
                                Materials.MNEMONIC_FRAGMENT,
                                Materials.AURIC_EMBERS,
                                Materials.MALIGNANT_LEAD
                        ).nextLine()
                        .addItems(
                                Materials.SOUL_STAINED_STEEL_INGOT,
                                Materials.SOUL_STAINED_STEEL_PLATING,
                                Materials.SOUL_STAINED_STEEL_NUGGET,
                                Materials.HALLOWED_GOLD_INGOT,
                                Materials.HALLOWED_GOLD_INLAY,
                                Materials.HALLOWED_GOLD_NUGGET,
                                Materials.MALIGNANT_PEWTER_INGOT,
                                Materials.MALIGNANT_PEWTER_PLATING,
                                Materials.MALIGNANT_PEWTER_NUGGET
                        ).nextLine()
                        .addItems(
                                Materials.SOULWOVEN_SILK,
                                Materials.PARACAUSAL_FLAME,
                                Materials.CONVOLUTED_LENS,
                                Materials.MIMICRY_RELAY,
                                Materials.POPPET,
                                Materials.IMITATION_FLESH,
                                Materials.IMITATION_HEART
                        ).nextLine()
                        .addItems(
                                Materials.ANOMALOUS_DESIGN,
                                Materials.COMPLETE_DESIGN,
                                Materials.FUSED_CONSCIOUSNESS
                        ).nextLine()
                        .addItems(
                                CompactBlocks.BLOCK_OF_NULL_SLATE,
                                CompactBlocks.BLOCK_OF_VOID_SALTS,
                                CompactBlocks.BLOCK_OF_MNEMONIC_FRAGMENT,
                                CompactBlocks.BLOCK_OF_AURIC_EMBERS,
                                CompactBlocks.BLOCK_OF_MALIGNANT_LEAD
                        ).nextLine()
                        .addItems(
                                CompactBlocks.BLOCK_OF_SOUL_STAINED_STEEL,
                                CompactBlocks.BLOCK_OF_HALLOWED_GOLD,
                                CompactBlocks.BLOCK_OF_MALIGNANT_PEWTER
                        ).bake();
                createCategory("earthen_treasures")
                        .addItems(
                                CompactBlocks.BLOCK_OF_RAW_SOULSTONE, CompactBlocks.BLOCK_OF_REFINED_SOULSTONE, Materials.DEEPSLATE_SOULSTONE_ORE, Materials.SOULSTONE_ORE,
                                Materials.RAW_SOULSTONE, Materials.REFINED_SOULSTONE, Materials.SOULSTONE_BUD, Materials.REALIZED_SOULSTONE_BUD
                        ).nextLine()
                        .addItems(
                                CompactBlocks.BLOCK_OF_RAW_BRILLIANCE, CompactBlocks.BLOCK_OF_BRILLIANCE, Materials.BRILLIANT_DEEPSLATE, Materials.BRILLIANT_STONE,
                                Materials.RAW_BRILLIANCE, Materials.REFINED_BRILLIANCE
                        ).nextLine()
                        .addItems(
                                CompactBlocks.BLOCK_OF_BLAZING_QUARTZ, Materials.BLAZING_QUARTZ_ORE,
                                Materials.BLAZING_QUARTZ
                        ).nextLine()
                        .addItems(
                                CompactBlocks.BLOCK_OF_CTHONIC_GOLD, Materials.CTHONIC_GOLD_ORE,
                                Materials.CTHONIC_GOLD, Materials.CTHONIC_GOLD_FRAGMENT
                        )
                        .addItems(Materials.MUNDANE_QUARTZ::addToCreativeTab)
                        .addItems(Materials.VIVID_AMETRINE::addToCreativeTab)
                        .addItems(Materials.MARINE_AGATE::addToCreativeTab)
                        .addItems(Materials.RUGGED_CITRINE::addToCreativeTab)
                        .bake();
        }
}