package com.sammy.malum.common.book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.book.BookScreen;
import net.minecraft.client.Minecraft;

import java.util.function.Predicate;

import static com.sammy.malum.common.book.BookScreen.screen;
import static net.minecraft.client.gui.AbstractGui.blit;

public class BackArrowObject extends BookObject
{
    public BackArrowObject(int posX, int posY, int width, int height)
    {
        super(posX, posY, width, height);
    }
    
    @Override
    public void draw(Minecraft minecraft, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        if (isHovering)
        {
            minecraft.getTextureManager().bindTexture(screen.texture());
            blit(matrixStack, posX, posY, 1, 231, 18, 18, 512, 512);
            return;
        }
        minecraft.getTextureManager().bindTexture(screen.texture());
        blit(matrixStack, posX, posY, 1, 211, 18, 18, 512, 512);
    }
    
    @Override
    public boolean canAccess(BookScreen screen)
    {
        if (screen.currentObject instanceof EntryObject)
        {
            if (screen.currentPage > 0)
            {
                return true;
            }
        }
        if (screen.currentObject instanceof CategoryObject)
        {
            if (screen.currentGrouping > 0)
            {
                return true;
            }
        }
        if (screen.currentObject != null && screen.currentObject.returnObject != null)
        {
            return true;
        }
        return false;
    }
    
    @Override
    public void interact(BookScreen screen)
    {
        if (screen.currentObject instanceof EntryObject)
        {
            if (screen.currentPage > 0)
            {
                screen.playSound();
                screen.currentPage--;
                return;
            }
        }
        if (screen.currentObject instanceof CategoryObject)
        {
            if (screen.currentGrouping > 0)
            {
                screen.playSound();
                screen.currentGrouping--;
                return;
            }
        }
        if (screen.currentObject != null && screen.currentObject.returnObject != null)
        {
            screen.currentObject.returnObject.interact(screen);
        }
    }
}
