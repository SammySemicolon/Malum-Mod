package com.sammy.malum.client.screen.codex.pages.text;

import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class TextPage<T extends EntryScreen<T, ?>> extends BookPage<T> {
    public final String translationKey;

    public TextPage(String translationKey) {
        super(null);
        this.translationKey = translationKey;
    }

    public String translationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void render(T screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        renderWrappingText(guiGraphics, translationKey(), left + 6, top + 5, 130);
    }
}
