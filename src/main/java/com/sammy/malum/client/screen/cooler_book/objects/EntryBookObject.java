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
        if (isHovering)
        {
            screen.renderWrappedToolTip(matrixStack, Arrays.asList(new TranslationTextComponent(entry.translationKey()), new TranslationTextComponent(entry.descriptionTranslationKey()).mergeStyle(TextFormatting.GRAY)), mouseX, mouseY, minecraft.fontRenderer);
        }
    }
}
