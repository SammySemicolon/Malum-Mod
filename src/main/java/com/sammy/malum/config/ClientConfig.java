package com.sammy.malum.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {

    public static ForgeConfigSpec.ConfigValue<Boolean> BETTER_LAYERING;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Graphics settings").push("graphics");
        BETTER_LAYERING = builder.comment("Should particles render on the delayed buffer? This means they will properly render after clouds do, but could cause issues with mods like sodium.")
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
