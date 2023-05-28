package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.screen.codex.*;
import net.minecraft.client.*;
import net.minecraft.resources.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;
import static com.sammy.malum.client.screen.codex.EntryScreen.*;

public class BookPage {
    public final ResourceLocation BACKGROUND;

    public BookPage(ResourceLocation background) {
        this.BACKGROUND = background;
    }

    public boolean isValid() {
        return true;
    }

    public void renderLeft(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, float yOffset, int mouseX, int mouseY, float partialTicks, float xOffset) {

    }

    public void renderRight(Minecraft minecraft, PoseStack poseStack, EntryScreen screen, float yOffset, int mouseX, int mouseY, float partialTicks, float xOffset) {

    }

    public void renderBackgroundLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderTexture(BACKGROUND, poseStack, guiLeft, guiTop, 1, 1, entryScreen.bookWidth - 147, entryScreen.bookHeight, 512, 512);
    }

    public void renderBackgroundRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        renderTexture(BACKGROUND, poseStack, guiLeft + 147, guiTop, 148, 1, entryScreen.bookWidth - 147, entryScreen.bookHeight, 512, 512);
    }

    public int guiLeft() {
        return (entryScreen.width - entryScreen.bookWidth) / 2;
    }

    public int guiTop() {
        return (entryScreen.height - entryScreen.bookHeight) / 2;
    }
}