package com.sammy.malum.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import team.lodestar.lodestone.systems.config.ConfigValueHolder;
import team.lodestar.lodestone.systems.config.LodestoneConfig;

import java.util.ArrayList;
import java.util.List;

import static com.sammy.malum.MalumMod.MALUM_CONFIG_GROUP;

public class CommonConfig extends LodestoneConfig {

    public static ConfigValueHolder<Boolean> ULTIMATE_REBOUND = new ConfigValueHolder<>(MALUM_CONFIG_GROUP, "common/item/rebound", (builder ->
            builder.comment("If set to true, you may put rebound on any weapon in the game.")
                    .define("enableUltimateRebound", false)));

    public static ConfigValueHolder<Boolean> AWARD_CODEX_ON_KILL = new ConfigValueHolder<>(MALUM_CONFIG_GROUP, "common/codex", (builder ->
            builder.comment("If set to true, the first undead enemy a player slays will drop the encyclopedia arcana.")
                    .define("enableCodexDrop", true)));

    public static ConfigValueHolder<Boolean> NO_FANCY_SPIRITS = new ConfigValueHolder<>(MALUM_CONFIG_GROUP, "common/spirit", (builder ->
            builder.comment("If set to true, any spirits dropped will simply take the form of an item.")
                    .define("noFancySpirits", false)));

    public static ConfigValueHolder<Boolean> SOULLESS_SPAWNERS = new ConfigValueHolder<>(MALUM_CONFIG_GROUP, "common/spirit/spawner", (builder ->
            builder.comment("If set to true, mob spawners will create soulless mobs instead.")
                    .define("lameSpawners", false)));

    public static ConfigValueHolder<Boolean> USE_DEFAULT_SPIRIT_VALUES = new ConfigValueHolder<>(MALUM_CONFIG_GROUP, "common/spirit/defaults", (builder ->
            builder.comment("Whether entities without spirit jsons will use the default spirit data for their category.")
                    .define("defaultSpiritValues", true)));

    public static ConfigValueHolder<Double> SOUL_WARD_PHYSICAL = new ConfigValueHolder<>(MALUM_CONFIG_GROUP, "common/spirit/affinity/soul_ward", (builder ->
            builder.comment("Multiplier for physical damage taken while soul ward is active.")
                    .defineInRange("soulWardPhysical", 0.7f, 0, 1)));
    public static ConfigValueHolder<Double> SOUL_WARD_MAGIC = new ConfigValueHolder<>(MALUM_CONFIG_GROUP, "common/spirit/affinity/soul_ward", (builder ->
            builder.comment("Multiplier for magic damage taken while soul ward is active.")
                    .defineInRange("soulWardMagic", 0.1f, 0, 1)));
    public static ConfigValueHolder<Integer> SOUL_WARD_RATE = new ConfigValueHolder<>(MALUM_CONFIG_GROUP, "common/spirit/affinity/soul_ward", (builder ->
            builder.comment("Base time in ticks it takes for one point of soul ward to recover.")
                    .define("soulWardRate", 60)));

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        super(MALUM_CONFIG_GROUP, builder);
    }

    public static final CommonConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {

        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
