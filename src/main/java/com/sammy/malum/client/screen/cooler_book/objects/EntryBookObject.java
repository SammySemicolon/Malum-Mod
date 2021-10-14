package com.sammy.malum.client.screen.cooler_book.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.client.screen.cooler_book.EntryScreen;
import com.sammy.malum.client.screen.cooler_book.BookEntry;
import com.sammy.malum.client.screen.cooler_book.BookEntry.EntryLine.LineEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;

import static com.sammy.malum.client.screen.cooler_book.ProgressionBookScreen.*;

public class EntryBookObject extends BookObject
{
    public final BookEntry entry;
    public EntryBookObject(BookEntry entry, int posX, int posY)
    {
        super(posX, posY, 32, 32);
        this.entry = entry;
    }

    @Override
    public void click(float xOffset, float yOffset, double mouseX, double mouseY)
    {
        EntryScreen.openScreen(entry);
    }

    @Override
    public void render(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        renderTexture(FRAME_TEXTURE, matrixStack, posX, posY, 1, 252, width, height, 512, 512);
        minecraft.getItemRenderer().renderItemAndEffectIntoGUI(entry.iconStack, posX + 8, posY + 8);
        for (BookEntry.EntryLine arrow : entry.arrows)
        {
            int arrowPosX = posX + arrow.xOffset * 32;
            int arrowPosY = posY + arrow.yOffset * -32;
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
        if (isHovering)
        {
            screen.renderWrappedToolTip(matrixStack, Arrays.asList(new TranslationTextComponent(entry.translationKey()), new TranslationTextComponent(entry.descriptionTranslationKey()).mergeStyle(TextFormatting.GRAY)), mouseX, mouseY, minecraft.fontRenderer);
        }
    }
    public void renderArrow(MatrixStack matrixStack, int arrowPosX, int arrowPosY, int uOffset, int vOffset)
    {
        renderTexture(FRAME_TEXTURE, matrixStack, arrowPosX, arrowPosY, uOffset, vOffset, 32, 32, 512, 512);
    }
}
