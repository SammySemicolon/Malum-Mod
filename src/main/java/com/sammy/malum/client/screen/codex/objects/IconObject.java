package com.sammy.malum.client.screen.codex.objects;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.screen.codex.AbstractProgressionCodexScreen;
import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.EntryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;
import static com.sammy.malum.client.screen.codex.ArcanaProgressionScreen.FRAME_FADE_TEXTURE;
import static com.sammy.malum.client.screen.codex.ArcanaProgressionScreen.FRAME_TEXTURE;

public class IconObject extends EntryObject {
    public final ResourceLocation textureLocation;

    public IconObject(AbstractProgressionCodexScreen screen, BookEntry entry, ResourceLocation textureLocation, int posX, int posY) {
        super(screen, entry, posX, posY);
        this.textureLocation = textureLocation;
    }

    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        super.render(minecraft, guiGraphics, xOffset, yOffset, mouseX, mouseY, partialTicks);
        renderWavyIcon(textureLocation, guiGraphics.pose(), offsetPosX(xOffset) + 8, offsetPosY(yOffset) + 8);
    }
}