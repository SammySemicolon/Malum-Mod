package com.sammy.malum.client.screen.codex.pages.text;

import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.display.*;
import com.sammy.malum.client.screen.codex.display.gizmo.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;

public class HeadlineSpiritPage extends HeadlineTextPage {

    private final DisplayedSpiritSymbol symbol;

    public static HeadlineTextPage spirit(String text, DisplayedSpiritSymbol symbol) {
        return new HeadlineSpiritPage(text, text +".1", symbol);
    }

    protected HeadlineSpiritPage(String headline, String text, DisplayedSpiritSymbol symbol) {
        super(headline, text);
        this.symbol = symbol;
    }

    @Override
    public ResourceLocation getBackground() {
        return MalumMod.malumPath("textures/gui/book/pages/headline_spirit_page.png");
    }

    @Override
    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        CodexTextRenderer.create()
                .renderHeadline(guiGraphics, headline, left, top)
                .renderWrappingText(guiGraphics, text, left + 6, top + 163);
        symbol.render(screen, this, guiGraphics, left + 39, top + 59, mouseX, mouseY, partialTicks);
    }
}