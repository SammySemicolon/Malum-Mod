package com.sammy.malum.core.systems.spirit;

import net.minecraft.resources.*;

public class SpiritTextureData {

    protected final ResourceLocation id;

    protected ResourceLocation symbolTexture;

    public SpiritTextureData(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getId() {
        return id;
    }

    public ResourceLocation getSymbolTexture() {
        if (symbolTexture == null) {
            symbolTexture = id
                    .withPath(p -> "textures/gui/book/spirit/" + p)
                    .withSuffix(".png");
        }
        return symbolTexture;
    }
}
