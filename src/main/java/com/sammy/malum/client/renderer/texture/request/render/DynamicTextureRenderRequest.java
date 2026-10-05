package com.sammy.malum.client.renderer.texture.request.render;

import com.sammy.malum.client.renderer.texture.DynamicTextureRenderer;
import com.sammy.malum.client.renderer.texture.LodestoneDynamicTexture;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public abstract class DynamicTextureRenderRequest {

    private final ResourceLocation writeLocation;

    protected DynamicTextureRenderRequest(ResourceLocation writeLocation) {
        this.writeLocation = writeLocation.withPath(p -> p.endsWith(".png") ? p : p + ".png").withSuffix("generated/");
    }

    public ResourceLocation getWriteLocation() {
        return writeLocation;
    }

    public abstract void drawTexture(LodestoneDynamicTexture texture, GuiGraphics guiGraphics);

    public void modify(DynamicTextureRenderer builder) {

    }
}
