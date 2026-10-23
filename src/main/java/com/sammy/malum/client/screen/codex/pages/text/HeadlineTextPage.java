package com.sammy.malum.client.screen.codex.pages.text;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.display.*;
import com.sammy.malum.client.screen.codex.display.gizmo.GizmoTooltipBuilder;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;

public class HeadlineTextPage extends BookPage implements IGizmoHolder {

    protected final Component headline;
    protected final Component text;

    protected final String id;

    public static HeadlineTextPage headlineText(String text) {
        return new HeadlineTextPage(text, text +".1");
    }

    public static HeadlineTextPage headlineText(String headline, String text) {
        return new HeadlineTextPage(headline, text);
    }

    protected HeadlineTextPage(String headline, String text) {
        this.headline = Component.translatable(PageLangKeyCommons.headlineKey(headline));

        this.text = Component.translatable(PageLangKeyCommons.textKey(text));
        this.id = headline;
    }

    @Override
    public ResourceLocation getBackground() {
        return MalumMod.malumPath("textures/gui/book/pages/headline_page.png");
    }

    @Override
    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        CodexTextRenderer.create()
                .renderHeadline(guiGraphics, headline, left, top)
                .renderWrappingText(guiGraphics, text, left + 6, top + 32);
    }

    @Override
    public void addGizmoTooltip(GizmoTooltipBuilder builder) {
        builder.addTitle(PageLangKeyCommons.headlineKey(id));
        builder.addDefaultSubtext(id);
    }
}