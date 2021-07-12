package com.sammy.malum.common.cooler_book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.common.cooler_book.CoolerBookEntry;
import com.sammy.malum.common.cooler_book.CoolerBookEntry.EntryLine.LineEnum;
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

        matrixStack.push();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        for (CoolerBookEntry.EntryLine arrow : entry.arrows)
        {
            int arrowPosX = posX + arrow.xOffset*32;
            int arrowPosY = posY + arrow.yOffset*-32;
            for (LineEnum arrowEnum : arrow.lines)
            {
                switch (arrowEnum)
                {
                    case VERTICAL:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 1, 285);
                        break;
                    }
                    case HORIZONTAL:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 34, 285);
                        break;
                    }

                    case BEND_UP_LEFT:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 34, 351);
                        break;
                    }
                    case BEND_UP_RIGHT:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 1, 351);
                        break;
                    }
                    case BEND_DOWN_LEFT:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 34, 318);
                        break;
                    }
                    case BEND_DOWN_RIGHT:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 1, 318);
                        break;
                    }

                    case TWO_WAY_BEND_UP:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 67, 351);
                        break;
                    }
                    case TWO_WAY_BEND_DOWN:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 100, 318);
                        break;
                    }
                    case TWO_WAY_BEND_LEFT:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 100, 351);
                        break;
                    }
                    case TWO_WAY_BEND_RIGHT:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 67, 318);
                        break;
                    }

                    case CONNECTION_UP:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 34, 384);
                        break;
                    }
                    case CONNECTION_DOWN:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 1, 417);
                        break;
                    }
                    case CONNECTION_LEFT:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 1, 384);
                        break;
                    }
                    case CONNECTION_RIGHT:
                    {
                        renderArrow(matrixStack, arrowPosX, arrowPosY, 34, 417);
                        break;
                    }
                }
            }
        }
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        matrixStack.pop();
    }
    public void renderArrow(MatrixStack matrixStack, int arrowPosX, int arrowPosY, int uOffset, int vOffset)
    {
        renderTexture(FRAME_TEXTURE, matrixStack, arrowPosX, arrowPosY, uOffset, vOffset, 32, 32, 512, 512);
    }
}
