package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import static com.sammy.malum.client.screen.codex.EntryScreen.screen;

public class BookPage
{
    public final ResourceLocation BACKGROUND;
    public BookPage(ResourceLocation background)
    {
        this.BACKGROUND = background;
    }

    public void renderLeft(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {

    }
    public void renderRight(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {

    }
    public void renderBackgroundLeft(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderTexture(BACKGROUND, matrixStack,guiLeft, guiTop,1,1,screen.bookWidth-147, screen.bookHeight,512,512);
    }
    public void renderBackgroundRight(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderTexture(BACKGROUND, matrixStack,guiLeft+147, guiTop,148,1,screen.bookWidth-147, screen.bookHeight,512,512);
    }
    public int guiLeft()
    {
        return (screen.width - screen.bookWidth) / 2;
    }
    public int guiTop()
    {
        return (screen.height - screen.bookHeight) / 2;
    }
}
