package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.client.screen.codex.BookEntry;
import net.minecraft.client.Minecraft;

import static com.sammy.malum.client.screen.codex.ProgressionBookScreen.*;

public class ImportantEntryObject extends EntryObject {

    public ImportantEntryObject(BookEntry entry, int posX, int posY) {
        super(entry, posX, posY);
    }

    @Override
    public void render(Minecraft minecraft, MatrixStack matrixStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        renderTexture(FRAME_TEXTURE, matrixStack, posX, posY, 34, 252, width, height, 512, 512);
        minecraft.getItemRenderer().renderAndDecorateItem(entry.iconStack, posX + 8, posY + 8);
    }
}