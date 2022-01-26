package com.sammy.malum.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {

    //worldgen
    public static ForgeConfigSpec.ConfigValue<Boolean> GENERATE_RUNEWOOD_TREES;
    public static ForgeConfigSpec.DoubleValue COMMON_RUNEWOOD_CHANCE;
    public static ForgeConfigSpec.DoubleValue RARE_RUNEWOOD_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Boolean> GENERATE_BLAZE_QUARTZ;
    public static ForgeConfigSpec.ConfigValue<Integer> BLAZE_QUARTZ_SIZE;
    public static ForgeConfigSpec.ConfigValue<Integer> BLAZE_QUARTZ_AMOUNT;

    public static ForgeConfigSpec.ConfigValue<Boolean> GENERATE_BRILLIANT_STONE;
    public static ForgeConfigSpec.ConfigValue<Integer> BRILLIANT_STONE_SIZE;
    public static ForgeConfigSpec.ConfigValue<Integer> BRILLIANT_STONE_MIN_Y;
    public static ForgeConfigSpec.ConfigValue<Integer> BRILLIANT_STONE_MAX_Y;
    public static ForgeConfigSpec.ConfigValue<Integer> BRILLIANT_STONE_AMOUNT;

    public static ForgeConfigSpec.ConfigValue<Boolean> GENERATE_SOULSTONE;
    public static ForgeConfigSpec.ConfigValue<Integer> SOULSTONE_SIZE;
    public static ForgeConfigSpec.ConfigValue<Integer> SOULSTONE_MIN_Y;
    public static ForgeConfigSpec.ConfigValue<Integer> SOULSTONE_MAX_Y;
    public static ForgeConfigSpec.ConfigValue<Integer> SOULSTONE_AMOUNT;

    public static ForgeConfigSpec.ConfigValue<Boolean> GENERATE_SURFACE_SOULSTONE;
    public static ForgeConfigSpec.ConfigValue<Integer> SURFACE_SOULSTONE_SIZE;
    public static ForgeConfigSpec.ConfigValue<Integer> SURFACE_SOULSTONE_MIN_Y;
    public static ForgeConfigSpec.ConfigValue<Integer> SURFACE_SOULSTONE_MAX_Y;
    public static ForgeConfigSpec.ConfigValue<Integer> SURFACE_SOULSTONE_AMOUNT;

    //item
    public static ForgeConfigSpec.ConfigValue<Boolean> ULTIMATE_REBOUND;

    //affinity
    public static ForgeConfigSpec.DoubleValue SOUL_WARD_PHYSICAL;
    public static ForgeConfigSpec.DoubleValue SOUL_WARD_MAGIC;
    public static ForgeConfigSpec.ConfigValue<Integer> SOUL_WARD_RATE;

    public static ForgeConfigSpec.DoubleValue HEART_OF_STONE_COST;
    public static ForgeConfigSpec.ConfigValue<Integer> HEART_OF_STONE_RATE;



    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Worldgen Settings").push("worldgen");

        builder.comment("Runewood Config").push("runewood");
        GENERATE_RUNEWOOD_TREES = builder.comment("Should runewood trees naturally generate?")
                .define("generateRunewood", true);
        COMMON_RUNEWOOD_CHANCE = builder.comment("Chance for runewood trees to generate in open biomes such as plains.")
                .defineInRange("runewoodCommonChance", 0.02d, 0, 1);
        RARE_RUNEWOOD_CHANCE = builder.comment("Chance for runewood trees to generate in forest biomes.")
                .defineInRange("runewoodRareChance", 0.01d, 0, 1);
        builder.pop();

        builder.comment("Blaze Quartz Config").push("blaze_quartz");
        GENERATE_BLAZE_QUARTZ = builder.comment("Should blaze quartz ore generate?")
                .define("generateBlazeQuartz", true);
        BLAZE_QUARTZ_SIZE = builder.comment("Size of blaze quartz ore veins.")
                .define("blazeQuartzSize", 14);
        BLAZE_QUARTZ_AMOUNT = builder.comment("Amount of blaze quartz ore veins.")
                .define("blazeQuartzSize", 16);
        builder.pop();

        builder.comment("Brilliant Stone Config").push("brilliant_stone");
        GENERATE_BRILLIANT_STONE = builder.comment("Should brilliant stone generate?")
                .define("generateBrilliantStone", true);
        BRILLIANT_STONE_SIZE = builder.comment("Size of brilliant stone veins.")
                .define("brilliantStoneSize", 2);
        BRILLIANT_STONE_AMOUNT = builder.comment("Amount of brilliant stone veins.")
                .define("brilliantStoneSize", 4);
        BRILLIANT_STONE_AMOUNT = builder.comment("Amount of brilliant stone veins.")
                .define("brilliantStoneAmount", 4);
        BRILLIANT_STONE_MIN_Y = builder.comment("Minimum height at which brilliant stone can spawn.")
                .define("brilliantStoneMinY", -80);
        BRILLIANT_STONE_MAX_Y = builder.comment("Maximum height at which brilliant stone can spawn.")
                .define("brilliantStoneMaxY", 40);
        builder.pop();

        builder.comment("Soulstone Config").push("soulstone");
        GENERATE_SOULSTONE = builder.comment("Should soulstone ore generate underground?")
                .define("generateSoulstone", true);
        GENERATE_SURFACE_SOULSTONE = builder.comment("Should soulstone ore generate on the surface?")
                .define("generateSurfaceSoulstone", true);

        SOULSTONE_SIZE = builder.comment("Size of soulstone ore veins underground.")
                .define("soulstoneSize", 12);
        SOULSTONE_AMOUNT = builder.comment("Amount of soulstone ore veins.")
                .define("soulstoneAmount", 8);
        SURFACE_SOULSTONE_SIZE = builder.comment("Size of soulstone ore veins on the surface.")
                .define("surfaceSoulstoneSize", 6);
        SURFACE_SOULSTONE_AMOUNT = builder.comment("Amount of soulstone ore veins on the surface.")
                .define("surfaceSoulstoneAmount", 5);
        SOULSTONE_MIN_Y = builder.comment("Minimum height at which soulstone ore can spawn.")
                .define("soulstoneMinY", -80);
        SURFACE_SOULSTONE_MIN_Y = builder.comment("Minimum height at which surface soulstone ore can spawn.")
                .define("surfaceSoulstoneMinY", 60);
        SOULSTONE_MAX_Y = builder.comment("Maximum height at which soulstone ore can spawn.")
                .define("soulstoneMaxY", 30);
        SURFACE_SOULSTONE_MAX_Y = builder.comment("Maximum height at which surface soulstone ore can spawn.")
                .define("surfaceSoulstoneMaxY", 100);
        builder.pop();

        builder.pop();

        builder.comment("Item Settings").push("item");

        builder.comment("Scythe Settings").push("scythe");
        ULTIMATE_REBOUND = builder.comment("If set to true, you may put rebound on any weapon in the game.")
                .define("enableUltimateRebound", false);
        builder.pop();

        builder.comment("Spirit Affinity Settings").push("spirit_affinity");

        builder.comment("Soul Ward").push("soul_ward");
        SOUL_WARD_PHYSICAL = builder.comment("Multiplier for physical damage taken while soul ward is active.")
                .defineInRange("soulWardPhysical", 0.7f, 0, 1);

        SOUL_WARD_MAGIC = builder.comment("Multiplier for magic damage taken while soul ward is active.")
                .defineInRange("soulWardMagic", 0.1f, 0, 1);

        SOUL_WARD_RATE = builder.comment("Base time in ticks it takes for one point of soul ward to recover.")
                .define("soulWardRate", 60);
        builder.pop();

        builder.comment("Heart of Stone").push("heart_of_stone");
        HEART_OF_STONE_COST = builder.comment("Amount of hunger consumed when recovering a point of heart of stone. Do note that this will only matter if the player has the earthen affinity.")
                .defineInRange("heartOfStoneCost", 0.2d, 0, 1);

        HEART_OF_STONE_RATE = builder.comment("Base time in ticks it takes for one point of heart of stone to recover.")
                .define("heartOfStoneRate", 40);
        builder.pop();

        builder.pop();
    }

    public static final CommonConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
