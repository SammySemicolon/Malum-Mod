package com.sammy.malum.config;


import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.ortus.systems.config.OrtusConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import static com.sammy.malum.MalumMod.MALUM;

public class ClientConfig extends OrtusConfig {

    public static ConfigValueHolder<ProgressionBookScreen.BookTheme> GENERATE_RUNEWOOD_TREES = new ConfigValueHolder<>(MALUM,"client/codex", (builder ->
            builder.comment("What theme should the encyclopedia arcana be in?")
                    .defineEnum("bookTheme", ProgressionBookScreen.BookTheme.DEFAULT)));

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        super(MALUM,"client", builder);
    }

    public static final ClientConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}