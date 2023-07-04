package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.*;
import net.minecraft.client.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

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
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, float yOffset, int mouseX, int mouseY, float partialTicks, float xOffset) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderWrappingText(poseStack, translationKey(), guiLeft + 14, guiTop + 10, 126);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, float yOffset, int mouseX, int mouseY, float partialTicks, float xOffset) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderWrappingText(poseStack, translationKey(), guiLeft + 156, guiTop + 10, 126);
    }
}
