package com.sammy.malum.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {

    public static ForgeConfigSpec.ConfigValue<Boolean> DELAYED_PARTICLE_RENDERING;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Graphics settings").push("graphics");
        DELAYED_PARTICLE_RENDERING = builder.comment("Should particles render on the delayed buffer? This means they will properly render after clouds do, but could cause issues with mods like sodium.")
                .define("buffer_particles", true);
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
