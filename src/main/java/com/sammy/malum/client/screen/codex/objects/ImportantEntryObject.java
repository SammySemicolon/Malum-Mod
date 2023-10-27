package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;
import static com.sammy.malum.client.screen.codex.ArcanaProgressionScreen.*;

public class ImportantEntryObject extends EntryObject {

    public ImportantEntryObject(AbstractProgressionCodexScreen screen, BookEntry entry, int posX, int posY) {
        super(screen, entry, posX, posY);
    }

    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        renderTransparentTexture(FADE_TEXTURE, guiGraphics.pose(), posX-13, posY-13, 1, 252, 58, 58, 512, 512);
        renderTexture(FRAME_TEXTURE, guiGraphics.pose(), posX, posY, 34, getFrameTextureV(), width, height, 512, 512);
        renderTexture(FRAME_TEXTURE, guiGraphics.pose(), posX, posY, 133, getBackgroundTextureV(), width, height, 512, 512);
        guiGraphics.renderItem(entry.iconStack, posX + 8, posY + 8);
    }
}