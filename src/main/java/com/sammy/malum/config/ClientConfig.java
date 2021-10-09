package com.sammy.malum.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {

    public static ForgeConfigSpec.ConfigValue<Boolean> BETTER_LAYERING;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Graphics settings").push("graphics");
        BETTER_LAYERING = builder.comment("Enable better particle/effect layering.")
                .comment("Fixes particles and effects rendering behind clouds and weather.")
                .comment("NOTE: Does NOT work with fabulous graphics mode.")
                .define("betterLayering", true);
        builder.pop();
    }

    public static final ClientConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
