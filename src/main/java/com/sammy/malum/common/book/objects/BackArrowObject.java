package com.sammy.malum.common.book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.book.BookScreen;
import net.minecraft.client.Minecraft;

import java.util.function.Predicate;

import static com.sammy.malum.common.book.BookScreen.screen;
import static net.minecraft.client.gui.AbstractGui.blit;

public class BackArrowObject extends BookObject
{
    public BackArrowObject(int posX, int posY, int width, int height, Predicate<BookScreen> showPredicate)
    {
        super(posX, posY, width, height, showPredicate);
    }
    
    @Override
    public void draw(Minecraft minecraft, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        minecraft.getTextureManager().bindTexture(screen.texture());
        blit(matrixStack, posX, posY, 51, 211, 20, 20, 512, 512);
    }
    
    @Override
    public void interact(BookScreen screen)
    {
        if (screen.currentObject.returnObject != null)
        {
            screen.currentObject.returnObject.interact(screen);
        }
    }
}
