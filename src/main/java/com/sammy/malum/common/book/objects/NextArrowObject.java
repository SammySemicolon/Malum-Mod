package com.sammy.malum.common.book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.book.BookScreen;
import net.minecraft.client.Minecraft;

import java.util.function.Predicate;

import static com.sammy.malum.common.book.BookScreen.screen;
import static net.minecraft.client.gui.AbstractGui.blit;

public class NextArrowObject extends BookObject
{
    public NextArrowObject(int posX, int posY, int width, int height)
    {
        super(posX, posY, width, height);
    }
    
    @Override
    public void draw(Minecraft minecraft, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        if (isHovering)
        {
            minecraft.getTextureManager().bindTexture(screen.texture());
            blit(matrixStack, posX, posY, 63, 231, 32, 18, 512, 512);
            return;
        }
        minecraft.getTextureManager().bindTexture(screen.texture());
        blit(matrixStack, posX, posY, 63, 211, 32, 18, 512, 512);
    }
    
    @Override
    public boolean canAccess(BookScreen screen)
    {
        if (screen.currentObject instanceof EntryObject)
        {
            EntryObject object = (EntryObject) screen.currentObject;
            return object.entry.pages.size() > 2 && screen.currentPage < object.entry.pages.size()/2f;
        }
        if (screen.currentObject instanceof CategoryObject)
        {
            CategoryObject object = (CategoryObject) screen.currentObject;
            return object.category.groupings.size() > 2 && screen.currentGrouping < object.category.groupings.size()/2f;
        }
        return false;
    }
    
    @Override
    public void interact(BookScreen screen)
    {
        if (screen.currentObject instanceof EntryObject)
        {
            EntryObject object = (EntryObject) screen.currentObject;
            if (object.entry.pages.size() > 2 && screen.currentPage < object.entry.pages.size()/2f)
            {
                screen.currentPage++;
                screen.playSound();
                return;
            }
        }
        if (screen.currentObject instanceof CategoryObject)
        {
            CategoryObject object = (CategoryObject) screen.currentObject;
            if (object.category.groupings.size() > 2 && screen.currentGrouping < object.category.groupings.size()/2f)
            {
                screen.currentGrouping++;
                screen.playSound();
            }
        }
    }
}
