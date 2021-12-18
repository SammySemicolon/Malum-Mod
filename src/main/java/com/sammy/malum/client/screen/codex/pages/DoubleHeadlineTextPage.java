package com.sammy.malum.client.screen.codex.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.ProgressionBookScreen;
import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class DoubleHeadlineTextPage extends BookPage
{
    private final String headlineTranslationKey;
    private final String translationKey;
    private final String secondHeadlineTranslationKey;
    private final String secondTranslationKey;
    public DoubleHeadlineTextPage(String headlineTranslationKey, String translationKey, String secondHeadlineTranslationKey, String secondTranslationKey)
    {
        super(DataHelper.prefix("textures/gui/book/pages/spirit_rite_text_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.translationKey = translationKey;
        this.secondHeadlineTranslationKey = secondHeadlineTranslationKey;
        this.secondTranslationKey = secondTranslationKey;
    }
    public DoubleHeadlineTextPage(String headlineTranslationKey, String translationKey)
    {
        this(headlineTranslationKey, translationKey, "corrupted_"+headlineTranslationKey, "corrupted_"+translationKey);
    }

    public String getHeadlineTranslationKey()
    {
        return "malum.gui.book.entry.page.headline." + headlineTranslationKey;
    }
    public String getTranslationKey()
    {
        return "malum.gui.book.entry.page.text." + translationKey;
    }

    public String getSecondHeadlineTranslationKey()
    {
        return "malum.gui.book.entry.page.headline." + secondHeadlineTranslationKey;
    }
    public String getCorruptedTranslationKey()
    {
        return "malum.gui.book.entry.page.text." + secondTranslationKey;
    }
    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = new TranslatableComponent(getHeadlineTranslationKey());
        ProgressionBookScreen.renderText(poseStack, component, guiLeft+75 - minecraft.font.width(component.getString())/2,guiTop+10);
        ProgressionBookScreen.renderWrappingText(poseStack, getTranslationKey(), guiLeft+16,guiTop+31,120);

        ProgressionBookScreen.renderText(poseStack, component, guiLeft+75 - minecraft.font.width(component.getString())/2,guiTop+87);
        ProgressionBookScreen.renderWrappingText(poseStack, getTranslationKey(), guiLeft+16,guiTop+108,120);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = new TranslatableComponent(getHeadlineTranslationKey());
        ProgressionBookScreen.renderText(poseStack, component, guiLeft+218 - minecraft.font.width(component.getString())/2,guiTop+10);
        ProgressionBookScreen.renderWrappingText(poseStack, getTranslationKey(), guiLeft+158,guiTop+31,120);

        ProgressionBookScreen.renderText(poseStack, component, guiLeft+218 - minecraft.font.width(component.getString())/2,guiTop+87);
        ProgressionBookScreen.renderWrappingText(poseStack, getTranslationKey(), guiLeft+158,guiTop+108,120);
    }
}
