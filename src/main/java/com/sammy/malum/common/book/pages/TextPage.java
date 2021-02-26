package com.sammy.malum.common.book.pages;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.BookObject;

import static com.sammy.malum.common.book.BookScreen.drawWrappingText;

public class TextPage extends BookPage
{
    public final String text;
    
    public TextPage(String text)
    {
        this.text = "malum.gui.book.page." + text;
    }
    
    @Override
    public void draw(MatrixStack stack, BookObject object, BookScreen screen, int guiLeft, int guiTop)
    {
        float brightness = (20 - object.draw) / 20f;
        drawWrappingText(stack, ClientHelper.simpleTranslatableComponent(text), guiLeft + 35, guiTop + 10, 100, brightness);
    }
}