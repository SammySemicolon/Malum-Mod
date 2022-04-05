package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.EntryScreen;
import com.sammy.malum.client.screen.codex.BookEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
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
    public void render(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        renderTransparentTexture(FADE_TEXTURE, poseStack, posX-16, posY-16, 0, 252, 64, 64, 512, 512);
        renderTexture(FRAME_TEXTURE, poseStack, posX, posY, 1, 252, width, height, 512, 512);
        minecraft.getItemRenderer().renderAndDecorateItem(entry.iconStack, posX + 8, posY + 8);
    }

    @Override
    public void lateRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (isHovering)
        {
            screen.renderComponentTooltip(poseStack, Arrays.asList(new TranslatableComponent(entry.translationKey()), new TranslatableComponent(entry.descriptionTranslationKey()).withStyle(ChatFormatting.GRAY)), mouseX, mouseY, minecraft.font);
        }
    }
}
