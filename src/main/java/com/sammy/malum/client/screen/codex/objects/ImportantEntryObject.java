package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.BookEntry;
import net.minecraft.client.Minecraft;

import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.*;

public class ImportantEntryObject extends EntryObject {

    public ImportantEntryObject(BookEntry entry, int posX, int posY) {
        super(entry, posX, posY);
    }

    @Override
    public void render(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        renderTransparentTexture(FADE_TEXTURE, poseStack, posX-13, posY-13, 1, 252, 58, 58, 512, 512);
        renderTexture(FRAME_TEXTURE, poseStack, posX, posY, 34, 252, width, height, 512, 512);
        minecraft.getItemRenderer().renderAndDecorateItem(entry.iconStack, posX + 8, posY + 8);
    }
}