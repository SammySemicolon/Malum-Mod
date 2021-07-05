package com.sammy.malum.common.cooler_book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;

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

    public void render(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {

    }
}
