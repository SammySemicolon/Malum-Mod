package com.sammy.malum.common.book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.BookObject;
import com.sammy.malum.common.book.objects.EntryObject;

import static com.sammy.malum.common.book.BookScreen.drawWrappingText;

public class TextPage extends BookPage
{
    public final String text;
    public TextPage(String text)
    {
        this.text = "malum.gui.book.page." + text;
    }
    
    @Override
    public void draw(MatrixStack stack, EntryObject object, BookScreen screen, int mouseX, int mouseY, int guiLeft, int guiTop, boolean isSecondPage)
    {
        Pair<Integer, Integer> position = getPosition(guiLeft, guiTop, isSecondPage);
        
        drawWrappingText(stack, ClientHelper.simpleTranslatableComponent(text), position.first+2, position.second+7, 100, 0);
    }
}