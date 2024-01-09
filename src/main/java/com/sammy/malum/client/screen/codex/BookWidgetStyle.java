package com.sammy.malum.client.screen.codex;

import net.minecraft.resources.*;

import static com.sammy.malum.MalumMod.malumPath;

public record BookWidgetStyle(ResourceLocation frameTexture, ResourceLocation fillingTexture) {

    private static final ResourceLocation RUNEWOOD_FRAME = texturePath("runewood_frame");
    private static final ResourceLocation GILDED_RUNEWOOD_FRAME = texturePath("runewood_frame_gilded");
    private static final ResourceLocation SMALL_RUNEWOOD_FRAME = texturePath("runewood_frame_small");
    private static final ResourceLocation TOTEMIC_RUNEWOOD_FRAME = texturePath("runewood_frame_totemic");

    private static final ResourceLocation SOULWOOD_FRAME = texturePath("soulwood_frame");
    private static final ResourceLocation GILDED_SOULWOOD_FRAME = texturePath("soulwood_frame_gilded");
    private static final ResourceLocation SMALL_SOULWOOD_FRAME = texturePath("soulwood_frame_small");
    private static final ResourceLocation TOTEMIC_SOULWOOD_FRAME = texturePath("soulwood_frame_totemic");

    private static final ResourceLocation PAPER_FILLING = texturePath("paper_filling");
    private static final ResourceLocation GILDED_PAPER_FILLING = texturePath("paper_filling_gilded");
    private static final ResourceLocation SMALL_PAPER_FILLING = texturePath("paper_filling_small");

    private static final ResourceLocation DARK_FILLING = texturePath("dark_filling");
    private static final ResourceLocation GILDED_DARK_FILLING = texturePath("dark_filling_gilded");
    private static final ResourceLocation SMALL_DARK_FILLING = texturePath("dark_filling_small");

    public static final BookWidgetStyle RUNEWOOD = new BookWidgetStyle(RUNEWOOD_FRAME, PAPER_FILLING);
    public static final BookWidgetStyle TOTEMIC_RUNEWOOD = new BookWidgetStyle(TOTEMIC_RUNEWOOD_FRAME, PAPER_FILLING);
    public static final BookWidgetStyle GILDED_RUNEWOOD = new BookWidgetStyle(GILDED_RUNEWOOD_FRAME, GILDED_PAPER_FILLING);
    public static final BookWidgetStyle SMALL_RUNEWOOD = new BookWidgetStyle(SMALL_RUNEWOOD_FRAME, SMALL_PAPER_FILLING);

    public static final BookWidgetStyle DARK_RUNEWOOD = new BookWidgetStyle(RUNEWOOD_FRAME, DARK_FILLING);
    public static final BookWidgetStyle DARK_TOTEMIC_RUNEWOOD = new BookWidgetStyle(TOTEMIC_RUNEWOOD_FRAME, DARK_FILLING);
    public static final BookWidgetStyle DARK_GILDED_RUNEWOOD = new BookWidgetStyle(GILDED_RUNEWOOD_FRAME, GILDED_DARK_FILLING);
    public static final BookWidgetStyle DARK_SMALL_RUNEWOOD = new BookWidgetStyle(SMALL_RUNEWOOD_FRAME, SMALL_DARK_FILLING);

    public static final BookWidgetStyle SOULWOOD = new BookWidgetStyle(SOULWOOD_FRAME, PAPER_FILLING);
    public static final BookWidgetStyle TOTEMIC_SOULWOOD = new BookWidgetStyle(TOTEMIC_SOULWOOD_FRAME, PAPER_FILLING);
    public static final BookWidgetStyle GILDED_SOULWOOD = new BookWidgetStyle(GILDED_SOULWOOD_FRAME, GILDED_PAPER_FILLING);
    public static final BookWidgetStyle SMALL_SOULWOOD = new BookWidgetStyle(SMALL_SOULWOOD_FRAME, SMALL_PAPER_FILLING);

    public static final BookWidgetStyle DARK_SOULWOOD = new BookWidgetStyle(SOULWOOD_FRAME, DARK_FILLING);
    public static final BookWidgetStyle DARK_TOTEMIC_SOULWOOD = new BookWidgetStyle(TOTEMIC_SOULWOOD_FRAME, DARK_FILLING);
    public static final BookWidgetStyle DARK_GILDED_SOULWOOD = new BookWidgetStyle(GILDED_SOULWOOD_FRAME, GILDED_DARK_FILLING);
    public static final BookWidgetStyle DARK_SMALL_SOULWOOD = new BookWidgetStyle(SMALL_SOULWOOD_FRAME, SMALL_DARK_FILLING);

    public static ResourceLocation texturePath(String name) {
        return malumPath("textures/gui/book/widgets/" + name + ".png");
    }
}