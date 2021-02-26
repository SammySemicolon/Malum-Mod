package com.sammy.malum.common.book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.common.book.BookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

import java.util.function.Predicate;

import static com.sammy.malum.common.book.BookScreen.screen;
import static net.minecraft.client.gui.AbstractGui.blit;

public class NameObject extends BookObject
{
    public String text;
    public NameObject(String text, int posX, int posY, int width, int height)
    {
        super(posX, posY, width, height);
        this.text = text;
    }
    
    @Override
    public void interact(BookScreen screen)
    {
    
    }
    
    @Override
    public void draw(Minecraft minecraft, MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        float brightness = hover / 20f;
        minecraft.getTextureManager().bindTexture(screen.texture());
        blit(matrixStack, posX,posY, 104, 193, 101, 16, 512, 512);
        ITextComponent component = ClientHelper.simpleTranslatableComponent(text);
        screen.renderPurpleText(matrixStack, component, posX + width/2 - minecraft.fontRenderer.getStringWidth(component.getString())/2, posY-8+ height/2, brightness);
    
        super.draw(minecraft, matrixStack, mouseX, mouseY, partialTicks);
    }
}
