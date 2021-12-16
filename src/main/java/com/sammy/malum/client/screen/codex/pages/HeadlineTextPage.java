package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

public class HeadlineTextPage extends BookPage
{
    private final String headlineTranslationKey;
    private final String translationKey;
    public HeadlineTextPage(String headlineTranslationKey, String translationKey)
    {
        super(MalumHelper.prefix("textures/gui/book/pages/headline_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.translationKey = translationKey;
    }

    public String headlineTranslationKey()
    {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
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
        ITextComponent component = ClientHelper.simpleTranslatableComponent(headlineTranslationKey());
        ProgressionBookScreen.renderText(matrixStack, component, guiLeft+75 - minecraft.font.width(component.getString())/2,guiTop+10);
        ProgressionBookScreen.renderWrappingText(matrixStack, translationKey(), guiLeft+16,guiTop+31,120);
    }

    @Override
    public void renderRight(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ITextComponent component = ClientHelper.simpleTranslatableComponent(headlineTranslationKey());
        ProgressionBookScreen.renderText(matrixStack, component, guiLeft+218 - minecraft.font.width(component.getString())/2,guiTop+10);
        ProgressionBookScreen.renderWrappingText(matrixStack, translationKey(), guiLeft+158,guiTop+31,120);
    }
}
