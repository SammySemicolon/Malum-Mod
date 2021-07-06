package com.sammy.malum.common.cooler_book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.cooler_book.CoolerBookEntry;
import net.minecraft.client.Minecraft;

import static com.sammy.malum.common.cooler_book.CoolerBookScreen.*;

public class EntryBookObject extends CoolerBookObject
{
    public final CoolerBookEntry entry;
    public EntryBookObject(CoolerBookEntry entry, int posX, int posY)
    {
        super(posX, posY, 32, 32);
        this.entry = entry;
    }

    @Override
    public void render(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = (width - screen.bookWidth) / 2;
        int guiTop = (height - screen.bookHeight) / 2;
        int posX = (int) (guiLeft+ this.posX + xOffset);
        int posY = (int) (guiTop + this.posY + yOffset);
        renderTexture(FRAME_TEXTURE, matrixStack, posX, posY, 1, 252, width, height, 512, 512);
        minecraft.getItemRenderer().renderItemAndEffectIntoGUI(entry.iconStack, posX + 8, posY + 8);
        for (CoolerBookEntry.Arrow arrow : entry.arrows)
        {
            switch (arrow)
            {
                case UP:
                {
                    renderTexture(FRAME_TEXTURE, matrixStack, posX, posY-32, 33, 285, 32, 32, 512, 512);
                    break;
                }
                case DOWN:
                {
                    renderTexture(FRAME_TEXTURE, matrixStack, posX, posY+32, 1, 285, 32, 32, 512, 512);
                    break;
                }
                case LEFT:
                {
                    renderTexture(FRAME_TEXTURE, matrixStack, posX-32, posY, 1, 317, 32, 32, 512, 512);
                    break;
                }
                case RIGHT:
                {
                    renderTexture(FRAME_TEXTURE, matrixStack, posX+32, posY, 33, 317, 32, 32, 512, 512);
                    break;
                }
                case UP_LEFT:
                {
                    renderTexture(FRAME_TEXTURE, matrixStack, posX-32, posY-64, 66, 285, 64, 64, 512, 512);
                    break;
                }
                case UP_RIGHT:
                {
                    renderTexture(FRAME_TEXTURE, matrixStack, posX, posY-64, 131, 285, 64, 64, 512, 512);
                    break;
                }
                case DOWN_LEFT:
                {
                    renderTexture(FRAME_TEXTURE, matrixStack, posX-32, posY+32, 196, 350, 64, 64, 512, 512);
                    break;
                }
                case DOWN_RIGHT:
                {
                    renderTexture(FRAME_TEXTURE, matrixStack, posX, posY+32, 261, 350, 64, 64, 512, 512);
                    break;
                }
                case LEFT_UP:
                {
                    renderTexture(FRAME_TEXTURE, matrixStack, posX-64, posY-32, 66, 350, 64, 64, 512, 512);
                    break;
                }
                case LEFT_DOWN:
                {
                    renderTexture(FRAME_TEXTURE, matrixStack, posX-64, posY, 196, 285, 64, 64, 512, 512);
                    break;
                }
                case RIGHT_UP:
                {
                    renderTexture(FRAME_TEXTURE, matrixStack, posX+32, posY-32, 131, 350, 64, 64, 512, 512);
                    break;
                }
                case RIGHT_DOWN:
                {
                    renderTexture(FRAME_TEXTURE, matrixStack, posX+32, posY, 261, 285, 64, 64, 512, 512);
                    break;
                }
            }
        }
    }
}
