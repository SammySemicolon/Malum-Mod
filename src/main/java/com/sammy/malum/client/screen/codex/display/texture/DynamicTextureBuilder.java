package com.sammy.malum.client.screen.codex.display.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.*;
import com.sammy.malum.client.screen.codex.display.texture.request.DynamicTextureRequest;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"unused"})
public class DynamicTextureBuilder {

    protected final DynamicTextureRequest request;
    protected int width = 16, height = 16;
    protected float hScale = 1, vScale = 1;

    protected boolean isTicking = false;

    public static DynamicTextureBuilder create(DynamicTextureRequest request) {
        return new DynamicTextureBuilder(request);
    }

    private DynamicTextureBuilder(DynamicTextureRequest request) {
        this.request = request;
    }

    public DynamicTextureBuilder setTextureSize(int size) {
        return setTextureSize(size, size);
    }

    public DynamicTextureBuilder setTextureSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public DynamicTextureBuilder setScale(float scale) {
        this.hScale = scale;
        this.vScale = scale;
        return this;
    }

    public DynamicTextureBuilder setTicking(boolean isTicking) {
        this.isTicking = isTicking;
        return this;
    }

    public RenderedDynamicTexture bakeTexture() {
        request.modify(this);
        var texture = DynamicTextureCache.pushTexture(request, this::createTexture);
        if (texture != null && isTicking) {
            texture.redraw();
        }

        return texture;
    }

    private CompletableFuture<RenderedDynamicTexture> createTexture(ResourceLocation key) {
        var nested = new CompletableFuture<RenderedDynamicTexture>();
        RenderSystem.recordRenderCall(() -> {
            try {
                var texture = new RenderedDynamicTexture(request, width, height, hScale, vScale);
                nested.complete(texture);
            } catch (Throwable t) {
                MalumMod.LOGGER.error("Failed to create dynamic texture for id {}", request.getWriteLocation(), t);
                nested.completeExceptionally(t);
            }
        });
        return nested;
    }
}