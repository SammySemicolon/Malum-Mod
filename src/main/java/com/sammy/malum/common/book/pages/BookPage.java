package com.sammy.malum.common.book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.BookObject;
import com.sammy.malum.common.book.objects.EntryObject;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import static com.sammy.malum.common.book.BookScreen.drawWrappingText;
import static com.sammy.malum.common.book.BookScreen.screen;
import static net.minecraft.client.gui.AbstractGui.blit;

public class BookPage
{
    public ResourceLocation BACKGROUND;
    public BookPage()
    {
    
    }
    public Pair<Integer, Integer> getPosition(int guiLeft, int guiTop, boolean isSecondPage)
    {
        int posX = guiLeft + 34;
        int posY = guiTop + 8;
        if (isSecondPage)
        {
            posX = guiLeft + 154;
        }
        return Pair.of(posX,posY);
    }
    public void draw(MatrixStack stack, EntryObject object, BookScreen screen, int mouseX, int mouseY, int guiLeft, int guiTop, boolean isSecondPage)
    {
        if (BACKGROUND != null)
        {
            Minecraft minecraft = Minecraft.getInstance();
            Pair<Integer, Integer> position = getPosition(guiLeft, guiTop, isSecondPage);
            int posX = position.first;
            int posY = position.second;
            minecraft.getTextureManager().bindTexture(BACKGROUND);
            blit(stack, posX, posY, 1, 1, 106, 144, 512, 512);
        }
    }
}
