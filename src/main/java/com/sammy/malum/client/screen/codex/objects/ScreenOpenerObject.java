package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.*;
import net.minecraft.client.*;
import net.minecraft.resources.*;

public class ScreenOpenerObject<T extends AbstractProgressionCodexScreen> extends IconObject<T> {
    private final Runnable screenOpener;
    public ScreenOpenerObject(T screen, BookEntry<T> entry, int posX, int posY, Runnable screenOpener, ResourceLocation textureLocation) {
        super(screen, entry, posX, posY, textureLocation);
        this.screenOpener = screenOpener;
    }

    public ScreenOpenerObject(T screen, BookEntry<T> entry, int posX, int posY, Runnable screenOpener, ResourceLocation textureLocation, int textureWidth, int textureHeight) {
        super(screen, entry, posX, posY, textureLocation, textureWidth, textureHeight);
        this.screenOpener = screenOpener;
    }

    @Override
    public void click(float xOffset, float yOffset, double mouseX, double mouseY) {
        screenOpener.run();
        if (Minecraft.getInstance().screen instanceof AbstractProgressionCodexScreen screen) {
            screen.transitionTimer = 80;
        }
    }
}
