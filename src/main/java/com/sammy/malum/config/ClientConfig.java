package com.sammy.malum.config;


import com.sammy.malum.client.screen.codex.helper.*;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec;
import team.lodestar.lodestone.modules.core.config.LodestoneConfig;

import static com.sammy.malum.MalumMod.MALUM;

public class ClientConfig extends LodestoneConfig {

    public static ConfigValueHolder<CodexTextHelper.BookTheme> BOOK_THEME = new ConfigValueHolder<>(MALUM, "client/codex", (builder ->
            builder.comment("What theme should the encyclopedia arcana be in?")
                    .defineEnum("bookTheme", CodexTextHelper.BookTheme.DEFAULT)));

    public static ConfigValueHolder<Boolean> SCROLL_DIRECTION = new ConfigValueHolder<>(MALUM, "client/codex", (builder ->
            builder.comment("Should the scroll direction be reversed in the encyclopedia arcana entry screen? This simply affects how you move through pages in an entry.")
                    .define("scrollDirection", false)));

    public static ConfigValueHolder<Integer> UI_SHIELD_X_OFFSET = new ConfigValueHolder<>(MALUM, "client/ui", (builder ->
            builder.comment("X offset from the center of the screen for the Soul Ward and Malignant Aegis HUD Elements.")
                    .define("shieldX", 0)));
    public static ConfigValueHolder<Integer> UI_SHIELD_Y_OFFSET = new ConfigValueHolder<>(MALUM, "client/ui", (builder ->
            builder.comment("Y offset from the bottom of the screen for the Soul Ward and Malignant Aegis HUD Elements.")
                    .define("shieldY", 47)));

    public static ConfigValueHolder<Integer> SCARF_LENGTH = new ConfigValueHolder<>(MALUM, "client/scarf", (builder ->
            builder.comment("How long should the Malignant Stronghold Scarf be? This value will affect all rendered scarves, not just the one worn by the local player.")
                    .define("scarfLength", 30)));
    public static ConfigValueHolder<Double> SCARF_OPACITY = new ConfigValueHolder<>(MALUM, "client/scarf", (builder ->
            builder.comment("How visible should the Malignant Stronghold Scarf be in first person?")
                    .define("scarfOpacity", 0.4d)));

    public static ConfigValueHolder<Boolean> PARALLEL_WORLD = new ConfigValueHolder<>(MALUM, "client/renderpass", (builder ->
            builder.comment("Enable or disable the parallel world renderer, used for various effects related to a certain feature. Disable if it causes framerate issues.")
                    .define("renderParallelWorld", true)));

    public ClientConfig(ModConfigSpec.Builder builder) {
        super(MALUM, "client", builder);
    }

    public static final ClientConfig INSTANCE;
    public static final IConfigSpec SPEC;

    static {
        final var specPair = new ModConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
