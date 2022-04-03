package com.sammy.malum.config;

import com.sammy.malum.core.systems.config.SimpleConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig extends SimpleConfig {

    public static ConfigValueHolder<Boolean> DELAYED_PARTICLE_RENDERING = new ConfigValueHolder<>("client/graphics/particle", builder ->
            builder.comment("Should particles render on the delayed buffer? This means they will properly render after clouds do, but could cause issues with mods like sodium.")
                    .define("buffer_particles", true));

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        super("client", builder);
    }

    public static final ClientConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}