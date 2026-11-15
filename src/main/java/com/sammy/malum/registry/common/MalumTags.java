package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.magic.*;
import net.minecraft.core.component.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.*;

public class MalumTags {

    public static class Items {

        public static final TagKey<Item> SOUL_SHATTER_CAPABLE_WEAPON = tag("soul_shatter_capable_weapon");
        public static final TagKey<Item> MAGIC_CAPABLE_WEAPON = tag("magic_capable_weapon");

        public static final TagKey<Item> SCYTHES = tag("scythe");
        public static final TagKey<Item> SCYTHES_COMMON = commonTag("tools/scythe");

        public static final TagKey<Item> STAVES = tag("staff");
        public static final TagKey<Item> STAVES_COMMON = commonTag("tools/staff");

        public static final TagKey<Item> KNIVES = modTag("farmersdelight:tools/knives");
        public static final TagKey<Item> KNIVES_COMMON = commonTag("tools/knife");

        public static final TagKey<Item> ANIMATED_ENCHANTABLE = tag("enchantable/animated");
        public static final TagKey<Item> HAUNTED_ENCHANTABLE = tag("enchantable/haunted");
        public static final TagKey<Item> SPIRIT_PLUNDER_ENCHANTABLE = tag("enchantable/spirit_plunder");

        public static final TagKey<Item> SCYTHE_ENCHANTABLE = tag("enchantable/scythe");
        public static final TagKey<Item> REBOUND_ENCHANTABLE = tag("enchantable/rebound");
        public static final TagKey<Item> ASCENSION_ENCHANTABLE = tag("enchantable/ascension");

        public static final TagKey<Item> SPELLWEAVING_ENCHANTABLE = tag("enchantable/spellweaving");
        public static final TagKey<Item> WEAVERS_PROPAGATION_ENCHANTABLE = tag("enchantable/weavers_propagation");
        public static final TagKey<Item> WEAVERS_HASTE_ENCHANTABLE = tag("enchantable/weavers_haste");

        public static final TagKey<Item> STAFF_ENCHANTABLE = tag("enchantable/staff");
        public static final TagKey<Item> REPLENISHING_ENCHANTABLE = tag("enchantable/replenishing");
        public static final TagKey<Item> CAPACITOR_ENCHANTABLE = tag("enchantable/capacitor");

        public static final TagKey<Item> SPIRITS = tag("spirits");
        public static final TagKey<Item> ASPECTED_SPIRITS = tag("aspected_spirits");
        public static final TagKey<Item> MOB_DROPS = tag("mob_drops");
        public static final TagKey<Item> MATERIALS = tag("materials");
        public static final TagKey<Item> MINERALS = tag("minerals");
        public static final TagKey<Item> AUGMENTS = tag("augments");
        public static final TagKey<Item> CORE_AUGMENTS = tag("augments");
        public static final TagKey<Item> METAL_NODES = tag("metal_nodes");
        public static final TagKey<Item> IMPETUS = tag("impetus");
        public static final TagKey<Item> METAL_IMPETUS = tag("metal_impetus");
        public static final TagKey<Item> FRACTURED_IMPETUS = tag("fractured_impetus");
        public static final TagKey<Item> FRACTURED_METAL_IMPETUS = tag("fractured_metal_impetus");

        public static final TagKey<Item> ARMORS = tag("armors");

        public static final TagKey<Item> RUNES_STONE = tag("runes/stone");
        public static final TagKey<Item> RUNES_WOODEN = tag("runes/wooden");
        public static final TagKey<Item> RUNES_VOID = tag("runes/void");

        public static final TagKey<Item> NECKLACE_CURIO = modTag("curios:necklace");
        public static final TagKey<Item> RING_CURIO = modTag("curios:ring");
        public static final TagKey<Item> BELT_CURIO = modTag("curios:belt");
        public static final TagKey<Item> BROOCH_CURIO = modTag("curios:brooch");
        public static final TagKey<Item> RUNE_CURIO = modTag("curios:rune");
        public static final TagKey<Item> CHARM_CURIO = modTag("curios:charm");

        public static final TagKey<Item> IS_TOTEMIC_TOOL = tag("totemic_tool");
        public static final TagKey<Item> IS_REDSTONE_TOOL = tag("redstone_tool");
        public static final TagKey<Item> IS_ARTIFICE_TOOL = tag("artifice_tool");

        public static final TagKey<Item> COUNTS_AS_EMPTY_HAND = tag("counts_as_empty_hand");

        public static final TagKey<Item> SAPBALLS = tag("sapballs");
        public static final TagKey<Item> GROSS_FOODS = tag("gross_foods");

        public static final TagKey<Item> PROSPECTORS_TREASURE = tag("prospectors_treasure");

        public static final TagKey<Item> SOULWOVEN_POUCH_EFFICIENT = tag("soulwoven_pouch_efficient");
        public static final TagKey<Item> SOULWOVEN_POUCH_AUTOCOLLECT = tag("soulwoven_pouch_autocollect");

        public static final TagKey<Item> ARCANE_ELEGY_COMPONENTS = tag("arcane_elegy_component");

        public static final TagKey<Item> VOID_SOULSTONE_CONVERSION = tag("void_soulstone_material");

        public static final TagKey<Item> HIDDEN_ALWAYS = tag("hidden_items/always");
        public static final TagKey<Item> HIDDEN_UNTIL_VOID = tag("hidden_items/void");
        public static final TagKey<Item> HIDDEN_UNTIL_BLACK_CRYSTAL = tag("hidden_items/black_crystal");
        public static final TagKey<Item> HIDDEN_AS_RESULT_ONLY = tag("hidden_items/result_only");


        public static final TagKey<Item> RUNEWOOD_LOGS = tag("runewood_logs");
        public static final TagKey<Item> SOULWOOD_LOGS = tag("soulwood_logs");

        public static final TagKey<Item> TAINTED_ROCK = tag("tainted_rock");
        public static final TagKey<Item> TWISTED_ROCK = tag("twisted_rock");

        public static final TagKey<Item> CONJUNCTURE_CRYSTALLARIUM_FUEL = tag("conjuncture_crystallarium_fuel");



        private static TagKey<Item> modTag(String path) {
            return TagKey.create(Registries.ITEM, ResourceLocation.parse(path));
        }

        private static TagKey<Item> tag(String path) {
            return TagKey.create(Registries.ITEM, MalumMod.malumPath(path));
        }

        private static TagKey<Item> commonTag(String name) {
            return net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }

    public static class Blocks {

        public static final TagKey<Block> NATURAL_SOULSTONE_BUD_SURFACE = tag("natural_soulstone_bud_surface");
        public static final TagKey<Block> PREFERRED_SOULSTONE_BUD_SURFACE = tag("preferred_soulstone_bud_surface");
        public static final TagKey<Block> PREFERRED_WITCHHAZEL_SURFACE = tag("preferred_witchhazel_surface");
        public static final TagKey<Block> PREFERRED_EBONY_SURFACE = tag("preferred_ebony_surface");

        public static final TagKey<Block> EBONY_REPLACEABLE = tag("ebony_replaceable");

        public static final TagKey<Block> UNCHAINED_RITE_CATALYST = tag("unchained_rite_catalyst");
        public static final TagKey<Block> IS_RITE_IMMUNE = tag("is_rite_immune");
        public static final TagKey<Block> GREATER_AERIAL_WHITELIST = tag("greater_aerial_whitelist");

        public static final TagKey<Block> BLIGHT_PLACEABLE_ON = tag("blight_placeable_on");
        public static final TagKey<Block> BLIGHT_REPLACEABLE = tag("blight_replaceable");
        public static final TagKey<Block> BLIGHT_REMOVABLE = tag("blight_removable");

        public static final TagKey<Block> BLIGHT = tag("blight");
        public static final TagKey<Block> BLIGHTED_PLANTS = tag("blighted_plants");

        public static final TagKey<Block> TAINTED_ROCK = tag("tainted_rock");
        public static final TagKey<Block> TWISTED_ROCK = tag("twisted_rock");

        public static final TagKey<Block> DROSS_STONE = tag("dross_stone");
        public static final TagKey<Block> DROSS_STONE_BLOCKS = tag("dross_stone_blocks");
        public static final TagKey<Block> DROSS_STONE_SLABS = tag("dross_stone_slabs");
        public static final TagKey<Block> DROSS_STONE_STAIRS = tag("dross_stone_stairs");
        public static final TagKey<Block> DROSS_STONE_WALLS = tag("dross_stone_walls");

        public static final TagKey<Block> WEEPING_WELL = tag("weeping_well");

        public static final TagKey<Block> SUNDERING_ANCHOR_PLEASE_BE_A_KNIFE = tag("sundering_anchor_please_be_a_knife");

        public static final TagKey<Block> FD_TRAY_HEAT_SOURCES = modTag("farmersdelight:tray_heat_sources");
        public static final TagKey<Block> FD_HEAT_SOURCES = modTag("farmersdelight:heat_sources");
        public static final TagKey<Block> FD_MINEABLE_WITH_KNIFE = modTag("farmersdelight:mineable/knife");

        public static final TagKey<Block> CREATE_WRENCH_PICKUP = modTag("create:wrench_pickup");
        public static final TagKey<Block> EIDOLON_ALTAR_BLOCK = modTag("eidolon_repraised:altar_blocks");

        public static final TagKey<Block> REAPING_ORES = tag("reaping_ores");

        private static TagKey<Block> modTag(String path) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.parse(path));
        }

        private static TagKey<Block> tag(String path) {
            return TagKey.create(Registries.BLOCK, MalumMod.malumPath(path));
        }

        private static TagKey<Block> commonTag(String name) {
            return net.minecraft.tags.BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }

    public static class GeasTypes {

        public static final TagKey<GeasEffectType> IS_BOND = tag("is_bond");
        public static final TagKey<GeasEffectType> IS_OATH = tag("is_oath");
        public static final TagKey<GeasEffectType> IS_AUTHORITY = tag("is_authority");

        public static final TagKey<GeasEffectType> HIDDEN_UNTIL_BLACK_CRYSTAL = tag("hidden_geas/black_crystal");

        private static TagKey<GeasEffectType> tag(String path) {
            return TagKey.create(MalumGeasEffectTypes.GEAS_TYPES_KEY, MalumMod.malumPath(path));
        }
    }

    public static class DamageTypes {

        public static final TagKey<DamageType> BYPASSES_HALF_ARMOR = tag("bypasses_half_armor");
        public static final TagKey<DamageType> BYPASSES_SOUL_WARD = tag("bypasses_soul_ward");
        public static final TagKey<DamageType> BYPASSES_MALIGNANT_AEGIS = tag("bypasses_malignant_aegis");

        public static final TagKey<DamageType> SOUL_SHATTER_DAMAGE = tag("can_soul_shatter");

        public static final TagKey<DamageType> IS_SCYTHE = tag("is_scythe");
        public static final TagKey<DamageType> IS_SCYTHE_MELEE = tag("is_scythe_melee");
        public static final TagKey<DamageType> TRIGGERS_SCYTHE_COMBO = tag("triggers_scythe_combo");

        public static final TagKey<DamageType> IS_NITRATE = tag("is_nitrate");

        public static final TagKey<DamageType> IS_HIDDEN_BLADE = tag("is_hidden_blade");
        public static final TagKey<DamageType> IS_SUNDERING_ANCHOR_COMBO = tag("is_sundering_anchor_combo");

        public static final TagKey<DamageType> IS_INVERTED_HEART = tag("is_soulwashing");
        public static final TagKey<DamageType> INVERTED_HEART_RETALIATION_BLACKLIST = tag("soulwashing_retaliation_blacklist");
        public static final TagKey<DamageType> INVERTED_HEART_PROPAGATION_BLACKLIST = tag("soulwashing_propagation_blacklist");

        public static final TagKey<DamageType> GLEEFUL_TARGET_BLACKLIST = tag("lions_heart_blacklist");

        public static TagKey<DamageType> tag(String path) {
            return TagKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath(path));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> HAS_SOULSTONE = tag("has_soulstone");
        public static final TagKey<Biome> HAS_BRILLIANCE = tag("has_brilliant");
        public static final TagKey<Biome> HAS_CTHONIC_GOLD = tag("has_cthonic_gold");

        public static final TagKey<Biome> HAS_RUNEWOOD = tag("has_runewood");
        public static final TagKey<Biome> HAS_RARE_RUNEWOOD = tag("has_rare_runewood");

        public static final TagKey<Biome> HAS_AZURE_RUNEWOOD = tag("has_azure_runewood");
        public static final TagKey<Biome> HAS_RARE_AZURE_RUNEWOOD = tag("has_rare_azure_runewood");

        public static final TagKey<Biome> HAS_EBONY = tag("has_ebony");

        public static final TagKey<Biome> HAS_WEEPING_WELL = tag("has_weeping_well");

        public static TagKey<Biome> tag(String path) {
            return TagKey.create(Registries.BIOME, MalumMod.malumPath(path));
        }
    }

    public static class Entities {

        public static final TagKey<EntityType<?>> CULTIST = tag("cultist");

        public static final TagKey<EntityType<?>> IGNORE_SEAT = tag("ignore_seat");

        public static TagKey<EntityType<?>> tag(String path) {
            return TagKey.create(Registries.ENTITY_TYPE, MalumMod.malumPath(path));
        }
    }

    public static class DataComponents {
        public static final TagKey<DataComponentType<?>> SPIRIT_INFUSION_BLACKLIST = TagKey.create(Registries.DATA_COMPONENT_TYPE, MalumMod.malumPath("spirit_infusion_blacklist"));
    }
}