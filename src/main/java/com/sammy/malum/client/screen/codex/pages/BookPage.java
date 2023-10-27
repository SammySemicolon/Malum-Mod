package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;
import static com.sammy.malum.client.screen.codex.EntryScreen.*;

public class BookPage {
    public final ResourceLocation background;
    protected BookEntry parentEntry;

    public BookPage(ResourceLocation background) {
        this.background = background;
    }

    public boolean isValid() {
        return true;
    }

    public void render(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {

    }

    public void renderLeft(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {

    }

    public void renderRight(Minecraft minecraft, GuiGraphics guiGraphics, EntryScreen screen, int mouseX, int mouseY, float partialTicks) {

    }

    public void renderBackgroundLeft(PoseStack poseStack) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderTexture(background, poseStack, guiLeft, guiTop, 1, 1, entryScreen.bookWidth - 147, entryScreen.bookHeight, 512, 512);
    }

    public void renderBackgroundRight(PoseStack poseStack) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderTexture(background, poseStack, guiLeft + 147, guiTop, 148, 1, entryScreen.bookWidth - 147, entryScreen.bookHeight, 512, 512);
    }

    public int guiLeft() {
        return (entryScreen.width - entryScreen.bookWidth) / 2;
    }

    public int guiTop() {
        return (entryScreen.height - entryScreen.bookHeight) / 2;
    }

    public BookPage setParentEntry(BookEntry parentEntry) {
        this.parentEntry = parentEntry;
        return this;
    }
}