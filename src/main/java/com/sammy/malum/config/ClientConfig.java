package com.sammy.malum.config;


import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import team.lodestar.lodestone.systems.config.LodestoneConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import static com.sammy.malum.MalumMod.MALUM;

public class ClientConfig extends LodestoneConfig {

    public static ConfigValueHolder<ProgressionBookScreen.BookTheme> BOOK_THEME = new ConfigValueHolder<>(MALUM,"client/codex", (builder ->
            builder.comment("What theme should the encyclopedia arcana be in?")
                    .defineEnum("bookTheme", ProgressionBookScreen.BookTheme.DEFAULT)));

    public static ConfigValueHolder<Boolean> SCROLL_DIRECTION = new ConfigValueHolder<>(MALUM,"client/codex", (builder ->
            builder.comment("Should the scroll direction be reversed in the encyclopedia arcana entry screen? This simply affects how you move through pages in an entry.")
                    .define("scrollDirection", false)));

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