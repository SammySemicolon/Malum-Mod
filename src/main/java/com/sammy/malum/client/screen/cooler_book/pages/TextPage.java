package com.sammy.malum.client.screen.cooler_book.pages;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.cooler_book.ProgressionBookScreen;
import net.minecraft.client.Minecraft;

public class TextPage extends BookPage
{
    public final String translationKey;
    public TextPage(String translationKey)
    {
        super(MalumHelper.prefix("textures/gui/book/pages/blank_page.png"));
        this.translationKey = translationKey;
    }

    public String translationKey()
    {
        return "malum.gui.book.entry.page.text." + translationKey;
    }
    @Override
    public void renderLeft(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderWrappingText(matrixStack, translationKey(), guiLeft+16,guiTop+10,120);
    }

    @Override
    public void renderRight(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ProgressionBookScreen.renderWrappingText(matrixStack, translationKey(), guiLeft+158,guiTop+10,120);
    }
}
