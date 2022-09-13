package com.sammy.malum.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import team.lodestar.lodestone.systems.config.LodestoneConfig;

import java.util.ArrayList;
import java.util.List;

import static com.sammy.malum.MalumMod.MALUM;

public class CommonConfig extends LodestoneConfig {

    //worldgen
    public static ConfigValueHolder<Boolean> GENERATE_RUNEWOOD_TREES = new ConfigValueHolder<>(MALUM,"common/worldgen/runewood", (builder ->
            builder.comment("Should runewood trees naturally generate?")
                    .define("generateRunewood", true)));
    public static ConfigValueHolder<Double> COMMON_RUNEWOOD_CHANCE = new ConfigValueHolder<>(MALUM,"common/worldgen/runewood", (builder ->
            builder.comment("Chance for runewood trees to generate in open biomes such as plains.")
                    .defineInRange("runewoodCommonChance", 0.02d, 0, 1)));
    public static ConfigValueHolder<Double> RARE_RUNEWOOD_CHANCE = new ConfigValueHolder<>(MALUM,"common/worldgen/runewood", (builder ->
            builder.comment("Chance for runewood trees to generate in forest biomes.")
                    .defineInRange("runewoodRareChance", 0.01d, 0, 1)));

    public static ConfigValueHolder<Boolean> GENERATE_BLAZE_QUARTZ = new ConfigValueHolder<>(MALUM,"common/worldgen/blazing_quartz", (builder ->
            builder.comment("Should blaze quartz ore generate?")
                    .define("generateBlazeQuartz", true)));
    public static ConfigValueHolder<Integer> BLAZE_QUARTZ_SIZE = new ConfigValueHolder<>(MALUM,"common/worldgen/blazing_quartz", (builder ->
            builder.comment("Size of blaze quartz ore veins.")
                    .define("blazeQuartzSize", 14)));
    public static ConfigValueHolder<Integer> BLAZE_QUARTZ_AMOUNT = new ConfigValueHolder<>(MALUM,"common/worldgen/blazing_quartz", (builder ->
            builder.comment("Amount of blaze quartz ore veins.")
                    .define("blazeQuartzAmount", 16)));

    public static ConfigValueHolder<Boolean> GENERATE_BRILLIANT_STONE = new ConfigValueHolder<>(MALUM,"common/worldgen/brilliance", (builder ->
            builder.comment("Should brilliant stone generate?")
                    .define("generateBrilliantStone", true)));
    public static ConfigValueHolder<Integer> BRILLIANT_STONE_SIZE = new ConfigValueHolder<>(MALUM,"common/worldgen/brilliance", (builder ->
            builder.comment("Size of brilliant stone veins.")
                    .define("brilliantStoneSize", 4)));
    public static ConfigValueHolder<Integer> BRILLIANT_STONE_AMOUNT = new ConfigValueHolder<>(MALUM,"common/worldgen/brilliance", (builder ->
            builder.comment("Amount of brilliant stone veins.")
                    .define("brilliantStoneAmount", 3)));
    public static ConfigValueHolder<Integer> BRILLIANT_STONE_MIN_Y = new ConfigValueHolder<>(MALUM,"common/worldgen/brilliance", (builder ->
            builder.comment("Minimum height at which brilliant stone can spawn.")
                    .define("brilliantStoneMinY", -64)));
    public static ConfigValueHolder<Integer> BRILLIANT_STONE_MAX_Y = new ConfigValueHolder<>(MALUM,"common/worldgen/brilliance", (builder ->
            builder.comment("Maximum height at which brilliant stone can spawn.")
                    .define("brilliantStoneMaxY", 40)));

    public static ConfigValueHolder<Boolean> GENERATE_SOULSTONE = new ConfigValueHolder<>(MALUM,"common/worldgen/soulstone", (builder ->
            builder.comment("Should soulstone ore generate underground?")
                    .define("generateSoulstone", true)));
    public static ConfigValueHolder<Integer> SOULSTONE_SIZE = new ConfigValueHolder<>(MALUM,"common/worldgen/soulstone", (builder ->
            builder.comment("Size of soulstone ore veins underground.")
                    .define("soulstoneSize", 12)));
    public static ConfigValueHolder<Integer> SOULSTONE_AMOUNT = new ConfigValueHolder<>(MALUM,"common/worldgen/soulstone", (builder ->
            builder.comment("Amount of soulstone ore veins.")
                    .define("soulstoneAmount", 8)));
    public static ConfigValueHolder<Integer> SOULSTONE_MIN_Y = new ConfigValueHolder<>(MALUM,"common/worldgen/soulstone", (builder ->
            builder.comment("Minimum height at which soulstone ore can spawn.")
                    .define("soulstoneMinY", -64)));
    public static ConfigValueHolder<Integer> SOULSTONE_MAX_Y = new ConfigValueHolder<>(MALUM,"common/worldgen/soulstone", (builder ->
            builder.comment("Maximum height at which soulstone ore can spawn.")
                    .define("soulstoneMaxY", 30)));

    public static ConfigValueHolder<Boolean> GENERATE_SURFACE_SOULSTONE = new ConfigValueHolder<>(MALUM,"common/worldgen/soulstone", (builder ->
            builder.comment("Should soulstone ore generate on the surface?")
                    .define("generateSurfaceSoulstone", true)));
    public static ConfigValueHolder<Integer> SURFACE_SOULSTONE_SIZE = new ConfigValueHolder<>(MALUM,"common/worldgen/soulstone", (builder ->
            builder.comment("Size of soulstone ore veins on the surface.")
                    .define("surfaceSoulstoneSize", 6)));
    public static ConfigValueHolder<Integer> SURFACE_SOULSTONE_AMOUNT = new ConfigValueHolder<>(MALUM,"common/worldgen/soulstone", (builder ->
            builder.comment("Amount of soulstone ore veins on the surface.")
                    .define("surfaceSoulstoneAmount", 8)));
    public static ConfigValueHolder<Integer> SURFACE_SOULSTONE_MIN_Y = new ConfigValueHolder<>(MALUM,"common/worldgen/soulstone", (builder ->
            builder.comment("Minimum height at which surface soulstone ore can spawn.")
                    .define("surfaceSoulstoneMinY", 60)));
    public static ConfigValueHolder<Integer> SURFACE_SOULSTONE_MAX_Y = new ConfigValueHolder<>(MALUM,"common/worldgen/soulstone", (builder ->
            builder.comment("Maximum height at which surface soulstone ore can spawn.")
                    .define("surfaceSoulstoneMaxY", 100)));

    public static ConfigValueHolder<Boolean> GENERATE_NATURAL_QUARTZ = new ConfigValueHolder<>(MALUM,"common/worldgen/natural_quartz", (builder ->
            builder.comment("Should natural quartz ore generate?")
                    .define("generateBrilliantStone", true)));
    public static ConfigValueHolder<Integer> NATURAL_QUARTZ_SIZE = new ConfigValueHolder<>(MALUM,"common/worldgen/natural_quartz", (builder ->
            builder.comment("Size of natural quartz ore veins.")
                    .define("naturalQuartzSize", 5)));
    public static ConfigValueHolder<Integer> NATURAL_QUARTZ_AMOUNT = new ConfigValueHolder<>(MALUM,"common/worldgen/natural_quartz", (builder ->
            builder.comment("Amount of natural quartz ore veins.")
                    .define("naturalQuartzAmount", 2)));
    public static ConfigValueHolder<Integer> NATURAL_QUARTZ_MIN_Y = new ConfigValueHolder<>(MALUM,"common/worldgen/natural_quartz", (builder ->
            builder.comment("Minimum height at which natural quartz ore can spawn.")
                    .define("naturalQuartzMinY", -64)));
    public static ConfigValueHolder<Integer> NATURAL_QUARTZ_MAX_Y = new ConfigValueHolder<>(MALUM,"common/worldgen/natural_quartz", (builder ->
            builder.comment("Maximum height at which natural quartz ore can spawn.")
                    .define("naturalQuartzMaxY", 10)));

    public static ConfigValueHolder<Boolean> GENERATE_QUARTZ_GEODES = new ConfigValueHolder<>(MALUM,"common/worldgen/natural_quartz_geode", (builder ->
            builder.comment("Should quartz geodes generate?")
                    .define("generateQuartzGeodes", true)));

    public static final ConfigValueHolder<List<? extends String>> QUARTZ_GEODE_ALLOWED_DIMENSIONS = new ConfigValueHolder<>(MALUM,"common/worldgen/natural_quartz_geode", (builder) ->
        builder.comment("Which dimensions can quartz geodes generate in?")
            .defineList("quartzGeodeDimensions", new ArrayList<>(List.of("minecraft:overworld")), s -> s instanceof String));

    public static ConfigValueHolder<Boolean> GENERATE_RARE_EARTH = new ConfigValueHolder<>(MALUM,"common/worldgen/rare_earth", (builder ->
            builder.comment("Should rare earth generate?")
                    .define("generateRareEarth", true)));

    public static final ConfigValueHolder<List<? extends String>> RARE_EARTHS_ALLOWED_DIMENSIONS = new ConfigValueHolder<>(MALUM,"common/worldgen/rare_earth", (builder) ->
        builder.comment("Which dimensions can rare earths generate in?")
            .defineList("rareEarthDimensions", new ArrayList<>(List.of("minecraft:overworld")), s -> s instanceof String));

    public static ConfigValueHolder<Boolean> ULTIMATE_REBOUND = new ConfigValueHolder<>(MALUM,"common/item/rebound", (builder ->
            builder.comment("If set to true, you may put rebound on any weapon in the game.")
                    .define("enableUltimateRebound", false)));

    public static ConfigValueHolder<Boolean> AWARD_CODEX_ON_KILL = new ConfigValueHolder<>(MALUM,"common/codex", (builder ->
            builder.comment("If set to true, the first undead enemy a player slays will drop the encyclopedia arcana.")
                    .define("enableCodexDrop", true)));

    public static ConfigValueHolder<Boolean> NO_FANCY_SPIRITS = new ConfigValueHolder<>(MALUM,"common/spirit", (builder ->
        builder.comment("If set to true, any spirits dropped will simply take the form of an item.")
            .define("noFancySpirits", false)));

    public static ConfigValueHolder<Boolean> SOULLESS_SPAWNERS = new ConfigValueHolder<>(MALUM,"common/spirit/spawner", (builder ->
            builder.comment("If set to true, mob spawners will create soulless mobs instead.")
                    .define("lameSpawners", false)));

    public static ConfigValueHolder<Boolean> USE_DEFAULT_SPIRIT_VALUES = new ConfigValueHolder<>(MALUM,"common/spirit/defaults", (builder ->
        builder.comment("Whether entities without spirit jsons will use the default spirit data for their category.")
            .define("defaultSpiritValues", true)));

    public static ConfigValueHolder<Double> SOUL_WARD_PHYSICAL = new ConfigValueHolder<>(MALUM,"common/spirit/affinity/soul_ward", (builder ->
            builder.comment("Multiplier for physical damage taken while soul ward is active.")
                    .defineInRange("soulWardPhysical", 0.7f, 0, 1)));
    public static ConfigValueHolder<Double> SOUL_WARD_MAGIC = new ConfigValueHolder<>(MALUM,"common/spirit/affinity/soul_ward", (builder ->
            builder.comment("Multiplier for magic damage taken while soul ward is active.")
                    .defineInRange("soulWardMagic", 0.1f, 0, 1)));
    public static ConfigValueHolder<Integer> SOUL_WARD_RATE = new ConfigValueHolder<>(MALUM,"common/spirit/affinity/soul_ward", (builder ->
            builder.comment("Base time in ticks it takes for one point of soul ward to recover.")
                    .define("soulWardRate", 60)));

    public static ConfigValueHolder<Double> HEART_OF_STONE_COST = new ConfigValueHolder<>(MALUM,"common/spirit/affinity/heart_of_stone", (builder ->
            builder.comment("Amount of hunger consumed when recovering a point of heart of stone. Do note that this will only matter if the player has the earthen affinity.")
                    .defineInRange("heartOfStoneCost", 0.2d, 0, 1)));
    public static ConfigValueHolder<Integer> HEART_OF_STONE_RATE = new ConfigValueHolder<>(MALUM,"common/spirit/affinity/heart_of_stone", (builder ->
            builder.comment("Base time in ticks it takes for one point of heart of stone to recover.")
                    .define("heartOfStoneRate", 40)));


    public CommonConfig(ForgeConfigSpec.Builder builder) {
        super(MALUM, "common", builder);
    }

    public static final CommonConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
