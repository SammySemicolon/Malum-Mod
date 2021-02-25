package com.sammy.malum.common.book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.pages.BookPage;
import net.minecraft.client.Minecraft;

import java.util.function.Predicate;

import static com.sammy.malum.common.book.BookScreen.screen;
import static net.minecraft.client.gui.AbstractGui.blit;

public class LinkedPageObject extends PageObject
{
    public LinkedPageObject(int posX, int posY, int width, int height, BookObject returnObject, Predicate<BookScreen> showPredicate, BookPage page)
    {
        super(posX, posY, width, height, returnObject, showPredicate, page);
    }
    
    @Override
    public void draw(Minecraft minecraft, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        if (isHovering)
        {
            screen.renderTooltip(matrixStack, ClientHelper.simpleTranslatableComponent(page.translationKey), mouseX, mouseY);
        }
        minecraft.getTextureManager().bindTexture(screen.texture());
        blit(matrixStack, posX,posY, 1, 211, 26, 26, 512, 512);
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(page.iconStack, posX + 5, posY + 5);
        
    }
    
    @Override
    public void interact(BookScreen screen)
    {
        draw = 0;
        if (screen.currentObject.equals(this))
        {
            screen.currentObject = returnObject;
            return;
        }
        screen.currentObject = this;
    }
}