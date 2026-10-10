package com.sammy.malum.client.screen.codex.pages.text;

import com.sammy.malum.client.screen.codex.display.CodexTextRenderer;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;

public class TextPage extends BookPage {
    public final Component text;

    public static TextPage textPage(String text) {
        return new TextPage(text);
    }

    protected TextPage(String text) {
        this.text = Component.translatable(PageLangKeyCommons.textKey(text));
    }

    @Override
    public ResourceLocation getBackground() {
        return null;
    }

    @Override
    public void render(CodexEntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        CodexTextRenderer.create()
                .renderWrappingText(guiGraphics, text, left + 6, top + 4);
    }
}
