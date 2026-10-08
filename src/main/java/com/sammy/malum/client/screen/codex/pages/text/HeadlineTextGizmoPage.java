package com.sammy.malum.client.screen.codex.pages.text;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.display.*;
import com.sammy.malum.client.screen.codex.display.gizmo.DisplayedGizmo;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class HeadlineTextGizmoPage extends HeadlineTextPage {

    private final DisplayedGizmo icon;

    public static HeadlineTextPage headlineTextGizmoPage(String text, DisplayedGizmo displayedGizmo) {
        return new HeadlineTextGizmoPage(text, text +".1", displayedGizmo);
    }

    public static HeadlineTextPage headlineTextGizmoPage(String headline, String text, DisplayedGizmo displayedGizmo) {
        return new HeadlineTextGizmoPage(headline, text, displayedGizmo);
    }

    protected HeadlineTextGizmoPage(String headline, String text, DisplayedGizmo icon) {
        super(headline, text);
        this.icon = icon;
    }

    @Override
    public ResourceLocation getBackground() {
        return MalumMod.malumPath("textures/gui/book/pages/headline_gizmo_page.png");
    }

    @Override
    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        CodexTextRenderer.create()
                .renderHeadline(guiGraphics, headline, left, top)
                .renderWrappingText(guiGraphics, text, left + 6, top + 91);
        icon.render(screen, this, guiGraphics, left + 63, top + 47, mouseX, mouseY, partialTicks);
    }
}