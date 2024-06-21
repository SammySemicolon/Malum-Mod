package com.sammy.malum.client.screen.codex.objects.progression;

import com.sammy.malum.client.screen.codex.BookEntry;
import com.sammy.malum.client.screen.codex.screens.AbstractProgressionCodexScreen;
import net.minecraft.resources.ResourceLocation;

public class ScreenOpenerObject extends IconObject {
    private final Runnable screenOpener;

    public ScreenOpenerObject(BookEntry entry, int posX, int posY, Runnable screenOpener, ResourceLocation textureLocation) {
        super(entry, posX, posY, textureLocation);
        this.screenOpener = screenOpener;
    }

    public ScreenOpenerObject(BookEntry entry, int posX, int posY, Runnable screenOpener, ResourceLocation textureLocation, int textureWidth, int textureHeight) {
        super(entry, posX, posY, textureLocation, textureWidth, textureHeight);
        this.screenOpener = screenOpener;
    }

    @Override
    public void click(AbstractProgressionCodexScreen screen, double mouseX, double mouseY) {
        if (!entry.isFragment) {
            screenOpener.run();
        }
    }
}
