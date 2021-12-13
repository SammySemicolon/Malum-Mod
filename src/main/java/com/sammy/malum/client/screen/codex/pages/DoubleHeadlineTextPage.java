package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

public class DoubleHeadlineTextPage extends BookPage
{
    private final String headlineTranslationKey;
    private final String translationKey;
    private final String secondHeadlineTranslationKey;
    private final String secondTranslationKey;
    public DoubleHeadlineTextPage(String headlineTranslationKey, String translationKey, String secondHeadlineTranslationKey, String secondTranslationKey)
    {
        super(MalumHelper.prefix("textures/gui/book/pages/spirit_rite_text_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.translationKey = translationKey;
        this.secondHeadlineTranslationKey = secondHeadlineTranslationKey;
        this.secondTranslationKey = secondTranslationKey;
    }
    public DoubleHeadlineTextPage(String headlineTranslationKey, String translationKey)
    {
        this(headlineTranslationKey, translationKey, "corrupted_"+headlineTranslationKey, "corrupted_"+translationKey);
    }

    public String headlineTranslationKey()
    {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }
    public String translationKey()
    {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    public String corruptedHeadlineTranslationKey()
    {
        return "malum.gui.book.entry.page.headline." + secondHeadlineTranslationKey;
    }
    public String corruptedTranslationKey()
    {
        return "malum.gui.book.entry.page.text." + secondTranslationKey;
    }
    @Override
    public void renderLeft(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ITextComponent component = ClientHelper.simpleTranslatableComponent(headlineTranslationKey());
        ProgressionBookScreen.renderText(matrixStack, component, guiLeft+75 - minecraft.fontRenderer.getStringWidth(component.getString())/2,guiTop+10);
        ProgressionBookScreen.renderWrappingText(matrixStack, translationKey(), guiLeft+16,guiTop+31,120);

        ProgressionBookScreen.renderText(matrixStack, component, guiLeft+75 - minecraft.fontRenderer.getStringWidth(component.getString())/2,guiTop+87);
        ProgressionBookScreen.renderWrappingText(matrixStack, translationKey(), guiLeft+16,guiTop+108,120);
    }

    @Override
    public void renderRight(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ITextComponent component = ClientHelper.simpleTranslatableComponent(headlineTranslationKey());
        ProgressionBookScreen.renderText(matrixStack, component, guiLeft+218 - minecraft.fontRenderer.getStringWidth(component.getString())/2,guiTop+10);
        ProgressionBookScreen.renderWrappingText(matrixStack, translationKey(), guiLeft+158,guiTop+31,120);

        ProgressionBookScreen.renderText(matrixStack, component, guiLeft+218 - minecraft.fontRenderer.getStringWidth(component.getString())/2,guiTop+87);
        ProgressionBookScreen.renderWrappingText(matrixStack, translationKey(), guiLeft+158,guiTop+108,120);
    }
}
