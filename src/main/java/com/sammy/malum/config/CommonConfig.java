package com.sammy.malum.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {

    public static ForgeConfigSpec.ConfigValue<Boolean> GENERATE_RUNEWOOD_TREES;
    public static ForgeConfigSpec.ConfigValue<Float> COMMON_RUNEWOOD_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Float> RARE_RUNEWOOD_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Boolean> GENERATE_BLAZE_QUARTZ;
    public static ForgeConfigSpec.ConfigValue<Integer> BLAZE_QUARTZ_SIZE;

    public static ForgeConfigSpec.ConfigValue<Boolean> GENERATE_SOULSTONE;
    public static ForgeConfigSpec.ConfigValue<Integer> SOULSTONE_SIZE;
    public static ForgeConfigSpec.ConfigValue<Boolean> GENERATE_SURFACE_SOULSTONE;
    public static ForgeConfigSpec.ConfigValue<Integer> SURFACE_SOULSTONE_SIZE;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Worldgen Settings").push("worldgen");
        GENERATE_RUNEWOOD_TREES = builder.comment("Should runewood trees naturally generate?")
                .define("generateRunewood", true);
        COMMON_RUNEWOOD_CHANCE = builder.comment("Chance for runewood trees to generate in open biomes such as plains.")
                .define("runewoodCommonChance", 0.04f);
        RARE_RUNEWOOD_CHANCE = builder.comment("Chance for runewood trees to generate in forest biomes.")
                .define("runewoodRareChance", 0.02f);

        GENERATE_BLAZE_QUARTZ = builder.comment("Should blaze quartz ore generate?")
                .define("generateBlazeQuartz", true);
        BLAZE_QUARTZ_SIZE = builder.comment("Size of blaze quartz ore veins.")
                .define("blazeQuartzSize", 7);

        GENERATE_SOULSTONE = builder.comment("Should soulstone ore generate underground?")
                .define("generateSoulstone", true);
        SOULSTONE_SIZE = builder.comment("Size of soulstone ore veins.")
                .define("soulstoneSize", 8);

        GENERATE_SURFACE_SOULSTONE = builder.comment("Should soulstone ore generate underground?")
                .define("generateSurfaceSoulstone", true);
        SURFACE_SOULSTONE_SIZE = builder.comment("Size of soulstone ore veins on the surface.")
                .define("surfaceSoulstoneSize", 12);

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
