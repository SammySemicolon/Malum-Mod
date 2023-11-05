package com.sammy.malum.client.screen.codex.pages;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderWrappingText;

public class TextPage extends BookPage {
    public final String translationKey;

    public TextPage(String translationKey) {
        super(MalumMod.malumPath("textures/gui/book/pages/blank_page.png"));
        this.translationKey = translationKey;
    }

    public String translationKey() {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderWrappingText(guiGraphics, translationKey(), guiLeft + 14, guiTop + 10, 126);
    }

    @Override
    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderWrappingText(guiGraphics, translationKey(), guiLeft + 156, guiTop + 10, 126);
    }
}
