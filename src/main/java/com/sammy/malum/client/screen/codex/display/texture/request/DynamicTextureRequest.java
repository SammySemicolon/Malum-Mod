package com.sammy.malum.client.screen.codex.display.texture.request;

import com.sammy.malum.client.screen.codex.display.texture.RenderedDynamicTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public abstract class DynamicTextureRequest {

    private final ResourceLocation writeLocation;

    protected DynamicTextureRequest(ResourceLocation writeLocation) {
        this.writeLocation = writeLocation.withPath(p -> p.endsWith(".png") ? p : p + ".png").withSuffix("generated/");
    }

    public ResourceLocation getWriteLocation() {
        return writeLocation;
    }

    public void unregister() {
        var textureManager = Minecraft.getInstance().getTextureManager();
        textureManager.release(getWriteLocation());
    }

    public abstract void drawTexture(RenderedDynamicTexture texture, GuiGraphics guiGraphics);

}
