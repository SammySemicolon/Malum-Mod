package com.sammy.malum.client.screen.codex.objects.progression;

import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.screens.AbstractProgressionCodexScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.renderWavyIcon;

public class IconObject extends ProgressionEntryObject {
    public final ResourceLocation textureLocation;
    public final int textureWidth;
    public final int textureHeight;

    public IconObject(BookEntry entry, int posX, int posY, ResourceLocation textureLocation) {
        this(entry, posX, posY, textureLocation, 16, 16);
    }

    public IconObject(BookEntry entry, int posX, int posY, ResourceLocation textureLocation, int textureWidth, int textureHeight) {
        super(entry, posX, posY);
        this.textureLocation = textureLocation;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public void render(AbstractProgressionCodexScreen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(screen, guiGraphics, mouseX, mouseY, partialTicks);
        final int x = getOffsetXPosition() + 8 - (style.textureWidth() - 32) / 4;
        final int y = getOffsetYPosition() + 8 - (style.textureHeight() - 32) / 4;
        renderWavyIcon(textureLocation, guiGraphics.pose(), x, y, 0, textureWidth, textureHeight);
    }

}