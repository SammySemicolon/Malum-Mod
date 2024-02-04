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

public class IconObject<T extends AbstractProgressionCodexScreen> extends EntryObject<T> {
    public final ResourceLocation textureLocation;
    public final int textureWidth;
    public final int textureHeight;

    public IconObject(T screen, BookEntry<T> entry, int posX, int posY, ResourceLocation textureLocation) {
        this(screen, entry, posX, posY, textureLocation, 16, 16);
    }
    public IconObject(T screen, BookEntry<T> entry, int posX, int posY, ResourceLocation textureLocation, int textureWidth, int textureHeight) {
        super(screen, entry, posX, posY);
        this.textureLocation = textureLocation;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        super.render(minecraft, guiGraphics, xOffset, yOffset, mouseX, mouseY, partialTicks);
        renderWavyIcon(textureLocation, guiGraphics.pose(), offsetPosX(xOffset) + 8 - (style.textureWidth()-32)/4, offsetPosY(yOffset) + 8 - (style.textureHeight()-32)/4, 0, textureWidth, textureHeight);
    }
}