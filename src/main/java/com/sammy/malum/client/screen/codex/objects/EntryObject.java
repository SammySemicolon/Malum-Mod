package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.client.screen.codex.EntryScreen;
import com.sammy.malum.client.screen.codex.BookEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Arrays;

import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.*;

public class EntryObject extends BookObject
{
    public final BookEntry entry;
    public EntryObject(BookEntry entry, int posX, int posY)
    {
        super(posX, posY, 32, 32);
        this.entry = entry;
    }

    @Override
    public void click(float xOffset, float yOffset, double mouseX, double mouseY)
    {
        EntryScreen.openScreen(this);
    }

    @Override
    public void render(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        renderTexture(FRAME_TEXTURE, matrixStack, posX, posY, 1, 252, width, height, 512, 512);
        minecraft.getItemRenderer().renderAndDecorateItem(entry.iconStack, posX + 8, posY + 8);
    }

    @Override
    public void lateRender(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (isHovering)
        {
            screen.renderWrappedToolTip(matrixStack, Arrays.asList(new TranslatableComponent(entry.translationKey()), new TranslatableComponent(entry.descriptionTranslationKey()).withStyle(TextFormatting.GRAY)), mouseX, mouseY, minecraft.font);
        }
    }
}
