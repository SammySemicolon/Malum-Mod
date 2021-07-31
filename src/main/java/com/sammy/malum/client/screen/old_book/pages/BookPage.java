package com.sammy.malum.client.screen.old_book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.client.screen.old_book.BookScreen;
import com.sammy.malum.client.screen.old_book.objects.EntryObject;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import static com.sammy.malum.client.screen.old_book.BookScreen.drawWrappingText;
import static net.minecraft.client.gui.AbstractGui.blit;

public class BookPage
{
    public ResourceLocation BACKGROUND;
    public BookPage()
    {
    
    }
    public Pair<Integer, Integer> getPosition(int guiLeft, int guiTop, boolean isSecondPage)
    {
        int posX = guiLeft + 11;
        int posY = guiTop + 8;
        if (isSecondPage)
        {
            posX = guiLeft + 153;
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
            blit(stack, posX, posY, 1, 1, 128, 155, 512, 512);
        }
    }
}
