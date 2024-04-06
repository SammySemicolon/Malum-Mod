package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class IconObject<T extends AbstractProgressionCodexScreen<T>> extends ProgressionEntryObject<T> {
    public final ResourceLocation textureLocation;
    public final int textureWidth;
    public final int textureHeight;

    public IconObject(BookEntry<T> entry, int posX, int posY, ResourceLocation textureLocation) {
        this(entry, posX, posY, textureLocation, 16, 16);
    }
    public IconObject(BookEntry<T> entry, int posX, int posY, ResourceLocation textureLocation, int textureWidth, int textureHeight) {
        super(entry, posX, posY);
        this.textureLocation = textureLocation;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public void render(T screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(screen, guiGraphics, mouseX, mouseY, partialTicks);
        final int x = getOffsetXPosition() + 8 - (style.textureWidth() - 32) / 4;
        final int y = getOffsetYPosition() + 8 - (style.textureHeight() - 32) / 4;
        renderWavyIcon(textureLocation, guiGraphics.pose(), x, y, 0, textureWidth, textureHeight);
    }
}