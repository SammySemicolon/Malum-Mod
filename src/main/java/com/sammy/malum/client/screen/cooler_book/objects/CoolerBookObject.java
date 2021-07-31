package com.sammy.malum.client.screen.cooler_book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.client.screen.cooler_book.CoolerBookScreen;
import net.minecraft.client.Minecraft;

import static com.sammy.malum.client.screen.cooler_book.CoolerBookScreen.screen;

public class CoolerBookObject
{
    public boolean isHovering;
    public float hover;
    public int posX;
    public int posY;
    public int width;
    public int height;

    public CoolerBookObject(int posX, int posY, int width, int height)
    {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }
    public int hoverCap()
    {
        return 20;
    }

    public void render(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {

    }
    public void click(float xOffset, float yOffset, double mouseX, double mouseY)
    {

    }
    public boolean isHovering(float xOffset, float yOffset, double mouseX, double mouseY)
    {
        return CoolerBookScreen.isHovering(mouseX,mouseY, offsetPosX(xOffset), offsetPosY(yOffset), width, height);
    }
    public int offsetPosX(float xOffset)
    {
        int guiLeft = (width - screen.bookWidth) / 2;
        return (int) (guiLeft+ this.posX + xOffset);
    }
    public int offsetPosY(float yOffset)
    {
        int guiTop = (height - screen.bookHeight) / 2;
        return (int) (guiTop + this.posY + yOffset);
    }
}
