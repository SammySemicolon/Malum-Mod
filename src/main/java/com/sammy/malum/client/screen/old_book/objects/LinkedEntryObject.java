package com.sammy.malum.client.screen.old_book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.client.screen.old_book.entries.BookEntry;
import net.minecraft.client.Minecraft;

import static com.sammy.malum.client.screen.old_book.BookScreen.screen;
import static net.minecraft.client.gui.AbstractGui.blit;

public class LinkedEntryObject extends EntryObject
{
    public LinkedEntryObject(int posX, int posY, int width, int height, BookObject returnObject, BookEntry page)
    {
        super(posX, posY, width, height, returnObject, page);
    }
    
    @Override
    public void draw(Minecraft minecraft, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        if (isHovering)
        {
            screen.renderTooltip(matrixStack, ClientHelper.simpleTranslatableComponent(entry.translationKey), mouseX, mouseY);
        }
        minecraft.getTextureManager().bindTexture(screen.texture());
        blit(matrixStack, posX,posY, 27, 193, 24, 24, 512, 512);
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(entry.iconStack, posX + 4, posY + 4);
    
    }
}