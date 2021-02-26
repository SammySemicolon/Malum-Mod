package com.sammy.malum.common.book.pages;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.BookObject;
import com.sammy.malum.common.book.objects.EntryObject;

import static com.sammy.malum.common.book.BookScreen.drawWrappingText;

public class TextPage extends BookPage
{
    public final String text;
    public boolean hasHeadline;
    public TextPage(String text)
    {
        this.text = "malum.gui.book.page." + text;
    }
    
    @Override
    public void draw(MatrixStack stack, EntryObject object, BookScreen screen, int mouseX, int mouseY, int guiLeft, int guiTop, boolean isSecondPage)
    {
        int xOffset = 35;
        if (isSecondPage)
        {
            xOffset = 155;
        }
        int yOffset = 10;
        if (hasHeadline)
        {
            yOffset+= 22;
        }
        drawWrappingText(stack, ClientHelper.simpleTranslatableComponent(text), guiLeft + xOffset, guiTop + yOffset, 100, object.nameObject.hover / 20f);
    }
}