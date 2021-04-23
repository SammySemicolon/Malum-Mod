package com.sammy.malum.common.book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.entries.BookEntry;
import net.minecraft.client.Minecraft;

import static com.sammy.malum.common.book.BookScreen.screen;
import static net.minecraft.client.gui.AbstractGui.blit;

public class EntryObject extends BookObject
{
    public final BookEntry entry;
    public EntryObject(int posX, int posY, int width, int height, BookObject returnObject, BookEntry entry)
    {
        super(posX, posY, width, height, returnObject);
        this.entry = entry;
    }
    
    @Override
    public void interact(BookScreen screen)
    {
        screen.currentPage = 0;
        draw = 0;
        super.interact(screen);
    }
    
    @Override
    public void draw(Minecraft minecraft, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        matrixStack.push();
        float brightness = hover / 20f;
        minecraft.getTextureManager().bindTexture(screen.texture());
        blit(matrixStack, posX, posY, 183, 193, 128, 24, 512, 512);
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(entry.iconStack, posX + 4, posY + 4);
        matrixStack.translate(brightness*2,0,0);
        screen.renderPurpleText(matrixStack, ClientHelper.simpleTranslatableComponent(entry.translationKey), posX + 26, posY-4+ height/2, brightness);
        matrixStack.pop();
    }
}