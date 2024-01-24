package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.*;
import net.minecraft.resources.*;

public class ScreenOpenerObject extends IconObject{
    private final Runnable screenOpener;
    public ScreenOpenerObject(AbstractProgressionCodexScreen screen, BookEntry entry, int posX, int posY, Runnable screenOpener, ResourceLocation textureLocation) {
        super(screen, entry, posX, posY, textureLocation);
        this.screenOpener = screenOpener;
    }

    public ScreenOpenerObject(AbstractProgressionCodexScreen screen, BookEntry entry, int posX, int posY, Runnable screenOpener, ResourceLocation textureLocation, int textureWidth, int textureHeight) {
        super(screen, entry, posX, posY, textureLocation, textureWidth, textureHeight);
        this.screenOpener = screenOpener;
    }

    @Override
    public void click(float xOffset, float yOffset, double mouseX, double mouseY) {
        screenOpener.run();
    }
}
