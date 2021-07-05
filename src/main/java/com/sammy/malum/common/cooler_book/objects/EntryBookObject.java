package com.sammy.malum.common.cooler_book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;

import static com.sammy.malum.common.cooler_book.CoolerBookScreen.*;

public class EntryBookObject extends CoolerBookObject
{
    public EntryBookObject(int posX, int posY)
    {
        super(posX, posY, 32, 32);
    }

    @Override
    public void render(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        renderTransparentTexture(FADE_TEXTURE, matrixStack, (int)(posX + xOffset), (int)(posY + yOffset), 1, 252, width, height, 512, 512);
        renderTexture(FRAME_TEXTURE, matrixStack, (int)(posX + xOffset), (int)(posY + yOffset), 1, 252, width, height, 512, 512);
    }
}
