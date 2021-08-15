package com.sammy.malum.client.screen.cooler_book.pages;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.cooler_book.CoolerBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class HeadlineTextPage extends CoolerBookPage
{
    private final String headlineTranslationKey;
    private final String translationKey;
    private final ItemStack attachmentStack;
    public HeadlineTextPage(String headlineTranslationKey, String translationKey, ItemStack attachmentStack)
    {
        super(MalumHelper.prefix("textures/gui/book/pages/headline_page.png"));
        this.headlineTranslationKey = headlineTranslationKey;
        this.translationKey = translationKey;
        this.attachmentStack = attachmentStack;
    }
    public HeadlineTextPage(String headlineTranslationKey, String translationKey, Item attachmentItem)
    {
        this(headlineTranslationKey,translationKey, attachmentItem.getDefaultInstance());
    }

    @Override
    public boolean hasAttachment()
    {
        return true;
    }

    @Override
    public ItemStack attachmentIcon()
    {
        return attachmentStack;
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
        CoolerBookScreen.renderText(matrixStack, component, guiLeft+75 - minecraft.fontRenderer.getStringWidth(component.getString())/2,guiTop+10);
        CoolerBookScreen.renderWrappingText(matrixStack, translationKey(), guiLeft+16,guiTop+31,124);
    }

    @Override
    public void renderRight(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        ITextComponent component = ClientHelper.simpleTranslatableComponent(headlineTranslationKey());
        CoolerBookScreen.renderText(matrixStack, component, guiLeft+218 - minecraft.fontRenderer.getStringWidth(component.getString())/2,guiTop+10);
        CoolerBookScreen.renderWrappingText(matrixStack, translationKey(), guiLeft+158,guiTop+31,124);
    }
}
