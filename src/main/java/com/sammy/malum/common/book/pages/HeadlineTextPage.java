package com.sammy.malum.common.book.pages;

import com.ibm.icu.impl.Pair;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.ClientHelper;
import com.sammy.malum.common.book.BookScreen;
import com.sammy.malum.common.book.objects.EntryObject;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

import static com.sammy.malum.common.book.BookScreen.drawWrappingText;
import static com.sammy.malum.common.book.BookScreen.screen;
import static net.minecraft.client.gui.AbstractGui.blit;

public class HeadlineTextPage extends BookPage
{
    public final String text;
    public final String headline;
    public float hover;
    public HeadlineTextPage(String text)
    {
        this.text = "malum.gui.book.page." + text;
        this.headline = "malum.gui.book.page.headline." + text;
    }
    public int textWidth()
    {
        return 110;
    }
    @Override
    public void draw(MatrixStack stack, EntryObject object, BookScreen screen, int mouseX, int mouseY, int guiLeft, int guiTop, boolean isSecondPage)
    {
        super.draw(stack, object, screen, mouseX, mouseY, guiLeft, guiTop, isSecondPage);
        Pair<Integer, Integer> position = getPosition(guiLeft, guiTop, isSecondPage);
        
        int width = 101;
        int height = 16;
        int posX = position.first+6;
        int posY = position.second + 7;
        if (screen.isHovering(mouseX, mouseY, posX,posY,width,height))
        {
            if (hover < 20)
            {
                hover++;
            }
        }
        else if (hover > 0)
        {
            hover--;
        }
        float brightness = hover / 20f;
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(screen.texture());
        blit(stack, posX,posY, 104, 193, 101, 16, 512, 512);
        ITextComponent component = ClientHelper.simpleTranslatableComponent(headline);
        screen.renderPurpleText(stack, component, posX + width/2 - minecraft.fontRenderer.getStringWidth(component.getString())/2, posY-8+ height/2, brightness);
    
        drawWrappingText(stack, ClientHelper.simpleTranslatableComponent(text), position.first+2, posY + 17, textWidth(), brightness);
        
    }
}