package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.*;
import com.sammy.malum.client.screen.codex.screens.*;
import net.minecraft.resources.*;

public class ScreenOpenerObject<T extends AbstractProgressionCodexScreen<T>> extends IconObject<T> {
    private final Runnable screenOpener;
    public ScreenOpenerObject(BookEntry<T> entry, int posX, int posY, Runnable screenOpener, ResourceLocation textureLocation) {
        super(entry, posX, posY, textureLocation);
        this.screenOpener = screenOpener;
    }

    public ScreenOpenerObject(BookEntry<T> entry, int posX, int posY, Runnable screenOpener, ResourceLocation textureLocation, int textureWidth, int textureHeight) {
        super(entry, posX, posY, textureLocation, textureWidth, textureHeight);
        this.screenOpener = screenOpener;
    }

    @Override
    public void click(T screen, double mouseX, double mouseY) {
        screenOpener.run();
    }
}
