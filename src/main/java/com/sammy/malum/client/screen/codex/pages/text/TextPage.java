package com.sammy.malum.client.screen.codex.pages.text;

import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.client.screen.codex.screens.EntryScreen;
import net.minecraft.client.gui.GuiGraphics;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderWrappingText;

public class TextPage extends BookPage {
    public final String translationKey;

    public TextPage(String translationKey) {
        super(null);
        this.translationKey = translationKey;
    }

    public String translationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        renderWrappingText(guiGraphics, translationKey(), left + 6, top + 5, 130);
    }
}
